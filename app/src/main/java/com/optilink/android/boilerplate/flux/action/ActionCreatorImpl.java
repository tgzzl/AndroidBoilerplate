package com.optilink.android.boilerplate.flux.action;

import android.support.annotation.NonNull;

import com.optilink.android.boilerplate.flux.Dispatcher;

public final class ActionCreatorImpl implements ActionCreator<ActionImpl> {
    static ActionCreatorImpl sInstance;
    Dispatcher dispatcher;

    ActionCreatorImpl(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public static ActionCreatorImpl getInstance(Dispatcher dispatcher) {
        if (null == sInstance) {
            sInstance = new ActionCreatorImpl(dispatcher);
        }
        return sInstance;
    }

    @Override
    public ActionImpl createAction(String type, Object... params) {
        return new ActionImpl(type, params);
    }

    public void sendAction(@NonNull String action) {
        this.dispatcher.dispatch(createAction(action));
    }

    public void sendAction(@NonNull String action, String key, Object obj) {
        this.dispatcher.dispatch(createAction(action, key, obj));
    }

    public void sendAction(@NonNull String action, Object... params) {
        this.dispatcher.dispatch(createAction(action, params));
    }
}
