package ru.popov.bodya.rxjava2;

import io.reactivex.Observable;

public class Test {

    public static void main(String[] args) {
        final int maxValue = 500;
        Observable
                .range(0, maxValue)
                .takeUntil(integer -> integer.compareTo(maxValue / 2) >= 0)
                .subscribe(LogObserverFactory.createObserver());

    }
}
