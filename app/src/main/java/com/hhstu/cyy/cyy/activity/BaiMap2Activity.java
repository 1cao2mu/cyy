package com.hhstu.cyy.cyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.adapter.SelectPhotoAdapter;
import com.hhstu.cyy.cyy.application.MineApplication;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.view.ScroViewFroRecyclerVeiw;


import org.json.JSONArray;


public class BaiMap2Activity extends BaseAppCompatActivity implements BaiduMap.OnMapTouchListener {

    RecyclerView rv_list;
    LinearLayoutManager linearLayoutManager;
    SelectPhotoAdapter adapter;
    JSONArray array;


    ScroViewFroRecyclerVeiw scroViewFroRecyclerVeiw;
    MapView mMapView;
    BaiduMap mBaiduMap;
    //TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map_2);
        initView();
        initLocation();
        initData();
    }


    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText("客户信息");
        findViewById(R.id.iv_back).setOnClickListener(this);

//      label = (TextView) findViewById(R.id.label);
        scroViewFroRecyclerVeiw = (ScroViewFroRecyclerVeiw) findViewById(R.id.scroViewFroRecyclerVeiw);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapTouchListener(this);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        linearLayoutManager = new GridLayoutManager(getContext(), 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(linearLayoutManager);
//      adapter = new SelectPhotoAdapter(array, getContext());
//     adapter.setOnItemChildViewClickListener(BaiMap2Activity.this);
//      rv_list.setAdapter(adapter);
    }

    private void initLocation() {
        // 地图初始化
        // 关闭定位图层,定位图标
        mBaiduMap.setMyLocationEnabled(false);
        //隐藏百度图标
        mMapView.removeViewAt(1);
        //隐藏刻度尺
        mMapView.removeViewAt(2);
        //隐藏百度地图缩放按钮
        mMapView.showZoomControls(false);
        //隐藏指南针禁止手势3d
        UiSettings mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(false);
        mUiSettings.setOverlookingGesturesEnabled(false);


        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(MineApplication.latLng).zoom(14.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_launcher);
       //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().anchor(0.5f, 0.5f)
                .position(MineApplication.latLng)
                .icon(bitmap);
       //在地图上添加Marker，并显示
        Marker marker = (Marker) (mBaiduMap.addOverlay(option));
        //创建InfoWindow展示的view
        View view = View.inflate(getContext(), R.layout.text_dialog, null);
        TextView label = (TextView) view;
        label.setVisibility(View.VISIBLE);
        String s = "我在 " + MineApplication.str + " 附近 >";
        SpannableStringBuilder ssb = new SpannableStringBuilder(s);
        ssb.setSpan(new RelativeSizeSpan(1.3f), 3, s.length() - 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        label.setText(ssb);
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(label, MineApplication.latLng, -30);
        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                scroViewFroRecyclerVeiw.requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                scroViewFroRecyclerVeiw.requestDisallowInterceptTouchEvent(true);
                break;
        }
    }
}
