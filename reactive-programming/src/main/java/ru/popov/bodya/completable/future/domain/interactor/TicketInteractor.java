package ru.popov.bodya.completable.future.domain.interactor;

import io.reactivex.Observable;
import org.jetbrains.annotations.Nullable;
import ru.popov.bodya.completable.future.data.Flight;
import ru.popov.bodya.completable.future.data.Ticket;
import ru.popov.bodya.interoperability.CompletableRxAdapter;
import ru.popov.bodya.reactive.extensions.utils.Sleep;

import java.util.concurrent.CompletableFuture;

import static ru.popov.bodya.reactive.extensions.utils.Logger.log;

public class TicketInteractor {

    private final CompletableRxAdapter rxAdapter;

    public TicketInteractor(CompletableRxAdapter rxAdapter) {
        this.rxAdapter = rxAdapter;
    }

    public CompletableFuture<Ticket> bookAsync(@Nullable Flight flight) {
        return CompletableFuture.supplyAsync(() -> book(flight));
    }

    public Observable<Ticket> rxBook(@Nullable Flight flight) {
        return rxAdapter.observe(bookAsync(flight));
    }

    private Ticket book(@Nullable Flight flight) {
        log("book with flight: " + flight);
        Sleep.sleepSeconds(3);
        return new Ticket(flight);
    }

}
