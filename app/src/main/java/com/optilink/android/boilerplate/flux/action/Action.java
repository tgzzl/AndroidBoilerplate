package com.optilink.android.boilerplate.flux.action;


public interface Action<P> {
    String getType();

    P getData();
}
