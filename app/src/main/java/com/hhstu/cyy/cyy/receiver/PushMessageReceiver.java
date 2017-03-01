package com.hhstu.cyy.cyy.receiver;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;


import com.hhstu.cyy.cyy.R;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/15.
 */
public class PushMessageReceiver extends BroadcastReceiver {
    public static final String MESSAGE_RECEIVED_ACTION = "com.hhstu.cyy.cyy.receiver.pushmessagereceiver";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private static final long[] VIBRATE = {0, 500};
    Context context;

    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        int requestCode = (int) System.currentTimeMillis();
        if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
            String messge = intent.getStringExtra(KEY_MESSAGE);
            //  Utils.showToast(context, "接受消息:" + messge);
            //playSound();
            playMedia();



//            Intent intentPend = new Intent();
//            intentPend.setAction("aa.bb.cc");
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentPend, PendingIntent.FLAG_ONE_SHOT) ;
//            builder.setContentIntent(pendingIntent);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("您有新的消息");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentText(messge);
            Bitmap bp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            builder.setLargeIcon(bp);
            builder.setContentInfo("");
            builder.setWhen(System.currentTimeMillis());
            builder.setVibrate(VIBRATE);
            builder.setAutoCancel(true);

//            Intent intent1 = new Intent(context, MsgClassActivity.class);
//            //延迟意图  只有点击通知的时候  intent才会被触发
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent1, PendingIntent.FLAG_ONE_SHOT);
//            //将延迟意图放到通知中
//            builder.setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = builder.build();
            notify.flags = Notification.FLAG_AUTO_CANCEL;
            manager.notify(requestCode, notify);

            String extras = intent.getStringExtra(KEY_EXTRAS);
            StringBuilder showMsg = new StringBuilder();
            showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
            if (!TextUtils.isEmpty(extras)) {
                showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
            }
            //setCostomMsg(showMsg.toString()); 接受到的消息数据s
        }
    }

    public void playSound() {
        String uri = "android.resource://" + context.getPackageName() + "/" + R.raw.msg;
        Uri no = Uri.parse(uri);
        Ringtone r = RingtoneManager.getRingtone(context, no);
        r.play();
    }

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
}
