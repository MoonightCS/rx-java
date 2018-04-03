package ru.popov.bodya.rxjava2;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.popov.bodya.utils.Logger;

public class LogObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        Logger.log("onSubscribe");
    }

    @Override
    public void onNext(T value) {
        Logger.log("onNext with value: " + value);
    }

    @Override
    public void onError(Throwable e) {
        Logger.log("onError");
    }

    @Override
    public void onComplete() {
        Logger.log("onComplete");
    }
}
