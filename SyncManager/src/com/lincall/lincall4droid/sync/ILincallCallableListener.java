package com.lincall.lincall4droid.sync;

public interface ILincallCallableListener {
    /**
     * Callable 작업 시작시 호출
     *
     * @param executor
     */
    void onStart(LincallCallable executor);

    /**
     * Callable 동작이 모두 성공시
     *
     * @param executor
     * @param result
     */
    void onSuccess(LincallCallable executor, LincallCallableExecutedResult result);

    /**
     * 정상적으로 실패한 경우
     *
     * @param executor
     */
    void onFail(LincallCallable executor);

    /**
     * 예외 발생시
     *
     * @param executor
     * @param e
     */
    void onError(LincallCallable executor, Exception e);
}
