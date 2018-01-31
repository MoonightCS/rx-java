package ru.popov.bodya.reactive.extensions.subscriber;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

public class CreateWithDelayExample {


    public static void main(String[] args) {
        Observable<Integer> bigDelayObservable = delayed(3, 10);
        Observable<Integer> smallDelayObservable = delayed(4, 3);

        bigDelayObservable.subscribe(integer ->
                System.out.println("bigDelay on: " + Thread.currentThread() + " with value: " + integer));

        smallDelayObservable.subscribe(integer ->
                System.out.println("smallDelay on: " + Thread.currentThread() + " with value: " + integer));
    }

    private static <T> Observable<T> delayed(final T x, final int timeout) {
        return Observable.unsafeCreate(subscriber -> {
            Runnable r = () -> {
                sleep(timeout, TimeUnit.SECONDS);
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(x);
                    subscriber.onCompleted();
                }
            };
            final Thread thread = new Thread(r);
            thread.start();
            subscriber.add(Subscriptions.create(thread::interrupt));
        });
    }

    private static void sleep(int timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            // ignored
        }
    }

}
