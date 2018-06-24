package ru.popov.bodya.rxjava2.findbugs;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static ru.popov.bodya.reactive.extensions.utils.Logger.log;

public class ObservableVersion {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        getFinalStatus()
                .subscribe(status -> {
                    log("final status is" + status);
                    latch.countDown();
                });
        latch.await();
        log("main ends");
    }

    private static Observable<Status> getFinalStatus() {
        return Observable
                .zip(getFirstStatus(), getSecondStatus(), (status, status2) -> {
                    log("zip values");
                    return new Status(status.isEnabled() && status2.isEnabled());
                })
                .doOnSubscribe(disposable -> log("onSubscribe with Final Status"));
    }

    private static Observable<Status> getFirstStatus() {
        return Observable
                .just(new Status(getValue()))
                .doOnNext(status -> log("onNext with First"))
                .doOnSubscribe(disposable -> log("onSubscribe with First"))
                .subscribeOn(Schedulers.computation());
    }

    private static Observable<Status> getSecondStatus() {
        return Observable
                .just(new Status(getValue()))
                .doOnNext(status -> log("onNext with Second"))
                .doOnSubscribe(disposable -> log("onSubscribe with Second"))
                .subscribeOn(Schedulers.computation());
    }

    private static boolean getValue() {
        return new Random().nextBoolean();
    }
}
