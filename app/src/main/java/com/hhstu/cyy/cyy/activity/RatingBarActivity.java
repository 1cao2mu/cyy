package com.hhstu.cyy.cyy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.view.StarBar;

public class RatingBarActivity extends BaseAppCompatActivity {

    private StarBar starBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);
        findViewById(R.id.iv_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText("RatingBar");

        starBar= (StarBar) findViewById(R.id.sb_big);
        starBar.setIntegerMark(false);
        starBar.setStarMark(3.6f);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
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
