package de.d3adspace.caladrius.config;

import de.d3adspace.caladrius.Caladrius;

import java.nio.file.Path;
import java.util.Map;

public abstract class AbstractMapBasedConfigReader<ConfigObjectType> extends AbstractConfigReader<ConfigObjectType> {

    private final Map<String, Object> content;

    public AbstractMapBasedConfigReader(ConfigMeta<ConfigObjectType> configMeta, Path path) {
        super(configMeta, path);
        content = doRead();
    }

    /**
     * Read the whole config into a map.
     *
     * @return The map.
     */
    protected abstract Map<String, Object> doRead();

    /**
     * Resolve the correct level for nested objects.
     *
     * @param key The key.
     * @return The content root.
     */
    protected Map<String, Object> resolveContentRoot(String key) {
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
}
