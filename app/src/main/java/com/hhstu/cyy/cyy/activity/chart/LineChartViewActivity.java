package com.hhstu.cyy.cyy.activity.chart;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;

import java.util.HashMap;

public class LineChartViewActivity extends BaseAppCompatActivity {

    private SimpleLineChart mSimpleLineChart;
    private SeekBar sb;
private TextView tv_sb_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_view);
        mSimpleLineChart = (SimpleLineChart) findViewById(R.id.simpleLineChart);
        sb = (SeekBar) findViewById(R.id.sb);
        tv_sb_progress = (TextView) findViewById(R.id.tv_sb_progress);
        String[] xItem = {"1", "2", "3", "4", "5", "6", "7"};
        String[] yItem = {"10k", "20k", "30k", "40k", "50k"};
        if (mSimpleLineChart == null)
            Log.e("wing", "null!!!!");
        mSimpleLineChart.setXItem(xItem);
        mSimpleLineChart.setYItem(yItem);
        HashMap<Integer, Integer> pointMap = new HashMap();
        for (int i = 0; i < xItem.length; i++) {
            pointMap.put(i, (int) (Math.random() * 5));
        }
        mSimpleLineChart.setData(pointMap);

//        sb.setPadding(0, 0, 0, 0);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_sb_progress.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
