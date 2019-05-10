package de.d3adspace.caladrius.config.yml;

import de.d3adspace.caladrius.Caladrius;
import de.d3adspace.caladrius.config.AbstractConfigReader;
import de.d3adspace.caladrius.config.AbstractMapBasedConfigReader;
import de.d3adspace.caladrius.config.ConfigMeta;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class YAMLConfigReader<ConfigObjectType> extends AbstractMapBasedConfigReader<ConfigObjectType> {

    /**
     * The yaml instance used for loading content into yaml maps.
     */
    private static final Yaml YAML = new Yaml();

    public YAMLConfigReader(ConfigMeta<ConfigObjectType> configMeta, Path path) {
        super(configMeta, path);
    }

    @Override
    protected String readString(String key) {
        Map<String, Object> content = resolveContentRoot(key);
        return (String) content.get(stripKey(key));
    }

    @Override
    protected int readInt(String key) {
        Map<String, Object> content = resolveContentRoot(key);
        return (int) content.get(stripKey(key));
    }

    @Override
    protected double readDouble(String key) {
        Map<String, Object> content = resolveContentRoot(key);
        return (double) content.get(stripKey(key));
    }

    @Override
    protected float readFloat(String key) {
        Map<String, Object> content = resolveContentRoot(key);
        return (float) content.get(stripKey(key));
    }

    @Override
    protected boolean readBoolean(String key) {
        Map<String, Object> content = resolveContentRoot(key);
        return (boolean) content.get(stripKey(key));
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
