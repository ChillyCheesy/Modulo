package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegexHttpPathControllerTest {

    @Test
    public void testIfTheRegexPathMatch() throws Exception {
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new RegexHttpPathController("/my/.*"));
        builder.add(new SimpleController("Ee chee wa maa"));
        final Controller controller = builder.build();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/my/path");
        final Object response = controller.apply(request, null, new Configuration());
        assertEquals("Ee chee wa maa", response);

        final HttpServletRequest request2 = mock(HttpServletRequest.class);
        when(request2.getRequestURI()).thenReturn("/my/other/path");
        final Object response2 = controller.apply(request2, null, new Configuration());
        assertEquals("Ee chee wa maa", response2);

        final HttpServletRequest request3 = mock(HttpServletRequest.class);
        when(request3.getRequestURI()).thenReturn("/my/other/other/path");
        final Object response3 = controller.apply(request3, null, new Configuration());
        assertEquals("Ee chee wa maa", response3);

        final HttpServletRequest request4 = mock(HttpServletRequest.class);
        when(request4.getRequestURI()).thenReturn("/m/path");
        final Object response4 = controller.apply(request4, null, new Configuration());
        assertNull(response4);
    }

}
