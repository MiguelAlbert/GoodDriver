package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.POWER_SERVICE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class AlarmService extends BroadcastReceiver {
    SharedPreferences.Editor editor;
    int temps;
    int tempsAmplitude;
    Calendar CalendrierDateFin = Calendar.getInstance();
    String heurefin;
    @Override
    public void onReceive(Context context, Intent intent) {

        int NotiID = 1;
        if (intent.getAction().equals("SomeAction")) {  //my custom intent
            //temps = intent.getIntExtra("int_value", 0);
            sendNoti(context, NotiID);
        }
    }

    public void sendNoti(Context context, int notiID) {

        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //---PendingIntent to launch activity if the user selects
        // the notification---
        Intent notificationIntent = new Intent(context, MainActivity.class);

        //notificationIntent.putExtra("mText", info);

        PendingIntent contentIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            contentIntent = PendingIntent.getActivity(context, notiID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        } else{
            contentIntent = PendingIntent.getActivity(context, notiID, notificationIntent, 0);
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //create the notification
        Notification notif = new NotificationCompat.Builder(context, AmplitudeFragment.id)
                .setSmallIcon(R.drawable.ic_baseline_more_time_24)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_baseline_access_time_24))
                .setWhen(System.currentTimeMillis()) //When the event occurred, now, since noti are stored by time.
                .setContentTitle("Validité de carte") //Title message top row.
                .setContentText("Votre carte tachygraphe va bientôt expirer") //message when looking at the notification, second row
                .setContentIntent(contentIntent) //what activity to open.
                .setChannelId(AmplitudeFragment.id)
                .setSound(alarmSound)
                .setAutoCancel(true) //allow auto cancel when pressed.
                .build(); //finally build and return a Notification.
        //Show the notification
        mManager.notify(1, notif);  //and if we want different notifications, use notiID here instead of 1.

    }

}
