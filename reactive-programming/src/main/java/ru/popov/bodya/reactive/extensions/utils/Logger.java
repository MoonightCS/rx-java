package ru.popov.bodya.reactive.extensions.utils;

public class Logger {

    public static void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }

}
