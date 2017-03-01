package com.hhstu.cyy.cyy.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;

public class TextViewActivity extends AppCompatActivity {

    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private TextView tv_6;
    private TextView tv_7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_1.getPaint().setTypeface(Typeface.DEFAULT);
        tv_1.setText("Typeface.DEFAULT呵呵呵大打啊啊大大的开发还是地方很i和");
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_2.getPaint().setTypeface(Typeface.DEFAULT_BOLD);
        tv_2.setText("Typeface.DEFAULT_BOLD呵呵呵大打啊啊大大的开发还是地方很i和");
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_3.getPaint().setTypeface(Typeface.SANS_SERIF);
        tv_3.setText("Typeface.SANS_SERIF呵呵呵大打啊啊大大的开发还是地方很i和");
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_4.getPaint().setTypeface(Typeface.SERIF);
        tv_4.setText("Typeface.SERIF呵呵呵大打啊啊大大的开发还是地方很i和");
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_5.getPaint().setTypeface(Typeface.MONOSPACE);
        tv_5.setText("Typeface.MONOSPACE呵呵呵大打啊啊大大的开发还是地方很i和");

        tv_6 = (TextView) findViewById(R.id.tv_6);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(),
                "yu.ttf");
        tv_6.setTypeface(myTypeface);
        tv_6.setText("Typeface.MONOSPACE呵呵呵大打啊啊大大的开发还是地方很i和");


        tv_7 = (TextView) findViewById(R.id.tv_7);
        Typeface myTypeface1 = Typeface.createFromAsset(getAssets(),
                "324.ttf");
        tv_7.setTypeface(myTypeface1);
        tv_7.setText("Typeface.MONOSPACE呵呵呵大打啊啊大大的开发还是地方很i和");


    }
}
