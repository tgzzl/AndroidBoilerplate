package com.optilink.android.boilerplate.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.optilink.android.boilerplate.R;

public class ProgressFragmentDialog extends DialogFragment {

    public static ProgressFragmentDialog newInstance(String message) {
        ProgressFragmentDialog frag = new ProgressFragmentDialog();
        if (!TextUtils.isEmpty(message)) {
            Bundle args = new Bundle();
            args.putString("message", message);
            frag.setArguments(args);
        }
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_processing, container, false);
        TextView textView = (TextView) v.findViewById(R.id.message);
        if (getArguments() != null) {
            textView.setText(getArguments().getString("message"));
        }
        return v;
    }
}
