package com.chillycheesy.modulo.controllers.factory;

import com.chillycheesy.modulo.controllers.ControllerBuilder;
import com.chillycheesy.modulo.controllers.InternalFileResourceController;
import com.chillycheesy.modulo.modules.Module;

import java.lang.annotation.Annotation;
import java.util.jar.JarFile;

public class ServeResourceControllerAnnotationBinder implements ControllerAnnotationBinder {

    @Override
    public void bindControllerAnnotations(Annotation annotation, Module module, ControllerBuilder builder) throws Exception {
        final JarFile jarFile = module.getJarFile();
        final InternalFileResourceController controller = new InternalFileResourceController(jarFile);
        builder.add(controller);
    }

    public boolean match(Annotation annotation) {
        return annotation instanceof ServeResource;
    }

}
