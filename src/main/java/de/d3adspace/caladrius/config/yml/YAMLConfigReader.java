package de.d3adspace.caladrius.config.yml;

import de.d3adspace.caladrius.Caladrius;
import de.d3adspace.caladrius.config.AbstractConfigReader;
import de.d3adspace.caladrius.config.ConfigMeta;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class YAMLConfigReader<ConfigObjectType> extends AbstractConfigReader<ConfigObjectType> {

    /**
     * The yaml instance used for loading content into yaml maps.
     */
    private static final Yaml YAML = new Yaml();

    /**
     * The yaml maps with the config content.
     */
    private final Map<String, Object> content;

    public YAMLConfigReader(ConfigMeta<ConfigObjectType> configMeta, Path path) {
        super(configMeta);

        try {
            content = YAML.load(Files.readString(path));
        } catch (IOException e) {
            throw new IllegalStateException("Can't read config " + path);
        }
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

    /**
     * Resolve the correct level for nested objects.
     *
     * @param key The key.
     * @return The content root.
     */
    private Map<String, Object> resolveContentRoot(String key) {
        if (!key.contains(Caladrius.PATH_DELIMITER)) {
            return content;
        }

        Map<String, Object> content = this.content;
        String[] split = key.split(Caladrius.PATH_DELIMITER_PATTERN);
        for (int i = 0; i < split.length - 1; i++) {
            content = (Map<String, Object>) content.get(split[i]);
        }

        return content;
    }

    /**
     * Strip the key of his path and return the bare key.
     *
     * @param key The key.
     * @return The bare key without path.
     */
    private String stripKey(String key) {
        if (!key.contains(Caladrius.PATH_DELIMITER)) {
            return key;
        }

        String[] split = key.split(Caladrius.PATH_DELIMITER_PATTERN);
        return split[split.length - 1];
    }
}
