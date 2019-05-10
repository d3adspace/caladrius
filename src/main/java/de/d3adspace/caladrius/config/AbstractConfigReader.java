package de.d3adspace.caladrius.config;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Map;

public abstract class AbstractConfigReader<ConfigObjectType> implements ConfigReader<ConfigObjectType> {

    private final ConfigMeta<ConfigObjectType> configMeta;

    public AbstractConfigReader(ConfigMeta<ConfigObjectType> configMeta) {
        this.configMeta = configMeta;
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
                e.printStackTrace();
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
        }

        return null;
    }

    protected abstract String readString(String key);
    protected abstract int readInt(String key);
    protected abstract double readDouble(String key);
    protected abstract float readFloat(String key);
    protected abstract boolean readBoolean(String key);
}
