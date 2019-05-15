package de.d3adspace.caladrius.config.yml;

import com.google.common.flogger.FluentLogger;
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
    protected long readLong(String key) {
        Map<String, Object> content = resolveContentRoot(key);
        return (long) content.get(stripKey(key));
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
