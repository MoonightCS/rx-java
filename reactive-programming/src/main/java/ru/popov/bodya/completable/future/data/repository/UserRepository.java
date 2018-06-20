package ru.popov.bodya.completable.future.data.repository;

import ru.popov.bodya.completable.future.data.User;
import ru.popov.bodya.reactive.extensions.utils.Logger;
import ru.popov.bodya.reactive.extensions.utils.Sleep;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class UserRepository {

    private final HashMap<Long, User> mUserBase;

    public UserRepository() {
        mUserBase = new HashMap<>();
    }

    public CompletableFuture<User> getUserByIdAsync(long id) {
        return CompletableFuture.supplyAsync(() -> getUserById(id));
    }

    public User getUserById(long id) {
        Logger.log("getUserById with id: " + id);
        Sleep.sleepSeconds();
        return mUserBase.compute(id, (idKey, user)
                -> user == null ? new User("name" + id, "surname" + id) : user);
    }
}
