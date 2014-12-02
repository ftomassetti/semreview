package it.polito.softeng.common.observerpattern;

import it.polito.softeng.common.exceptions.DuplicateInsertionException;
import it.polito.softeng.common.exceptions.UnknownElementException;

import java.util.HashMap;
import java.util.Map;

public class ComposedObservableWithNotifier<E, N> extends ObservableWithNotifierImpl<E, N>{

	private Map<ObservableWithNotifier<E, N>,ObserverWithNotifier<E,N>> items = new HashMap<ObservableWithNotifier<E, N>,ObserverWithNotifier<E,N>>();
	
	public void add(ObservableWithNotifier<E, N> item){
		if (items.containsKey(item)){
			throw new DuplicateInsertionException(item);
		}
		ObserverWithNotifier<E,N> obs = new ObserverWithNotifier<E, N>() {

			@Override
			public void receiveNotification(N notifier, E event) {
				ComposedObservableWithNotifier.this.notifyEvent(notifier, event);
			}
		};
		item.registerObserver(obs);
		items.put(item, obs);
	}
	
	public void remove(ObservableWithNotifier<E, N> item){
		if (!items.containsKey(item)){
			throw new UnknownElementException(item);
		}
		item.unregisterObserver(items.get(item));
		items.remove(item);
	}
}
