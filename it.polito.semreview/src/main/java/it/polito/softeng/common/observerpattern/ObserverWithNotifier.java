package it.polito.softeng.common.observerpattern;

/**
 * 
 * @author Federico Tomassetti
 *
 * @param <E>
 * @param <N>
 * 
 * @since 1.1.2
 */
public interface ObserverWithNotifier<E,N> {
	void receiveNotification(N notifier, E event);
}
