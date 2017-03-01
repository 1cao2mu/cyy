package com.hhstu.cyy.cyy.activity;

import android.os.Bundle;
import android.view.View;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.utils.Animator.HeartLayout;

public class HeartActivity extends BaseAppCompatActivity {

    private HeartLayout heartLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);
        heartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        findViewById(R.id.member_send_good).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartLayout.addFavor();
            }
        });
    }
}
