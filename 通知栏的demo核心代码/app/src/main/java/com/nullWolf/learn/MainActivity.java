package com.nullWolf.learn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView tv_normal_notification;
    private TextView tv_diy_notification;

    private String CHANNEL_ID = "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //普通的通知栏
        tv_normal_notification = findViewById(R.id.tv_normal_notification);
        tv_normal_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "name";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);
                } else {
                    builder = new NotificationCompat.Builder(MainActivity.this);
                }
                builder.setSmallIcon(R.mipmap.ic_launcher_round)
                        .setAutoCancel(true)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setChannelId(CHANNEL_ID)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle("普通通知栏测试")
                        .setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());
            }
        });

        //自定义通知栏
        tv_diy_notification = findViewById(R.id.tv_diy_notification);
        tv_diy_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "name";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);
                } else {
                    builder = new NotificationCompat.Builder(MainActivity.this);
                }
                //自定义
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.diy_notification);
                remoteViews.setImageViewResource(R.id.iv_icon, R.mipmap.ic_launcher);
                remoteViews.setTextViewText(R.id.tv_title, "自定义通知栏标题");
                remoteViews.setTextViewText(R.id.tv_content, "自定义通知栏内容");
                Intent intent1 = new Intent(MainActivity.this, Main3Activity.class);
                PendingIntent pendingIntent1 = PendingIntent.getActivity(MainActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.iv_icon, pendingIntent1);
                builder.setSmallIcon(R.mipmap.ic_launcher_round)
                        .setAutoCancel(true)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setChannelId(CHANNEL_ID)
                        .setContentIntent(pendingIntent)
                        .setContent(remoteViews);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(2, builder.build());
            }
        });
    }


}
