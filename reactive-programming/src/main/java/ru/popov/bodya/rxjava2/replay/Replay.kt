package ru.popov.bodya.rxjava2.replay

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

fun check() {
    val subject = PublishSubject.create<Int>()

    val obs: Observable<Int> = subject
        .flatMap { data -> Observable.fromCallable { if (data == 5) throw RuntimeException("my message") else data } }
        .replay(1)
        .autoConnect()

    obs.subscribe({ println("onNext1: $it") }, { println("onError1: ${it.message}") })
    subject.onNext(1)
    obs.subscribe({ println("onNext2: $it") }, { println("onError2: ${it.message}") })
    subject.onNext(2)
    subject.onNext(3)
    subject.onNext(5)
    obs.subscribe({ println("onNext3: $it") }, { println("onError3: ${it.message}") })
    subject.onNext(4)
    obs.subscribe({ println("onNext4: $it") }, { println("onError4: ${it.message}") })
}

fun main(args: Array<String>) {
    check()
}