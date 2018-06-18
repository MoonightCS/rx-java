package ru.popov.bodya.rxjava2.examples.speech;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

import static io.reactivex.Observable.just;

public class Speech {

    private static final class Pair<L, R> {
        private final L left;
        private final R right;

        Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        L getLeft() {
            return left;
        }

        R getRight() {
            return right;
        }
    }

    public static void main(String[] args) {
        new Speech()
                .speak("To be, or not to be: that is the question", 110)
                .blockingSubscribe(s -> System.out.print(s + " "));
    }

    Observable<String> speak(String quote, long millisPerChar) {
        final String[] tokens = quote.replaceAll("[:,]", "").split(" ");
        final Observable<String> words = Observable.fromArray(tokens);

        final Observable<Long> absoluteDelay = words
                .map(String::length)
                .map(len -> len * millisPerChar)
                .scan((total, current) -> total + current);

        return words
                .zipWith(absoluteDelay.startWith(0L), Pair::new)
                .flatMap(pair -> just(pair.getLeft()).delay(pair.getRight(), TimeUnit.MILLISECONDS));
    }

}
