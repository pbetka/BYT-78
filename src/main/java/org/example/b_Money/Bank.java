package org.example.b_Money;

import java.util.Hashtable;

public class Bank {
	private Hashtable<String, Account> accountList = new Hashtable<String, Account>(); //Proper name accountList
	private String name;
	private Currency currency;
	
	/**
	 * New Bank
	 * @param name Name of this bank
	 * @param currency Base currency of this bank (If this is a Swedish bank, this might be a currency class representing SEK)
	 */
	Bank(String name, Currency currency) {
		this.name = name;
		this.currency = currency;
	}
	
	/**
	 * Get the name of this bank
	 * @return Name of this bank
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the Currency of this bank
	 * @return The Currency of this bank
	 */
	public Currency getCurrency() {
		return currency;
	}

//	for testing
	public Hashtable<String, Account> getAccountList() {
		return accountList;
	}

	/**
	 * Open an account at this bank.
	 * @param accountid The ID of the account
	 * @throws AccountExistsException If the account already exists
	 */



	public void openAccount(String accountid) throws AccountExistsException {
		if (accountList.containsKey(accountid)) {
			throw new AccountExistsException();
		}
		else {
			//accountlist.get(accountid);  //Error openAccount should add account to accountList
			accountList.put(accountid, new Account(accountid, currency));
		}
	}
	
	/**
	 * Deposit money to an account
	 * @param accountid Account to deposit to
	 * @param money Money to deposit.
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	public void deposit(String accountid, Money money) throws AccountDoesNotExistException {
		if (accountList.containsKey(accountid)) {
//			throw new AccountDoesNotExistException();  //Error invert logic
			Account account = accountList.get(accountid);
			account.deposit(money);
		}
		else {
//			Account account = accountList.get(accountid);  //Error invert logic
//			account.deposit(money);
			throw new AccountDoesNotExistException();
		}
	}
	
	/**
	 * Withdraw money from an account
	 * @param accountid Account to withdraw from
	 * @param money Money to withdraw
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	public void withdraw(String accountid, Money money) throws AccountDoesNotExistException {
		if (!accountList.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		}
		else {
			Account account = accountList.get(accountid);
//			account.deposit(money); //Error should use withdraw instead of deposit
			account.withdraw(money);
		}
	}
	
	/**
	 * Get the balance of an account
	 * @param accountid Account to get balance from
	 * @return Balance of the account
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	public Integer getBalance(String accountid) throws AccountDoesNotExistException {
		if (!accountList.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		}
		else {
			return accountList.get(accountid).getBalance();
		}
	}

	/**
	 * Transfer money between two accounts
	 * @param fromaccount Id of account to deduct from in this Bank
	 * @param tobank Bank where receiving account resides
	 * @param toaccount Id of receiving account
	 * @param amount Amount of Money to transfer
	 * @throws AccountDoesNotExistException If one of the accounts do not exist
	 */
	public void transfer(String fromaccount, Bank tobank, String toaccount, Money amount) throws AccountDoesNotExistException {
		if (!accountList.containsKey(fromaccount) || !tobank.accountList.containsKey(toaccount)) {
			throw new AccountDoesNotExistException();
		}
//		Error if sender has not enough money sender will get transferred money
//		else {
//			accountList.get(fromaccount).withdraw(amount);
//			tobank.accountList.get(toaccount).deposit(amount);
//		}
		else if (accountList.get(fromaccount).getBalance() >= amount.getAmount()) {
			accountList.get(fromaccount).withdraw(amount);
			tobank.accountList.get(toaccount).deposit(amount);
		}
		else {
			System.err.println("Sender has not enough money to transfer");
		}
	}

	/**
	 * Transfer money between two accounts on the same bank
	 * @param fromaccount Id of account to deduct from
	 * @param toaccount Id of receiving account
	 * @param amount Amount of Money to transfer
	 * @throws AccountDoesNotExistException If one of the accounts do not exist
	 */
	public void transfer(String fromaccount, String toaccount, Money amount) throws AccountDoesNotExistException {
		transfer(fromaccount, this, fromaccount, amount);
	}

	/**
	 * Add a timed payment
	 * @param accountid Id of account to deduct from in this Bank
	 * @param payid Id of timed payment
	 * @param interval Number of ticks between payments
	 * @param next Number of ticks till first payment
	 * @param amount Amount of Money to transfer each payment
	 * @param tobank Bank where receiving account resides
	 * @param toaccount Id of receiving account
	 */
	public void addTimedPayment(String accountid, String payid, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) {
		Account account = accountList.get(accountid);
		account.addTimedPayment(payid, interval, next, amount, tobank, toaccount);
	}
	
	/**
	 * Remove a timed payment
	 * @param accountid Id of account to remove timed payment from
	 * @param id Id of timed payment
	 */
	public void removeTimedPayment(String accountid, String id) {
		Account account = accountList.get(accountid);
		account.removeTimedPayment(id);
	}
	
	/**
	 * A time unit passes in the system
	 */
	public void tick() throws AccountDoesNotExistException {
		for (Account account : accountList.values()) {
			account.tick();
		}
	}	
}
