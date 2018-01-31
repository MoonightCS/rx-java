package ru.popov.bodya.reactive.extensions.subscriber;

import rx.Observable;

public class SubscriberUntilExample {

    public static void main(String[] args) {
        final int maxValue = 100;
        Observable.range(0, maxValue)
                .takeUntil(integer -> integer.compareTo(maxValue / 2) >= 0)
                .subscribe(new LogSubscriber<>());
    }
}
