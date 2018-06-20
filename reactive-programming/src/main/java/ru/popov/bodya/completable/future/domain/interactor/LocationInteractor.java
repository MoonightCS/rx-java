package ru.popov.bodya.completable.future.domain.interactor;

import ru.popov.bodya.completable.future.data.GeoLocation;
import ru.popov.bodya.reactive.extensions.utils.Sleep;

import java.util.concurrent.CompletableFuture;

import static ru.popov.bodya.reactive.extensions.utils.Logger.log;

public class LocationInteractor {

    public GeoLocation locate() {
        log("locate");
        Sleep.sleepSeconds();
        return new GeoLocation(55.751244, 37.618423);
    }

    public CompletableFuture<GeoLocation> locateAsync() {
        return CompletableFuture.supplyAsync(this::locate);
    }

}
