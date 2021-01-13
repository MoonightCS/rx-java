package ru.popov.bodya.rxjava2.scheduling

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val disposable = checkPaymentState()
        .doOnNext {  }
        .subscribe { value ->
            log("value received: $value")
        }

    TimeUnit.SECONDS.sleep(2)
    disposable.dispose()
    TimeUnit.SECONDS.sleep(7)
}

private fun checkPaymentState(): Observable<Int> {
    val paymentService = Service()
    return Single.create<Int> { emitter ->
        paymentService.startWork(TimeoutListener(emitter))
    }
        .toObservable()
        .startWith(4)
}

private class Service {
    fun startWork(timeoutListener: TimeoutListener) {
        Thread {
            TimeUnit.SECONDS.sleep(5)
            timeoutListener.callback()
        }.start()
    }
}

private class TimeoutListener(private val emitter: SingleEmitter<Int>) {
    fun callback() {
        log("is emitter disposed: ${emitter.isDisposed}")
        emitter.onSuccess(5)
    }
}

private fun doOnSubscribeCase() {
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
    println("Thread: ${Thread.currentThread().name}, msg: $msg")
}