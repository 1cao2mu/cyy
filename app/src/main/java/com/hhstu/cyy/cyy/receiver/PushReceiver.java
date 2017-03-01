package com.hhstu.cyy.cyy.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.hhstu.cyy.cyy.activity.ultra.WebPtrActivity;
import com.hhstu.cyy.cyy.application.MineApplication;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        this.context = context;
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
//            TextInputLayoutActivity loginActivity = (TextInputLayoutActivity) MineApplication.getActivity(TextInputLayoutActivity.class);
//            if (loginActivity != null) {
//                loginActivity.initializedJPush();
//            }
//            RegisterActivity registerActivity = (RegisterActivity) MineApplication.getActivity(RegisterActivity.class);
//            if (registerActivity != null) {
//                registerActivity.initializedJPush();
//            }
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            //  processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + bundle.getString(JPushInterface.EXTRA_ALERT));
            playMedia();
            if (MineApplication.dialogActivity != null&&MineApplication.activitie != null) {
                new AlertDialog.Builder(MineApplication.dialogActivity).setTitle("通知消息")
                        .setMessage(bundle.getString(JPushInterface.EXTRA_ALERT))
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                            }
                        }).show();
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            Intent i;
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (!TextUtils.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (null != extraJson && extraJson.length() > 0) {
                        if (extraJson.has("url")) {
                            i = new Intent(context, WebPtrActivity.class);
                            i.putExtra("url", extraJson.getString("url"));
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(i);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            //打开自定义的Activity
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }

    }

    private MediaPlayer mediaPlayer;

    public void playMedia() {
        mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor descriptor = context.getAssets().openFd("msg.mp3");
            mediaPlayer.setDataSource(descriptor.getFileDescriptor());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent msgIntent = new Intent(PushMessageReceiver.MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(PushMessageReceiver.KEY_MESSAGE, message);
        if (!TextUtils.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                if (null != extraJson && extraJson.length() > 0) {
                    msgIntent.putExtra(PushMessageReceiver.KEY_EXTRAS, extras);
                }
            } catch (JSONException e) {

            }

        }
        context.sendBroadcast(msgIntent);
    }
}
