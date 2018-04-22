package ru.popov.bodya.rxjava2.operators.zip;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

import java.util.concurrent.TimeUnit;

public class SameFrequencyEmitter {

    public static void main(String[] args) {
        emitWithSameFreq();
    }

    private static void emitWithSameFreq() {

        Observable<Long> red = Observable.interval(10, TimeUnit.MILLISECONDS);
        Observable<Long> green = Observable.interval(10, TimeUnit.MILLISECONDS);

        Observable
                .zip(red.timestamp(), green.timestamp(), (redLongTimed, greenLongTimed) -> redLongTimed.time() - greenLongTimed.time())
                .blockingSubscribe(System.out::println);
    }

}
