package albert.miguel.gooddriver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * this receiver get the on boot completed message then starts the service.
 * it's registered in the androidmanifest file.
 */
public class MyReceiverBoot extends BroadcastReceiver {

    String TAG = "MyReceiverBoot";

    @Override
    public void onReceive(Context context, Intent intent) {
        int NotiID = 1;
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            //boot has completed, now time to start our background service.
            Log.wtf(TAG, "Got the boot one!");
            sendNoti(context, NotiID);

        }

    }



    public void sendNoti(Context context, int notiID) {

        String info = "Je suis GoodDriver";
        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //---PendingIntent to launch activity if the user selects
        // the notification---
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.putExtra("mText", info);

        PendingIntent contentIntent = PendingIntent.getActivity(context, notiID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        //create the notification
        Notification notif = new NotificationCompat.Builder(context, MainActivity.id)
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setWhen(System.currentTimeMillis()) //When the event occurred, now, since noti are stored by time.
                .setContentTitle("!GoodDriver!") //Title message top row.
                .setContentText(info) //message when looking at the notification, second row
                .setContentIntent(contentIntent) //what activity to open.
                .setChannelId(MainActivity.id)
                .setAutoCancel(true) //allow auto cancel when pressed.
                .build(); //finally build and return a Notification.
        //Show the notification
        mManager.notify(1, notif);  //and if we want different notifications, use notiID here instead of 1.
    }

}
