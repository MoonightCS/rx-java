package ru.popov.bodya.rxjava2.operators.zip;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static ru.popov.bodya.reactive.extensions.utils.Logger.log;

public class SingleZip {

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
                .zip(getGoogleStatus(), getSamsungStatus(), (status, status2) -> {
                    log("zip values");
                    return new Status(status.isEnabled && status2.isEnabled);
                })
                .doOnSubscribe(disposable -> log("onSubscribe with Final Status"))
                .doOnNext(status -> log("on next with final status: " + status));
    }

    private static Observable<Status> getGoogleStatus() {
        return Observable
                .just(new Status(getValue()))
                .doOnNext(status -> log("onSuccess with Google"))
                .doOnSubscribe(disposable -> log("onSubscribe with Google"))
                .subscribeOn(Schedulers.computation());
    }

    private static Observable<Status> getSamsungStatus() {
        return Observable
                .just(new Status(getValue()))
                .doOnNext(status -> log("onSuccess with Samsung"))
                .doOnSubscribe(disposable -> log("onSubscribe with Samsung"))
                .subscribeOn(Schedulers.computation());
    }

    private static boolean getValue() {
        return new Random().nextBoolean();
    }

    private static final class Status {
        private final boolean isEnabled;

        private Status(boolean isEnabled) {
            this.isEnabled = isEnabled;
        }

        @Override
        public String toString() {
            return "Status{" +
                    "isEnabled=" + isEnabled +
                    '}';
        }
    }

}
