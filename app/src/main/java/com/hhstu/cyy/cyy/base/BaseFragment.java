package com.hhstu.cyy.cyy.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

import com.hhstu.cyy.cyy.dialog.LoadingDialog;
import com.hhstu.cyy.cyy.listener.OnItemChildViewClickListener;


/**
 * Created by Administrator on 15-11-12.
 */
public class BaseFragment extends Fragment implements View.OnClickListener, OnItemChildViewClickListener {
    boolean REFRESHABLE = true;
    int page = 1, LAST_VISIABLE_IETM_INDEX, FIRST_VISIABLE_IETM_INDEX;
    public LoadingDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new LoadingDialog(getContext());
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                } else {
                    return false; //默认返回 false
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemChildViewClickListener(int id, int position) {

    }
}
