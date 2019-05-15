package de.d3adspace.caladrius.config;

import de.d3adspace.caladrius.Caladrius;

import java.nio.file.Path;

/**
 * A config abstraction that focuses on providing pathed key support and providing the {@link Path} and
 * {@link ConfigMeta<ContentObjectType>} to any config IO implementation.
 *
 * @param <ContentObjectType> The generic type of the config model.
 */
public abstract class AbstractConfigIO<ContentObjectType> {

    /**
     * The meta information of the config.
     */
    private final ConfigMeta<ContentObjectType> configMeta;

    /**
     * The path where the config file should be located.
     */
    private final Path path;

    /**
     * Create a new config io provider.
     *
     * @param configMeta The meta information of the config.
     * @param path path where the config file should be located.
     */
    public AbstractConfigIO(ConfigMeta<ContentObjectType> configMeta, Path path) {
        this.configMeta = configMeta;
        this.path = path;
    }

    /**
     * Get the path of the config.
     *
     * @return The path of the config.
     */
    protected Path getPath() {
        return path;
    }

    /**
     * Get the meta information about the config.
     *
     * @return The meta information about the config.
     */
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
