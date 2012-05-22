package org.thecloudforge.collections.enummaps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DoubleEnumMapTest {

	private enum Enum1 {
		A, B, C, D, E, F, G;
	}

	private enum Enum2 {
		ONE, TWO, THREE, FOUR, FIVE, SIX;
	}

	private enum Enum3 {
		ALPHA, BETA, GAMA, DELTA;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void doubleMap() throws Exception {
		final DoubleEnumMap<Enum1, Enum2, Integer> map = new DoubleEnumMap<Enum1, Enum2, Integer>();

		assertTrue(map.isEmpty());

		map.put(Enum1.A, Enum2.SIX, 899);
		map.put(Enum1.B, Enum2.FOUR, 8);
		map.put(Enum1.C, Enum2.FIVE, 80);

		assertFalse(map.isEmpty());

		assertEquals(new Integer(899), map.get(Enum1.A, Enum2.SIX));
		assertEquals(new Integer(8), map.get(Enum1.B, Enum2.FOUR));
		assertEquals(new Integer(80), map.get(Enum1.C, Enum2.FIVE));

		final DoubleEnumMap<Enum1, Enum2, Integer> map3 = new DoubleEnumMap<Enum1, Enum2, Integer>(
				map);

		assertEquals(map, map3);

		try {
			map.containsKeys(null, null);
			map.containsKeys(Enum1.A, null);
			map.containsKeys(null, Enum2.ONE);
			map.get(null, null);
			map.get(Enum1.A, null);
			map.get(null, Enum2.FIVE);
			map.put(null, null, null);
			map.put(null, Enum2.ONE, null);
			map.put(Enum1.A, null, null);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
		} catch (final ClassCastException e) {
			// TODO: handle exception
		}

		assertEquals(new Integer(899), map.remove(Enum1.A, Enum2.SIX));
		assertEquals(new Integer(8), map.remove(Enum1.B, Enum2.FOUR));
		assertEquals(new Integer(80), map.remove(Enum1.C, Enum2.FIVE));

		assertTrue(map.isEmpty());
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void tripleMap() throws Exception {
		final TripleEnumMap<Enum1, Enum2, Enum3, Integer> map = new TripleEnumMap<Enum1, Enum2, Enum3, Integer>();

		assertTrue(map.isEmpty());

		map.put(Enum1.A, Enum2.SIX, Enum3.ALPHA, 899);
		map.put(Enum1.B, Enum2.FOUR, Enum3.BETA, 8);
		map.put(Enum1.C, Enum2.FIVE, Enum3.GAMA, 80);

		assertFalse(map.isEmpty());

		assertEquals(new Integer(899), map.get(Enum1.A, Enum2.SIX, Enum3.ALPHA));
		assertEquals(new Integer(8), map.get(Enum1.B, Enum2.FOUR, Enum3.BETA));
		assertEquals(new Integer(80), map.get(Enum1.C, Enum2.FIVE, Enum3.GAMA));

		final TripleEnumMap<Enum1, Enum2, Enum3, Integer> map3 = new TripleEnumMap<Enum1, Enum2, Enum3, Integer>(
				map);

		assertEquals(map, map3);

		try {
			map.containsKeys(null, null, null);
			map.containsKeys(Enum1.A, null, null);
			map.containsKeys(null, Enum2.ONE, null);
			map.get(null, null, null);
			map.get(Enum1.A, null, null);
			map.get(null, Enum2.FIVE, null);
			map.put(null, null, null);
			map.put(null, Enum2.ONE, null);
			map.put(Enum1.A, null, null);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
		} catch (final ClassCastException e) {
			// TODO: handle exception
		}

		assertEquals(new Integer(899),
				map.remove(Enum1.A, Enum2.SIX, Enum3.ALPHA));
		assertEquals(new Integer(8),
				map.remove(Enum1.B, Enum2.FOUR, Enum3.BETA));
		assertEquals(new Integer(80),
				map.remove(Enum1.C, Enum2.FIVE, Enum3.GAMA));

		assertTrue(map.isEmpty());
	}

	@Test
	public void testClass() throws Exception {

		DoubleEnumMap<Enum1, Enum2, Integer> doubleEnumMap = new DoubleEnumMap<Enum1, Enum2, Integer>();
	}
}
