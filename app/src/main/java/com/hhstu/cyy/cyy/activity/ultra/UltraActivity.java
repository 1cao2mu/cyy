package com.hhstu.cyy.cyy.activity.ultra;

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
public class UltraActivity extends BaseAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultra);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText("Ultra+ListView");
        initView();
    }

    private void initView() {
        initData();
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_1:
                intent = new Intent(getContext(), LvPtrActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_2:
                intent = new Intent(getContext(), WebPtrActivity.class);
                intent.putExtra("url","http://q.url.cn/s/6Jnnv");
                startActivity(intent);
                break;
        }
    }
}
