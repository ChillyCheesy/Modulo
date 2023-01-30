package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;
import com.chillycheesy.modulo.controllers.methodcontroller.HttpParam;
import com.chillycheesy.modulo.controllers.methodcontroller.PathVariable;
import com.chillycheesy.modulo.controllers.methodcontroller.RequestBody;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.util.stream.Stream;

import static com.chillycheesy.modulo.controllers.HttpParamVariableController.PARAM_VARIABLE_SECTION;
import static com.chillycheesy.modulo.controllers.HttpPathVariableController.PATH_VARIABLE_SECTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodControllerParameterAnnotationTest {

    private static class EndorController {

        public String returnBodyAsString(String body) {
            return body;
        }

        public String returnBodyAsStringWithRequestBodyAnnotation(@RequestBody String body) {
            return body;
        }

        public String returnParamVariableAndPathVariableAsString(@PathVariable String path, @HttpParam String where) {
            return String.format("%s, %s", path, where);
        }

        public String returnParamVariableAndPathVariableAsStringWithCustomName(@PathVariable("hello") String path, @HttpParam("place") String where) {
            return String.format("%s, %s", path, where);
        }

        public String returnEwokName(@RequestBody Ewok ewok) {
            return ewok.getName();
        }
    }

    private static class Ewok {
        private String name;

        public Ewok() { }

        public Ewok(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void shouldReturnBodyAsString() throws Exception {
        final EndorController endorController = new EndorController();
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new MethodController(endorController, EndorController.class.getMethod("returnBodyAsString", String.class)));
        final Controller controller = builder.build();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.lines()).thenReturn(Stream.of("Endor is good place to live"));
        when(request.getReader()).thenReturn(bufferedReader);

        final Object result = controller.apply(request, null, null);
        assertEquals("Endor is good place to live", result);
    }

    @Test
    public void shouldReturnBodyAsStringWithRequestBodyAnnotation() throws Exception {
        final EndorController endorController = new EndorController();
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new MethodController(endorController, EndorController.class.getMethod("returnBodyAsStringWithRequestBodyAnnotation", String.class)));
        final Controller controller = builder.build();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.lines()).thenReturn(Stream.of("Endor is good place to live"));
        when(request.getReader()).thenReturn(bufferedReader);

        final Object result = controller.apply(request, null, null);
        assertEquals("Endor is good place to live", result);
    }

    @Test
    public void shouldReturnParamVariableAndPathVariableAsString() throws Exception {
        final EndorController endorController = new EndorController();
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new MethodController(endorController, EndorController.class.getMethod("returnParamVariableAndPathVariableAsString", String.class, String.class)));
        final Controller controller = builder.build();

        final Configuration configuration = new Configuration();
        configuration.set(PATH_VARIABLE_SECTION + ".path", "Endor");
        configuration.set(PARAM_VARIABLE_SECTION + ".where", "in the forest");

        final HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.lines()).thenReturn(Stream.of("Endor is good place to live"));
        when(request.getReader()).thenReturn(bufferedReader);

        final Object result = controller.apply(request, null, configuration);
        assertEquals("Endor, in the forest", result);
    }

    @Test
    public void shouldReturnParamVariableAndPathVariableAsStringWithCustomName() throws Exception {
        final EndorController endorController = new EndorController();
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new MethodController(endorController, EndorController.class.getMethod("returnParamVariableAndPathVariableAsStringWithCustomName", String.class, String.class)));
        final Controller controller = builder.build();

        final Configuration configuration = new Configuration();
        configuration.set(PATH_VARIABLE_SECTION + ".hello", "Endor");
        configuration.set(PARAM_VARIABLE_SECTION + ".place", "in the forest");

        final HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.lines()).thenReturn(Stream.of("Endor is good place to live"));
        when(request.getReader()).thenReturn(bufferedReader);

        final Object result = controller.apply(request, null, configuration);
        assertEquals("Endor, in the forest", result);
    }

    @Test
    public void shouldReturnEwokNameFromJson() throws Exception {
        final EndorController endorController = new EndorController();
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new MethodController(endorController, EndorController.class.getMethod("returnEwokName", Ewok.class)));
        final Controller controller = builder.build();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.lines()).thenReturn(Stream.of("{\"name\":\"Wicket\"}"));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getReader()).thenReturn(bufferedReader);

        final Object result = controller.apply(request, null, null);
        assertEquals("Wicket", result);
    }

}
