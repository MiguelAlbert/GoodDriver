package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    SharedPreferences.Editor editor;
    int temps;
    int tempsAmplitude;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        tempsAmplitude = pref.getInt("key_Amplitude_Journaliere", 13);
        int NotiID = 1;
        if (intent.getAction().equals("SomeAction")) {  //my custom intent
            temps = intent.getIntExtra("int_value", 0);
            sendNoti(context, NotiID);
        }
    }

    public void sendNoti(Context context, int notiID) {
        String Titre = "!! GoodDriver !!"; //changed below.
        String info = "Attention à votre temps d'Amplitude " + temps;
        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //---PendingIntent to launch activity if the user selects
        // the notification---
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.putExtra("mText", info);

        PendingIntent contentIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            contentIntent = PendingIntent.getActivity(context, notiID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        } else{
            contentIntent = PendingIntent.getActivity(context, notiID, notificationIntent, 0);
        }

        //create the notification
        Notification notif = new NotificationCompat.Builder(context, MainActivity.id)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setWhen(System.currentTimeMillis()) //When the event occurred, now, since noti are stored by time.
                .setContentTitle(Titre) //Title message top row.
                .setContentText(info + " " + tempsAmplitude) //message when looking at the notification, second row
                .setContentIntent(contentIntent) //what activity to open.
                .setChannelId(MainActivity.id)
                .setAutoCancel(true) //allow auto cancel when pressed.
                .build(); //finally build and return a Notification.
        //Show the notification
        mManager.notify(1, notif);  //and if we want different notifications, use notiID here instead of 1.

        // handler to dismiss notification after seconds
        //Handler h = new Handler() ;
        //long delayInMilliseconds = 60000 ;
        //h.postDelayed( new Runnable() {
        //    public void run () {
        //        mManager.cancel(1);
        //    }
        //} , delayInMilliseconds) ;
    }

}
