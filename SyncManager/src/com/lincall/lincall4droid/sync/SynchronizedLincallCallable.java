package com.lincall.lincall4droid.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by dookim on 10/2/15.
 */
public abstract class SynchronizedLincallCallable extends LincallCallable {
    private final CountDownLatch mCountDownLatch;

    public SynchronizedLincallCallable(long timeLimit, int countDown) {
        super(timeLimit);
        mCountDownLatch = new CountDownLatch(countDown);
    }

    public SynchronizedLincallCallable(long timeLimit) {
        this(timeLimit, 1);
    }

    public abstract LincallCallableExecutedResult callAndWaitUntilDone() throws Exception;

    public void done() {
        mCountDownLatch.countDown();
    }

    @Override
    public LincallCallableExecutedResult doCall() throws Exception {
        LincallCallableExecutedResult result = callAndWaitUntilDone();
        mCountDownLatch.await(mExecutionTimeLimit, TimeUnit.MILLISECONDS);
        //만약 반환되고 나서 result의 issuccess값이 바뀌는 경우 무의미하다.(깊은 복사를 사용해서 반환하기 때문)
        return result.clone();
    }
}
