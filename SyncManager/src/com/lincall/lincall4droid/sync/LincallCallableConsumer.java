package com.lincall.lincall4droid.sync;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LincallCallableConsumer {

    private final BlockingQueue<LincallCallableContainer> mQueue;
    private final Thread mConsumerThread;
    private ConsumeRunnable mConsumeRunnable;
    private static LincallCallableConsumer mLincallCallableConsumer;

    public static LincallCallableConsumer getInstance() {
        if (mLincallCallableConsumer == null) {
            mLincallCallableConsumer = new LincallCallableConsumer(new LincallCallablesBlockingQueue(new LinkedBlockingQueue<LincallCallableContainer>()));
        }
        return mLincallCallableConsumer;
    }

    private LincallCallableConsumer(BlockingQueue<LincallCallableContainer> queue) {
        // TODO Auto-generated constructor stub
        this.mQueue = queue;
        this.mConsumeRunnable = new ConsumeRunnable();
        this.mConsumerThread = new Thread(mConsumeRunnable);
    }

    public void startConsuming() {
        if (!mConsumeRunnable.isConsuming) {
            mConsumerThread.start();
        }
    }

    public void stopConsuming() {
        if (mConsumeRunnable.isConsuming) {
            mConsumerThread.interrupt();
        }
    }

    public void addLincallCallables(LincallCallableContainer callables) {
        mQueue.add(callables);
    }

    public Iterator<LincallCallableContainer> getCurrentQueuedList() {
        return mQueue.iterator();
    }

    class ConsumeRunnable implements Runnable {

        public volatile boolean isConsuming;

        public ConsumeRunnable() {
            // TODO Auto-generated constructor stub
            this.isConsuming = false;
        }

        @Override
        public void run() {
            isConsuming = true;
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    LincallCallable taken = mQueue.take();
                    taken.call();
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
