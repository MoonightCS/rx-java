package ru.popov.bodya.reactive.extensions.utils;

import java.util.concurrent.TimeUnit;

public class Sleep {

    private static final long DEFAULT_TIME = 1;

    public static void sleepSeconds() {
        sleepSeconds(DEFAULT_TIME);
    }

    public static void sleepSeconds(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
