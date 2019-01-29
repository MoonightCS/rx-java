package ru.popov.bodya.rxjava2.single;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ru.popov.bodya.reactive.extensions.utils.Logger;

public class SingleTry {

    public static void main(String[] args) {
        tryExceptionSingleToDefault();
    }

    private static void tryExceptionSingleToDefault() {
        final Disposable disposable = Single
                .just(5)
                .flatMap(integer -> Completable
                        .fromAction(() -> {
                            int a = 5 / 0;
                            Logger.log("from action run");
                        })
                        .toSingleDefault(integer)
                )
                .map(value -> value + value)
                .subscribe(
                        integer -> Logger.log("onSuccess with value: " + integer),
                        throwable -> Logger.log("onError with value: " + throwable)
                );
    }
}
