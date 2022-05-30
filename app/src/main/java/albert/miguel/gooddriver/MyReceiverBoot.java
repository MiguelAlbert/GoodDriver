package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * this receiver get the on boot completed message then starts the service.
 * it's registered in the androidmanifest file.
 */
public class MyReceiverBoot extends BroadcastReceiver {

    String TAG = "MyReceiverBoot";
    SharedPreferences.Editor editor;
    boolean booleanalarm;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        int NotiID = 9;
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            //boot has completed, now time to start our background service.
            booleanalarm = pref.getBoolean("Key_alarm",false);
            if(booleanalarm){
                Log.wtf(TAG, "Got the boot one!");
                sendNoti(context, NotiID);
                editor.putBoolean("Key_alarm", false);
                editor.apply();
            }

        }
    }



    public void sendNoti(Context context, int notiID) {

        String info = "Surveillez votre temps d'amplitude.\nEn redémarrant votre téléphone\nla notification de fin d'amplitude\na été supprimée.";
        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //---PendingIntent to launch activity if the user selects
        // the notification---
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("fragment", "amplitude");
        //notificationIntent.putExtra("mText", info);

        PendingIntent contentIntent = PendingIntent.getActivity(context, notiID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        //create the notification
        Notification notif = new NotificationCompat.Builder(context, AmplitudeFragment.id)
                .setSmallIcon(R.drawable.ic_logo)
                .setWhen(System.currentTimeMillis()) //When the event occurred, now, since noti are stored by time.
                .setContentTitle("GoodDriver") //Title message top row.
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(info))
                .setContentText(info) //message when looking at the notification, second row
                .setContentIntent(contentIntent) //what activity to open.
                .setChannelId(AmplitudeFragment.id)
                .setAutoCancel(true) //allow auto cancel when pressed.
                .build(); //finally build and return a Notification.
        //Show the notification
        mManager.notify(1000, notif);  //and if we want different notifications, use notiID here instead of 1.
    }



}
