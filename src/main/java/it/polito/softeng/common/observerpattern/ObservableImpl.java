package it.polito.softeng.common.observerpattern;

import it.polito.softeng.common.exceptions.UnknownElementException;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Federico Tomassetti
 * @since 1.0.5
 */
public class ObservableImpl<E> implements Observable<E> {
	
	private List<Observer<E>> observers = new LinkedList<Observer<E>>();

	@Override
	public void registerObserver(Observer<E> newObserver) {
		observers.add(newObserver);
	}

	@Override
	public void unregisterObserver(Observer<E> oldObserver) {
		if (!observers.contains(oldObserver)){
			throw new UnknownElementException(oldObserver);
		}
		if (!observers.remove(oldObserver)){
			throw new RuntimeException();
		}
	}

	@Override
	public void notifyEvent(E event) {
		for (Observer<E> observer : observers){
			observer.receiveNotification(event);
		}
	}

	@Override
	public List<Observer<E>> getAllObservers() {
		List<Observer<E>> copy = new LinkedList<Observer<E>>();
		copy.addAll(observers);
		return copy;
	}

	@Override
	public boolean isRegistered(Observer<E> observer) {
		return observers.contains(observer);
	}

}
