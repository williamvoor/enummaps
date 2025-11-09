package org.thecloudforge.collections.enummaps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleEnumMapTest {

    private enum Enum1 {
        A, B, C, D, E, F, G
    }

    private enum Enum2 {
        ONE, TWO, THREE, FOUR, FIVE, SIX
    }

    private enum Enum3 {
        ALPHA, BETA, GAMMA, DELTA
    }

    @Test
    void doubleMapBasicOperations() {
        var map = new DoubleEnumMap<Enum1, Enum2, Integer>();

        assertTrue(map.isEmpty());
        assertEquals(0, map.size());

        map.put(Enum1.A, Enum2.SIX, 899);
        map.put(Enum1.B, Enum2.FOUR, 8);
        map.put(Enum1.C, Enum2.FIVE, 80);

        assertFalse(map.isEmpty());
        assertEquals(3, map.size());

        assertEquals(899, map.get(Enum1.A, Enum2.SIX));
        assertEquals(8, map.get(Enum1.B, Enum2.FOUR));
        assertEquals(80, map.get(Enum1.C, Enum2.FIVE));

        // Test Optional API
        assertTrue(map.getOptional(Enum1.A, Enum2.SIX).isPresent());
        assertEquals(899, map.getOptional(Enum1.A, Enum2.SIX).orElse(0));
        assertTrue(map.getOptional(Enum1.D, Enum2.ONE).isEmpty());
    }

    @Test
    void doubleMapCopyConstructor() {
        var map = new DoubleEnumMap<Enum1, Enum2, Integer>();
        map.put(Enum1.A, Enum2.SIX, 899);
        map.put(Enum1.B, Enum2.FOUR, 8);
        map.put(Enum1.C, Enum2.FIVE, 80);

        var map2 = new DoubleEnumMap<>(map);
        assertEquals(map, map2);
        assertEquals(map.size(), map2.size());
    }

    @Test
    void doubleMapNullKeyValidation() {
        var map = new DoubleEnumMap<Enum1, Enum2, Integer>();

        assertThrows(NullPointerException.class, () ->
            map.get(null, null));
        assertThrows(NullPointerException.class, () ->
            map.get(Enum1.A, null));
        assertThrows(NullPointerException.class, () ->
            map.get(null, Enum2.ONE));
        assertThrows(NullPointerException.class, () ->
            map.put(null, null, null));
        assertThrows(NullPointerException.class, () ->
            map.put(null, Enum2.ONE, null));
        assertThrows(NullPointerException.class, () ->
            map.put(Enum1.A, null, null));
    }

    @Test
    void doubleMapRemoval() {
        var map = new DoubleEnumMap<Enum1, Enum2, Integer>();
        map.put(Enum1.A, Enum2.SIX, 899);
        map.put(Enum1.B, Enum2.FOUR, 8);
        map.put(Enum1.C, Enum2.FIVE, 80);

        assertEquals(3, map.size());

        assertEquals(899, map.remove(Enum1.A, Enum2.SIX));
        assertEquals(2, map.size());

        assertEquals(8, map.remove(Enum1.B, Enum2.FOUR));
        assertEquals(1, map.size());

        assertEquals(80, map.remove(Enum1.C, Enum2.FIVE));
        assertEquals(0, map.size());

        assertTrue(map.isEmpty());
    }

    @Test
    void doubleMapContainsValue() {
        var map = new DoubleEnumMap<Enum1, Enum2, Integer>();
        map.put(Enum1.A, Enum2.SIX, 899);
        map.put(Enum1.B, Enum2.FOUR, 8);

        assertTrue(map.containsValue(899));
        assertTrue(map.containsValue(8));
        assertFalse(map.containsValue(100));
    }

    @Test
    void tripleMapBasicOperations() {
        var map = new TripleEnumMap<Enum1, Enum2, Enum3, Integer>();

        assertTrue(map.isEmpty());

        map.put(Enum1.A, Enum2.SIX, Enum3.ALPHA, 899);
        map.put(Enum1.B, Enum2.FOUR, Enum3.BETA, 8);
        map.put(Enum1.C, Enum2.FIVE, Enum3.GAMMA, 80);

        assertFalse(map.isEmpty());

        assertEquals(899, map.get(Enum1.A, Enum2.SIX, Enum3.ALPHA));
        assertEquals(8, map.get(Enum1.B, Enum2.FOUR, Enum3.BETA));
        assertEquals(80, map.get(Enum1.C, Enum2.FIVE, Enum3.GAMMA));

        // Test Optional API
        assertTrue(map.getOptional(Enum1.A, Enum2.SIX, Enum3.ALPHA).isPresent());
        assertEquals(899, map.getOptional(Enum1.A, Enum2.SIX, Enum3.ALPHA).orElse(0));
        assertTrue(map.getOptional(Enum1.D, Enum2.ONE, Enum3.DELTA).isEmpty());
    }

    @Test
    void tripleMapCopyConstructor() {
        var map = new TripleEnumMap<Enum1, Enum2, Enum3, Integer>();
        map.put(Enum1.A, Enum2.SIX, Enum3.ALPHA, 899);
        map.put(Enum1.B, Enum2.FOUR, Enum3.BETA, 8);
        map.put(Enum1.C, Enum2.FIVE, Enum3.GAMMA, 80);

        var map2 = new TripleEnumMap<>(map);
        assertEquals(map, map2);
    }

    @Test
    void tripleMapNullKeyValidation() {
        var map = new TripleEnumMap<Enum1, Enum2, Enum3, Integer>();

        assertThrows(NullPointerException.class, () ->
            map.get(null, null, null));
        assertThrows(NullPointerException.class, () ->
            map.get(Enum1.A, null, null));
        assertThrows(NullPointerException.class, () ->
            map.get(null, Enum2.FIVE, null));
        assertThrows(NullPointerException.class, () ->
            map.put(null, null, null, null));
        assertThrows(NullPointerException.class, () ->
            map.put(null, Enum2.ONE, null, null));
        assertThrows(NullPointerException.class, () ->
            map.put(Enum1.A, null, null, null));
    }

    @Test
    void tripleMapRemoval() {
        var map = new TripleEnumMap<Enum1, Enum2, Enum3, Integer>();
        map.put(Enum1.A, Enum2.SIX, Enum3.ALPHA, 899);
        map.put(Enum1.B, Enum2.FOUR, Enum3.BETA, 8);
        map.put(Enum1.C, Enum2.FIVE, Enum3.GAMMA, 80);

        assertEquals(899, map.remove(Enum1.A, Enum2.SIX, Enum3.ALPHA));
        assertEquals(8, map.remove(Enum1.B, Enum2.FOUR, Enum3.BETA));
        assertEquals(80, map.remove(Enum1.C, Enum2.FIVE, Enum3.GAMMA));

        assertTrue(map.isEmpty());
    }

    @Test
    void quadMapBasicOperations() {
        var map = new QuadEnumMap<Enum1, Enum2, Enum3, Enum3, Integer>();

        assertTrue(map.isEmpty());

        map.put(Enum1.A, Enum2.SIX, Enum3.ALPHA, Enum3.BETA, 899);
        map.put(Enum1.B, Enum2.FOUR, Enum3.BETA, Enum3.GAMMA, 8);
        map.put(Enum1.C, Enum2.FIVE, Enum3.GAMMA, Enum3.DELTA, 80);

        assertFalse(map.isEmpty());

        assertEquals(899, map.get(Enum1.A, Enum2.SIX, Enum3.ALPHA, Enum3.BETA));
        assertEquals(8, map.get(Enum1.B, Enum2.FOUR, Enum3.BETA, Enum3.GAMMA));
        assertEquals(80, map.get(Enum1.C, Enum2.FIVE, Enum3.GAMMA, Enum3.DELTA));

        // Test Optional API
        assertTrue(map.getOptional(Enum1.A, Enum2.SIX, Enum3.ALPHA, Enum3.BETA).isPresent());
        assertEquals(899, map.getOptional(Enum1.A, Enum2.SIX, Enum3.ALPHA, Enum3.BETA).orElse(0));
    }

    @Test
    void quadMapNullKeyValidation() {
        var map = new QuadEnumMap<Enum1, Enum2, Enum3, Enum3, Integer>();

        assertThrows(NullPointerException.class, () ->
            map.get(null, null, null, null));
        assertThrows(NullPointerException.class, () ->
            map.put(null, null, null, null, null));
    }

    @Test
    void quadMapRemoval() {
        var map = new QuadEnumMap<Enum1, Enum2, Enum3, Enum3, Integer>();
        map.put(Enum1.A, Enum2.SIX, Enum3.ALPHA, Enum3.BETA, 899);
        map.put(Enum1.B, Enum2.FOUR, Enum3.BETA, Enum3.GAMMA, 8);
        map.put(Enum1.C, Enum2.FIVE, Enum3.GAMMA, Enum3.DELTA, 80);

        assertEquals(899, map.remove(Enum1.A, Enum2.SIX, Enum3.ALPHA, Enum3.BETA));
        assertEquals(8, map.remove(Enum1.B, Enum2.FOUR, Enum3.BETA, Enum3.GAMMA));
        assertEquals(80, map.remove(Enum1.C, Enum2.FIVE, Enum3.GAMMA, Enum3.DELTA));

        assertTrue(map.isEmpty());
    }
}
