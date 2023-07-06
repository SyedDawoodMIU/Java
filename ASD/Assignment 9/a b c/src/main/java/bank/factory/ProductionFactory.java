package bank.factory;

import bank.dao.AccountDAO;
import bank.dao.IAccountDAO;
import bank.observers.EmailSenderObserver;

public class ProductionFactory implements AbstractFactory {
    @Override
    public IAccountDAO createAccountDAO() {
        return new AccountDAO();
    }

    @Override
    public EmailSenderObserver createEmailSender() {
        return EmailSenderObserver.getInstance();
    }
}