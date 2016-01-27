package com.optilink.android.boilerplate.flux.action;

public interface ActionCreator<T extends Action> {
    T createAction(String type, Object... params);
}
