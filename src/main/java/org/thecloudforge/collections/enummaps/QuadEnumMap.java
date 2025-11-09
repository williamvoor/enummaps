package org.thecloudforge.collections.enummaps;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;

/**
 * A specialized map implementation that uses four enum keys for efficient value lookup.
 * This extends TripleEnumMap by adding a fourth dimension.
 *
 * @param <K1> the type of the first enum key
 * @param <K2> the type of the second enum key
 * @param <K3> the type of the third enum key
 * @param <K4> the type of the fourth enum key
 * @param <V> the type of mapped values
 */
public class QuadEnumMap<K1 extends Enum<K1>, K2 extends Enum<K2>, K3 extends Enum<K3>, K4 extends Enum<K4>, V>
        extends TripleEnumMap<K1, K2, K3, EnumMap<K4, V>> {

    private int size = 0;

    /**
     * Creates an empty QuadEnumMap.
     */
    public QuadEnumMap() {
    }

    /**
     * Returns true if this map contains a mapping for the specified four keys.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @param key4 the fourth key
     * @return true if this map contains a mapping for the keys
     */
    public boolean containsKeys(K1 key1, K2 key2, K3 key3, K4 key4) {
        if (!super.containsKeys(key1, key2, key3)) {
            return false;
        }

        if (key4 == null) {
            return false;
        }

        var innerMap = super.get(key1, key2, key3);
        return innerMap != null && innerMap.containsKey(key4);
    }

    /**
     * Associates the specified value with the four specified keys in this map.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @param key4 the fourth key
     * @param value the value to be associated with the keys
     * @return the previous value associated with the keys, or null if there was no mapping
     */
    public V put(K1 key1, K2 key2, K3 key3, K4 key4, V value) {
        Objects.requireNonNull(key1, "First key must not be null");
        Objects.requireNonNull(key2, "Second key must not be null");
        Objects.requireNonNull(key3, "Third key must not be null");
        Objects.requireNonNull(key4, "Fourth key must not be null");

        var innerMap = super.get(key1, key2, key3);
        if (innerMap == null) {
            innerMap = new EnumMap<>(key4.getDeclaringClass());
            super.put(key1, key2, key3, innerMap);
        }

        var oldValue = innerMap.put(key4, value);
        if (oldValue == null) {
            size++;
        }
        return oldValue;
    }

    /**
     * Returns the value to which the specified four keys are mapped,
     * or null if this map contains no mapping for the keys.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @param key4 the fourth key
     * @return the value mapped to the keys, or null if no mapping exists
     */
    public V get(K1 key1, K2 key2, K3 key3, K4 key4) {
        Objects.requireNonNull(key1, "First key must not be null");
        Objects.requireNonNull(key2, "Second key must not be null");
        Objects.requireNonNull(key3, "Third key must not be null");
        Objects.requireNonNull(key4, "Fourth key must not be null");

        if (!super.containsKeys(key1, key2, key3)) {
            return null;
        }

        var innerMap = super.get(key1, key2, key3);
        return innerMap != null ? innerMap.get(key4) : null;
    }

    /**
     * Returns an Optional containing the value mapped to the specified four keys,
     * or an empty Optional if no mapping exists.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @param key4 the fourth key
     * @return an Optional containing the value, or empty if no mapping exists
     */
    public Optional<V> getOptional(K1 key1, K2 key2, K3 key3, K4 key4) {
        return Optional.ofNullable(get(key1, key2, key3, key4));
    }

    /**
     * Removes the mapping for the four keys from this map if it is present.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @param key4 the fourth key
     * @return the previous value associated with the keys, or null if there was no mapping
     */
    public V remove(K1 key1, K2 key2, K3 key3, K4 key4) {
        Objects.requireNonNull(key1, "First key must not be null");
        Objects.requireNonNull(key2, "Second key must not be null");
        Objects.requireNonNull(key3, "Third key must not be null");
        Objects.requireNonNull(key4, "Fourth key must not be null");

        if (!super.containsKeys(key1, key2, key3)) {
            return null;
        }

        var innerMap = super.get(key1, key2, key3);
        if (innerMap != null) {
            var value = innerMap.remove(key4);
            if (value != null) {
                size--;
                if (innerMap.isEmpty()) {
                    super.remove(key1, key2, key3);
                }
                return value;
            }
        }
        return null;
    }

    /**
     * Validates that all four keys are non-null.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @param key4 the fourth key
     * @return true if all keys are non-null, false otherwise
     */
    protected boolean validKeys(K1 key1, K2 key2, K3 key3, K4 key4) {
        return super.validKeys(key1, key2, key3) && key4 != null;
    }

    @Override
    public int size() {
        return size;
    }
}
