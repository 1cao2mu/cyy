package com.hhstu.cyy.cyy.activity;

import android.app.ProgressDialog;
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

/**
 * 基本模板
 * Created by Administrator on 2015/12/14.
 *
 */
public class DialogActivity extends BaseAppCompatActivity {


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 要做的事情
            dialog.dismiss();
            super.handleMessage(msg);
        }
    };
    private ProgressDialog dialog2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
        findViewById(R.id.bt_4).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText("dialog");

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_1:
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);// 发送消息
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.bt_2:
                RunManDialog dialog = new RunManDialog(getContext(), "拼命加载中", R.anim.dialog_runmam);
                dialog.show();
                break;
            case R.id.bt_3:
                SwipeRefreshLayoutDialog dialog1 = new SwipeRefreshLayoutDialog(getContext());
                dialog1.show();
                break;
            case R.id.bt_4:
                ProgressDialog dialog2= new ProgressDialog(getContext());
                dialog2.setMessage("正在加载中");
                dialog2.show();
                break;
            case R.id.iv_action:
                break;
        }
    }

}
