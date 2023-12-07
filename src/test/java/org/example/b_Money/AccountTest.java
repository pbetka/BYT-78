package org.example.b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1", 10, 10, new Money(100, SEK), SweBank, "Alice");
		testAccount.addTimedPayment("1", 10, 10, new Money(100, SEK), SweBank, "Alice");
		assertTrue("test timedPayment already exists", testAccount.timedPaymentExists("1"));
		testAccount.removeTimedPayment("1");
		assertFalse("test removed payment does not exist", testAccount.timedPaymentExists("1"));
		testAccount.removeTimedPayment("1");
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("1", Integer.valueOf(2), Integer.valueOf(0), new Money(10000, SEK), SweBank, "Alice");

		for (int i = 0; i < 10; i++) {
			testAccount.tick();
		}


		assertEquals("timed payment account balance", Integer.valueOf(9960000), testAccount.getBalance());
	}

	@Test
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(5000000, SEK));
		assertEquals("test withdraw Hans account balance", Integer.valueOf(5000000), testAccount.getBalance());
		testAccount.withdraw(new Money(990000000, SEK));
		assertEquals("test withdraw Hans account balance not enough money", Integer.valueOf(5000000), testAccount.getBalance());  //Error Expected: 5000000, Actual: -985000000
	}
	
	@Test
	public void testGetBalance() {

		assertEquals("test get balance", Integer.valueOf(10000000), testAccount.getBalance()); //Expected: 10000000, Actual: 100000 SEK
	}
}
