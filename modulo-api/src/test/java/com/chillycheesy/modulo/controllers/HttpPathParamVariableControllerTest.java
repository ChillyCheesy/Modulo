package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpPathParamVariableControllerTest {

    @Test
    public void shouldRegisterParameters() throws Exception {
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new HttpParamVariableController());
        builder.add(new SimpleController("Ee chee wa maa"));
        final Controller controller = builder.build();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final Iterator<String> iterator = Arrays.asList("name", "age").iterator();
        final Enumeration<String> enumeration = mock(Enumeration.class);
        when(enumeration.asIterator()).thenReturn(iterator);
        when(request.getParameterNames()).thenReturn(enumeration);
        when(request.getParameter("name")).thenReturn("Wicket");
        when(request.getParameter("age")).thenReturn("16");

        final Configuration configuration = new Configuration();
        controller.apply(request, null, configuration);

        assertEquals("Wicket", configuration.getString("param-variable.name"));
        assertEquals(16, configuration.getInteger("param-variable.age"));
        assertNull(configuration.get("param-variable.unknown"));
    }


}
