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
import com.hhstu.cyy.cyy.activity.BaiMapActivity;
import com.hhstu.cyy.cyy.activity.JsoupActivity;
import com.hhstu.cyy.cyy.gaode.GaoDe0Activity;
import com.hhstu.cyy.cyy.activity.MPAndroidChartActivity;
import com.hhstu.cyy.cyy.activity.PermissionsActivity;
import com.hhstu.cyy.cyy.activity.SelectPhotoActivity;
import com.hhstu.cyy.cyy.activity.ultra.UltraActivity;
import com.hhstu.cyy.cyy.listener.OnItemChildViewClickListener;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * Created by Administrator on 15-11-12.
 */
public class Tab3Fragment extends Fragment implements View.OnClickListener, OnItemChildViewClickListener {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ShareSDK.initSDK(getActivity());
        if (view != null) {
            return view;
        }
        view = inflater.inflate(R.layout.fragment_tab3, null);
        view.findViewById(R.id.iv_back).setVisibility(View.GONE);
        view.findViewById(R.id.bt_1).setOnClickListener(this);
        view.findViewById(R.id.bt_2).setOnClickListener(this);
        view.findViewById(R.id.bt_3).setOnClickListener(this);
        view.findViewById(R.id.bt_4).setOnClickListener(this);
        view.findViewById(R.id.bt_5).setOnClickListener(this);
        view.findViewById(R.id.bt_6).setOnClickListener(this);
        view.findViewById(R.id.bt_7).setOnClickListener(this);
        view.findViewById(R.id.bt_8).setOnClickListener(this);
        view.findViewById(R.id.bt_9).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.tv_title)).setText("第三方");
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        OnekeyShare oks = new OnekeyShare();
        switch (v.getId()) {
            case R.id.bt_1:
                intent = new Intent(getContext(), UltraActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_2:
                intent = new Intent(getContext(), SelectPhotoActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_3:
                intent = new Intent(getContext(), PermissionsActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_4:
                //关闭sso授权
                oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                oks.setTitle(getString(R.string.share));
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");
                // 启动分享GUI
                oks.show(getActivity());
                break;
            case R.id.bt_5:
                oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
                oks.show(getContext());
                break;
            case R.id.bt_6:
                intent = new Intent(getContext(), BaiMapActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_7:
                intent = new Intent(getContext(), MPAndroidChartActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_8:
                intent = new Intent(getContext(), GaoDe0Activity.class);
                startActivity(intent);
                break;
            case R.id.bt_9:
                intent = new Intent(getContext(), JsoupActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemChildViewClickListener(int id, int position) {

    }

    public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {


        @Override
        public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
            if (SinaWeibo.NAME.equals(platform.getName())) {
                String text = "AAAA";
                paramsToShare.setText(text);
            }
            if (TencentWeibo.NAME.equals(platform.getName())) {
                String text = "BBBB";
                paramsToShare.setText(text);
            }
            if (QZone.NAME.equals(platform.getName())) {
                String text = "CCCC";
                paramsToShare.setText(text);
            }
            if (Wechat.NAME.equals(platform.getName())) {
                paramsToShare.setTitle(getString(R.string.share));
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                paramsToShare.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                paramsToShare.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                paramsToShare.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                paramsToShare.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                paramsToShare.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                paramsToShare.setSiteUrl("http://sharesdk.cn");
            }
            if (WechatFavorite.NAME.equals(platform.getName())) {
                String text = "EEE";
                paramsToShare.setText(text);
            }
            if (WechatMoments.NAME.equals(platform.getName())) {
                String text = "FFF";
                paramsToShare.setText(text);
            }
            if (QQ.NAME.equals(platform.getName())) {
                String text = "GGG";
                paramsToShare.setText(text);
            }
            if (Email.NAME.equals(platform.getName())) {
                String text = "HHH";
                paramsToShare.setText(text);
            }
            if (ShortMessage.NAME.equals(platform.getName())) {
                String text = "III";
                paramsToShare.setText(text);
            }
        }
    }

}