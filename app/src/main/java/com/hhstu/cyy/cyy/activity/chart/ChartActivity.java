package com.hhstu.cyy.cyy.activity.chart;

import android.os.Bundle;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.view.ChartView;

public class ChartActivity extends BaseAppCompatActivity {

  private ChartView CvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        CvTest=(ChartView)findViewById(R.id.CvTest);
        CvTest.SetInfo(
                new String[]{"7-11", "7-12", "7-13", "7-14", "7-15", "7-16", "7-17"},   //X轴刻度
                new String[]{"", "50", "100", "150", "200", "250"},   //Y轴刻度
                new String[]{"15", "23", "10", "36", "45", "40", "12"},  //数据
                "图标的标题"
        );
    }
}
