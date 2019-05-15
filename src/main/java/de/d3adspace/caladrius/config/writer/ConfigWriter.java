package de.d3adspace.caladrius.config.writer;

public interface ConfigWriter<ConfigObjectType> {

    void writeConfig(ConfigObjectType configObject);
}
