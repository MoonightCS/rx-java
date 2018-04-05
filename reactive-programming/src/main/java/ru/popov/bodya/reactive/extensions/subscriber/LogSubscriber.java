package ru.popov.bodya.reactive.extensions.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import ru.popov.bodya.reactive.extensions.utils.Logger;

public class LogSubscriber<T> implements Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        Logger.log("e");
    }

    @Override
    public void onComplete() {
        Logger.log("onComplete");
    }

    @Override
    public void onSubscribe(Subscription s) {
        Logger.log("onSubscribe");
    }

    @Override
    public void onNext(T t) {
        Logger.log(t);
    }
}
