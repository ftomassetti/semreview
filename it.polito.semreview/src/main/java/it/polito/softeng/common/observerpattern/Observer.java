package it.polito.softeng.common.observerpattern;

/**
 * 
 * @author Federico Tomassetti <federico.tomassetti@polito.it>
 * @since 1.0.5
 */
public interface Observer<E> {
	void receiveNotification(E event);
}
