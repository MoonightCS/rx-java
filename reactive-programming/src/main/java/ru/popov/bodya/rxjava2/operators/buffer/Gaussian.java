package ru.popov.bodya.rxjava2.operators.buffer;

import io.reactivex.Observable;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Gaussian {

    public static void main(String[] args) {
        new Gaussian().movingAverage();
    }

    private void movingAverage() {
        final Random random = new Random();
        Observable
                .defer(() -> Observable.just(random.nextGaussian()))
                .repeat(1000)
                .buffer(100, 1)
                .map(this::averageOfList)
                .subscribe(System.out::println);
    }

    private double averageOfList(List<Double> list) {
        return list
                .stream()
                .collect(Collectors.averagingDouble(x -> x));
    }
}
