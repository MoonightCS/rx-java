package ru.popov.bodya.completable.future.domain.interactor;

import org.jetbrains.annotations.Nullable;
import ru.popov.bodya.completable.future.data.Flight;
import ru.popov.bodya.completable.future.data.Ticket;
import ru.popov.bodya.reactive.extensions.utils.Sleep;

import java.util.concurrent.CompletableFuture;

import static ru.popov.bodya.reactive.extensions.utils.Logger.log;

public class TicketInteractor {

    public Ticket book(@Nullable Flight flight) {
        log("book with flight: " + flight);
        Sleep.sleepSeconds(3);
        return new Ticket(flight);
    }

    public CompletableFuture<Ticket> bookAsync(@Nullable Flight flight) {
        return CompletableFuture.supplyAsync(() -> book(flight));
    }
}
