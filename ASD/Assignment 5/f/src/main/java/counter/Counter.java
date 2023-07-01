package counter;

import java.util.ArrayList;
import java.util.List;

public class Counter implements Subject {

	private int count;
	private List<Observer> observers;
	private CounterState state;

	public Counter() {
		count = 0;
		state = new SingleDigitState(this);
		observers = new ArrayList<Observer>();
	}

	public int getValue() {
		return count;
	}

	public void increment() {
		count = state.increment();
		state = state.checkState();
		notifyObservers();
	}

	public void decrement() {
		count = state.decrement();
		state = state.checkState();
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
