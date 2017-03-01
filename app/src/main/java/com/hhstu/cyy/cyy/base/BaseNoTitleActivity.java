package com.hhstu.cyy.cyy.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hhstu.cyy.cyy.application.MineApplication;
import com.hhstu.cyy.cyy.dialog.LoadingDialog;
import com.hhstu.cyy.cyy.listener.OnItemChildViewClickListener;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2015/11/26.
 */
public class BaseNoTitleActivity extends Activity implements View.OnClickListener, OnItemChildViewClickListener {
    public static String TAG;
    public boolean REFRESHABLE = true;
    public boolean ISCONNECTED = false;
    public int page = 1, LAST_VISIABLE_IETM_INDEX;
    public LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new LoadingDialog(this);
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
        TAG = this.getClass().getName();
        MineApplication.activities.add(this);
    }

    public void onStart() {
        super.onStart();
    }

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        MineApplication.dialogActivity = this;
        JPushInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MineApplication.activities.remove(this);
        MineApplication.getInstance().cancelPendingRequests(TAG);
    }

    public Context getContext() {
        return this;
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemChildViewClickListener(int id, int position) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void backClick(View v) {
        finish();
    }
    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //显示SwipeRefreshLayout和Dialog
    //如果不用哪个,可传null
    public void showSwipeRefreshLayout(final SwipeRefreshLayout s, Dialog d) {
        if (s != null) {
            s.post(new Runnable() {
                @Override
                public void run() {
                    s.setRefreshing(true);
                }
            });
        }
        if (d != null) {
            dialog.show();
        }
    }

    //隐藏SwipeRefreshLayout和Dialog
    public void dismissSwipeRefreshLayout(final SwipeRefreshLayout s, Dialog d) {
        if (s != null) {
            s.post(new Runnable() {
                @Override
                public void run() {
                    s.setRefreshing(false);
                }
            });
            //真实项目中
//            s.post(new Runnable() {
//                @Override
//                public void run() {
//                    s.setRefreshing(false);
//                }
//            });
        }
        if (d != null) {
            dialog.dismiss();
        }
    }
}
