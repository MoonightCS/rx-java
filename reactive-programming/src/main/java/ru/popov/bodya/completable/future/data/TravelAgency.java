package ru.popov.bodya.completable.future.data;

import java.util.Calendar;
import java.util.concurrent.CompletableFuture;

import static ru.popov.bodya.reactive.extensions.utils.Logger.log;
import static ru.popov.bodya.reactive.extensions.utils.Sleep.sleepSeconds;

public class TravelAgency {

    private final int id;

    public TravelAgency(int id) {
        this.id = id;
    }

    public Flight search(User user, GeoLocation geoLocation) {
        log("search with id: " + id + " user: " + user + " geoLocation: " + geoLocation);
        sleepSeconds();
        return new Flight(user, geoLocation, Calendar.getInstance().getTime());
    }

    public CompletableFuture<Flight> searchAsync(User user, GeoLocation geoLocation) {
        return CompletableFuture.supplyAsync(() -> search(user, geoLocation));
    }
}
