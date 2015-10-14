package com.lincall.lincall4droid.sync;

import java.util.concurrent.Callable;

public abstract class LincallCallable implements Callable<LincallCallableExecutedResult> {

    protected final long mExecutionTimeLimit;
    protected ILincallCallableListener mLincallCallableListener;

    public enum LincallCallableType {
        LOCK_SYNC(1),
        UNLOCK_SYNC(2),
        CARD_SYNC_UP(3),
        CARD_SYNC_DOWN(4),
        MEMBER_SYNC_DOWN(5),
        MEMBER_SYNC_UP(6),
        BELONG_SYNC_DOWN(7),
        BELONG_SYNC_UP(8),
        FOLLOW_META_SYNC_DOWN(9),
        FOLLOW_META_SYNC_UP(10),
        GROUP_SYNC_DOWN(11),
        GROUP_SYNC_UP(12),
        GRP_CREATE_GROUP_ENTITY(13),
        GRP_CREATE_BELONG_ENTITY(14);

        private int mType;

        LincallCallableType(int mType) {
            this.mType = mType;
        }

        public int getType() {
            return mType;
        }
    }

    public void setILincallCallableListener(ILincallCallableListener lincallCallableListener) {
        this.mLincallCallableListener = lincallCallableListener;
    }

    public LincallCallable(long timeLimit) {
        this.mExecutionTimeLimit = timeLimit;
    }

    public abstract LincallCallableExecutedResult doCall() throws Exception;

    public abstract int getType();

    @Override
    public int hashCode() {
        int typeVal = getType();
        typeVal ^= (typeVal >>> 20) ^ (typeVal >>> 12);
        return typeVal ^ (typeVal >>> 7) ^ (typeVal >>> 4);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof LincallCallable) && obj.hashCode() == hashCode();
    }

    @Override
    public LincallCallableExecutedResult call() throws Exception {
        if (mLincallCallableListener != null) mLincallCallableListener.onStart(this);
        LincallCallableExecutedResult result = null;
        try {
            result = doCall();
        } catch (Exception e) {
            result.isSuccess = false;
            if (mLincallCallableListener != null) mLincallCallableListener.onError(this, e);
            throw e;
        }
        if (mLincallCallableListener != null && result.isSuccess)
            mLincallCallableListener.onSuccess(this, result);
        if (mLincallCallableListener != null && !result.isSuccess)
            mLincallCallableListener.onFail(this);
        return result;
    }
}
