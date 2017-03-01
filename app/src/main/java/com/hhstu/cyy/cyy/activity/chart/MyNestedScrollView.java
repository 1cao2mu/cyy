package com.hhstu.cyy.cyy.activity.chart;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by CYY
 * on 2017/1/13.
 */

public class MyNestedScrollView extends NestedScrollView {


    private String TAG = "MyMyNestedScrollView";
    private onActionUp onActionUp;

    public void setOnActionUp(MyNestedScrollView.onActionUp onActionUp) {
        this.onActionUp = onActionUp;
    }

    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (onActionUp != null) {
                onActionUp.onActionUp((int) ev.getX());
            }
        }
        Log.e(TAG, "onTouchEvent" + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent" + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
    }


    public interface onActionUp {
        void onActionUp(int ex);
    }
}
