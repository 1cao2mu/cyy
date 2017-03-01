package com.hhstu.cyy.cyy.activity.ultra;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 基本模板
 * Created by Administrator on 2015/12/14.
 */
public class LvPtrActivity extends BaseAppCompatActivity {

    ListView rv_list;
    LvModelAdapter adapter;
    PtrClassicFrameLayout mPtrFrame;
    View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_lv);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.no_content).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText("Ultra+ListView");
        footerView = LayoutInflater.from(this).inflate(R.layout.item_model_footer_pro, null);
        footerView.findViewById(R.id.cp_progress).setVisibility(View.VISIBLE);
        footerView.findViewById(R.id.tv_all_ed).setVisibility(View.GONE);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                REFRESHABLE = true;
                footerView.findViewById(R.id.cp_progress).setVisibility(View.VISIBLE);
                footerView.findViewById(R.id.tv_all_ed).setVisibility(View.GONE);
                initData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.post(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        });
        initView();
    }

    private void initView() {
        rv_list = (ListView) findViewById(R.id.rotate_header_list_view);
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
            }
        });
        rv_list.addFooterView(footerView);
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
                        footerView.findViewById(R.id.cp_progress).setVisibility(View.GONE);
                        footerView.findViewById(R.id.tv_all_ed).setVisibility(View.VISIBLE);
                    }
                    if (adapter != null) {
                        if (page == 1) {
                            if (Utils.arrayIsNull(array)) {
                                findViewById(R.id.no_content).setVisibility(View.VISIBLE);
                                findViewById(R.id.rotate_header_list_view_frame).setVisibility(View.GONE);
                            } else {
                                findViewById(R.id.no_content).setVisibility(View.GONE);
                                findViewById(R.id.rotate_header_list_view_frame).setVisibility(View.VISIBLE);
                            }
                            adapter.refresh(array);
                        } else {
                            adapter.append(array);
                        }
                    } else {
                        if (Utils.arrayIsNull(array)) {
                            findViewById(R.id.no_content).setVisibility(View.VISIBLE);
                            findViewById(R.id.rotate_header_list_view_frame).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.no_content).setVisibility(View.GONE);
                            findViewById(R.id.rotate_header_list_view_frame).setVisibility(View.VISIBLE);
                        }
                        adapter = new LvModelAdapter(array, getContext());
                        adapter.setOnItemChildViewClickListener(LvPtrActivity.this);
                        rv_list.setAdapter(adapter);
                    }
                    ISCONNECTED = false;
                    mPtrFrame.refreshComplete();
                    dialog.dismiss();
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
            case R.id.no_content:
                dialog.show();
                initData();
                break;
        }
    }
}
