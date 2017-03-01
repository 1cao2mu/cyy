package com.hhstu.cyy.cyy.activity.ultra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.utils.WebViewBaseClient;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 基本模板
 * Created by Administrator on 2015/12/14.
 */
public class WebPtrActivity extends BaseAppCompatActivity {


    PtrClassicFrameLayout mPtrFrame;

    WebView wv_web;
    TextView tv_title;
    Intent inintent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_web);
        findViewById(R.id.iv_back).setOnClickListener(this);
        inintent = getIntent();
        tv_title = (TextView) findViewById(R.id.tv_title);
        wv_web = (WebView) findViewById(R.id.rotate_header_web_view);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_web_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                wv_web.reload();
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
        initView();
    }

    private void initView() {
        wv_web.setWebViewClient(new WebViewBaseClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {

                    view.loadUrl(url);
                    if (dialog != null) {
                        dialog.show();
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                tv_title.setText(wv_web.getTitle());
                tv_title.requestFocus();
                dialog.dismiss();
                mPtrFrame.refreshComplete();

            }
        });
        wv_web.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(getContext()).setTitle("提示")
                        .setMessage(message)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tv_title.setText(title);
                tv_title.requestFocus();
                mPtrFrame.refreshComplete();
                dialog.dismiss();
            }
        });
//        wv_web.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        srl_refresh.setEnabled(false);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL:
//                        srl_refresh.setEnabled(true);
//                }
//                return false;
//            }
//        });
        WebSettings webSettings = wv_web.getSettings();

        // 设置支持加载图片
        webSettings.setBlockNetworkImage(false);
        // 设置支持缓存
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath());
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        String url = inintent.getExtras().getString("url");
        wv_web.loadUrl(url);
        mPtrFrame.post(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        wv_web.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && wv_web.canGoBack()) {

            tv_title.setText("正在载入中...");
            tv_title.requestFocus();
            wv_web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
