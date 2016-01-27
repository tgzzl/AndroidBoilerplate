package com.optilink.android.boilerplate.flux.store;

import com.optilink.android.boilerplate.flux.action.ActionImpl;

import timber.log.Timber;

/**
 * Created by tanner.tan on 2016/1/26.
 */
public final class StoreImpl extends Store<ActionImpl> {
    static StoreImpl sInstance;

    StoreImpl() {
    }

    public static StoreImpl getInstance() {
        if (sInstance == null) {
            sInstance = new StoreImpl();
        }
        return sInstance;
    }

    @Override
    public void onAction(ActionImpl action) {
        Timber.d("[onAction] %s", action);
        fireStoreChangeEvent(action);
    }

    @Override
    public StoreChangeEvent onStoreChangeEvent(ActionImpl action) {
        return new StoreChangeEvent(action);
    }
}
