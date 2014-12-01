package it.polito.softeng.common.observerpattern;

import java.util.List;

/**
 * 
 * @author Federico Tomassetti <federico.tomassetti@polito.it>
 * @since 1.1.2
 */
public interface ObservableWithNotifier<E,N> {
	void registerObserver(ObserverWithNotifier<E, N> newObserver);
	void unregisterObserver(ObserverWithNotifier<E, N> oldObserver);
	void notifyEvent(N notifier, E event);
	List<ObserverWithNotifier<E,N>> getAllObservers();
	boolean isRegistered(ObserverWithNotifier<E, N> observer);
}
