package com.lincall.lincall4droid.sync;

public final class LincallCallableExecutedResult {

	public volatile Object result;
	public volatile boolean isSuccess;
	
	public LincallCallableExecutedResult(Object obj, boolean isSuccess) {
		// TODO Auto-generated constructor stub
		this.result = obj;
		this.isSuccess = isSuccess;
	}

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public LincallCallableExecutedResult clone() {
        return new LincallCallableExecutedResult(this.result, this.isSuccess);
    }
}
