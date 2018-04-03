package ru.popov.bodya.reactive.extensions.subscriber;

import ru.popov.bodya.reactive.extensions.utils.Logger;
import rx.Subscriber;

public class LogSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        Logger.log("onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Logger.log("e");
    }

    @Override
    public void onNext(T t) {
        Logger.log(t);
    }
}
