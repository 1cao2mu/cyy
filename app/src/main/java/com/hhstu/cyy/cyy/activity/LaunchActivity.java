package com.hhstu.cyy.cyy.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;


import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.hhstu.cyy.cyy.base.BaseNoTitleActivity;
import com.hhstu.cyy.cyy.utils.Constants;
import com.hhstu.cyy.cyy.utils.Utils;

import java.io.File;

/**
 * Created by Administrator on 2015/11/13.
 */
public class LaunchActivity extends BaseNoTitleActivity {
    String TAG = "LaunchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
//      imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//      imageView.setImageResource(R.mipmap.guide1);
        setContentView(imageView);
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(1000);
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    File sdcardDir = Environment.getExternalStorageDirectory();
                    String path_downloads = sdcardDir.getPath() + "/" + Constants.DIR_BASE + "/" + Constants.DIR_DOWNLOADS;
                    String path_images = sdcardDir.getPath() + "/" + Constants.DIR_BASE + "/" + Constants.DIR_IMG;
                    File dir_downloads = new File(path_downloads);
                    if (!dir_downloads.exists()) {
                        dir_downloads.mkdirs();
                    }
                    File dir_images = new File(path_images);
                    if (!dir_images.exists()) {
                        dir_images.mkdirs();
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.CALL_PHONE},
                        new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                SharedPreferences sp = getSharedPreferences(Constants.SP_CONFIG, Context.MODE_PRIVATE);
                                if (sp.getInt(Constants.SP_KEY_OLD_VERSION, 0) != Utils.getAppVersionCode(LaunchActivity.this)) {
                                    Intent intent = new Intent(LaunchActivity.this, GuideActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onDenied(String permission) {
                                Log.e(TAG, permission);
                                SharedPreferences sp = getSharedPreferences(Constants.SP_CONFIG, Context.MODE_PRIVATE);
                                if (sp.getInt(Constants.SP_KEY_OLD_VERSION, 0) != Utils.getAppVersionCode(LaunchActivity.this)) {
                                    Intent intent = new Intent(LaunchActivity.this, GuideActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }
        });
        imageView.setAnimation(animationSet);
        imageView.startAnimation(animationSet);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "Activity-onRequestPermissionsResult() PermissionsManager.notifyPermissionsChange()");
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public Context getContext() {
        return this;
    }

    public Activity getActivity() {
        return this;
    }
}
