package ru.popov.bodya.completable.future.domain;

import io.reactivex.Observable;
import ru.popov.bodya.completable.future.data.*;
import ru.popov.bodya.completable.future.data.repository.UserRepository;
import ru.popov.bodya.completable.future.domain.interactor.LocationInteractor;
import ru.popov.bodya.completable.future.domain.interactor.TicketInteractor;
import ru.popov.bodya.interoperability.CompletableRxAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.function.Function.identity;
import static ru.popov.bodya.reactive.extensions.utils.Logger.log;

public class TravelInteractorFacade {

    private final UserRepository mUserRepository;
    private final LocationInteractor mLocationInteractor;
    private final TicketInteractor mTicketInteractor;
    private final CompletableRxAdapter mCompletableRxAdapter;

    public TravelInteractorFacade(UserRepository userRepository,
                                  LocationInteractor locationInteractor,
                                  TicketInteractor ticketInteractor,
                                  CompletableRxAdapter completableRxAdapter) {
        mUserRepository = userRepository;
        mLocationInteractor = locationInteractor;
        mTicketInteractor = ticketInteractor;
        mCompletableRxAdapter = completableRxAdapter;
    }

    public Observable<Ticket> getTicketObservable() {
        final Observable<TravelAgency> agencies = agencies(4);
        final Observable<User> user = mUserRepository.rxGetUserById(3);
        final Observable<GeoLocation> location = mLocationInteractor.rxLocate();
        return user.zipWith(location, (userObs, locationObs) ->
                agencies
                        .flatMap(travelAgency -> travelAgency.rxSearch(userObs, locationObs))
                        .first(createDefaultFlight()))
                .flatMapSingle(flightSingle -> flightSingle)
                .flatMap(mTicketInteractor::rxBook);
    }

    public Ticket getTicket() throws ExecutionException, InterruptedException {
        final List<TravelAgency> agencies = createAgencies(5);
        final CompletableFuture<User> user = mUserRepository.getUserByIdAsync(2);
        final CompletableFuture<GeoLocation> location = mLocationInteractor.locateAsync();
        final CompletableFuture<Ticket> ticketCompletableFuture = user.thenCombine(location, (userParam, geoLocationParam) ->
                agencies
                        .stream()
                        .map(agency -> agency.searchAsync(userParam, geoLocationParam))
                        .reduce((f1, f2) -> f1.applyToEither(f2, identity()))
                        .get())
                .thenCompose(identity())
                .thenCompose(mTicketInteractor::bookAsync);

        return ticketCompletableFuture.get();
    }

    private Flight createDefaultFlight() {
        return new Flight(new User("Bodya", "Popov"), new GeoLocation(0.0, 0.0), Calendar.getInstance().getTime());
    }

    private Observable<TravelAgency> agencies(int size) {
        return Observable.fromIterable(createAgencies(size));
    }

    private List<TravelAgency> createAgencies(int size) {

        if (size < 0) {
            throw new IllegalArgumentException("size can't be less than zero");
        }

        final List<TravelAgency> travelAgencies = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            log("agency" + i + " created");
            travelAgencies.add(new TravelAgency(size, mCompletableRxAdapter));
        }
        return travelAgencies;
    }
}
