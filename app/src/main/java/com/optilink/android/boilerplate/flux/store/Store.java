package com.optilink.android.boilerplate.flux.store;

import com.optilink.android.boilerplate.flux.action.Action;
import com.optilink.android.boilerplate.flux.action.ActionImpl;
import com.squareup.otto.Bus;

import timber.log.Timber;

public abstract class Store<T extends Action> {
    static final Bus sBus = new Bus();

    Store() {
    }

    public Store register(final Object view) {
        Timber.d("[register] Object:%s", view);
        sBus.register(view);
        return this;
    }

    public Store unregister(final Object view) {
        Timber.d("[unregister] Object:%s", view);
        sBus.unregister(view);
        return this;
    }

    public abstract void onAction(T action);

    public abstract StoreChangeEvent onStoreChangeEvent(ActionImpl action);

    void fireStoreChangeEvent(ActionImpl action) {
        sBus.post(onStoreChangeEvent(action));
    }

    public static class StoreChangeEvent {
        public ActionImpl action;

        public StoreChangeEvent(ActionImpl action) {
            this.action = action;
        }

        public boolean success() {
            return null != action.getData();
        }
    }
}
