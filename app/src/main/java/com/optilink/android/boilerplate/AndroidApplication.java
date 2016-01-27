package com.optilink.android.boilerplate;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by tanner.tan on 2016/1/25.
 */
public class AndroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }


    }
}
