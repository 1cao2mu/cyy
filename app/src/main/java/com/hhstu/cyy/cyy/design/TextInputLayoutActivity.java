package com.hhstu.cyy.cyy.design;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;

public class TextInputLayoutActivity extends BaseAppCompatActivity {

    private TextInputLayout til2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input_layout);
        initView();
    }

    private void initView() {
        til2 = (TextInputLayout) findViewById(R.id.til_2);
        til2.setError("sb刘飞");
        til2.getPasswordVisibilityToggleContentDescription();
    }
}
