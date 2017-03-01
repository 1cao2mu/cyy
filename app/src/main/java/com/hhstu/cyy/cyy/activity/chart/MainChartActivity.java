package com.hhstu.cyy.cyy.activity.chart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;

public class MainChartActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chart);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
        findViewById(R.id.bt_4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_1:
                intent = new Intent(getContext(), ChartActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_2:
                intent = new Intent(getContext(), LineChartViewActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_3:
                intent = new Intent(getContext(), HelloChartsActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_4:
                intent = new Intent(getContext(), MyChartsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
