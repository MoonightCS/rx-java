package ru.popov.bodya.rxjava2.operators.interval;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class Emitter {

    public static void main(String[] args) {
        emitWithInterval(1);
    }

    private static void emitWithInterval(long period) {

        Observable
                .interval(period, TimeUnit.SECONDS)
                .subscribe(aLong -> System.out.println("long value: " + aLong + " on thread: " + Thread.currentThread().getName()));

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
