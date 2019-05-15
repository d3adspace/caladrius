package de.d3adspace.caladrius.config.reader;

import com.google.common.flogger.FluentLogger;
import de.d3adspace.caladrius.config.AbstractConfigIO;
import de.d3adspace.caladrius.config.ConfigMeta;
import de.d3adspace.caladrius.config.reader.ConfigReader;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Map;

public abstract class AbstractConfigReader<ConfigObjectType> extends AbstractConfigIO<ConfigObjectType> implements ConfigReader<ConfigObjectType> {

    private final FluentLogger logger = FluentLogger.forEnclosingClass();

    public AbstractConfigReader(ConfigMeta<ConfigObjectType> configMeta, Path path) {
        super(configMeta, path);
    }

    @Override
    public ConfigObjectType readConfig(ConfigObjectType configObject) {
        Map<String, Field> fields = getConfigMeta().getFields();

        fields.forEach((key, field) -> {

            Class<?> fieldType = field.getType();
            Object value = readValue(key, fieldType);

            try {
                field.setAccessible(true);
                field.set(configObject, value);
            } catch (IllegalAccessException e) {
                logger.atWarning()
                        .withCause(e)
                        .log("Couldn't set field %s in config model.", key);
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

    protected abstract String readString(String key);
    protected abstract int readInt(String key);
    protected abstract double readDouble(String key);
    protected abstract float readFloat(String key);
    protected abstract boolean readBoolean(String key);
    protected abstract long readLong(String key);
}
