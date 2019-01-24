package ru.popov.bodya.completable.future;

import ru.popov.bodya.completable.future.data.Ticket;
import ru.popov.bodya.completable.future.data.repository.UserRepository;
import ru.popov.bodya.completable.future.domain.TravelInteractorFacade;
import ru.popov.bodya.completable.future.domain.interactor.LocationInteractor;
import ru.popov.bodya.completable.future.domain.interactor.TicketInteractor;
import ru.popov.bodya.reactive.extensions.utils.Logger;

import java.util.concurrent.ExecutionException;

public class Usage {

    public static void main(String[] args) {

        final CompletableRxAdapter rxAdapter = new CompletableRxAdapter();
        final TravelInteractorFacade travelInteractorFacade = new TravelInteractorFacade(new UserRepository(rxAdapter),
                new LocationInteractor(rxAdapter), new TicketInteractor(rxAdapter), rxAdapter);
        tryRxWithAdapter(travelInteractorFacade);
    }

    private static void tryRxWithAdapter(TravelInteractorFacade facade) {
        facade.getTicketObservable()
                .blockingSubscribe(ticket -> Logger.log("found ticket"));
    }

    private static void tryCompletableFuture(TravelInteractorFacade facade) throws ExecutionException, InterruptedException {
        final Ticket ticket = facade.getTicket();
        System.out.println("result ticket is: " + ticket);
    }
}
