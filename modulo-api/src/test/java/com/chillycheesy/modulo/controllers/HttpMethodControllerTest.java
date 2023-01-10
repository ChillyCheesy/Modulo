package com.chillycheesy.modulo.controllers;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpMethodControllerTest {

    @Test
    public void testIfTheMethodMatch() {
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new HttpMethodController("GET"));
        builder.add(new SimpleController("Ee chee wa maa"));
        final Controller controller = builder.build();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        final String response = controller.apply(request, null);
        assertEquals("Ee chee wa maa", response);

        final HttpServletRequest request2 = mock(HttpServletRequest.class);
        when(request2.getMethod()).thenReturn("POST");
        final String response2 = controller.apply(request2, null);
        assertNull(response2);
    }

}
