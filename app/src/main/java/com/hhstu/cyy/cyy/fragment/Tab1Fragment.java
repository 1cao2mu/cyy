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
import com.hhstu.cyy.cyy.activity.BottoNavigationbarActivity;
import com.hhstu.cyy.cyy.activity.TabLayoutActivity;
import com.hhstu.cyy.cyy.design.SnackbarActivity;
import com.hhstu.cyy.cyy.design.TextInputLayoutActivity;
import com.hhstu.cyy.cyy.listener.OnItemChildViewClickListener;


/**
 * Created by Administrator on 15-11-12.
 */
public class Tab1Fragment extends Fragment implements View.OnClickListener, OnItemChildViewClickListener {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            return view;
        }
        view = inflater.inflate(R.layout.fragment_tab1, null);
        view.findViewById(R.id.iv_back).setVisibility(View.GONE);
        view.findViewById(R.id.bt_1).setOnClickListener(this);
        view.findViewById(R.id.bt_2).setOnClickListener(this);
        view.findViewById(R.id.bt_3).setOnClickListener(this);
        view.findViewById(R.id.bt_4).setOnClickListener(this);

        ((TextView) view.findViewById(R.id.tv_title)).setText("design");
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_1:
                intent = new Intent(getContext(), BottoNavigationbarActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_2:
                intent = new Intent(getContext(), TabLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_3:
                intent = new Intent(getContext(), TextInputLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_4:
                intent = new Intent(getContext(), SnackbarActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemChildViewClickListener(int id, int position) {

    }
}