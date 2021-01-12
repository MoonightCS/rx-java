package ru.popov.bodya.rxjava2.scheduling

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {

    Observable.fromCallable {
        log("fromCallable")
        1
    }
        .observeOn(Schedulers.computation())
        .doOnSubscribe { log("upper doOnSubscribe") }
        .doOnSubscribe { log("middle doOnSubscribe") }
        .subscribeOn(Schedulers.newThread())
        .doOnSubscribe { log("lower doOnSubscribe") }
        .subscribeOn(Schedulers.io())
        .subscribe { log("value received") }

    TimeUnit.SECONDS.sleep(5)
}

private fun log(msg: String) {
    println("Thread: ${Thread.currentThread()}, msg: $msg")
}