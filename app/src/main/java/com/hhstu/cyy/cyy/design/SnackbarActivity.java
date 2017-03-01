package com.hhstu.cyy.cyy.design;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.utils.SnackbarUtils.SnackbarUtils;

public class SnackbarActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private LinearLayout activitySnackbar;
    private Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_1:
                SnackbarUtils.showContent0(activitySnackbar,"LENGTH_SHORT");
                break;
            case R.id.bt_2:
                SnackbarUtils.showContent1(activitySnackbar,"LENGTH_LONG");
                break;
            case R.id.bt_3:
                SnackbarUtils.showContent2(activitySnackbar,"LENGTH_INDEFINITE");
                break;
        }
    }

    private void initView() {
        activitySnackbar = (LinearLayout) findViewById(R.id.activity_snackbar);
        bt1 = (Button) findViewById(R.id.bt_1);
    }
}
