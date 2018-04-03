package ru.popov.bodya.rxjava2;

public class LogObserverFactory {

    public static <T> LogObserver<? super T> createObserver() {
        return new LogObserver<>();
    }

}
