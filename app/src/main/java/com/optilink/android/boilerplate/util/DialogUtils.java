package com.optilink.android.boilerplate.util;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.optilink.android.boilerplate.dialog.ProgressFragmentDialog;

import timber.log.Timber;

public class DialogUtils {

    public static void showProgressDialog(FragmentManager manager, String message, @NonNull String tag) {
        show(manager, tag, ProgressFragmentDialog.newInstance(message), true);
    }

    public static void showProgressDialog(FragmentManager manager, String message, @NonNull String tag, boolean cancelable) {
        show(manager, tag, ProgressFragmentDialog.newInstance(message), cancelable);
    }

    public static void dismiss(FragmentManager manager, @NonNull String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        android.support.v4.app.Fragment prev = manager.findFragmentByTag(tag);
        if (prev != null && !prev.getActivity().isFinishing()) {
            ft.remove(prev);
            try {
                // call commit  after host activity onSaveInstanceState will throws
                //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
                ft.commitAllowingStateLoss();

            } catch (IllegalStateException e) {
                Timber.d(e.getMessage());
            }
        }
    }

    static void show(FragmentManager manager, @NonNull String tag, DialogFragment dialog, boolean cancelable) {
        FragmentTransaction ft = manager.beginTransaction();
        android.support.v4.app.Fragment prev = manager.findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
        dialog.show(ft, tag);
        dialog.setCancelable(cancelable);
    }
}
