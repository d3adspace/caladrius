package de.d3adspace.caladrius.config.map;

import java.util.Map;

public interface ContentRootMapResolver<KeyType, ValueType> {

    /**
     * Resolve the content root of the given key and return the end element.
     *
     * @param key The key.
     * @return The content root.
     */
    Map<KeyType, ValueType> resolveContentRoot(String key);
}
