package org.thecloudforge.collections.enummaps;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class DoubleEnumMap<K1 extends Enum<K1>, K2 extends Enum<K2>, V>
		implements DoubleMap<K1, K2, V> {

	private EnumMap<K2, EnumSet<K1>> keySet1;
	private EnumMap<K1, EnumSet<K2>> keySet2;

	private transient int size = 0;

	private EnumMap<K1, EnumMap<K2, V>> m;

	public DoubleEnumMap() {

	}

	public DoubleEnumMap(final DoubleEnumMap<K1, K2, V> m) {
		this.size = m.size();
		this.keySet1 = m.keySet1.clone();
		this.keySet2 = m.keySet2.clone();
		this.m = m.m;
	}

	public boolean containsKeys(final K1 key1, final K2 key2) {

		if (!this.validKeys(key1, key2))
			return false;

		if (this.m == null)
			return false;

		if (!this.m.containsKey(key1))
			return false;

		return this.m.get(key1).containsKey(key2);
	}

	public boolean containsValue(final Object value) {

		if (this.m == null)
			return false;

		if (this.m.isEmpty())
			return false;

		for (final EnumMap<K2, V> entry : this.m.values()) {
			for (final V v : entry.values()) {
				if (v.equals(value))
					return true;
			}
		}
		return false;
	}

	public V get(final K1 key1, final K2 key2) {

		if (!this.validKeys(key1, key2))
			throw new IllegalArgumentException("Invalid keys");

		if (this.m == null)
			return null;

		if (!this.m.containsKey(key1))
			return null;
		return this.m.get(key1).get(key2);
	}

	public V put(final K1 key1, final K2 key2, final V value) {

		if (!this.validKeys(key1, key2))
			throw new IllegalArgumentException("Invalid keys");

		if (this.m == null) {
			this.m = new EnumMap<K1, EnumMap<K2, V>>(key1.getDeclaringClass());
		}

		EnumMap<K2, V> map2;
		if (!this.m.containsKey(key1)) {
			map2 = new EnumMap<K2, V>(key2.getDeclaringClass());
			this.m.put(key1, map2);
		} else {
			map2 = this.m.get(key1);
		}
		final V oldVal = map2.put(key2, value);
		if (oldVal == null) {
			this.size++;
		}
		this.addToKeySets(key1, key2);

		return oldVal;
	}

	protected boolean validKeys(final K1 key1, final K2 key2) {
		return key1 != null && key2 != null;
	}

	private void addToKeySets(final K1 key1, final K2 key2) {

		this.initKeySets(key1, key2);
		this.keySet1.get(key2).add(key1);
		this.keySet2.get(key1).add(key2);
	}

	public V remove(final K1 key1, final K2 key2) {

		if (!this.validKeys(key1, key2))
			throw new IllegalArgumentException("Invalid keys");

		if (this.m == null)
			return null;

		final EnumMap<K2, V> enumMap = this.m.get(key1);
		if (enumMap != null) {
			final V removed = enumMap.remove(key2);
			if (removed != null) {
				this.size--;
				if (enumMap.isEmpty()) {
					this.m.remove(key1);
				}
				this.removeFromKeySets(key1, key2);
				return removed;
			}
		}
		return null;
	}

	private void removeFromKeySets(final K1 key1, final K2 key2) {
		this.initKeySets(key1, key2);
		this.keySet1.get(key2).remove(key1);
		this.keySet2.get(key1).remove(key2);
	}

	private void initKeySets(final K1 key1, final K2 key2) {
		final Class<K2> key2type = key2.getDeclaringClass();
		final Class<K1> key1type = key1.getDeclaringClass();
		if (this.keySet1 == null || this.keySet2 == null) {
			this.keySet1 = new EnumMap<K2, EnumSet<K1>>(key2type);
			this.keySet2 = new EnumMap<K1, EnumSet<K2>>(key1type);
			for (final K1 k : EnumSet.allOf(key1type)) {
				this.keySet2.put(k, EnumSet.noneOf(key2type));
			}

			for (final K2 l : EnumSet.allOf(key2type)) {
				this.keySet1.put(l, EnumSet.noneOf(key1type));
			}
		}
	}

	public int size() {
		return this.size;
	}

	public Set<K1> keySet1(final K2 l) {

		if (this.keySet1 == null)
			return new HashSet<K1>();

		return this.keySet1.get(l);
	}

	public Set<K2> keySet2(final K1 k) {

		if (this.keySet2 == null)
			return new HashSet<K2>();
		return this.keySet2.get(k);
	}

	public void putAll(
			final DoubleMap<? extends K1, ? extends K2, ? extends V> m) {

	}

	public boolean isEmpty() {

		if (this.m == null)
			return true;

		return this.m.isEmpty();
	}

	public void clear() {

		if (this.m != null) {
			this.m.clear();
		}
	}

	@Override
	public int hashCode() {
		return this.m == null ? 0 : this.m.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final DoubleEnumMap other = (DoubleEnumMap) obj;
		if (!(this.m == null ? other.m == null : this.m.equals(other.m)))
			return false;
		return true;
	}
}
