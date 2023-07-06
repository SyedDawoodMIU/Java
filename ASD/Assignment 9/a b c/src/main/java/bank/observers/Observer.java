package bank.observers;

import bank.domain.Account;

public interface Observer {
    public void update(Account account);

}
