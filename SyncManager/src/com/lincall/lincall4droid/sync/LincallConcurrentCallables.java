//package com.lincall.lincall4droid.sync;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//public class LincallConcurrentCallables extends LincallCallables {
//	
//	private final ExecutorService mExecutorService = Executors.newCachedThreadPool();
//	private final BlockingQueue<LincallCallable> mTasks = new LinkedBlockingQueue<>();
//	private boolean mDoQuitIfAnyTaskIsFailed = false;
//	private static final long DEFAULT_TIME_LIMIT = 5000;
//	private IOnMergedListener mOnMergedListener;
//	
//	public LincallConcurrentCallables(boolean doesQuitIfAnyTaskIsFailed, long mExecutionTimeLimit, LincallCallableExecutedResult result) {
//		// TODO Auto-generated constructor stub
//		super(mExecutionTimeLimit, result);
//		this.mDoQuitIfAnyTaskIsFailed = doesQuitIfAnyTaskIsFailed;
//	}
//	
//	public LincallConcurrentCallables() {
//		// TODO Auto-generated constructor stub
//		super(DEFAULT_TIME_LIMIT, null);
//		this.mDoQuitIfAnyTaskIsFailed = true;
//	}
//	
//	public void setLincallCallableListener(ILincallCallableListener lincallExecutorListener) {
//		this.lincallCallableListener = lincallExecutorListener;
//	}
//	
//	public void setOnMergeListener(IOnMergedListener onMergedListener) {
//		this.mOnMergedListener = onMergedListener;
//	}
//
//	@Override
//	public LincallCallableExecutedResult doCall() throws Exception {
//		// TODO Auto-generated method stub
//
//        LincallCallableExecutedResult combinatedResult = new LincallCallableExecutedResult(null, false);
//        try {
//            List<FutureTaskExecutedResult> FutureResults = new ArrayList<>();
//            boolean isSuccess = true;
//
//            for(LincallCallable callable : mTasks) {
//                Future<LincallCallableExecutedResult> futureResult = mExecutorService.submit(callable);
//                FutureResults.add(new FutureTaskExecutedResult(futureResult, callable.mExecutionTimeLimit));
//            }
//
//            List<LincallCallableExecutedResult> results = new ArrayList<>();
//
//            for(FutureTaskExecutedResult futureResult : FutureResults) {
//                LincallCallableExecutedResult result = futureResult.mFutureTaskExecutedResult.get(futureResult.mExecutionTimeOut, TimeUnit.MILLISECONDS);
//                if(!result.isSuccess && mDoQuitIfAnyTaskIsFailed) {
//                    isSuccess = false;
//                    break;
//                }
//                results.add(result);
//            }
//            combinatedResult.isSuccess = isSuccess;
//            if(isSuccess && mOnMergedListener != null) combinatedResult.result = mOnMergedListener.onMerge(results);
//        } catch (Exception e) {
//            combinatedResult.isSuccess = false;
//        } finally {
//             mExecutorService.shutdownNow();
//        }
//
//		return combinatedResult;
//	}
//	
//	private class FutureTaskExecutedResult {
//		Future<LincallCallableExecutedResult> mFutureTaskExecutedResult;
//		long mExecutionTimeOut;
//		
//		public FutureTaskExecutedResult(Future<LincallCallableExecutedResult> futureResult, long timeout) {
//			// TODO Auto-generated constructor stub
//			this.mFutureTaskExecutedResult = futureResult;
//			this.mExecutionTimeOut = timeout;
//		}
//	}
//
//
//	@Override
//	public void addTask(LincallCallable executor) {
//		// TODO Auto-generated method stub
//
//		mTasks.add(executor);
//		
//	}
//
//	@Override
//	public void removeTask(LincallCallable executor) {
//		// TODO Auto-generated method stub
//		mTasks.remove(executor);
//		
//	}
//
//	@Override
//	public boolean contains(LincallCallable callable) {
//		// TODO Auto-generated method stub
//		return mTasks.contains(callable);
//	}
//
//	@Override
//	public boolean addAllTask(Collection<? extends LincallCallable> executor) {
//		// TODO Auto-generated method stub
//		synchronized (mTasks) {
//			return mTasks.addAll(executor);
//		}
//		
//	}
//
//	@Override
//	public Collection<LincallCallable> getAllTasks() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//
//
//}
