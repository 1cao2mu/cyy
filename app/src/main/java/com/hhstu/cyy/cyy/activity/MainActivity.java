package com.hhstu.cyy.cyy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.application.MineApplication;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.fragment.Tab1Fragment;
import com.hhstu.cyy.cyy.fragment.Tab2Fragment;
import com.hhstu.cyy.cyy.fragment.Tab3Fragment;
import com.hhstu.cyy.cyy.fragment.Tab4Fragment;
import com.hhstu.cyy.cyy.fragment.Tab5Fragment;
import com.hhstu.cyy.cyy.utils.Utils;
import com.hhstu.cyy.cyy.view.TabFragment;


public class MainActivity extends BaseAppCompatActivity {

    private TabFragment tabHost;
    /**
     * Fragment数组界面
     */
    private Class tabFragment[] = {Tab1Fragment.class, Tab2Fragment.class, Tab3Fragment.class,
            Tab4Fragment.class,Tab5Fragment.class};
    /**
     * 存放按下图片数组liu
     */
    private int tabImgsPassed[] = {R.mipmap.tab_red,
            R.mipmap.tab_red, R.mipmap.tab_red, R.mipmap.tab_red, R.mipmap.tab_red};
    /**
     * 存放图片数组
     */
    private int tabImgsNormal[] = {R.mipmap.tab_black,
            R.mipmap.tab_black, R.mipmap.tab_black, R.mipmap.tab_black,R.mipmap.tab_black};

    /**
     * 选修卡文字
     */
    private String tabText[] = {"design", "v4和v7", "第三方", "自定义","基本"};

    /**
     * 基本控件
     */
    private String tag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (MineApplication.activitie != null) {
            MineApplication.activitie.finish();
        }
        MineApplication.activitie = this;
        initView();
    }


    private void initView() {
        // 找到TabHost
        tabHost = (TabFragment) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.fl_content);
        // 得到fragment的个数
        int count = tabFragment.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabText[i]).setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            tabHost.addTab(tabSpec, tabFragment[i], null);
            // 设置Tab按钮的背景
            tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.mine_tab_bg_normal);
        }
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabText.length; i++) {
                    View view = tabHost.getTabWidget().getChildAt(i);
                    ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
                    TextView textView = (TextView) view.findViewById(R.id.tv_text);
                    TextView tv_dou = (TextView) view.findViewById(R.id.tv_dou);

                    if (tabId.equals(tabText[i])) {
                        textView.setTextColor(Color.WHITE);
                        imageView.setImageResource(tabImgsPassed[i]);
                        textView.setTextColor(getResources().getColor(R.color.text_red));
                        if (i == 0) {
                            Utils.showToast(getContext(), "加载");
                            tv_dou.setVisibility(View.GONE);
                        }//  toolbar.setTitle(tabText[i]);
                        //   view.setBackgroundColor(getResources().getColor(R.color.mine_tab_bg_passed));
                    } else {
                        imageView.setImageResource(tabImgsNormal[i]);
                        textView.setTextColor(getResources().getColor(R.color.text_black));
                        if (i == 0) {
                            tv_dou.setVisibility(View.VISIBLE);
                            tv_dou.setText("200");
                        } else {
                            tv_dou.setVisibility(View.GONE);
                        }
                        //view.setBackgroundColor(getResources().getColor(R.color.mine_tab_bg_normal));
                    }
                }
            }
        });
        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setCurrentTabByTag(tabText[0]);
        View view = tabHost.getCurrentTabView();
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
        TextView textView = (TextView) view.findViewById(R.id.tv_text);
        imageView.setImageResource(tabImgsPassed[0]);
        textView.setTextColor(getResources().getColor(R.color.text_red));

        //view.setBackgroundColor(getResources().getColor(R.color.mine_tab_bg_passed));
    }

    /**
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_main_tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
        imageView.setImageResource(tabImgsNormal[index]);
        TextView textView = (TextView) view.findViewById(R.id.tv_text);
        TextView tv_dou = (TextView) view.findViewById(R.id.tv_dou);
        tv_dou.setVisibility(View.GONE);
        textView.setText(tabText[index]);
        view.setBackgroundColor(getResources().getColor(R.color.mine_tab_bg_normal));
        return view;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_icon:
                break;
        }
    }

    public void showHome() {
        tabHost.setCurrentTab(0);
    }

    public void showShop() {
        tabHost.setCurrentTab(1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Utils.sysout("main" + "---" + resultCode + "---" + requestCode);
        //checkAutoLogin();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tabText[3]);
        if (fragment != null && fragment instanceof Tab4Fragment) {
            if (requestCode == 0) {
                //   ((Tab4Fragment) fragment).initLoginData();
            }
        }
        fragment = getSupportFragmentManager().findFragmentByTag(tabText[0]);
        if (fragment != null && fragment instanceof Tab1Fragment) {
            if (requestCode == 0) {
                //((Tab1Fragment) fragment).initLoginData();
            }
        }
        fragment = getSupportFragmentManager().findFragmentByTag(tabText[1]);
        if (fragment != null && fragment instanceof Tab2Fragment) {
            if (requestCode == 0) {
                //  ((Tab2Fragment) fragment).initLoginData();
            }
        }
        fragment = getSupportFragmentManager().findFragmentByTag(tabText[2]);
        if (fragment != null && fragment instanceof Tab3Fragment) {
            if (requestCode == 0) {
                //    ((Tab3Fragment) fragment).initLoginData();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("确定退出吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        MineApplication.exitApp();
                    }
                }).setNegativeButton("返回", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点击“返回”后的操作,这里不设置没有任何操作
            }
        }).show();
    }


}
