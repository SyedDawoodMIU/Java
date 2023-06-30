package bank.observers;

import bank.domain.Account;

public class EmailSenderObserver implements Observer {

    @Override
    public void update(Account account) {
        System.out.println("Email sent");
    }

}
