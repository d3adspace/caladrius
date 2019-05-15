package de.d3adspace.caladrius.annotation;

import de.d3adspace.caladrius.config.ConfigType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicated that the annotated class should be used as a config model.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    /**
     * The name of the config, that can be used as an identifier.
     *
     * @return The identifier of the config.
     */
    String name();

    /**
     * How the config should be encoded.
     *
     * @return The type of the config.
     */
    ConfigType type();
}
