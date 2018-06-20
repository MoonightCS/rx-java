package ru.popov.bodya.completable.future.domain;

import ru.popov.bodya.completable.future.data.GeoLocation;
import ru.popov.bodya.completable.future.data.Ticket;
import ru.popov.bodya.completable.future.data.TravelAgency;
import ru.popov.bodya.completable.future.data.User;
import ru.popov.bodya.completable.future.data.repository.UserRepository;
import ru.popov.bodya.completable.future.domain.interactor.LocationInteractor;
import ru.popov.bodya.completable.future.domain.interactor.TicketInteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.function.Function.identity;

public class TravelInteractorFacade {

    private final UserRepository mUserRepository;
    private final LocationInteractor mLocationInteractor;
    private final TicketInteractor mTicketInteractor;

    public TravelInteractorFacade(UserRepository mUserRepository,
                                  LocationInteractor mLocationInteractor,
                                  TicketInteractor mTicketInteractor) {
        this.mUserRepository = mUserRepository;
        this.mLocationInteractor = mLocationInteractor;
        this.mTicketInteractor = mTicketInteractor;
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

    private List<TravelAgency> createAgencies(int size) {

        if (size < 0) {
            throw new IllegalArgumentException("size can't be less than zero");
        }

        final List<TravelAgency> travelAgencies = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            travelAgencies.add(new TravelAgency(size));
        }
        return travelAgencies;
    }

}
