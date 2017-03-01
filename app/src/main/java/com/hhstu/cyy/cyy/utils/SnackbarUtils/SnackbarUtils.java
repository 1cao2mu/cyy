package com.hhstu.cyy.cyy.utils.SnackbarUtils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.application.MineApplication;

/**
 * Created by CYY
 * on 2016/12/21.
 */

public class SnackbarUtils {

    public static void showContent0(View rootView, String content) {
        Snackbar.make(rootView, content, Snackbar.LENGTH_SHORT).show();
    }

    public static void showContent1(View rootView, String content) {
        Snackbar.make(rootView, content, Snackbar.LENGTH_LONG).show();
    }

    public static void showContent2(View rootView, String content) {
        Snackbar.make(rootView, content, Snackbar.LENGTH_INDEFINITE).show();
    }

    public static void showContent3(View rootView, String content) {
        Snackbar.make(rootView, content, Snackbar.LENGTH_INDEFINITE).setAction("走你", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContent0(v, "length_short");
            }
        })
                .setActionTextColor(ActivityCompat.getColor(MineApplication.getInstance(), R.color.text_red))
                .setActionTextColor(Color.RED)
                .show();
    }
}
