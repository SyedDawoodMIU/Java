package counter;

import java.util.ArrayList;
import java.util.List;

public class Counter implements Subject {

	private int count = 0;
	private List<Observer> observers;

	public Counter() {
		observers = new ArrayList<Observer>();
	}

	public void increment() {
		count++;
		notifyObservers();
	}

	public void decrement() {
		count--;
		notifyObservers();
	}

	@Override
	public void addObserver(Observer observer) {

		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(count);
		}
	}

}
