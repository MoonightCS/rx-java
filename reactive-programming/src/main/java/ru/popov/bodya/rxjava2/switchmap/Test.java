package ru.popov.bodya.rxjava2.switchmap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    private static final AtomicInteger atomic = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
                .switchMap(atomic -> Observable.fromCallable(() -> atomic * 2).delay(1, TimeUnit.SECONDS))
                .blockingSubscribe(System.out::println);


        final Disposable disposable = Observable.fromIterable(Arrays.asList(1, 2, 3, 4, 5))
                .doOnNext(System.out::println)
                .flatMap((Function<Integer, ObservableSource<?>>) integer -> Observable.fromCallable(() -> {
                    TimeUnit.SECONDS.sleep(delayWith(integer));
                    return integer;
                }).subscribeOn(Schedulers.io()))

                .doOnNext(System.out::println)

                .switchMap(s -> Observable.just(s + "x")
                        .delay(1500, TimeUnit.MILLISECONDS))
                .subscribe(System.out::println);

        Thread.sleep(60000);
        disposable.dispose();

    }

    private static void testGenerics(List<? super Number> list) {
        List<Number> numberList = new ArrayList<>();
        numberList.add(5);
        list.add(numberList.get(0));
        System.out.println("all is good");
    }

    private static long delayWith(int value) {
        System.out.println("delayWith: " + value);
        return atomic.getAndIncrement();
    }

}