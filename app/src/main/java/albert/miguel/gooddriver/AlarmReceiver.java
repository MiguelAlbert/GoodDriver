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
import android.os.Handler;
import android.os.PowerManager;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    SharedPreferences.Editor editor;
    int temps;
    int tempsAmplitude;
    Calendar CalendrierDateFin = Calendar.getInstance();
    String heurefin;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        tempsAmplitude = pref.getInt("key_Amplitude_Journaliere", 13);
        int monthDebut = pref.getInt("key_Debut_Month", 0);
        int yearDebut = pref.getInt("key_Debut_Year", 0);
        int dayOfMonthDebut = pref.getInt("key_Debut_Day", 0);
        int heureDebut = pref.getInt("key_Debut_Hour", 0);
        int minuteDebut = pref.getInt("key_Debut_Minute", 0);
        CalendrierDateFin.set(Calendar.MONTH, monthDebut);
        CalendrierDateFin.set(Calendar.YEAR, yearDebut);
        CalendrierDateFin.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
        CalendrierDateFin.set(Calendar.HOUR_OF_DAY, heureDebut);
        CalendrierDateFin.set(Calendar.MINUTE, minuteDebut);
        CalendrierDateFin.set(Calendar.SECOND, 0);
        CalendrierDateFin.set(Calendar.MILLISECOND, 0);
        CalendrierDateFin.add(CalendrierDateFin.HOUR_OF_DAY, tempsAmplitude);
        int HourFin = CalendrierDateFin.get(Calendar.HOUR_OF_DAY);
        int MinuteFin = CalendrierDateFin.get(Calendar.MINUTE);
        heurefin = (String.format("%02d:%02d", HourFin, MinuteFin));
        int NotiID = 1;
        if (intent.getAction().equals("SomeAction")) {  //my custom intent
            temps = intent.getIntExtra("int_value", 0);
            sendNoti(context, NotiID);
        }
    }

    public void sendNoti(Context context, int notiID) {
        String Titre = "GoodDriver rappel d'amplitude"; //changed below.
        String info = "" + temps + "";
        editor.putBoolean("Key_alarm", false);
        editor.apply();
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
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //create the notification
        Notification notif = new NotificationCompat.Builder(context, AmplitudeFragment.id)
                .setSmallIcon(R.drawable.ic_logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo))
                .setWhen(System.currentTimeMillis()) //When the event occurred, now, since noti are stored by time.
                .setContentTitle(Titre) //Title message top row.
                .setContentText("Fin d'amplitude de " + tempsAmplitude +" heures dans " +temps + " mn à " + heurefin) //message when looking at the notification, second row
                .setContentIntent(contentIntent) //what activity to open.
                .setChannelId(AmplitudeFragment.id)
                .setSound(alarmSound)
                .setAutoCancel(true) //allow auto cancel when pressed.
                .build(); //finally build and return a Notification.
        //Show the notification
        mManager.notify(1, notif);  //and if we want different notifications, use notiID here instead of 1.

        PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myApp:notificationLock");
        wl.acquire(3000); //set your time in milliseconds
        wl.release();
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
