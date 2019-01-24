package ru.popov.bodya.completable.future.data.repository;

import io.reactivex.Observable;
import ru.popov.bodya.completable.future.data.User;
import ru.popov.bodya.completable.future.CompletableRxAdapter;
import ru.popov.bodya.reactive.extensions.utils.Logger;
import ru.popov.bodya.reactive.extensions.utils.Sleep;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class UserRepository {

    private final CompletableRxAdapter mRxAdapter;
    private final HashMap<Long, User> mUserBase;

    public UserRepository(CompletableRxAdapter rxAdapter) {
        mRxAdapter = rxAdapter;
        mUserBase = new HashMap<>();
    }

    public CompletableFuture<User> getUserByIdAsync(long id) {
        return CompletableFuture.supplyAsync(() -> getUserById(id));
    }

    public Observable<User> rxGetUserById(long id) {
        return mRxAdapter.observe(getUserByIdAsync(id));
    }

    private User getUserById(long id) {
        Logger.log("getUserById with id: " + id);
        Sleep.sleepSeconds();
        return mUserBase.compute(id, (idKey, user)
                -> user == null ? new User("name" + id, "surname" + id) : user);
    }
}
