package com.lincall.lincall4droid.sync.test;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.lincall.lincall4droid.sync.LincallCallable;
import com.lincall.lincall4droid.sync.LincallCallableConsumer;
import com.lincall.lincall4droid.sync.LincallCallableContainer;
import com.lincall.lincall4droid.sync.LincallSerialCallables;

public class ConsumerTest {
	LincallCallableConsumer consumer = LincallCallableConsumer.getInstance();
	@Before
	public void setup() {
		
		LincallCallableContainer container = new LincallSerialCallables();
		Util.addTask(container, 0, 3);
		consumer.addLincallCallables(container);
		
	}

	@Test
	public void singleaddTest() {
		LincallCallableContainer contained = consumer.getCurrentQueuedList().next();
		assertEquals(contained.getAllLincallCallables().size(), 4);
	}
	
	@Test
	public void addOverlappedDataTest() {
		LincallCallableContainer contained = consumer.getCurrentQueuedList().next();
		LincallCallableContainer container = new LincallSerialCallables();
		Util.addTask(container, 0, 4);
		consumer.addLincallCallables(container);
		assertEquals(contained.getAllLincallCallables().size(), 5);
		Iterator<LincallCallable> iterator = contained.getAllLincallCallables().iterator();
		int lastval = -1;
		while(iterator.hasNext()) {
			lastval = iterator.next().hashCode();
		}
		assertEquals(3, lastval);
	}
	
	public static void main(String[] args) {
		
	}
	
	
	



}
