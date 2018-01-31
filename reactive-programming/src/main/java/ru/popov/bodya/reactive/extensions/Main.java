package ru.popov.bodya.reactive.extensions;

import rx.Observable;
import rx.Subscriber;

public class Main {

    public static void main(String[] args) {

        Observable<String> first = Observable.unsafeCreate(subscriber -> new Thread(() -> {
            System.out.println("first create on thread: " + Thread.currentThread().getName());
            subscriberEvents(subscriber);
        }).start());

        Observable<String> second = Observable.unsafeCreate(subscriber -> new Thread(() -> {
            System.out.println("second create on thread: " + Thread.currentThread().getName());
            subscriberEvents(subscriber);
        }).start());

        Observable<String> third = Observable.unsafeCreate(subscriber -> new Thread(() -> {
            System.out.println("third create on thread: " + Thread.currentThread().getName());
            subscriberEvents(subscriber);
        }).start());

        Observable.merge(first, second, third)
                .filter(s -> s.matches(".*[0-3].*"))
                .forEach(s -> System.out.println(s + " forEach on: " + Thread.currentThread().getName()));

        System.out.println("main end");
    }

    private static void subscriberEvents(Subscriber<? super String> subscriber) {

        for (int i = 0; i < 5; i++) {
            subscriber.onNext("onNext" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
        subscriber.onCompleted();
    }

}
