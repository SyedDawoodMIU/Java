package bank.observers;

import bank.domain.Account;

public class MockEmailSenderObserver extends EmailSenderObserver {

    private static MockEmailSenderObserver instance = null;

    private MockEmailSenderObserver() {
    }

    public static MockEmailSenderObserver getInstance() {
        if (instance == null) {
            synchronized (MockEmailSenderObserver.class) {
                if (instance == null) {
                    instance = new MockEmailSenderObserver();
                }
            }
        }
        return instance;
    }

    @Override
    public void update(Account account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
