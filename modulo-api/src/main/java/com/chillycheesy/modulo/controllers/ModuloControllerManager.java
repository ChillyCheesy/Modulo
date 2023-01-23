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
import java.util.*;
import java.util.stream.Collectors;

/**
 * The controller manager.
 * It manages the controllers and the controller chain.
 *
 * @author ChillyCheesy
 */
public class ModuloControllerManager extends Manager<ModuloController> {

    /**
     * The controller chain.
     *
     * @param factory The controller factory.
     * @param module The module.
     * @param controllers The controllers.
     *
     * @return True if the controller was built.
     */
    public boolean buildAndRegisterController(ModuloControllerFactory factory, Module module, Object ...controllers) {
        return Arrays.stream(controllers).allMatch(controller -> {
            try {
                final int initialPriority = super.getAllItems().size();
                final ModuloController[] builtControllers = factory.createFromObject(module, controller, initialPriority);
                return super.registerItem(module, builtControllers);
            } catch (Exception exception) {
                module.error("Failed to register controller: " + controller.getClass().getName());
                exception.printStackTrace();
                return false;
            }
        });
    }

    /**
     * Apply the controller chain.
     * @param module The module.
     * @param controllers The controllers.
     * @return True if the controller chain was applied.
     */
    public boolean buildAndRegisterController(Module module, Object ...controllers) {
        final ModuloControllerFactory factory = new ModuloControllerFactory();
        return buildAndRegisterController(factory, module, controllers);
    }

    /**
     * Apply the controller chain.
     * @param request The http request.
     * @param response The http response.
     * @throws Exception If an error occurs.
     */
    public void apply(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final EventManager eventManager = ModuloAPI.getEvent().getEventManager();
        final List<ModuloController> controllers = super.getAllItems().stream()
            .sorted(Comparator.comparingInt(ModuloController::getPriority)).collect(Collectors.toList());
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

    /**
     * Write the controller result.
     * @param response The http response.
     * @param result The controller result.
     * @param configuration The configuration.
     * @throws IOException If an error occurs.
     */
    private void writeControllerResult(HttpServletResponse response, Object result, Configuration configuration) throws IOException {
        try (
            final OutputStream outputStream = response.getOutputStream();
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            final Writer writer = new OutputStreamWriter(bufferedOutputStream);
        ) {
            final boolean skipRegister = configuration.getBoolean("skip-register-result-in-response");
            if (!skipRegister) {
                response.setStatus(HttpServletResponse.SC_OK);
                final boolean decodeBase64 = configuration.getBoolean("base64");
                final String resultString = result.toString();
                if (decodeBase64) {
                    final byte[] decodedBytes = Base64.getDecoder().decode(resultString);
                    bufferedOutputStream.write(decodedBytes);
                } else writer.write(resultString);
            }
        }
    }

}
