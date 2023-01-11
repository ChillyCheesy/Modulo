package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;
import com.chillycheesy.modulo.utils.exception.InvalidPathVariableException;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpPathVariableControllerTest {

    @Test
    public void testIfTheHttpPathVariableMatch() throws Exception {
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new HttpPathVariableController("/test/{id}"));
        builder.add(new SimpleController("Ee chee wa maa"));
        final Controller controller = builder.build();

        final Configuration configuration = new Configuration();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/test/path");
        final Object response = controller.apply(request, null, configuration);
        assertEquals("Ee chee wa maa", response);
        assertEquals("path", configuration.get("id"));

        final Configuration configuration2 = new Configuration();
        final HttpServletRequest request2 = mock(HttpServletRequest.class);
        when(request2.getRequestURI()).thenReturn("/test/path/other");
        final Object response2 = controller.apply(request2, null, configuration2);
        assertNull(response2);

        final Configuration configuration3 = new Configuration();
        final HttpServletRequest request3 = mock(HttpServletRequest.class);
        when(request3.getRequestURI()).thenReturn("/test");
        final Object response3 = controller.apply(request3, null, configuration3);
        assertNull(response3);
    }

    @Test
    public void testIfTheHttpPathVariableMatchWithTwoArgs() throws Exception {
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new HttpPathVariableController("/test/{id}/{name}"));
        builder.add(new SimpleController("Ee chee wa maa"));
        final Controller controller = builder.build();

        final Configuration configuration = new Configuration();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/test/path/path2");
        final Object response = controller.apply(request, null, configuration);
        assertEquals("Ee chee wa maa", response);
        assertEquals("path", configuration.get("id"));
        assertEquals("path2", configuration.get("name"));

        final Configuration configuration2 = new Configuration();
        final HttpServletRequest request2 = mock(HttpServletRequest.class);
        when(request2.getRequestURI()).thenReturn("/test/path/other/plus");
        final Object response2 = controller.apply(request2, null, configuration2);
        assertNull(response2);
    }

    @Test
    public void testIfTheHttpPathVariableMatchWithStar() throws Exception {
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new HttpPathVariableController("/test/{id}/*/{name}"));
        builder.add(new SimpleController("Ee chee wa maa"));
        final Controller controller = builder.build();

        final Configuration configuration = new Configuration();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/test/path/truc/path2");
        final Object response = controller.apply(request, null, configuration);
        assertEquals("Ee chee wa maa", response);
        assertEquals("path", configuration.get("id"));
        assertEquals("path2", configuration.get("name"));

        final Configuration configuration2 = new Configuration();
        final HttpServletRequest request2 = mock(HttpServletRequest.class);
        when(request2.getRequestURI()).thenReturn("/test/path/bidule/path2");
        final Object response2 = controller.apply(request2, null, configuration2);
        assertEquals("Ee chee wa maa", response2);
        assertEquals("path", configuration2.get("id"));
        assertEquals("path2", configuration2.get("name"));

        final Configuration configuration3 = new Configuration();
        final HttpServletRequest request3 = mock(HttpServletRequest.class);
        when(request3.getRequestURI()).thenReturn("/test/path/wicket/path2");
        final Object response3 = controller.apply(request3, null, configuration3);
        assertEquals("Ee chee wa maa", response3);
        assertEquals("path", configuration3.get("id"));
        assertEquals("path2", configuration3.get("name"));
    }

    @Test
    public void shouldFailWithExceptionIfPathVariableIsEmpty() {
        final InvalidPathVariableException exception = assertThrows(InvalidPathVariableException.class, () -> {
            new HttpPathVariableController("");
        });
        assertEquals("The path variable can't be null or empty", exception.getMessage());
    }

    @Test
    public void shouldFailWithExceptionIfPathVariableIsNull() {
        final InvalidPathVariableException exception = assertThrows(InvalidPathVariableException.class, () -> {
            new HttpPathVariableController(null);
        });
        assertEquals("The path variable can't be null or empty", exception.getMessage());
    }

    @Test
    public void shouldFailWithExceptionIfPathVariableContainADoubleStarAtTheBadPlace() {
        final InvalidPathVariableException exception = assertThrows(InvalidPathVariableException.class, () -> {
            new HttpPathVariableController("/test/**/path");
        });
        assertEquals("The ** path can only be placed at the end.", exception.getMessage());
    }

    @Test
    public void shouldFailWithExceptionIfPathVariableContainADoubleStarAtTheBadPlace2() {
        final InvalidPathVariableException exception = assertThrows(InvalidPathVariableException.class, () -> {
            new HttpPathVariableController("**/test/path");
        });
        assertEquals("The ** path can only be placed at the end.", exception.getMessage());
    }

    @Test
    public void shouldFailWithExceptionIfPathVariableContainADoubleStarAtTheBadPlace3() {
        final InvalidPathVariableException exception = assertThrows(InvalidPathVariableException.class, () -> {
            new HttpPathVariableController("/**/test/path");
        });
        assertEquals("The ** path can only be placed at the end.", exception.getMessage());
    }

}
