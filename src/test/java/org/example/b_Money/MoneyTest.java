package org.example.b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals("test get amount", 10000, SEK100.getAmount(), 0);
		assertEquals("test get amount zero", Integer.valueOf(0), EUR0.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals("test get currency", EUR, EUR20.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("test toString zero", "0 EUR", EUR0.toString());
		assertEquals("test toString", "10 EUR", EUR10.toString());
		assertEquals("test toString negative", "-100 SEK", SEKn100.toString());
	}

	@Test
	public void testUniversalValue() {
		assertEquals("test Universal Value SEK", Integer.valueOf(1500), SEK100.universalValue());
		assertEquals("test Universal Value EUR", Integer.valueOf(3000), EUR20.universalValue());
		assertEquals("test Universal Value SEK negative", Integer.valueOf(-1500), SEKn100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertEquals("test equals true", true, EUR10.equals(SEK100));
		assertEquals("test equals false", false, EUR20.equals(SEK100));
	}

	@Test
	public void testAdd() {
		assertEquals("test add SEK100 + SEKn100", "0 SEK", SEK100.add(SEKn100).toString());
		assertEquals("test add SEK100 + EUR10", "200 SEK", SEK100.add(EUR10).toString());
	}

	@Test
	public void testSub() {
		assertEquals("test sub negative", "-100 SEK", SEK100.sub(SEK200).toString());
		assertEquals("test sub zero", "0 SEK", SEK100.sub(EUR10).toString());
	}

	@Test
	public void testIsZero() {
		assertEquals("test is zero true", true, SEK0.isZero());
		assertEquals("test is zero false", false, SEK100.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals("test negate", "-100 SEK", SEK100.negate().toString());
		assertEquals("test negate negative", "100 SEK", SEKn100.negate().toString());
	}

	@Test
	public void testCompareTo() {
		assertEquals("test compare equal", 0, SEK100.compareTo(SEK100));
		assertEquals("test compare less", -1, SEK100.compareTo(EUR20));
		assertEquals("test compare more", +1, SEK200.compareTo(SEKn100));
	}
}
