package com.chillycheesy.modulo.controllers.factory;

import com.chillycheesy.modulo.controllers.Controller;
import com.chillycheesy.modulo.controllers.ControllerBuilder;
import com.chillycheesy.modulo.controllers.MethodController;
import com.chillycheesy.modulo.controllers.ModuloController;
import com.chillycheesy.modulo.modules.Module;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class ModuloControllerFactory {

    private final List<ControllerAnnotationBinder> binders;

    public ModuloControllerFactory(List<ControllerAnnotationBinder> binders) {
        this.binders = binders;
    }

    public ModuloControllerFactory() {
        this(List.of(
            new RequestControllerAnnotationBinder(),
            new JsonResponseControllerAnnotationBinder(),
            new ServeResourceControllerAnnotationBinder())
        );
    }

    public ModuloController[] createFromObject(Module module, Object object) throws Exception {
        return createFromObject(module, object, 0);
    }

    public ModuloController[] createFromObject(Module module, Object object, int initialPriority) throws Exception {
        final Class<?> clazz = object.getClass();
        final Method[] methods = clazz.getDeclaredMethods();
        final ModuloController[] controllers = new ModuloController[methods.length];
        for (int i = 0; i < methods.length; i++) {
            final Method method = methods[i];
            final String name = method.getName();
            final Annotation[] annotations = method.getDeclaredAnnotations();
            final ModuloController rootController = new ModuloController(name, initialPriority + i);
            final ControllerBuilder builder = new ControllerBuilder();
            for (Annotation annotation : annotations)
                for (ControllerAnnotationBinder binder : binders)
                    if (binder.match(annotation))
                        binder.bindControllerAnnotations(annotation, module, builder);
            final MethodController methodController = new MethodController(object, method);
            final Controller controller = builder.add(methodController).build();
            rootController.setNextStep(controller);
            controllers[i] = rootController;
        }
        return controllers;
    }



}
