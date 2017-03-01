package com.hhstu.cyy.cyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.adapter.LvModelAdapter;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.utils.Constants;
import com.hhstu.cyy.cyy.utils.Data;
import com.hhstu.cyy.cyy.utils.Utils;

import org.json.JSONArray;

/**
 * 基本模板
 * Created by Administrator on 2015/12/14.
 */
public class LvAndSwActivity extends BaseAppCompatActivity {

    ListView rv_list;
    LvModelAdapter adapter;
    SwipeRefreshLayout srl_refresh;
    View footerView;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 要做的事情
            dismissSwipeRefreshLayout(srl_refresh, dialog);
            super.handleMessage(msg);
        }
    };
    private ListView rvlist;
    private SwipeRefreshLayout srlrefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv_and_sw);

        findViewById(R.id.iv_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText("LvAndSw");
        footerView = LayoutInflater.from(this).inflate(R.layout.item_model_footer_se, null);
        footerView.findViewById(R.id.cp_progress).setVisibility(View.VISIBLE);
        footerView.findViewById(R.id.tv_all_ed).setVisibility(View.GONE);
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
                footerView.findViewById(R.id.cp_progress).setVisibility(View.VISIBLE);
                footerView.findViewById(R.id.tv_all_ed).setVisibility(View.GONE);
                initData();
            }
        });
        rv_list = (ListView) findViewById(R.id.rv_list);
        rv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (adapter != null) {
                    if (!ISCONNECTED && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && LAST_VISIABLE_IETM_INDEX == adapter.getCount() && REFRESHABLE && LAST_VISIABLE_IETM_INDEX + 1 >= Constants.PAGE_SIZE) {
                        page++;
                        initData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                LAST_VISIABLE_IETM_INDEX = firstVisibleItem + visibleItemCount - 1;
                if (firstVisibleItem == 0)
                    srl_refresh.setEnabled(true);
                else
                    srl_refresh.setEnabled(false);
            }
        });
        rv_list.addFooterView(footerView);
        showSwipeRefreshLayout(srl_refresh, dialog);
        initData();
    }

    private void initData() {
        try {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    JSONArray array = new JSONArray();
                    array = Data.getData(page);
                    if (array == null || array.length() < Constants.PAGE_SIZE) {
                        REFRESHABLE = false;
                        footerView.findViewById(R.id.cp_progress).setVisibility(View.GONE);
                        footerView.findViewById(R.id.tv_all_ed).setVisibility(View.VISIBLE);
                    }
                    if (adapter != null) {
                        if (page == 1) {
                            if (Utils.arrayIsNull(array)) {
                                findViewById(R.id.no_content).setVisibility(View.VISIBLE);
                                findViewById(R.id.rv_list).setVisibility(View.GONE);
                            } else {
                                findViewById(R.id.no_content).setVisibility(View.GONE);
                                findViewById(R.id.rv_list).setVisibility(View.VISIBLE);
                            }
                            adapter.refresh(array);
                        } else {
                            adapter.append(array);
                        }
                    } else {
                        if (Utils.arrayIsNull(array)) {
                            findViewById(R.id.no_content).setVisibility(View.VISIBLE);
                            findViewById(R.id.rv_list).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.no_content).setVisibility(View.GONE);
                            findViewById(R.id.rv_list).setVisibility(View.VISIBLE);
                        }
                        adapter = new LvModelAdapter(array, getContext());
                        adapter.setOnItemChildViewClickListener(LvAndSwActivity.this);
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
