package de.d3adspace.caladrius.config.writer;

import com.google.common.collect.Maps;
import de.d3adspace.caladrius.Caladrius;
import de.d3adspace.caladrius.config.ConfigMeta;
import de.d3adspace.caladrius.config.map.ContentRootMapResolver;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapBasedConfigWriter<ConfigObjectType> extends AbstractConfigWriter<ConfigObjectType> implements ContentRootMapResolver<String, Object> {

    private final Map<String, Object> content = Maps.newHashMap();

    public AbstractMapBasedConfigWriter(ConfigMeta<ConfigObjectType> configMeta, Path path) {
        super(configMeta, path);
    }

    @Override
    protected void writeMap(String key, Map mapValue) {
        Map<String, Object> contentRoot = resolveContentRoot(key);
        contentRoot.put(stripKey(key), mapValue);
    }

    @Override
    protected void writeList(String key, List listValue) {
        Map<String, Object> contentRoot = resolveContentRoot(key);
        contentRoot.put(stripKey(key), listValue);
    }

    @Override
    protected void writeLong(String key, long longValue) {
        Map<String, Object> contentRoot = resolveContentRoot(key);
        contentRoot.put(stripKey(key), longValue);
    }

    @Override
    protected void writeBoolean(String key, boolean booleanValue) {
        Map<String, Object> contentRoot = resolveContentRoot(key);
        contentRoot.put(stripKey(key), booleanValue);
    }

    @Override
    protected void writeFloat(String key, float floatValue) {
        Map<String, Object> contentRoot = resolveContentRoot(key);
        contentRoot.put(stripKey(key), floatValue);
    }

    @Override
    protected void writeDouble(String key, double doubleValue) {
        Map<String, Object> contentRoot = resolveContentRoot(key);
        contentRoot.put(stripKey(key), doubleValue);
    }

    @Override
    protected void writeInteger(String key, int integer) {
        Map<String, Object> contentRoot = resolveContentRoot(key);
        contentRoot.put(stripKey(key), integer);
    }

    @Override
    protected void writeString(String key, String string) {
        Map<String, Object> contentRoot = resolveContentRoot(key);
        contentRoot.put(stripKey(key), string);
    }

    @Override
    public Map<String, Object> resolveContentRoot(String key) {
        if (!key.contains(Caladrius.PATH_DELIMITER)) {
            return content;
        }

        Map<String, Object> content = this.content;
        String[] split = key.split(Caladrius.PATH_DELIMITER_PATTERN);

        for (int i = 0; i < split.length - 1; i++) {
            Object object = content.computeIfAbsent(split[i], k -> Maps.newHashMap());
            content = (Map<String, Object>) object;
        }

        return content;
    }

    protected Map<String, Object> getContent() {
        return content;
    }
}
