package bank.factory;

import bank.dao.IAccountDAO;
import bank.observers.EmailSenderObserver;

public interface AbstractFactory {
    IAccountDAO createAccountDAO();
    EmailSenderObserver createEmailSender();
}