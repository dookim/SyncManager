package com.lincall.lincall4droid.sync.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.lincall.lincall4droid.sync.LincallCallableExecutedResult;
import com.lincall.lincall4droid.sync.LincallableExecutor;
import com.lincall.lincall4droid.sync.SynchronizedLincallCallable;

import static org.junit.Assert.*;

public class SynchronizedCallableTest {
	@Test
    public void timerTest() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<LincallCallableExecutedResult> futureResult = executorService.submit(new SynchronizedLincallCallable(3000) {
            final LincallCallableExecutedResult mResult  = new LincallCallableExecutedResult(null, false);
            @Override
            public LincallCallableExecutedResult callAndWaitUntilDone() throws Exception {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                            mResult.setSuccess(true);
                            done();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        
                    }
                }.start();
                return mResult;
            }
			@Override
			public int getType() {
				// TODO Auto-generated method stub
				return 0;
			}
        });
        
        assertEquals(true, futureResult.get().isSuccess);    
        
    }
	
	@Test
    public void timeoutTest() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<LincallCallableExecutedResult> futureResult = executorService.submit(new SynchronizedLincallCallable(1000) {
            final LincallCallableExecutedResult mResult  = new LincallCallableExecutedResult(null, false);
            @Override
            public LincallCallableExecutedResult callAndWaitUntilDone() throws Exception {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                            mResult.setSuccess(true);
                            done();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        
                    }
                }.start();
                return mResult;
            }
			@Override
			public int getType() {
				// TODO Auto-generated method stub
				return 0;
			}
        });
        
        assertEquals(false, futureResult.get().isSuccess);    
        
    }
	
	static int I = 0;
	static int J = 0;
	

	

}
