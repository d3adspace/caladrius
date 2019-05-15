package de.d3adspace.caladrius.config.writer;

import com.google.common.flogger.FluentLogger;
import de.d3adspace.caladrius.config.AbstractConfigIO;
import de.d3adspace.caladrius.config.ConfigMeta;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Map;

public abstract class AbstractConfigWriter<ConfigObjectType> extends AbstractConfigIO<ConfigObjectType> implements ConfigWriter<ConfigObjectType> {

    private final FluentLogger logger = FluentLogger.forEnclosingClass();

    public AbstractConfigWriter(ConfigMeta<ConfigObjectType> configMeta, Path path) {
        super(configMeta, path);
    }

    @Override
    public void writeConfig(ConfigObjectType configObject) {
        Map<String, Field> fields = getConfigMeta().getFields();

        fields.forEach((key, field) -> {

            Class<?> fieldType = field.getType();

            try {
                writeValue(configObject, key, field, fieldType);
            } catch (IllegalAccessException e) {
                logger.atWarning()
                        .withCause(e)
                        .log("Couldn't set field %s in config.", key);
            }
        });

        doWrite();
    }

    protected abstract void doWrite();

    private void writeValue(ConfigObjectType configObject, String key, Field field, Class<?> fieldType) throws IllegalAccessException {

        if (fieldType == String.class) {
            Object object = field.get(configObject);
            writeString(key, object.toString());
        } else if (fieldType == Integer.TYPE) {
            int integer = field.getInt(configObject);
            writeInteger(key, integer);
        } else if (fieldType == Double.TYPE) {
            double doubleValue = field.getDouble(configObject);
            writeDouble(key, doubleValue);
        } else if (fieldType == Float.TYPE) {
            float floatValue = field.getFloat(configObject);
            writeFloat(key, floatValue);
        } else if (fieldType == Boolean.TYPE) {
            boolean booleanValue = field.getBoolean(configObject);
            writeBoolean(key, booleanValue);
        } else if (fieldType == Long.TYPE) {
            long longValue = field.getLong(configObject);
            writeLong(key, longValue);
        }
    }

    protected abstract void writeLong(String key, long longValue);
    protected abstract void writeBoolean(String key, boolean booleanValue);
    protected abstract void writeFloat(String key, float floatValue);
    protected abstract void writeDouble(String key, double doubleValue);
    protected abstract void writeInteger(String key, int integer);
    protected abstract void writeString(String key, String string);
}
