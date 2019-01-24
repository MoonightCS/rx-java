package ru.popov.bodya.rxjava2.maybe;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import ru.popov.bodya.reactive.extensions.utils.Logger;

public class MaybeTry {

    public static void main(String[] args) {
        final Disposable disposable = Observable.just(1, 2, 3, 4, 5)
                .map(x -> x + 1)
                .elementAt(6)
                .map(x -> x + 1)
                .subscribe(
                        integer -> Logger.log("onSuccess with: " + integer),
                        throwable -> Logger.log("onError"),
                        () -> Logger.log("onComplete"));
    }


}
