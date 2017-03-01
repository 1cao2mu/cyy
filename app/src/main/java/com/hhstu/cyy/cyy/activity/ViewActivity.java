package com.hhstu.cyy.cyy.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.dialog.RunManDialog;
import com.hhstu.cyy.cyy.dialog.SwipeRefreshLayoutDialog;
import com.hhstu.cyy.cyy.utils.Utils;

/**
 * 基本模板
 * Created by Administrator on 2015/12/14.
 */
public class ViewActivity extends BaseAppCompatActivity {

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 要做的事情
            super.handleMessage(msg);
        }
    };
    private ProgressDialog dialog2;
    private View v3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.finger_memo).setOnClickListener(this);
//        findViewById(R.id.v_1).setOnClickListener(this);
        findViewById(R.id.v_2).setEnabled(false);
        v3 = findViewById(R.id.v_3);
        v3.setContentDescription("v3的描述");
        ((TextView) findViewById(R.id.tv_title)).setText("View");

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_action:
                break;
            case R.id.v_1:
            case R.id.v_2:
                showAlerDialog("响应点击事件");
                break;
            case R.id.v_3:
                showAlerDialog(v3.getContentDescription().toString());
                break;
        }
    }

    public void showAlerDialog(String msg) {
        new android.app.AlertDialog.Builder(getActivity()).setTitle("提示")
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

}
