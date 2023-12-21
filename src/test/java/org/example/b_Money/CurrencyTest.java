package org.example.b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("test get name", "SEK", SEK.getName());
		assertEquals("test get name", "DKK", DKK.getName());
		assertEquals("test get name", "EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals("test get rate", 0.15, SEK.getRate(), 0.0001);
		assertEquals("test get rate", 0.20, DKK.getRate(), 0.0001);
		assertEquals("test get rate", 1.5, EUR.getRate(), 0.0001);
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(0.17);
		DKK.setRate(0.17);
		EUR.setRate(0.17);
		assertEquals("test set rate", 0.17, SEK.getRate(), 0.0001);
		assertEquals("test set rate", 0.17, DKK.getRate(), 0.0001);
		assertEquals("test set rate", 0.17, EUR.getRate(), 0.0001);
	}
	
	@Test
	public void testGlobalValue() {
		//test calculating universal / global value for 3 currencies
		assertEquals("test global value", 15, SEK.universalValue(100), 0.0001);
		assertEquals("test global value", 20, DKK.universalValue(100), 0.0001);
		assertEquals("test global value", 150, EUR.universalValue(100), 0.0001);
	}
	
	@Test
	public void testValueInThisCurrency() {
		//test calculating converting value from one to another currency
		assertEquals("test value in this currency", (int) (40 / 0.15), SEK.valueInThisCurrency(200, DKK), 0.0001);
		assertEquals("test value in this currency", (int) (600 / 0.2), DKK.valueInThisCurrency(400, EUR), 0.0001);
	}

}
