package com.chillycheesy.modulo.commands.builder;

import java.lang.annotation.*;

/**
 * Annotation to indicate that a {@link CommandBuilder} should be used to build a command.
 * The value is used to set the command Description.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Description {
    /**
     * The description of the command.
     * @return The description of the command.
     */
    String value();
}
