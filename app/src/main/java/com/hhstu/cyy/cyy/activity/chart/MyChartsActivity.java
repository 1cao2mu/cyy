package com.hhstu.cyy.cyy.activity.chart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.hhstu.cyy.cyy.R;

public class MyChartsActivity extends AppCompatActivity implements MyNestedScrollView.onActionUp {

    private LinearLayout activityMyCharts;
    private MyNestedScrollView mnsl;
    private MyChartView CvTest;
    private String TAG = "MyChartsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_charts);
        initView();
    }

    private void initView() {
        activityMyCharts = (LinearLayout) findViewById(R.id.activity_my_charts);
        mnsl = (MyNestedScrollView) findViewById(R.id.mnsl);
        CvTest = (MyChartView) findViewById(R.id.CvTest);
        mnsl.setOnActionUp(this);
        mnsl.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                Log.e(TAG, "onViewAttachedToWindow: ");
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Log.e(TAG, "onViewDetachedFromWindow: ");
            }

        });
        mnsl.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.e(TAG, "onLayoutChange: ");
            }
        });


    }

    @Override
    public void onActionUp(int ex) {
        CvTest.setActionUp(ex);
    }
}
