package com.hhstu.cyy.cyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.adapter.ReModelAdapter;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.utils.Constants;
import com.hhstu.cyy.cyy.utils.Data;
import com.hhstu.cyy.cyy.utils.Utils;

import org.json.JSONArray;

/**
 * 基本模板
 * Created by Administrator on 2015/12/14.
 */
public class ReAndSwActivity extends BaseAppCompatActivity {

    RecyclerView rv_list;
    ReModelAdapter adapter;
    SwipeRefreshLayout srl_refresh;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_and_sw);
        findViewById(R.id.iv_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText("ReAndSw");
        initView();
    }

    private void initView() {
        srl_refresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        srl_refresh.setColorSchemeResources(R.color.theme);
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                REFRESHABLE = true;
                showSwipeRefreshLayout(srl_refresh, dialog);
                initData();
            }
        });
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(layoutManager);
        rv_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (adapter != null) {
                    if (!ISCONNECTED && newState == RecyclerView.SCROLL_STATE_IDLE && LAST_VISIABLE_IETM_INDEX == adapter.getItemCount() - 1 && REFRESHABLE && LAST_VISIABLE_IETM_INDEX + 1 >= Constants.PAGE_SIZE) {
                        page++;
                        initData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LAST_VISIABLE_IETM_INDEX = layoutManager.findLastVisibleItemPosition();
            }
        });
        showSwipeRefreshLayout(srl_refresh, dialog);
        initData();
    }

    private void initData() {
        try {
            ISCONNECTED = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    JSONArray array = new JSONArray();
                    array = Data.getData(page);
                    if (array == null || array.length() < Constants.PAGE_SIZE) {
                        REFRESHABLE = false;
                    }
                    if (adapter != null) {
                        if (page == 1) {
                            if (Utils.arrayIsNull(array)) {
                                findViewById(R.id.no_content).setVisibility(View.VISIBLE);
                            } else {
                                findViewById(R.id.no_content).setVisibility(View.GONE);
                            }
                            adapter.refresh(array);
                        } else {
                            adapter.append(array);
                        }
                    } else {
                        if (Utils.arrayIsNull(array)) {
                            findViewById(R.id.no_content).setVisibility(View.VISIBLE);
                        } else {
                            findViewById(R.id.no_content).setVisibility(View.GONE);
                        }
                        adapter = new ReModelAdapter(array, getContext());
                        adapter.setOnItemChildViewClickListener(ReAndSwActivity.this);
                        rv_list.setAdapter(adapter);
                    }
                    ISCONNECTED = false;
                    dismissSwipeRefreshLayout(srl_refresh, dialog);
                }
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        }
    }

}
