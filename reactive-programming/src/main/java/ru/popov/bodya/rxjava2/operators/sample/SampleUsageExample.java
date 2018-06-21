package ru.popov.bodya.rxjava2.operators.sample;

import io.reactivex.Observable;
import ru.popov.bodya.reactive.extensions.utils.Logger;

import java.util.concurrent.TimeUnit;

public class SampleUsageExample {

    public static void main(String[] args) {

        Observable<String> names = Observable.just(
                "Mary", "Patricia", "Linda",
                "Barbara",
                "Elizabeth", "Jennifer", "Maria", "Susan",
                "Margaret", "Dorothy"
        );
        Observable<Long> absoluteDelayMillis = Observable.just(
                0.1, 0.6, 0.9,
                1.1,
                3.3, 3.4, 3.5, 3.6,
                4.4, 4.8
        )
                .map(d -> (long) (d * 1000));

        final Observable<String> delayedNames =
                names.zipWith(absoluteDelayMillis, (s, aLong) -> Observable
                        .just(s)
                        .delay(aLong, TimeUnit.MILLISECONDS)
                )
                        .flatMap(obs -> obs);

        delayedNames
                .sample(1, TimeUnit.SECONDS)
                .blockingSubscribe(Logger::log);
    }

}
