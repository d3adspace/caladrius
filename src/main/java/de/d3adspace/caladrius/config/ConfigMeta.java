package de.d3adspace.caladrius.config;

import java.lang.reflect.Field;
import java.util.Map;

public class ConfigMeta<ConfigObjectType> {

    private final Class<ConfigObjectType> clazz;
    private final ConfigType type;
    private final String name;
    private final Map<String, Field> fields;

    public ConfigMeta(Class<ConfigObjectType> clazz, ConfigType type, String name, Map<String, Field> fields) {
        this.clazz = clazz;
        this.type = type;
        this.name = name;
        this.fields = fields;
    }

    public Class<ConfigObjectType> getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public ConfigType getType() {
        return type;
    }

    public Map<String, Field> getFields() {
        return fields;
    }
}
