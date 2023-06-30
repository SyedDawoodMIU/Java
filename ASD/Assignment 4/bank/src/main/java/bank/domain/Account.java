package bank.domain;

import java.util.*;

import bank.observerables.AccountChangeSubject;
import bank.observerables.NewAccountSubject;
import bank.observers.Observer;

public class Account extends AccountChangeSubject {
	long accountnumber;
	Collection<AccountEntry> entryList = new ArrayList<AccountEntry>();
	Customer customer;
	NewAccountSubject newAccountSubject;

	public Account(long accountnr) {
		this.accountnumber = accountnr;
		newAccountSubject = new NewAccountSubject();
	}

	public long getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(long accountnumber) {
		this.accountnumber = accountnumber;
	}

	public double getBalance() {
		double balance = 0;
		for (AccountEntry entry : entryList) {
			balance += entry.getAmount();
		}
		return balance;
	}

	public void deposit(double amount) {
		AccountEntry entry = new AccountEntry(new Date(), amount, "deposit", "", "");
		entryList.add(entry);
	}

	public void withdraw(double amount) {
		AccountEntry entry = new AccountEntry(new Date(), -amount, "withdraw", "", "");
		entryList.add(entry);
	}

	private void addEntry(AccountEntry entry) {
		entryList.add(entry);
	}

	public void transferFunds(Account toAccount, double amount, String description) {
		AccountEntry fromEntry = new AccountEntry(new Date(), -amount, description, "" + toAccount.getAccountnumber(),
				toAccount.getCustomer().getName());
		AccountEntry toEntry = new AccountEntry(new Date(), amount, description, "" + toAccount.getAccountnumber(),
				toAccount.getCustomer().getName());
		entryList.add(fromEntry);
		toAccount.addEntry(toEntry);

	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Collection<AccountEntry> getEntryList() {
		return entryList;
	}

	public void registerBalanceObserver(Observer o) {
		super.registerObserver(o);

	}

	public void removeBalanceObserver(Observer o) {
		super.removeObserver(o);

	}

	public void notifyBalanceObservers() {

		super.notifyObservers(this);
	}

	public void registerNewAccountObserver(Observer o) {
		newAccountSubject.registerObserver(o);
	}

	public void removeNewAccountObserver(Observer o) {
		newAccountSubject.removeObserver(o);
	}

	public void notifyNewAccountObservers() {
		newAccountSubject.notifyObservers(this);
	}

}
