package com.hhstu.cyy.cyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;

/**
 * 基本模板
 * Created by Administrator on 2015/12/14.
 */
public class BaiMapActivity extends BaseAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText("百度地图");

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_1:
                intent = new Intent(getContext(), BaiMap1Activity.class);
                startActivity(intent);
                break;
            case R.id.bt_2:
                intent = new Intent(getContext(), BaiMap2Activity.class);
                startActivity(intent);
                break;
            case R.id.iv_action:
                break;
        }
    }

}
