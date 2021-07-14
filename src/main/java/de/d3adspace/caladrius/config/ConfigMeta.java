package de.d3adspace.caladrius.config;

import java.lang.reflect.Field;
import java.util.Map;

public record ConfigMeta<ConfigObjectType>(
  Class<ConfigObjectType> clazz,
  ConfigType type,
  String name,
  Map<String, Field> fields
) {

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
