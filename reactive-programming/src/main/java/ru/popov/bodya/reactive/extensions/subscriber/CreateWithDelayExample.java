package ru.popov.bodya.reactive.extensions.subscriber;


import rx.Observable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

import java.util.concurrent.TimeUnit;

public class CreateWithDelayExample {

    public static void main(String[] args) {
        Observable<Integer> bigDelayObservable = delayed(3, 10);
        Observable<Integer> smallDelayObservable = delayed(4, 3);

        Subscription bigDelaySubscription = bigDelayObservable.subscribe(integer ->
                System.out.println("bigDelay on: " + Thread.currentThread().getName() + " with value: " + integer));

        smallDelayObservable.subscribe(integer ->
                System.out.println("smallDelay on: " + Thread.currentThread().getName() + " with value: " + integer));

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                bigDelaySubscription.unsubscribe();
            } catch (InterruptedException ignored) {
                // ignored
            }
        }).start();


        System.out.println("finish on: " + Thread.currentThread().getName());
    }

    private static <T> Observable<T> delayed(final T x, final int timeout) {
        return Observable.unsafeCreate(subscriber -> {
            Runnable r = () -> {
                sleep(timeout, TimeUnit.SECONDS);
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(x);
                    subscriber.onCompleted();
                } else {
                    System.out.println("subscriber unSubscribed on: " + Thread.currentThread().getName());
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
        } catch (InterruptedException ignored) {
            // ignored
        }
    }

}
