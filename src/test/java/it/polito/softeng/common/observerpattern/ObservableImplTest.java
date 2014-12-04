package it.polito.softeng.common.observerpattern;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertSame;

public class ObservableImplTest {

	private class MockObserver implements Observer<String> {

		private List<String> events = new LinkedList<String>();
		
		@Override
		public void receiveNotification(String event) {
			events.add(event);
		}

		public String[] getEvents() {
			return events.toArray(new String[]{});
		}
		
	}
	
	@Test
	public void testRegisterObserver() {
		ObservableImpl<String> o = new ObservableImpl<String>();
		MockObserver m1 = new MockObserver();
		o.registerObserver(m1);
		assertSame(0,m1.getEvents().length);
		o.notifyEvent("abc");
		assertSame(1,m1.getEvents().length);
		assertSame("abc",m1.getEvents()[0]);
	}

	@Test
	public void testUnregisterObserver() {
		ObservableImpl<String> o = new ObservableImpl<String>();
		MockObserver m1 = new MockObserver();
		o.registerObserver(m1);
		assertSame(0,m1.getEvents().length);
		o.notifyEvent("abc");
		assertSame(1,m1.getEvents().length);
		assertSame("abc",m1.getEvents()[0]);
		o.unregisterObserver(m1);
		assertSame(1,m1.getEvents().length);
	}

}
