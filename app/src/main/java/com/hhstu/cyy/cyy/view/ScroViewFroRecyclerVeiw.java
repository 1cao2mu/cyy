package com.hhstu.cyy.cyy.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2015/11/20.
 */
public class ScroViewFroRecyclerVeiw extends NestedScrollView implements NestedScrollView.OnScrollChangeListener {
    private int downX;
    private int downY;
    private int mTouchSlop;
    int oldScrollY;

    public ScroViewFroRecyclerVeiw(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnScrollChangeListener(this);
    }

    public ScroViewFroRecyclerVeiw(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnScrollChangeListener(this);
    }

    public ScroViewFroRecyclerVeiw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnScrollChangeListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }

        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }
}