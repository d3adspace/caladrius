package de.d3adspace.caladrius.config;

import com.google.common.flogger.FluentLogger;
import de.d3adspace.caladrius.Caladrius;

import java.nio.file.Path;
import java.util.Map;

public abstract class AbstractMapBasedConfigReader<ConfigObjectType> extends AbstractConfigReader<ConfigObjectType> {

    /**
     * The logger that will log skipped fields and exceptions.
     */
    private final FluentLogger logger = FluentLogger.forEnclosingClass();

    /**
     * The content of the config in form of a map.
     */
    private final Map<String, Object> content;

    /**
     * Create a new yaml config reader by the meta of the config to read and the path of the config.
     *
     * @param configMeta The meta of the config to read.
     * @param path The path of the config.
     */
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
            Object object = content.get(split[i]);

            if (!(object instanceof Map)) {
                logger.atWarning().log("Object of path %s isn't a map.", key);
                continue;
            }

            content = (Map<String, Object>) object;
        }

        return content;
    }
}
