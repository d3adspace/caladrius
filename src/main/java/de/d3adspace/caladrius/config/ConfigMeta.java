package de.d3adspace.caladrius.config;

import java.lang.reflect.Field;
import java.util.Map;

public record ConfigMeta<ConfigObjectType>(
  Class<ConfigObjectType> clazz,
  ConfigType type,
  String name,
  Map<String, Field> fields
) {
  public Map<String, Field> getFields() {
    return fields;
  }
}
