package it.polito.softeng.common.observerpattern;

public class ComposedObservable<E> extends ObservableImpl<E> implements Observer<E>{

	public ComposedObservable(){
		
	}
	
	@Override
	public void receiveNotification(E event) {
		notifyEvent(event);
	}
	
	public void listenTo(Observable<E> observable){
		observable.registerObserver(this);
	}
	
}
