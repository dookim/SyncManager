package com.lincall.lincall4droid.sync.test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.lincall.lincall4droid.sync.LincallCallableConsumer;
import com.lincall.lincall4droid.sync.LincallCallableContainer;
import com.lincall.lincall4droid.sync.LincallSerialCallables;

public class Main {
	
	public static Set<Integer> produced = new HashSet<>();
	public static Set<Integer> consumed = new HashSet<>();
	
	public static void main(String[] args) {
		LincallCallableConsumer consumer = LincallCallableConsumer.getInstance();
		consumer.startConsuming();
		new Producer(consumer).start();		
	}
	
	static class Producer extends Thread {
		
		LincallCallableConsumer consumer;
		Random random = new Random();
		
		public Producer(LincallCallableConsumer consumer) {
			// TODO Auto-generated constructor stub
			this.consumer = consumer;
		}
		
		@Override
		public void run() {
			for(int i = 0; i < 10000; ++i) {
				System.out.println("e");
				int randVal = random.nextInt(10000);
				LincallCallableContainer added = new LincallSerialCallables();
				Util.addTask(added, randVal, randVal);
				consumer.addLincallCallables(added);
				produced.add(randVal);
			}
			
		}
	}

}
