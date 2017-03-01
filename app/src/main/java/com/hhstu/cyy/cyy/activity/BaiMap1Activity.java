package com.hhstu.cyy.cyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.utils.Utils;


public class BaiMap1Activity extends BaseAppCompatActivity {
    // 定位相关
    LocationClient mLocClient;
    MapView mMapView;

    TextView label;
    ImageView iv_zhen;
    BaiduMap mBaiduMap;
    boolean isFirstLoc = true; // 是否首次定位
    MyLocationListenner myListener = new MyLocationListenner();

    GeoCoder mSearch = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_baidu_map_1);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_action).setOnClickListener(this);
        findViewById(R.id.ll_dingwei).setOnClickListener(this);
        findViewById(R.id.ll_next).setOnClickListener(this);
        ((ImageView) findViewById(R.id.iv_back)).setImageResource(R.mipmap.ic_launcher);
        ((ImageView) findViewById(R.id.iv_action)).setImageResource(R.mipmap.ic_launcher);
        ((TextView) findViewById(R.id.tv_title)).setText("地图");
        initView();
        initLocation();
        initMap();
    }

    private void initMap() {
        BaiduMap.OnMapStatusChangeListener listener = new BaiduMap.OnMapStatusChangeListener() {
            /**
             * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
             * @param status 地图状态改变开始时的地图状态
             */
            public void onMapStatusChangeStart(MapStatus status) {
                label.setVisibility(View.GONE);
            }

            /**
             * 地图状态变化中
             * @param status 当前地图状态
             */
            public void onMapStatusChange(MapStatus status) {
            }

            /**
             * 地图状态改变结束
             * @param status 地图状态改变结束后的地图状态
             */
            public void onMapStatusChangeFinish(MapStatus status) {
                LatLng mCenterLatLng = status.target;
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(mCenterLatLng));

            }
        };
        mBaiduMap.setOnMapStatusChangeListener(listener);
        OnGetGeoCoderResultListener glistener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                label.setVisibility(View.VISIBLE);
                //label.setText(reverseGeoCodeResult.getAddress());
                if (reverseGeoCodeResult.getPoiList() != null) {
                    String s = "我在 " + reverseGeoCodeResult.getPoiList().get(0).name + " 附近 >";
                    SpannableStringBuilder ssb = new SpannableStringBuilder(s);
                    ssb.setSpan(new RelativeSizeSpan(1.3f), 3, s.length() - 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    label.setText(ssb);
                }else{
                    label.setText("未知");
                }
//                SpannableStringBuilder spanBuilder = new SpannableStringBuilder(s);
//                spanBuilder.setSpan(new TextAppearanceSpan(null, 0, 35, null, null), 3, s.length() - 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                label.setText(spanBuilder);
            }
        };
        mSearch.setOnGetGeoCodeResultListener(glistener);
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
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        dialog.show();
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        label = (TextView) findViewById(R.id.label);
        iv_zhen = (ImageView) findViewById(R.id.iv_zhen);
        mBaiduMap = mMapView.getMap();
        mSearch = GeoCoder.newInstance();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.ll_dingwei:
                mLocClient.start();
                dialog.show();
                break;
            case R.id.iv_action:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }

            LatLng mCenterLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            Utils.sysout(location.getLatitude());
            Utils.sysout(location.getLongitude());
            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(mCenterLatLng));

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                //isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
//                MineApplication.latLng = ll;
//                MineApplication.str = "六合之家";
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            mLocClient.stop();
            dialog.dismiss();
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
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
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
