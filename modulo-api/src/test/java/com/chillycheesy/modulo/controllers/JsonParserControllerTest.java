package com.chillycheesy.modulo.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonParserControllerTest {

    private static class Ewok {
        private final String name;
        private final int age;

        public Ewok(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    @Test
    public void shouldReturnJsonFormat() throws Exception {
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new JsonParserController());
        builder.add(new SimpleController(new Ewok("Wicket", 10)));
        final Controller controller = builder.build();

        final Object result = controller.apply(null, null, null);
        assertEquals("{\"name\":\"Wicket\",\"age\":10}", result);
    }


    @Test
    public void page() {
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new HttpMethodController("GET"))
                .add(new HttpPathController("/test"))
                .add(new JsonParserController())
                .add(new SimpleController(new Ewok("Wicket", 10)));
    }

    @Test
    public void shouldReturnNullIfNoNextController() throws Exception {
        final ControllerBuilder builder = new ControllerBuilder();
        builder.add(new JsonParserController());

        final Controller controller = builder.build();

        final Object result = controller.apply(null, null, null);
        assertNull(result);
    }


}
