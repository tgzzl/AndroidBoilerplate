package com.optilink.android.boilerplate.flux;

import com.optilink.android.boilerplate.flux.action.Action;
import com.optilink.android.boilerplate.flux.store.Store;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


public final class Dispatcher {

    static Dispatcher sInstance;
    final List<Store> stores;

    Dispatcher() {
        stores = new ArrayList<>();
    }

    public static Dispatcher getInstance() {
        if (sInstance == null) {
            sInstance = new Dispatcher();
        }
        return sInstance;
    }

    public Dispatcher register(final Store store) {
        if (!stores.contains(store)) {
            Timber.d("[register] Store:%s", store);
            stores.add(store);
        }
        return sInstance;
    }

    public Dispatcher unregister(final Store store) {
        Timber.d("[unregister] Store:%s", store);
        stores.remove(store);
        return sInstance;
    }

    public Dispatcher dispatch(final Action action) {
        for (Store store : stores) {
            store.onAction(action);
        }
        return sInstance;
    }
}
