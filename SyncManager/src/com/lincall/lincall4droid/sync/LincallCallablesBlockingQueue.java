package com.lincall.lincall4droid.sync;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class LincallCallablesBlockingQueue implements BlockingQueue<LincallCallableContainer> {

    BlockingQueue<LincallCallableContainer> mQueue;
    LincallCallableContainer mLastLincallCallables;

    public LincallCallablesBlockingQueue(BlockingQueue<LincallCallableContainer> queue) {
        this.mQueue = queue;
        this.mLastLincallCallables = null;
    }

    @Override
    public LincallCallableContainer remove() {
        return mQueue.remove();
    }

    @Override
    public LincallCallableContainer poll() {
        return mQueue.poll();
    }

    @Override
    public LincallCallableContainer element() {
        return mQueue.element();
    }

    @Override
    public LincallCallableContainer peek() {
        return mQueue.peek();
    }

    @Override
    public int size() {
        return mQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return mQueue.isEmpty();
    }

    @Override
    public Iterator<LincallCallableContainer> iterator() {
        return mQueue.iterator();
    }

    @Override
    public Object[] toArray() {
        return mQueue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return mQueue.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return mQueue.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends LincallCallableContainer> c) {
        boolean result = true;
        for (LincallCallableContainer callables : c) {
            result = add(callables);
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return mQueue.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return mQueue.retainAll(c);
    }

    @Override
    public void clear() {
        mQueue.clear();

    }

    @Override
    public synchronized boolean add(LincallCallableContainer e) {
        //그냥 뒤에 끼워넣으면 되는건지?
        if (mQueue.size() == 0) {
            mLastLincallCallables = e;
            return mQueue.add(e);
        } else {
            return mLastLincallCallables.addAllLincallCallables(e.getAllLincallCallables());
        }
    }

    @Override
    public boolean offer(LincallCallableContainer e) {
        return mQueue.offer(e);
    }

    @Override
    public void put(LincallCallableContainer e) throws InterruptedException {
        mQueue.put(e);

    }

    @Override
    public boolean offer(LincallCallableContainer e, long timeout, TimeUnit unit)
            throws InterruptedException {
        return mQueue.offer(e, timeout, unit);
    }

    @Override
    public LincallCallableContainer take() throws InterruptedException {
        return mQueue.take();
    }

    @Override
    public LincallCallableContainer poll(long timeout, TimeUnit unit)
            throws InterruptedException {
        return mQueue.poll(timeout, unit);
    }

    @Override
    public int remainingCapacity() {
        return mQueue.remainingCapacity();
    }

    @Override
    public boolean remove(Object o) {
        return mQueue.remove(o);
    }

    @Override
    public boolean contains(Object o) {
        //o가 단인 LincallCallables인 경우
        return mQueue.contains(o);
    }

    @Override
    public int drainTo(Collection<? super LincallCallableContainer> c) {
        return mQueue.drainTo(c);
    }

    @Override
    public int drainTo(Collection<? super LincallCallableContainer> c, int maxElements) {
        return mQueue.drainTo(c, maxElements);
    }
}
