package com.example.laundrypersonnelmis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;
import android.app.NotificationManager;

import android.app.PendingIntent;

import android.content.Context;

import android.content.Intent;



import android.view.View;

import android.widget.Button;

import java.util.Calendar;

public class notification extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        btn =  findViewById(R.id.idBtn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                ClickMe();

            }
        });
    }


    private void ClickMe() {

        Intent resultIntent = new Intent(this, Serviceman_home.class);

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK

                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent piResult = PendingIntent.getActivity(this, 0, resultIntent, 0);

//Assign inbox style notification

        NotificationCompat.InboxStyle inboxStyle =

                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Inbox Notification");

        inboxStyle.addLine("Message 1.");

        inboxStyle.addLine("Message 2.");

        inboxStyle.addLine("Message 3.");

        inboxStyle.addLine("Message 4.");

        inboxStyle.addLine("Message 5.");

        inboxStyle.setSummaryText("+2 more");

        NotificationCompat.Builder mBuilder =

                (NotificationCompat.Builder) new NotificationCompat.Builder(this)

                        .setSmallIcon(R.mipmap.ic_launcher)

                        .setContentTitle("Inbox style notification")

                        .setContentText("This is test of inbox style notification.")

                        .setStyle(inboxStyle)

                        .addAction(R.mipmap.ic_launcher, "show activity", piResult);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(0, mBuilder.build());

    }

}










