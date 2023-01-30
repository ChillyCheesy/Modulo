package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;
import com.chillycheesy.modulo.config.MockStream;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodControllerTest {

    private static class EwokController {

        public String ewokName(Controller controller) throws Exception {
            return (String) controller.apply(null, null, null);
        }

        public void manualResponseGesture(HttpServletRequest request, HttpServletResponse response) {
            try (
                final OutputStream outputStream = response.getOutputStream();
                final PrintWriter writer = new PrintWriter(outputStream)
            ) {
                writer.print("hello, endor");
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String bestMenOnTheEarth() {
            return "Benoit";
        }

        public String configurationKeyResult(Configuration configuration) {
            return configuration.getString("key");
        }

    }

    @Test
    public void shouldReturnWicketIfWePassAControllerInside() throws Exception {
        final EwokController ewokController = new EwokController();
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new MethodController(ewokController, ewokController.getClass().getMethod("ewokName", Controller.class)));
        builder.add(new SimpleController("Wicket"));
        final Controller controller = builder.build();

        final Object result = controller.apply(null, null, null);
        assertEquals("Wicket", result);
    }

    @Test
    public void shouldReturnTheBestMenOnTheEarth() throws Exception {
        final EwokController ewokController = new EwokController();
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new MethodController(ewokController, ewokController.getClass().getMethod("bestMenOnTheEarth")));
        final Controller controller = builder.build();
        final Configuration configuration = new Configuration();
        final Object result = controller.apply(null, null, configuration);
        assertEquals("Benoit", result);
        assertFalse(configuration.getBoolean("skip-register-result-in-response"));
    }

    @Test
    public void shouldReturnManualResponseGestureCall() throws Exception {
        final EwokController ewokController = new EwokController();
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new MethodController(ewokController, ewokController.getClass().getMethod("manualResponseGesture", HttpServletRequest.class, HttpServletResponse.class)));
        final Controller controller = builder.build();

        final HttpServletResponse response = mock(HttpServletResponse.class);
        final ServletOutputStream outputStream = new MockStream.MockServletOutputStream();
        when(response.getOutputStream()).thenReturn(outputStream);
        final Configuration configuration = new Configuration();
        final Object result = controller.apply(null, response, configuration);
        assertEquals("hello, endor", outputStream.toString());
        assertTrue(configuration.getBoolean("skip-register-result-in-response"));
    }

    @Test
    public void shouldReturnConfigurationKeyResult() throws Exception {
        final EwokController ewokController = new EwokController();
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new MethodController(ewokController, ewokController.getClass().getMethod("configurationKeyResult", Configuration.class)));
        final Controller controller = builder.build();
        final Configuration configuration = new Configuration();
        configuration.set("key", "value");
        final Object result = controller.apply(null, null, configuration);
        assertEquals("value", result);
        assertFalse(configuration.getBoolean("skip-register-result-in-response"));
    }

}
