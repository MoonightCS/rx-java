package ru.popov.bodya.rxjava2.findbugs;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static ru.popov.bodya.reactive.extensions.utils.Logger.log;

public class SingleVersion {

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

    private static Single<Status> getFinalStatus() {
        return Single
                .zip(getFirstStatus(), getSecondStatus(), (status, status2) -> {
                    log("zip values");
                    return new Status(status.isEnabled() && status2.isEnabled());
                })
                .doOnSubscribe(disposable -> log("onSubscribe with Final Status"));
    }

    private static Single<Status> getFirstStatus() {
        return Single
                .just(new Status(getValue()))
                .doOnSuccess(status -> log("onSuccess with First"))
                .doOnSubscribe(disposable -> log("onSubscribe with First"))
                .subscribeOn(Schedulers.computation());
    }

    private static Single<Status> getSecondStatus() {
        return Single
                .just(new Status(getValue()))
                .doOnSuccess(status -> log("onSuccess with Second"))
                .doOnSubscribe(disposable -> log("onSubscribe with Second"))
                .subscribeOn(Schedulers.computation());
    }

    private static boolean getValue() {
        return new Random().nextBoolean();
    }

}
