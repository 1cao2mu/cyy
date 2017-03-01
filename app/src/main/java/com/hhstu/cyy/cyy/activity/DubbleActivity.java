package com.hhstu.cyy.cyy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;

/**
 * 基本模板
 * Created by Administrator on 2015/12/14.
 */
public class DubbleActivity extends BaseAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dubble);
        ((TextView) findViewById(R.id.tv_title)).setText("气泡字");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
