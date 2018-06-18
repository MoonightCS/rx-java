package ru.popov.bodya.rxjava2.creation;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import ru.popov.bodya.reactive.extensions.utils.Logger;

public class BasicCreationExample {

    public static void main(String[] args) {

        Observable<String> first = Observable.create(observableEmitter -> new Thread(() -> {
            Logger.log("onSubscribe first action");
            subscriberEvents(observableEmitter);
        }).start());

        Observable<String> second = Observable.create(e -> new Thread(() -> {
            Logger.log("onSubscribe second action");
            subscriberEvents(e);
        }).start());

        Observable
                .merge(first, second)
                .doOnNext(s -> Logger.log("doOnNext before merge: " + s))
                .filter(s -> s.matches(".*onNext[0-4].*"))
                .forEach(s -> Logger.log("onNext on merge: " + s));

        System.out.println("main end");
    }


    private static void subscriberEvents(ObservableEmitter<? super String> observableEmitter) {
        for (int i = 0; i < 6; i++) {
            observableEmitter.onNext("onNext" + i);
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        observableEmitter.onComplete();
    }


}
