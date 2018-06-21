package ru.popov.bodya.interoperability;

import io.reactivex.Observable;
import ru.popov.bodya.reactive.extensions.utils.Logger;

import java.util.concurrent.CompletableFuture;

public class CompletableRxAdapter {

    public <T> Observable<T> observe(CompletableFuture<T> future) {
        return Observable.create(emitter -> future.whenComplete((value, exception) -> {
            if (exception != null) {
                emitter.onError(exception);
            } else {
                Logger.log("observe on thread :" + Thread.currentThread().getName());
                emitter.onNext(value);
                emitter.onComplete();
            }
        }));
    }


}
