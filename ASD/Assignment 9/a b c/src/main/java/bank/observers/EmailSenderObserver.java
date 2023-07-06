package bank.observers;

import bank.domain.Account;

public class EmailSenderObserver implements Observer {

    private static EmailSenderObserver instance = null;

    protected EmailSenderObserver() {
    }

    public static EmailSenderObserver getInstance() {
        if (instance == null) {
            synchronized (EmailSenderObserver.class) {
                if (instance == null) {
                    instance = new EmailSenderObserver();
                }
            }
        }
        return instance;
    }

    @Override
    public void update(Account account) {
        System.out.println("Email sent");
    }

}
