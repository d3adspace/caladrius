package de.d3adspace.caladrius.config;

import de.d3adspace.caladrius.Caladrius;

import java.nio.file.Path;

public abstract class AbstractConfigIO<ContentObjectType> {

    private final ConfigMeta<ContentObjectType> configMeta;
    private final Path path;

    public AbstractConfigIO(ConfigMeta<ContentObjectType> configMeta, Path path) {
        this.configMeta = configMeta;
        this.path = path;
    }

    protected Path getPath() {
        return path;
    }

    protected ConfigMeta<ContentObjectType> getConfigMeta() {
        return configMeta;
    }

    /**
     * Check if the given key is pathed.
     *
     * @param key The key.
     * @return If the key is pathed.
     */
    private boolean isPathedKey(String key) {
        return key.contains(Caladrius.PATH_DELIMITER);
    }

    /**
     * Strip the key of his path and return the bare key.
     *
     * @param key The key.
     * @return The bare key without path.
     */
    protected String stripKey(String key) {
        boolean isPathedKey = isPathedKey(key);
        if (!isPathedKey) {
            return key;
        }

        String[] split = key.split(Caladrius.PATH_DELIMITER_PATTERN);
        return split[split.length - 1];
    }
}
