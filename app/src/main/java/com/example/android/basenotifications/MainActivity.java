package com.example.android.basenotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private void createNotificationChannel(){
        //Create the NotificationChannel, but only on API 26+ because
        //the NotificationChannel class is new and not in the support library
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            //Register the channel with the system; you can't change the importance
            // or other notification behaviours after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    int id = 0;

    private void createAndShowNotification(){

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("My notification " + id)
                .setContentText("Hello Notification! " + id)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher_round, "hello", pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        //notificationId is a unique int for each notification that you must define
        notificationManager.notify(id++, mBuilder.build());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();


        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                for (int i=0; i<3000; i++) {
                    createAndShowNotification();
                }
            }
        });
    }
}
