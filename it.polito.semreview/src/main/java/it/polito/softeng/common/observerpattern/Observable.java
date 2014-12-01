package it.polito.softeng.common.observerpattern;

import java.util.List;

/**
 * 
 * @author Federico Tomassetti <federico.tomassetti@polito.it>
 * @since 1.0.5
 */
public interface Observable<E> {
	void registerObserver(Observer<E> newObserver);
	void unregisterObserver(Observer<E> oldObserver);
	void notifyEvent(E event);
	List<Observer<E>> getAllObservers();
	boolean isRegistered(Observer<E> observer);
}
