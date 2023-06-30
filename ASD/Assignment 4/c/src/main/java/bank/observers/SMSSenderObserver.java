package bank.observers;

import bank.domain.Account;

public class SMSSenderObserver implements Observer {

    @Override
    public void update(Account account) {
        System.out.println("SMS sent");
    }

}
