package bank.observerables;

import bank.domain.Account;
import bank.observers.Observer;

public interface Subject {
    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObservers(Account account);

}
