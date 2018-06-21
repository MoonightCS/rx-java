package ru.popov.bodya.completable.future.data;

import io.reactivex.Observable;
import ru.popov.bodya.interoperability.CompletableRxAdapter;

import java.util.Calendar;
import java.util.concurrent.CompletableFuture;

import static ru.popov.bodya.reactive.extensions.utils.Logger.log;
import static ru.popov.bodya.reactive.extensions.utils.Sleep.sleepSeconds;

public class TravelAgency {

    private final int mId;
    private final CompletableRxAdapter mRxAdapter;

    public TravelAgency(int mId, CompletableRxAdapter mRxAdapter) {
        this.mId = mId;
        this.mRxAdapter = mRxAdapter;
    }

    public CompletableFuture<Flight> searchAsync(User user, GeoLocation geoLocation) {
        return CompletableFuture.supplyAsync(() -> search(user, geoLocation));
    }

    public Observable<Flight> rxSearch(User user, GeoLocation geoLocation) {
        return mRxAdapter.observe(searchAsync(user, geoLocation));
    }

    private Flight search(User user, GeoLocation geoLocation) {
        log("search with mId: " + mId + " user: " + user + " geoLocation: " + geoLocation);
        sleepSeconds();
        return new Flight(user, geoLocation, Calendar.getInstance().getTime());
    }

}
