package com.lincall.lincall4droid.sync.test;

import com.lincall.lincall4droid.sync.LincallCallable;
import com.lincall.lincall4droid.sync.LincallCallableExecutedResult;
import com.lincall.lincall4droid.sync.LincallCallableContainer;

public class Util {
	public static void addTask(LincallCallableContainer callables, int from, int to) {
		for(int i = from; i <= to; ++i) {
			final int temp = i; 
			callables.addTask(new LincallCallable(3000) {
				@Override
				public LincallCallableExecutedResult doCall() throws Exception {
					// TODO Auto-generated method stub
					String result = "executed : " + temp;
					System.out.println(result);
					return new LincallCallableExecutedResult(result, true);
				}

				@Override
				public int hashCode() {
					// TODO Auto-generated method stub
					return temp;
				}
			});
		}
	}

}
