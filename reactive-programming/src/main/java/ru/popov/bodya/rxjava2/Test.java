package ru.popov.bodya.rxjava2;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import io.reactivex.Observable;
import ru.popov.bodya.rxjava2.utils.UtilsFactory;

public class Test {

    public static void main(String[] args) {
        try {
            tryDropbox();
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

    private static void tryDropbox() throws DbxException {
        DbxRequestConfig dropboxRequestConfig = new DbxRequestConfig("name/0.1");
        DbxClientV2 dbxClient =  new DbxClientV2(dropboxRequestConfig, "token");
        FullAccount account = dbxClient.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());
    }


    private static void tryRx() {
        final int maxValue = 500;
        Observable
                .range(0, maxValue)
                .takeUntil(integer -> integer.compareTo(maxValue / 2) >= 0)
                .subscribe(UtilsFactory.createObserver());
    }
}
