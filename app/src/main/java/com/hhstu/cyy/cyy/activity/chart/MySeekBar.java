package com.hhstu.cyy.cyy.activity.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsSeekBar;
import android.widget.SeekBar;

import com.hhstu.cyy.cyy.R;

/**
 * Created by CYY
 * on 2017/1/13.
 */

public class MySeekBar extends AbsSeekBar {

    public interface OnSeekBarChangeListener {
        void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);
    }

    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    public MySeekBar(Context context) {
        this(context, null);
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.seekBarStyle);
    }

    public MySeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Sets a listener to receive notifications of changes to the SeekBar's progress level. Also
     * provides notifications of when the user starts and stops a touch gesture within the SeekBar.
     *
     * @param l The seek bar notification listener
     *
     * @see SeekBar.OnSeekBarChangeListener
     */
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        mOnSeekBarChangeListener = l;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

}
