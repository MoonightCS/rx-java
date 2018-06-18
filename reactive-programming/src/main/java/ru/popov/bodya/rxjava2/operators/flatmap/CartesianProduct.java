package ru.popov.bodya.rxjava2.operators.flatmap;

import io.reactivex.Observable;

public class CartesianProduct {

    public static void main(String[] args) {
        cartesianProductExample();
    }

    private static void cartesianProductExample() {

        final Observable<Integer> oneToEight = Observable.range(1, 8);

        final Observable<String> ranks = oneToEight
                .map(Object::toString);

        final Observable<String> symbols = oneToEight
                .map(x -> 'a' + x - 1)
                .map(ascii -> (char) ascii.intValue())
                .map(ch -> Character.toString(ch));

        final Observable<String> cartesianProduct = symbols
                .flatMap(symbol -> ranks.map(rank -> symbol + rank));

        cartesianProduct
                .subscribe(System.out::println);

    }
}
