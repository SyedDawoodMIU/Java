package bank.observers;

import bank.domain.Account;

public class HistoryObserver implements Observer {

    @Override
    public void update(Account account) {
        System.out.println("History Logged");
    }

}
