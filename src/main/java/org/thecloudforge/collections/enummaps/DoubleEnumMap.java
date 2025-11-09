package org.thecloudforge.collections.enummaps;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * A specialized map implementation that uses two enum keys for efficient value lookup.
 * This implementation uses EnumMap internally for optimal performance with enum keys.
 *
 * @param <K1> the type of the first enum key
 * @param <K2> the type of the second enum key
 * @param <V> the type of mapped values
 */
public class DoubleEnumMap<K1 extends Enum<K1>, K2 extends Enum<K2>, V>
        implements DoubleMap<K1, K2, V> {

    private EnumMap<K2, EnumSet<K1>> keySet1;
    private EnumMap<K1, EnumSet<K2>> keySet2;
    private transient int size = 0;
    private EnumMap<K1, EnumMap<K2, V>> m;

    /**
     * Creates an empty DoubleEnumMap.
     */
    public DoubleEnumMap() {
    }

    /**
     * Creates a new DoubleEnumMap with the same mappings as the specified map.
     *
     * @param other the map whose mappings are to be copied
     */
    public DoubleEnumMap(DoubleEnumMap<K1, K2, V> other) {
        this.size = other.size();
        this.keySet1 = other.keySet1 != null ? other.keySet1.clone() : null;
        this.keySet2 = other.keySet2 != null ? other.keySet2.clone() : null;
        this.m = other.m != null ? new EnumMap<>(other.m) : null;
    }

    @Override
    public boolean containsKeys(K1 key1, K2 key2) {
        if (!validKeys(key1, key2)) {
            return false;
        }

        return m != null
            && m.containsKey(key1)
            && m.get(key1).containsKey(key2);
    }

    @Override
    public boolean containsValue(Object value) {
        if (m == null || m.isEmpty()) {
            return false;
        }

        return m.values().stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .anyMatch(v -> Objects.equals(v, value));
    }

    @Override
    public V get(K1 key1, K2 key2) {
        Objects.requireNonNull(key1, "First key must not be null");
        Objects.requireNonNull(key2, "Second key must not be null");

        if (m == null || !m.containsKey(key1)) {
            return null;
        }
        return m.get(key1).get(key2);
    }

    @Override
    public Optional<V> getOptional(K1 key1, K2 key2) {
        return Optional.ofNullable(get(key1, key2));
    }

    @Override
    public V put(K1 key1, K2 key2, V value) {
        Objects.requireNonNull(key1, "First key must not be null");
        Objects.requireNonNull(key2, "Second key must not be null");

        if (m == null) {
            m = new EnumMap<>(key1.getDeclaringClass());
        }

        var innerMap = m.computeIfAbsent(key1,
            k -> new EnumMap<>(key2.getDeclaringClass()));

        var oldValue = innerMap.put(key2, value);
        if (oldValue == null) {
            size++;
        }
        addToKeySets(key1, key2);

        return oldValue;
    }

    /**
     * Validates that the provided keys are non-null.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @return true if both keys are non-null, false otherwise
     */
    protected boolean validKeys(K1 key1, K2 key2) {
        return key1 != null && key2 != null;
    }

    private void addToKeySets(K1 key1, K2 key2) {
        initKeySets(key1, key2);
        keySet1.get(key2).add(key1);
        keySet2.get(key1).add(key2);
    }

    @Override
    public V remove(K1 key1, K2 key2) {
        Objects.requireNonNull(key1, "First key must not be null");
        Objects.requireNonNull(key2, "Second key must not be null");

        if (m == null) {
            return null;
        }

        var innerMap = m.get(key1);
        if (innerMap != null) {
            var removed = innerMap.remove(key2);
            if (removed != null) {
                size--;
                if (innerMap.isEmpty()) {
                    m.remove(key1);
                }
                removeFromKeySets(key1, key2);
                return removed;
            }
        }
        return null;
    }

    private void removeFromKeySets(K1 key1, K2 key2) {
        if (keySet1 != null && keySet2 != null) {
            keySet1.get(key2).remove(key1);
            keySet2.get(key1).remove(key2);
        }
    }

    private void initKeySets(K1 key1, K2 key2) {
        var key1Type = key1.getDeclaringClass();
        var key2Type = key2.getDeclaringClass();

        if (keySet1 == null || keySet2 == null) {
            keySet1 = new EnumMap<>(key2Type);
            keySet2 = new EnumMap<>(key1Type);

            EnumSet.allOf(key1Type).forEach(k ->
                keySet2.put(k, EnumSet.noneOf(key2Type))
            );

            EnumSet.allOf(key2Type).forEach(k ->
                keySet1.put(k, EnumSet.noneOf(key1Type))
            );
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K1> keySet1(K2 key2) {
        if (keySet1 == null || key2 == null) {
            return Collections.emptySet();
        }
        var result = keySet1.get(key2);
        return result != null ? result : Collections.emptySet();
    }

    @Override
    public Set<K2> keySet2(K1 key1) {
        if (keySet2 == null || key1 == null) {
            return Collections.emptySet();
        }
        var result = keySet2.get(key1);
        return result != null ? result : Collections.emptySet();
    }

    @Override
    public void putAll(DoubleMap<? extends K1, ? extends K2, ? extends V> other) {
        // Implementation can be added when needed
        throw new UnsupportedOperationException("putAll is not yet implemented");
    }

    @Override
    public boolean isEmpty() {
        return m == null || m.isEmpty();
    }

    @Override
    public void clear() {
        if (m != null) {
            m.clear();
        }
        if (keySet1 != null) {
            keySet1.clear();
        }
        if (keySet2 != null) {
            keySet2.clear();
        }
        size = 0;
    }

    @Override
    public int hashCode() {
        return m == null ? 0 : m.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        var other = (DoubleEnumMap<?, ?, ?>) obj;
        return Objects.equals(m, other.m);
    }

    @Override
    public String toString() {
        if (m == null || m.isEmpty()) {
            return "{}";
        }
        var sb = new StringBuilder();
        sb.append("{");
        m.forEach((k1, innerMap) ->
            innerMap.forEach((k2, v) ->
                sb.append(String.format("(%s, %s)=%s, ", k1, k2, v))
            )
        );
        // Remove trailing ", "
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }
}
