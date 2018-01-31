package ru.popov.bodya.utils;

public class Logger {

    public static void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }

}
