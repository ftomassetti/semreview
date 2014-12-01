package it.polito.softeng.common.observerpattern;

import it.polito.softeng.common.exceptions.UnknownElementException;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Federico Tomassetti <federico.tomassetti@polito.it>
 * @since 1.1.2
 */
public class ObservableWithNotifierImpl<E, N> implements
		ObservableWithNotifier<E, N> {

	private List<ObserverWithNotifier<E, N>> observers = new LinkedList<ObserverWithNotifier<E, N>>();

	@Override
	public void registerObserver(ObserverWithNotifier<E, N> newObserver) {
		observers.add(newObserver);
	}

	@Override
	public void unregisterObserver(ObserverWithNotifier<E, N> oldObserver) {
		if (!observers.contains(oldObserver)) {
			throw new UnknownElementException(oldObserver);
		}
		if (!observers.remove(oldObserver)) {
			throw new RuntimeException();
		}
	}

	@Override
	public void notifyEvent(N notifier, E event) {
		for (ObserverWithNotifier<E, N> observer : observers) {
			observer.receiveNotification(notifier, event);
		}
	}

	@Override
	public boolean isRegistered(ObserverWithNotifier<E, N> observer) {
		return observers.contains(observer);
	}

	@Override
	public List<ObserverWithNotifier<E, N>> getAllObservers() {
		List<ObserverWithNotifier<E, N>> copy = new LinkedList<ObserverWithNotifier<E,N>>();
		copy.addAll(observers);
		return copy;
	}
}
