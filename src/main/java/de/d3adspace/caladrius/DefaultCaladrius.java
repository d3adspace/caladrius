package de.d3adspace.caladrius;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import de.d3adspace.caladrius.annotation.Config;
import de.d3adspace.caladrius.annotation.Key;
import de.d3adspace.caladrius.config.ConfigMeta;
import de.d3adspace.caladrius.config.reader.ConfigReader;
import de.d3adspace.caladrius.config.ConfigType;
import de.d3adspace.caladrius.config.reader.yaml.YAMLConfigReader;
import de.d3adspace.caladrius.config.writer.ConfigWriter;
import de.d3adspace.caladrius.config.writer.yaml.YAMLConfigWriter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public final class DefaultCaladrius implements Caladrius {

    /**
     * All known config meta.
     */
    private final Map<Class<?>, ConfigMeta> configMeta;

    DefaultCaladrius(Map<Class<?>, ConfigMeta> configMeta) {
        this.configMeta = configMeta;
    }

    @Override
    public <ConfigObjectType> ConfigObjectType readConfig(Class<ConfigObjectType> configClazz, Path path) {
        Preconditions.checkNotNull(configClazz, "Config class may not be null.");
        Preconditions.checkNotNull(path, "Config file path may not be null.");
        Preconditions.checkArgument(Files.exists(path), "Config file doesn't exists.");

        // Get config meta
        ConfigMeta<ConfigObjectType> configMeta = getConfigMeta(configClazz);

        // Create reader
        ConfigReader<ConfigObjectType> configReader = createConfigReader(configMeta, path);

        // Create config object
        ConfigObjectType config;

        try {
            config = configClazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Can't instantiate config class. Does it have a no args constructor?", e);
        }

        // Read config
        config = configReader.readConfig(config);
        return config;
    }

    @Override
    public <ConfigObjectType> void writeConfig(ConfigObjectType configObject, Path path) {
        Class<ConfigObjectType> configClazz = (Class<ConfigObjectType>) configObject.getClass();
        ConfigMeta<ConfigObjectType> configMeta = getConfigMeta(configClazz);

        ConfigWriter<ConfigObjectType> configWriter = createConfigWriter(configMeta, path);
        configWriter.writeConfig(configObject);
    }

    private <ConfigObjectType> ConfigWriter<ConfigObjectType> createConfigWriter(ConfigMeta<ConfigObjectType> configMeta, Path path) {

        // We only support yaml atm
        return new YAMLConfigWriter<>(configMeta, path);
    }

    /**
     * Get the config meta of a config class and read it if its not known yet.
     *
     * @param configClazz The class of the config.
     * @return The config meta
     */
    private <ConfigObjectType> ConfigMeta<ConfigObjectType> getConfigMeta(Class<ConfigObjectType> configClazz) {
        ConfigMeta<ConfigObjectType> configMeta = this.configMeta.get(configClazz);

        if (configMeta == null) {
            configMeta = readConfigMeta(configClazz);
            this.configMeta.put(configClazz, configMeta);
        }

        return configMeta;
    }

    /**
     * Read the config meta of a config clazz.
     *
     * @param configClazz The config clazz.
     * @return The config meta.
     */
    private <ConfigObjectType> ConfigMeta<ConfigObjectType> readConfigMeta(Class<ConfigObjectType> configClazz) {
        boolean isConfigAnnotationPresent = configClazz.isAnnotationPresent(Config.class);
        if (!isConfigAnnotationPresent) {
            throw new IllegalArgumentException("The class " + configClazz + " doesn't have a config annotation.");
        }

        // Get the annotation
        Config config = configClazz.getDeclaredAnnotation(Config.class);

        String configName = config.name();
        ConfigType configType = config.type();

        // Read fields
        Map<String, Field> fields = readConfigFields(configClazz);

        return new ConfigMeta<>(configClazz, configType, configName, fields);
    }

    /**
     * Read all config fields of a class to its keys.
     *
     * @param configClazz The config clazz.
     * @return The fields with their keys.
     */
    private Map<String, Field> readConfigFields(Class configClazz) {
        Map<String, Field> fields = Maps.newHashMap();

        Field[] declaredFields = configClazz.getDeclaredFields();

        for (Field declaredField : declaredFields) {

            boolean isKeyAnnotationPresent = declaredField.isAnnotationPresent(Key.class);
            if (!isKeyAnnotationPresent) {
                continue;
            }

            Key keyAnnotation = declaredField.getDeclaredAnnotation(Key.class);
            String configKey = keyAnnotation.value();

            fields.put(configKey, declaredField);
        }

        return fields;
    }

    /**
     * Create a config reader for the given config type at the given path.
     *
     * @param configMeta The config meta.
     * @param path The path.
     * @return The config reader.
     */
    private <ConfigObjectType> ConfigReader<ConfigObjectType> createConfigReader(ConfigMeta<ConfigObjectType> configMeta, Path path) {

        // We only support yaml atm
        return new YAMLConfigReader<>(configMeta, path);
    }
}
