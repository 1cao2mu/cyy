package com.hhstu.cyy.cyy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.adapter.ReModelSampleAdapter;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.bean.ADInfo;
import com.hhstu.cyy.cyy.utils.Constants;
import com.hhstu.cyy.cyy.utils.Data;
import com.hhstu.cyy.cyy.utils.ViewFactory;
import com.hhstu.cyy.cyy.view.NoScrollLinearLayoutManager;
import com.hhstu.cyy.cyy.view.ScroViewFroRecyclerVeiw;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.androiddevelop.cycleviewpager.lib.CycleViewPager;

/**
 * 基本模板
 * Created by Administrator on 2015/12/14.
 */
public class AutoViewPagerActivity extends BaseAppCompatActivity {
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;

    ScroViewFroRecyclerVeiw sv_re;
    RecyclerView rv_list;
    ReModelSampleAdapter adapter;
    // SwipeRefreshLayout srl_refresh;
    NoScrollLinearLayoutManager layoutManager;

    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg"
            , "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg"
            , "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg"
            , "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_viewpager);
        findViewById(R.id.iv_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText("AutoViewPager");
        initialize();
        initView();
    }


    @SuppressLint("NewApi")
    private void initialize() {

        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);

        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);
        if (imageUrls.length == 1) cycleViewPager.setWheel(false);
        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                Toast.makeText(getActivity(),
                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
            }

        }

    };

    private void initView() {
//        srl_refresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
//        srl_refresh.setColorSchemeResources(R.color.theme);
//        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                REFRESHABLE = true;
//                showSwipeRefreshLayout(srl_refresh, dialog);
//                initData();
//            }
//        });
        sv_re = (ScroViewFroRecyclerVeiw) findViewById(R.id.sv_re);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        layoutManager = new NoScrollLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(layoutManager);

        //  showSwipeRefreshLayout(srl_refresh, dialog);
        showSwipeRefreshLayout(null, dialog);
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

                            adapter.refresh(array);
                        } else {
                            adapter.append(array);
                        }
                    } else {

                        adapter = new ReModelSampleAdapter(array, getContext());
                        adapter.setOnItemChildViewClickListener(AutoViewPagerActivity.this);
                        rv_list.setAdapter(adapter);
                    }
                    ISCONNECTED = false;
                    //dismissSwipeRefreshLayout(srl_refresh, dialog);
                    dismissSwipeRefreshLayout(null, dialog);
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
        }
    }
}
