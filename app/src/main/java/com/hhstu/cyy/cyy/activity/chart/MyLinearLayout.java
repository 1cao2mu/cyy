package com.hhstu.cyy.cyy.activity.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by CYY
 * on 2017/1/16.
 */

public class MyLinearLayout extends LinearLayout {
    private String TAG = "MyMyLinearLayout";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        if (ev.getAction()==MotionEvent.ACTION_CANCEL){
//            return false;
//        }
        Log.e(TAG, "onTouchEvent"+TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction()==MotionEvent.ACTION_CANCEL){
//            return false;
//        }
        Log.e(TAG, "onInterceptTouchEvent"+TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
    }
}
