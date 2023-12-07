package org.example.b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("test get name", "SweBank",  SweBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals("test get currency", "SEK" ,  SweBank.getCurrency().getName());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		Nordea.openAccount("John");  //Error account was not added
		assertEquals("test open account", "John" ,  Nordea.getAccountList().get("John").getName());
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		DanskeBank.deposit("Gertrud", new Money(10000, DKK)); //Error NullPointerException when depositing in existing account
		DanskeBank.deposit("Gertrud", new Money(20000, DKK));
		assertEquals("test deposit addition", Integer.valueOf(30000), DanskeBank.getBalance("Gertrud"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		Nordea.deposit("Bob", new Money(10000, SEK));
		Nordea.withdraw("Bob", new Money(5000, SEK));
		assertEquals("test withdraw", Integer.valueOf(5000), Nordea.getBalance("Bob")); //Error Expected: 5000, Actual: 15000
		Nordea.withdraw("Bob", new Money(5000, SEK));
		assertEquals("test withdraw", Integer.valueOf(0), Nordea.getBalance("Bob"));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals("test Get balance of Ulrika", Integer.valueOf(0), SweBank.getBalance("Ulrika"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		Nordea.deposit("Bob", new Money(10000, SEK));
		Nordea.transfer("Bob", SweBank,  "Ulrika", new Money(10000, SEK));
		assertEquals("test transfer sender account balance", Integer.valueOf(0), Nordea.getBalance("Bob"));  //Error Expected: 0, Actual: 10000
		assertEquals("test transfer receiver account balance", Integer.valueOf(10000), SweBank.getBalance("Ulrika"));
		SweBank.transfer("Ulrika", "Bob", new Money(15000, SEK));
		assertEquals("test transfer sender account balance", Integer.valueOf(10000), SweBank.getBalance("Ulrika"));  //Error Expected: 10000, Actual: 25000
		assertEquals("test transfer receiver account balance", Integer.valueOf(0), SweBank.getBalance("Bob"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Bob", "1", 8, 10, new Money(200, SEK), SweBank, "Ulrika");
		assertTrue("test add timed payment", SweBank.getAccountList().get("Bob").timedPaymentExists("1"));
		SweBank.removeTimedPayment("Bob", "1");
		assertFalse("test remove timed payment", SweBank.getAccountList().get("Bob").timedPaymentExists("1"));
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		SweBank.addTimedPayment("Ulrika", "2", 0, 1, new Money(100, SEK), SweBank, "Bob");
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		assertEquals("Test: check balance after timedPayment", Integer.valueOf(800), SweBank.getBalance("Ulrika")); //Error Expected: 800, Actual: 500
		assertEquals("Test: check balance after timedPayment", Integer.valueOf(200), SweBank.getBalance("Bob"));
	}
}
