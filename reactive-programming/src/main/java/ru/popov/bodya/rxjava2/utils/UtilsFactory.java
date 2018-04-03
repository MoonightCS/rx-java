package ru.popov.bodya.rxjava2.utils;

public class UtilsFactory {

    public static <T> LogObserver<? super T> createObserver() {
        return new LogObserver<>();
    }

}
