package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.config.Configuration;
import com.chillycheesy.modulo.controllers.factory.ModuloControllerFactory;
import com.chillycheesy.modulo.event.controllers.ControllerAppliedEvent;
import com.chillycheesy.modulo.event.controllers.ControllerCanceledEvent;
import com.chillycheesy.modulo.events.EventManager;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ModuloControllerManager extends Manager<ModuloController> {

    public boolean buildAndRegisterController(ModuloControllerFactory factory, Module module, Object ...controllers) {
        return Arrays.stream(controllers).allMatch(controller -> {
            try {
                final ModuloController[] builtControllers = factory.createFromObject(module, controller);
                return super.registerItem(module, builtControllers);
            } catch (Exception exception) {
                module.error("Failed to register controller: " + controller.getClass().getName());
                exception.printStackTrace();
                return false;
            }
        });
    }

    public boolean buildAndRegisterController(Module module, Object ...controllers) {
        final ModuloControllerFactory factory = new ModuloControllerFactory();
        return buildAndRegisterController(factory, module, controllers);
    }

    public void apply(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final EventManager eventManager = ModuloAPI.getEvent().getEventManager();
        final List<ModuloController> controllers = super.getAllItems().stream()
            .sorted(Comparator.comparingInt(ModuloController::getPriority)).toList();
        for (ModuloController controller : controllers) {
            final Module module = super.getModuleByItem(controller);
            final ControllerAppliedEvent event = new ControllerAppliedEvent(controller);
            eventManager.emitEvent(module, event);
            if (!event.isCanceled()) {
                final Configuration configuration = new Configuration();
                final Object result = controller.apply(request, response, configuration);
                if (Objects.nonNull(result)) writeControllerResult(response, result, configuration);
                else eventManager.emitEvent(module, new ControllerCanceledEvent(controller));
            }
        }
    }

    private void writeControllerResult(HttpServletResponse response, Object result, Configuration configuration) throws IOException {
        try (
            final PrintWriter writer = response.getWriter();
            final BufferedWriter bufferedWriter = new BufferedWriter(writer)
        ) {
            final boolean skipRegister = configuration.getBoolean("skip-register-result-in-response");
            if (!skipRegister) {
                response.setStatus(HttpServletResponse.SC_OK);
                bufferedWriter.write(result.toString());
            }
        }
    }

}
