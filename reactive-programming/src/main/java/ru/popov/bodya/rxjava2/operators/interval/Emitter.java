package ru.popov.bodya.rxjava2.operators.interval;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class Emitter {

    public static void main(String[] args) {
        emitWithInterval(10);
    }

    private static void emitWithInterval(long period) {
        Observable
                .interval(period, TimeUnit.SECONDS)
                .blockingSubscribe(aLong -> System.out.println("long value: " + aLong + " on thread: " + Thread.currentThread().getName()));
    }

}
