package ru.popov.bodya.rxjava2.operators.debounce;

import io.reactivex.Observable;

import java.math.BigDecimal;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class StockExchange {

    public static void main(String[] args) {
        final StockExchange stockExchange = new StockExchange();
        stockExchange
                .getDebouncedPrice()
                .blockingSubscribe(System.out::println);

    }

    private Observable<BigDecimal> getDebouncedPrice() {
        return getPrice()
                .debounce(bigDecimal -> {
                    final boolean goodPrice = bigDecimal.compareTo(BigDecimal.valueOf(150)) > 0;
                    return Observable
                            .empty()
                            .delay(goodPrice ? 10 : 100, MILLISECONDS);
                });
    }

    private Observable<BigDecimal> getPrice() {
        return Observable
                .interval(50, MILLISECONDS)
                .flatMap(this::randomDelay)
                .map(this::randomStockPrice)
                .map(BigDecimal::valueOf);
    }

    private Observable<Long> randomDelay(long x) {
        return Observable
                .just(x)
                .delay((long) (Math.random() * 100), MILLISECONDS);
    }

    private double randomStockPrice(long x) {
        return 100 + Math.random() * 10 + (Math.sin(x / 100.0)) * 60.0;
    }

}
