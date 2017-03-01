package com.hhstu.cyy.cyy.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.utils.Utils;
import com.hhstu.cyy.cyy.utils.WebViewBaseClient;


/**
 * 关于我们
 * Created by Administrator on 2015/11/13.
 */
public class SwAndWebActivity extends BaseAppCompatActivity {

    SwipeRefreshLayout srl_refresh;
    WebView wv_web;
    TextView tv_title;
    Intent inintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sw_we);

        findViewById(R.id.iv_back).setOnClickListener(this);
        inintent = getIntent();
        tv_title = (TextView) findViewById(R.id.tv_title);
        wv_web = (WebView) findViewById(R.id.wv_web);
        initView();
    }

    private void initView() {
        srl_refresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);

        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wv_web.reload();
                srl_refresh.setRefreshing(false);
            }
        });
        wv_web.setWebViewClient(new WebViewBaseClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("shouldOve", url);
                if (url.startsWith("http")) {
                    view.loadUrl(url);
                    if (srl_refresh != null) {
                        showSwipeRefreshLayout(srl_refresh, null);
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
                if (srl_refresh != null) {
                    if (srl_refresh.isRefreshing()) {
                        dismissSwipeRefreshLayout(srl_refresh, null);
                    }
                }
            }
        });
        wv_web.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(getContext()).setTitle("提示").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                if (srl_refresh != null) {
                    if (srl_refresh.isRefreshing()) {
                        dismissSwipeRefreshLayout(srl_refresh, null);
                    }
                }
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
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        String url = getIntent().getExtras().getString("url");
        Utils.sysout(url);
        wv_web.loadUrl(url);
        showSwipeRefreshLayout(srl_refresh, null);
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
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_action:
                //分享
                break;
        }
    }
}
