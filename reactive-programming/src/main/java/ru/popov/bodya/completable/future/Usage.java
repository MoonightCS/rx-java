package ru.popov.bodya.completable.future;

import ru.popov.bodya.completable.future.data.Ticket;
import ru.popov.bodya.completable.future.data.repository.UserRepository;
import ru.popov.bodya.completable.future.domain.TravelInteractorFacade;
import ru.popov.bodya.completable.future.domain.interactor.LocationInteractor;
import ru.popov.bodya.completable.future.domain.interactor.TicketInteractor;

import java.util.concurrent.ExecutionException;

public class Usage {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        TravelInteractorFacade travelInteractorFacade = new TravelInteractorFacade(new UserRepository(),
                new LocationInteractor(), new TicketInteractor());
        final Ticket ticket = travelInteractorFacade.getTicket();
        System.out.println("result ticket is: " + ticket);
        System.out.println("end main");
    }
}
