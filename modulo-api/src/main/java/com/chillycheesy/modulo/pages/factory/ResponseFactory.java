package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageManager;
import com.chillycheesy.modulo.pages.response.ResponseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ResponseFactory implements ResponseHandler {

    public static ResponseHandler create(Object object, Method method, ParameterAnnotationApplier parameterAnnotationApplier, ResponseAnnotationApplier responseAnnotationApplier) {
        return new ResponseFactory(object, method, parameterAnnotationApplier, responseAnnotationApplier);
    }

    private final Object object;
    private final Method method;
    private final ParameterAnnotationApplier parameterAnnotationApplier;
    private final ResponseAnnotationApplier responseAnnotationApplier;

    private ResponseFactory(Object object, Method method, ParameterAnnotationApplier parameterAnnotationApplier, ResponseAnnotationApplier responseAnnotationApplier) {
        this.object = object;
        this.method = method;
        this.parameterAnnotationApplier = parameterAnnotationApplier;
        this.responseAnnotationApplier = responseAnnotationApplier;
    }

    private Object[] createParameters(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Object[] builtParameter = new Object[method.getParameterCount()];
        final Parameter[] parameters = method.getParameters();
        final Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < method.getParameterCount(); ++i) {
            builtParameter[i] = new ParameterBuilder()
                .setParameter(parameters[i])
                .setAnnotations(annotations[i])
                .setParameterAnnotationApplier(parameterAnnotationApplier)
                .build(page, request, response);
        }
        return builtParameter;
    }

    @Override
    public boolean response(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Object[] parameters = createParameters(page, request, response);
        try {
            final Object result = method.invoke(object, parameters);
            return adaptResponse(page, result, request, response);
        } catch (InvocationTargetException | IllegalAccessException e) {
            final PageManager pageManager = ModuloAPI.getPage().getPageManager();
            final Module module = pageManager.getModuleByItem(page);
            if (module != null)
                ModuloAPI.getLogger().error(module, String.format("Error while invoking method %s in class %s.",  method.getName(), object.getClass().getName()));
            e.printStackTrace();
            return false;
        }
    }

    private boolean adaptResponse(Page page, Object result, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof ResponseBody)
                return responseAnnotationApplier.applyResponseBodyAnnotation((ResponseBody) annotation, response, page, result);
            else if (annotation instanceof ResponseResource)
                return responseAnnotationApplier.applyResponseResourceAnnotation((ResponseResource) annotation, page, request, response, result);
        }
        return false;
    }

}
