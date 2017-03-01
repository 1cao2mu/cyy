package com.hhstu.cyy.cyy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.hhstu.cyy.cyy.R;


public class SwipeRefreshLayoutDialog extends Dialog {
    public SwipeRefreshLayoutDialog(Context context) {
        super(context, R.style.ShareDialog);
    }

    public SwipeRefreshLayoutDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_swipe_refrsh_layout);
       // setCancelable(false);
    }
}
