package com.hhstu.cyy.cyy.gaode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.utils.Utils;

public class GaoDe0Activity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gao_de0);
        ((TextView) findViewById(R.id.tv_title)).setText("高德地图");
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_1:
                intent = new Intent(getContext(), GaoDe1Activity.class);
                startActivity(intent);
                break;
            case R.id.bt_2:
                int[] arr = new int[]{8, 2, 1, 0, 3};
                int[] index = new int[]{2, 0, 3, 2, 4, 0, 1, 3, 2, 3, 3};
                String tel = "";
                for (int i : index) {
                    tel += arr[i];
                }
                Utils.sysout(tel);
                break;
            case R.id.bt_3:

                break;
        }
    }
}
