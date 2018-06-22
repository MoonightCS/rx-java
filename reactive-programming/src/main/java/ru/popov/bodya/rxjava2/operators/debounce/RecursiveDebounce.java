package ru.popov.bodya.rxjava2.operators.debounce;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class RecursiveDebounce {

    public static void main(String[] args) {
        new RecursiveDebounce().timedDebounce(createUpstream()).blockingSubscribe(System.out::println);
    }

    private static Observable<Long> createUpstream() {
        return Observable.interval(99, TimeUnit.MILLISECONDS);
    }

    private Observable<Long> timedDebounce(Observable<Long> upstream) {
        Observable<Long> onTimeout = upstream
                .take(1)
                .concatWith(Observable.defer(() -> timedDebounce(upstream)));
        return upstream
                .debounce(100, TimeUnit.MILLISECONDS)
                .timeout(1, TimeUnit.SECONDS, onTimeout);
    }
}
