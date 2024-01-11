package com.example.laundrypersonnelmis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.RemoteMessage;

public class Serviceman_home extends AppCompatActivity {
DatabaseReference reff;
Boolean iscalled;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceman_home);
        progressBar=findViewById(R.id.taskprogressbar);
        Intent getphone=getIntent();
        String phone1= getphone.getStringExtra("phone");
        Intent getname = getIntent();
        String name1 = getname.getStringExtra("name");
        Intent getemail = getIntent();
        String email1 = getemail.getStringExtra("email");
        Intent getmessage = getIntent();
        String message1 = getmessage.getStringExtra("message");

        reff= FirebaseDatabase.getInstance().getReference("servicemen").child(phone1).child("Inprogress");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phone2=snapshot.child("phone").getValue(String.class);
                if(phone2.equals("null")){
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        iscalled=false;
        reff= FirebaseDatabase.getInstance().getReference("servicemen").child(phone1).child("new request");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String phone2=snapshot.child("phone").getValue(String.class);
                if(phone2.equals("null")){


                }else{
                    String channelid="Laundry Care";
                    NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    CharSequence channelname="LC";
                    int importance=NotificationManager.IMPORTANCE_DEFAULT;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel=new NotificationChannel(channelid,channelname,importance);
                    }
                    Intent intent=new Intent(Serviceman_home.this,Serviceman_inbox.class);
                    intent.putExtra("name", name1);
                    intent.putExtra("email", phone1);
                    intent.putExtra("phone", email1);
                    intent.putExtra("message", message1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    PendingIntent pendingIntent=PendingIntent.getActivity(Serviceman_home.this,0,intent,PendingIntent.FLAG_MUTABLE);
                    NotificationCompat.Builder builder= new NotificationCompat.Builder(Serviceman_home.this,"Laundry care")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("New request")
                            .setContentText("From "+phone2)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
                    NotificationManagerCompat notificationManage=NotificationManagerCompat.from(Serviceman_home.this);
                    notificationManager.notify(1,builder.build());













                    /*Context mContext=Serviceman_home.this;
                            NotificationManager mNotificationManager;

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(mContext.getApplicationContext(), "notify_001");
                    Intent ii = new Intent(mContext.getApplicationContext(), Serviceman_inbox.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);

                    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                    bigText.setBigContentTitle("Today's Bible Verse");
                    bigText.setSummaryText("Text in detail");

                    mBuilder.setContentIntent(pendingIntent);
                    mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                    mBuilder.setContentTitle("Your Title");
                    mBuilder.setContentText("Your text");
                    mBuilder.setPriority(Notification.PRIORITY_MAX);
                    mBuilder.setStyle(bigText);

                    mNotificationManager =
                            (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        String channelId = "Your_channel_id";
                        NotificationChannel channel = new NotificationChannel(
                                channelId,
                                "Channel human readable title",
                                NotificationManager.IMPORTANCE_HIGH);
                        mNotificationManager.createNotificationChannel(channel);
                        mBuilder.setChannelId(channelId);
                    }

                    mNotificationManager.notify(0, mBuilder.build());*/
                     /*NotificationManagerCompat notificationManager=NotificationManagerCompat.from(Serviceman_home.this);
                     Intent intent=new Intent(Serviceman_home.this,Serviceman_inbox.class);
                    intent.putExtra("email", phone1);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent=PendingIntent.getActivity(Serviceman_home.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification notification=new NotificationCompat.Builder(Serviceman_home.this,channelid)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(channelid)
                            .setContentText("New Request from:"+phone2)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .build();
                    notificationManager.notify(1,notification);*/


                  /*  NotificationCompat.Builder builder=new NotificationCompat.Builder(Serviceman_home.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(channelid)
                            .setContentText("New Request from:"+phone2)
                            .setAutoCancel(true);
                    Intent intent=new Intent(Serviceman_home.this,Serviceman_inbox.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("email", phone1);
                    PendingIntent pendingIntent=PendingIntent.getActivity(Serviceman_home.this,
                            0, intent,  PendingIntent.FLAG_MUTABLE );
                    builder.setContentIntent(pendingIntent);
                    NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0,builder.build());*/

                  /*  if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        NotificationChannel channel=new NotificationChannel("New Request","New Request",NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager manager=getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                    }

                    NotificationCompat.Builder builder=new NotificationCompat.Builder(Serviceman_home.this,channelid);
                    builder.setContentTitle("New request");
                    builder.setContentText("From: "+phone2);
                    builder .setSmallIcon(R.mipmap.ic_launcher);
                    builder.setAutoCancel(true);
                    NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(Serviceman_home.this);
                    notificationManagerCompat.notify(1,builder.build());*/
                  /*  NotificationCompat.Builder newreq= (NotificationCompat.Builder)new NotificationCompat.Builder(Serviceman_home.this)
                      .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("New request")
                            .setContentText("From "+phone2)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    newreq.build();
                    NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0,newreq.build());
                    Toast.makeText(Serviceman_home.this, "you have a new request", Toast.LENGTH_SHORT).show();*/
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    public void profile(View view) {
        Intent intent = new Intent(getApplicationContext(), userProfile.class);
        Intent getname=getIntent();
        String name1= getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone=getIntent();
        String phone1= getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail=getIntent();
        String email1= getemail.getStringExtra("email");
        String image=getemail.getStringExtra("image");
        // useremail.setText("password: "+email1);
        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);
        intent.putExtra("profile",image);
        startActivity(intent);

    }
    public void check_message(View view) {
        Intent intent = new Intent(getApplicationContext(),Serviceman_inbox.class);
       Intent getname = getIntent();
        String name1 = getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone = getIntent();
        String phone1 = getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail = getIntent();
        String email1 = getemail.getStringExtra("email");

        Intent getmessage = getIntent();
        String message1 = getmessage.getStringExtra("message");

        // useremail.setText("password: "+email1);
        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);
        intent.putExtra("message", message1);

        startActivity(intent);
    }

    public void check_inprogress(View view) {
        Intent intent = new Intent(getApplicationContext(),serviceman_Requestinprogress.class);
        Intent getname = getIntent();
        String name1 = getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone = getIntent();
        String phone1 = getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail = getIntent();
        String email1 = getemail.getStringExtra("email");

        Intent getmessage = getIntent();
        String message1 = getmessage.getStringExtra("message");

        // useremail.setText("password: "+email1);
        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);
        intent.putExtra("message", message1);

        startActivity(intent);
    }







    public void feedbackserviceman(View view) {
        Intent intent = new Intent(this, Servicemenfeedback.class);
        startActivity(intent);
    }

    public void exit(View view) {
        finishAffinity();
        System.exit(0);


    }
    public void notification(View view) {
        Intent intent = new Intent(getApplicationContext(), notification.class);
        Intent getname = getIntent();
        String name1 = getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone = getIntent();
        String phone1 = getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail = getIntent();
        String email1 = getemail.getStringExtra("email");
        // useremail.setText("password: "+email1);
        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);

        startActivity(intent);
    }

}