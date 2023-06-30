package bank.observerables;

import java.util.ArrayList;
import java.util.List;

import bank.domain.Account;
import bank.observers.Observer;

public class AccountChangeSubject implements Subject {
    private List<Observer> observers;

    @Override
    public void registerObserver(Observer o) {

        if (observers == null) {
            observers = new ArrayList<Observer>();
        }

        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {

        if (observers == null) {
            return;
        }

        observers.remove(o);
    }

    @Override
    public void notifyObservers(Account account) {

        if (observers == null) {
            return;
        }

        for (Observer observer : observers) {
            observer.update(account);
        }
    }

}
