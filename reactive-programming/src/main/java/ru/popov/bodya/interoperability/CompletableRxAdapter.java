package ru.popov.bodya.interoperability;

import io.reactivex.Observable;

import java.util.concurrent.CompletableFuture;

import static ru.popov.bodya.reactive.extensions.utils.Logger.log;

public class CompletableRxAdapter {

    public <T> Observable<T> observe(CompletableFuture<T> future) {
        log("observe before initialization");
        return Observable.create(emitter -> future.whenComplete((value, exception) -> {
            if (exception != null) {
                emitter.onError(exception);
            } else {
                log("observe on thread :" + Thread.currentThread().getName());
                emitter.onNext(value);
                emitter.onComplete();
            }
        }));
    }
}
