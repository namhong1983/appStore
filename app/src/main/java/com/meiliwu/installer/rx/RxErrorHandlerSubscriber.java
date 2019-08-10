package com.meiliwu.installer.rx;

import com.meiliwu.installer.entity.Result;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * @description :通过继承该观察者，实现错误交给RxErrorHandler进行处理。
 */
public abstract class RxErrorHandlerSubscriber<T> extends Subscriber<Result<T>> {
    @Override
    public void onStart() {
        //可以加载loading
        super.onStart();
    }

    @Override
    public void onCompleted() {

        //结束loading
    }

    @Override
    public void onNext(Result<T> result) {
        if (result.getCode() == 0) {
            onSuccess((T) result.getData().getData());

        } else if (result.getCode() == -1) {
            onError(result.getMessage());
        }

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            onError(e.getMessage());
        } else {
            onError(e.getMessage());
        }

    }

    public abstract void onSuccess(T data);

    public abstract void onError(String message);

}
