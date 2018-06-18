package ru.popov.bodya.rxjava2.operators.combine.latest;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class CombineLatestExample {

    public static void main(String[] args) {
        combineLatestValues();
    }

    private static void combineLatestValues() {
        Observable.combineLatest(
                Observable.interval(10, TimeUnit.MILLISECONDS).map(x -> "F" + x),
                Observable.interval(17, TimeUnit.MILLISECONDS).map(x -> "S" + x),
                (first, second) -> first + ":" + second
        ).blockingSubscribe(System.out::println);
    }


}
