package com.chillycheesy.modulo.commands.builder;

import java.lang.annotation.*;

/**
 * Annotation to indicate that a {@link CommandBuilder} should be used to build a command.
 * The value is used to set the command usages.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Usage {
    /**
     * The usages for the command.
     * @return the usages for the command.
     */
    String value();
}
