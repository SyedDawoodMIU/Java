package bank.factory;

import bank.dao.IAccountDAO;
import bank.dao.MockAccountDAO;
import bank.observers.EmailSenderObserver;
import bank.observers.MockEmailSenderObserver;

public class TestFactory implements AbstractFactory {
    @Override
    public IAccountDAO createAccountDAO() {
        return new MockAccountDAO();
    }

    @Override
    public EmailSenderObserver createEmailSender() {
        return MockEmailSenderObserver.getInstance();
    }
}