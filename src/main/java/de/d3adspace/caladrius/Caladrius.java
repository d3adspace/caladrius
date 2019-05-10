package de.d3adspace.caladrius;

import java.nio.file.Path;

public interface Caladrius {

    /**
     * Read the config of a class from a path.
     *
     * @param configClazz The configs model class.
     * @param path The path of the files.
     * @param <ConfigType> The generic type of the config.
     * @return The read config.
     */
    <ConfigType> ConfigType readConfig(Class<ConfigType> configClazz, Path path);
}
