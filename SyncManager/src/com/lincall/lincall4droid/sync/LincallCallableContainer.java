package com.lincall.lincall4droid.sync;

import java.util.Collection;
import java.util.Map;

public abstract class LincallCallableContainer extends LincallCallable {

    public LincallCallableContainer(long timeLimit) {
        super(timeLimit);
    }

    public Map<Integer, LincallCallableExecutedResult> results;

    public abstract void addTask(LincallCallable executor);

    public abstract boolean addAllLincallCallables(Collection<? extends LincallCallable> executor);

    public abstract void removeTask(LincallCallable executor);

    public abstract boolean contains(LincallCallable callable);

    public abstract Collection<LincallCallable> getAllLincallCallables();

    interface IOnMergedListener {
        public Object onMerge(Map<Integer, LincallCallableExecutedResult> results);
    }
}
