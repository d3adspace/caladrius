package de.d3adspace.caladrius.config.yml;

import de.d3adspace.caladrius.config.AbstractConfigReader;
import de.d3adspace.caladrius.config.ConfigMeta;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class YAMLConfigReader<ConfigObjectType> extends AbstractConfigReader<ConfigObjectType> {

    private static final Yaml YAML = new Yaml();
    private final Map<String, Object> content;

    public YAMLConfigReader(ConfigMeta<ConfigObjectType> configMeta, Path path) {
        super(configMeta, path);
        try {
            content = YAML.load(Files.readString(path));
        } catch (IOException e) {
            throw new IllegalStateException("Can't read config " + path);
        }
    }

    @Override
    protected String readString(String key) {
        return (String) content.get(key);
    }

    @Override
    protected int readInt(String key) {
        return (int) content.get(key);
    }

    @Override
    protected double readDouble(String key) {
        return (double) content.get(key);
    }

    @Override
    protected float readFloat(String key) {
        return (float) content.get(key);
    }

    @Override
    protected boolean readBoolean(String key) {
        return (boolean) content.get(key);
    }
}
