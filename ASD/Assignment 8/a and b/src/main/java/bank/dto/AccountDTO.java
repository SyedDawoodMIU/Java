package bank.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bank.domain.AccountEntry;

public class AccountDTO {
    private long accountNumber;
    private List<AccountEntryDTO> entryList = new ArrayList<>();
    private CustomerDTO customer;

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<AccountEntryDTO> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<AccountEntryDTO> entryList) {
        this.entryList = entryList;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public double getBalance() {
        double balance = 0;
        for (AccountEntryDTO entry : entryList) {
            balance += entry.getAmount();
        }
        return balance;
    }
}
