package com.hhstu.cyy.cyy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.activity.AutoViewPagerActivity;
import com.hhstu.cyy.cyy.activity.DialogActivity;
import com.hhstu.cyy.cyy.activity.DubbleActivity;
import com.hhstu.cyy.cyy.activity.HeartActivity;
import com.hhstu.cyy.cyy.activity.SpanActivity;
import com.hhstu.cyy.cyy.activity.chart.MainChartActivity;
import com.hhstu.cyy.cyy.listener.OnItemChildViewClickListener;


/**
 * Created by Administrator on 15-11-12.
 */
public class Tab4Fragment extends Fragment implements View.OnClickListener, OnItemChildViewClickListener {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            return view;
        }
        view = inflater.inflate(R.layout.fragment_tab4, null);
        view.findViewById(R.id.bt_dialog).setOnClickListener(this);
        view.findViewById(R.id.bt_3).setOnClickListener(this);
        view.findViewById(R.id.bt_4).setOnClickListener(this);
        view.findViewById(R.id.bt_5).setOnClickListener(this);
        view.findViewById(R.id.bt_6).setOnClickListener(this);
        view.findViewById(R.id.bt_7).setOnClickListener(this);
        view.findViewById(R.id.iv_back).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.tv_title)).setText("自定义");
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_dialog:
                intent = new Intent(getContext(), DialogActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_3:
                intent = new Intent(getContext(), AutoViewPagerActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_4:
                intent = new Intent(getContext(), SpanActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_5:
                intent = new Intent(getContext(), DubbleActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_6:
                intent = new Intent(getContext(), MainChartActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_7:
                intent = new Intent(getContext(), HeartActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemChildViewClickListener(int id, int position) {

    }
}