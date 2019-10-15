package de.d3adspace.caladrius.config.reader.yaml;

import de.d3adspace.caladrius.config.reader.AbstractMapBasedConfigReader;
import de.d3adspace.caladrius.config.ConfigMeta;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class YAMLConfigReader<ConfigObjectType> extends AbstractMapBasedConfigReader<ConfigObjectType> {

    /**
     * The yaml instance used for loading content into yaml maps.
     */
    private static final Yaml YAML = new Yaml();

    /**
     * Create a new yaml config reader by the meta of the config to read and the path of the config.
     *
     * @param configMeta The meta of the config to read.
     * @param path The path of the config.
     */
    public YAMLConfigReader(ConfigMeta<ConfigObjectType> configMeta, Path path) {
        super(configMeta, path);
    }

    @Override
    protected Map<String, Object> doRead() {
        try {
            return YAML.load(Files.readString(getPath()));
        } catch (IOException e) {
            throw new IllegalStateException("Can't read config " + getPath());
        }
    }
}
