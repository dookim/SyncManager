package com.lincall.lincall4droid.sync;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class LincallSerialCallables extends LincallCallableContainer {

    private List<LincallCallable> mTasks = new LinkedList<>();
    private Set<LincallCallable> mTasksSet = new HashSet<>();
    private boolean mDoQuitIfAnyTaskIsFailed = false;
    private final ExecutorService mCallableExecutionService = Executors.newSingleThreadExecutor();
    private static final long DEFAULT_TIME_LIMIT = 5000;
    private IOnMergedListener mOnMergedListener;

    public LincallSerialCallables(boolean doesQuitIfAnyTaskIsFailed,
                                  long mExecutionTimeLimit) {
        super(mExecutionTimeLimit);
        this.mDoQuitIfAnyTaskIsFailed = doesQuitIfAnyTaskIsFailed;
    }

    public LincallSerialCallables(long mExecutionTimeLimit) {
        super(mExecutionTimeLimit);
        this.mDoQuitIfAnyTaskIsFailed = true;
    }

    public void setOnMergedListener(IOnMergedListener onMergedListener) {
        this.mOnMergedListener = onMergedListener;
    }

    public LincallSerialCallables() {
        super(DEFAULT_TIME_LIMIT);
        this.mDoQuitIfAnyTaskIsFailed = true;

    }

    public void setLincallCallableListener(
            ILincallCallableListener lincallExecutorListener) {
        this.mLincallCallableListener = lincallExecutorListener;
    }

    @Override
    public void addTask(LincallCallable callable) {
        mTasks.add(callable);
        mTasksSet.add(callable);
    }

    @Override
    public void removeTask(LincallCallable executor) {
        mTasks.remove(executor);
        mTasksSet.remove(executor);

    }

    @Override
    public LincallCallableExecutedResult doCall() throws Exception {
        LincallCallableExecutedResult combinatedResult = new LincallCallableExecutedResult(null, false);
        try {
            results = new HashMap<>();
            boolean isSuccess = true;
            Iterator<LincallCallable> iterator = mTasks.iterator();
            while (iterator.hasNext()) {
                LincallCallable callable = iterator.next();
                Future<LincallCallableExecutedResult> futureResult = mCallableExecutionService.submit(callable);
                LincallCallableExecutedResult result = futureResult.get(callable.mExecutionTimeLimit, TimeUnit.MILLISECONDS);
                if (mDoQuitIfAnyTaskIsFailed && !result.isSuccess) {
                    isSuccess = result.isSuccess;
                    break;
                } else {
                    results.put(callable.getType(), result);
                }
            }
            combinatedResult.isSuccess = isSuccess;
            if (isSuccess && mOnMergedListener != null)
                combinatedResult.result = mOnMergedListener.onMerge(results);
        } finally {
            mCallableExecutionService.shutdownNow();
        }
        return combinatedResult;

    }

    @Override
    public boolean contains(LincallCallable callable) {
        return mTasksSet.contains(callable);
    }

    @Override
    public Collection<LincallCallable> getAllLincallCallables() {
        return mTasks;
    }

    private void addLincallCallableIfNotExist(LincallCallable callable) {
        if (!mTasksSet.contains(callable)) {
            mTasksSet.add(callable);
            mTasks.add(mTasks.size() - 1, callable);
        }
    }

    @Override
    public boolean addAllLincallCallables(Collection<? extends LincallCallable> executor) {
        for (LincallCallable callable : executor) {
            addLincallCallableIfNotExist(callable);
        }
        return true;
    }

    @Override
    public int getType() {
        int ret = 0;
        for (LincallCallable callable : mTasks) {
            ret |= callable.getType();
        }
        return ret;
    }

}
