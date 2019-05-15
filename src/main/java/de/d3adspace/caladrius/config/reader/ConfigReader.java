package de.d3adspace.caladrius.config.reader;

public interface ConfigReader<ConfigObjectType> {

    /**
     * Read the config into the given object and return it.
     *
     * @param configObject The config object.
     * @return The config object.
     */
    ConfigObjectType readConfig(ConfigObjectType configObject);
}
