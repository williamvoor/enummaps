package org.thecloudforge.collections.enummaps;

import java.util.Optional;
import java.util.Set;

/**
 * A map with two keys for value lookup.
 *
 * @param <K1> the type of the first key
 * @param <K2> the type of the second key
 * @param <V> the type of mapped values
 */
public interface DoubleMap<K1, K2, V> {

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings
     */
    int size();

    /**
     * Returns true if this map contains no key-value mappings.
     *
     * @return true if this map is empty
     */
    boolean isEmpty();

    /**
     * Returns true if this map contains a mapping for the specified keys.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @return true if this map contains a mapping for the keys
     */
    boolean containsKeys(K1 key1, K2 key2);

    /**
     * Returns true if this map maps one or more keys to the specified value.
     *
     * @param value the value to search for
     * @return true if this map contains the value
     */
    boolean containsValue(Object value);

    /**
     * Returns the value to which the specified keys are mapped, or null if this map
     * contains no mapping for the keys.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @return the value mapped to the keys, or null if no mapping exists
     */
    V get(K1 key1, K2 key2);

    /**
     * Returns an Optional containing the value mapped to the specified keys,
     * or an empty Optional if no mapping exists.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @return an Optional containing the value, or empty if no mapping exists
     */
    default Optional<V> getOptional(K1 key1, K2 key2) {
        return Optional.ofNullable(get(key1, key2));
    }

    /**
     * Associates the specified value with the specified keys in this map.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param value the value to be associated with the keys
     * @return the previous value associated with the keys, or null if there was no mapping
     */
    V put(K1 key1, K2 key2, V value);

    /**
     * Removes the mapping for the keys from this map if it is present.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @return the previous value associated with the keys, or null if there was no mapping
     */
    V remove(K1 key1, K2 key2);

    /**
     * Copies all of the mappings from the specified map to this map.
     *
     * @param m the map whose mappings are to be copied
     */
    void putAll(DoubleMap<? extends K1, ? extends K2, ? extends V> m);

    /**
     * Removes all mappings from this map.
     */
    void clear();

    /**
     * Returns a set of first keys that are associated with the given second key.
     *
     * @param key2 the second key
     * @return a set of first keys
     */
    Set<K1> keySet1(K2 key2);

    /**
     * Returns a set of second keys that are associated with the given first key.
     *
     * @param key1 the first key
     * @return a set of second keys
     */
    Set<K2> keySet2(K1 key1);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
