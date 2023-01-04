package com.chillycheesy.modulo.pages;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplatePageTest {

    @Test
    public void testSaveTemplateVariable() {
        final TemplatePage templatePage = new TemplatePage();
        templatePage.saveTemplateVariable(Map.of("name", "value", "name2", "value2"));
        assertEquals("value", templatePage.getTemplateVariable("name"));
        assertEquals("value2", templatePage.getTemplateVariable("name2"));
    }

}
