package org.thecloudforge.collections.enummaps;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;

/**
 * A specialized map implementation that uses three enum keys for efficient value lookup.
 * This extends DoubleEnumMap by adding a third dimension.
 *
 * @param <K1> the type of the first enum key
 * @param <K2> the type of the second enum key
 * @param <K3> the type of the third enum key
 * @param <V> the type of mapped values
 */
public class TripleEnumMap<K1 extends Enum<K1>, K2 extends Enum<K2>, K3 extends Enum<K3>, V>
        extends DoubleEnumMap<K1, K2, EnumMap<K3, V>> {

    private transient int size = 0;

    /**
     * Creates an empty TripleEnumMap.
     */
    public TripleEnumMap() {
    }

    /**
     * Creates a new TripleEnumMap with the same mappings as the specified map.
     *
     * @param other the map whose mappings are to be copied
     */
    public TripleEnumMap(TripleEnumMap<K1, K2, K3, V> other) {
        super(other);
        this.size = other.size;
    }

    /**
     * Returns true if this map contains a mapping for the specified three keys.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @return true if this map contains a mapping for the keys
     */
    public boolean containsKeys(K1 key1, K2 key2, K3 key3) {
        if (!super.containsKeys(key1, key2)) {
            return false;
        }

        if (key3 == null) {
            return false;
        }

        var innerMap = super.get(key1, key2);
        return innerMap != null && innerMap.containsKey(key3);
    }

    /**
     * Associates the specified value with the three specified keys in this map.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @param value the value to be associated with the keys
     * @return the previous value associated with the keys, or null if there was no mapping
     */
    public V put(K1 key1, K2 key2, K3 key3, V value) {
        Objects.requireNonNull(key1, "First key must not be null");
        Objects.requireNonNull(key2, "Second key must not be null");
        Objects.requireNonNull(key3, "Third key must not be null");

        var innerMap = super.get(key1, key2);
        if (innerMap == null) {
            innerMap = new EnumMap<>(key3.getDeclaringClass());
            super.put(key1, key2, innerMap);
        }

        var oldValue = innerMap.put(key3, value);
        if (oldValue == null) {
            size++;
        }
        return oldValue;
    }

    /**
     * Validates that all three keys are non-null.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @return true if all keys are non-null, false otherwise
     */
    protected boolean validKeys(K1 key1, K2 key2, K3 key3) {
        return super.validKeys(key1, key2) && key3 != null;
    }

    /**
     * Returns the value to which the specified three keys are mapped,
     * or null if this map contains no mapping for the keys.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @return the value mapped to the keys, or null if no mapping exists
     */
    public V get(K1 key1, K2 key2, K3 key3) {
        Objects.requireNonNull(key1, "First key must not be null");
        Objects.requireNonNull(key2, "Second key must not be null");
        Objects.requireNonNull(key3, "Third key must not be null");

        if (!super.containsKeys(key1, key2)) {
            return null;
        }
        var innerMap = super.get(key1, key2);
        return innerMap != null ? innerMap.get(key3) : null;
    }

    /**
     * Returns an Optional containing the value mapped to the specified three keys,
     * or an empty Optional if no mapping exists.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @return an Optional containing the value, or empty if no mapping exists
     */
    public Optional<V> getOptional(K1 key1, K2 key2, K3 key3) {
        return Optional.ofNullable(get(key1, key2, key3));
    }

    /**
     * Removes the mapping for the three keys from this map if it is present.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @return the previous value associated with the keys, or null if there was no mapping
     */
    public V remove(K1 key1, K2 key2, K3 key3) {
        Objects.requireNonNull(key1, "First key must not be null");
        Objects.requireNonNull(key2, "Second key must not be null");
        Objects.requireNonNull(key3, "Third key must not be null");

        var innerMap = super.get(key1, key2);
        if (innerMap != null) {
            var value = innerMap.remove(key3);
            if (value != null) {
                size--;
                if (innerMap.isEmpty()) {
                    super.remove(key1, key2);
                }
                return value;
            }
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }
}
