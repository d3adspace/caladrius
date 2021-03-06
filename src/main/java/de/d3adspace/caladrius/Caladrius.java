package de.d3adspace.caladrius;

import java.nio.file.Path;

public interface Caladrius {

    /**
     * The delimiter used for key paths.
     */
    String PATH_DELIMITER = ".";

    /**
     * The pattern to detect a {@link #PATH_DELIMITER}
     */
    String PATH_DELIMITER_PATTERN = "\\.";

    /**
     * Read the config of a class from a path.
     *
     * @param configClazz The configs model class.
     * @param path The path of the files.
     * @param <ConfigType> The generic type of the config.
     * @return The read config.
     */
    <ConfigType> ConfigType readConfig(Class<ConfigType> configClazz, Path path);

    /**
     * Write the given config to the given path.
     *
     * @param configObject The config object.
     * @param path The path of the file to write to.
     * @param <ConfigObjectType> The generic type of the config.
     */
    <ConfigObjectType> void writeConfig(ConfigObjectType configObject, Path path);
}
