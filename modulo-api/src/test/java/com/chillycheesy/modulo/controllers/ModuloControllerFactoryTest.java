package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.controllers.factory.JsonResponse;
import com.chillycheesy.modulo.controllers.factory.ModuloControllerFactory;
import com.chillycheesy.modulo.controllers.factory.Request;
import com.chillycheesy.modulo.modules.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModuloControllerFactoryTest {

    private static class EndorController {
        @Request(path = "/")
        public String hello() {
            return "Hello";
        }
    }

    private Module module;

    @BeforeEach
    public void init() {
        this.module = mock(Module.class);
    }

    @Test
    public void shouldReturnHello() throws Exception {
        final ModuloControllerFactory factory = new ModuloControllerFactory();
        final ModuloController[] controllers = factory.createFromObject(module, new EndorController());
        final ModuloController controller = controllers[0];

        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/");

        final Object response = controller.apply(request, null, null);
        assertEquals("Hello", response);
    }


}
