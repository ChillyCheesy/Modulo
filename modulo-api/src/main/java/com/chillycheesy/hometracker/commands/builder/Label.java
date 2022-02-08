package com.chillycheesy.hometracker.commands.builder;

import java.lang.annotation.*;

/**
 * Annotation to indicate that a {@link CommandBuilder} should be used to build a command.
 * The value is used to set the command Label and alias.
 * The first value is the command label, the other is the command aliases.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Label {
    /**
     * The label and alias to use for the command.
     * The first value is the command label, the other is the command aliases.
     * @return the label and aliases.
     */
    String[] value();
}
