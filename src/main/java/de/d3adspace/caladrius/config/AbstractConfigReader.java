package de.d3adspace.caladrius.config;

import com.google.common.flogger.FluentLogger;
import de.d3adspace.caladrius.Caladrius;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Map;

public abstract class AbstractConfigReader<ConfigObjectType> implements ConfigReader<ConfigObjectType> {

    private final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final ConfigMeta<ConfigObjectType> configMeta;
    private final Path path;

    public AbstractConfigReader(ConfigMeta<ConfigObjectType> configMeta, Path path) {
        this.configMeta = configMeta;
        this.path = path;
    }

    @Override
    public ConfigObjectType readConfig(ConfigObjectType configObject) {
        Map<String, Field> fields = configMeta.getFields();

        fields.forEach((key, field) -> {

            Class<?> fieldType = field.getType();
            Object value = readValue(key, fieldType);

            try {
                field.setAccessible(true);
                field.set(configObject, value);
            } catch (IllegalAccessException e) {
                logger.atWarning()
                        .withCause(e)
                        .log("Couldn't set field %s in config model.");
            }
        });

        return configObject;
    }

    private Object readValue(String key, Class<?> fieldType) {
        if (fieldType == String.class) {
            return readString(key);
        } else if (fieldType == Integer.TYPE) {
            return readInt(key);
        } else if (fieldType == Double.TYPE) {
            return readDouble(key);
        } else if (fieldType == Float.TYPE) {
            return readFloat(key);
        } else if (fieldType == Boolean.TYPE) {
            return readBoolean(key);
        } else if (fieldType == Long.TYPE) {
            return readLong(key);
        }

        return null;
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

    public Path getPath() {
        return path;
    }

    protected abstract String readString(String key);
    protected abstract int readInt(String key);
    protected abstract double readDouble(String key);
    protected abstract float readFloat(String key);
    protected abstract boolean readBoolean(String key);
    protected abstract long readLong(String key);
}
