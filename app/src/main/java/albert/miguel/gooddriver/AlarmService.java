package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;

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

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class AlarmService extends BroadcastReceiver {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int reglageday, reglageday2, differenceDay;
    int yearEcheance, monthEcheance, dayOfMonthEcheance;
    int yearVidage, monthVidage, dayOfMonthVidage;
    int yearProchainVidage, monthProchainVidage, dayOfMonthProchainVidage;
    int reglagedayVidage, reglagedayVidage2, differenceDayVidage;

    @Override
    public void onReceive(Context context, Intent intent) {

        pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        int NotiID = 2;
        int NotiID2 = 3;
        if (intent.getAction().equals("echeance")) {  //my custom intent
            //temps = intent.getIntExtra("int_value", 0);
            if(testDateEcheance()){
                sendNoti(context, NotiID);
            }
        }
        if (intent.getAction().equals("vidage")) {  //my custom intent
            //temps = intent.getIntExtra("int_value", 0);
            if(testDateVidage()){
                sendNoti2(context, NotiID2);
            }
        }
    }

    private boolean testDateEcheance() {
        Calendar now = Calendar.getInstance();
        Calendar DateEcheance = Calendar.getInstance();
        yearEcheance = pref.getInt("key_Echeance_Year", 0);
        monthEcheance = pref.getInt("key_Echeance_Month", 0);
        dayOfMonthEcheance = pref.getInt("key_Echeance_Day", 0);
        DateEcheance.set(Calendar.YEAR, yearEcheance);
        DateEcheance.set(Calendar.MONTH, monthEcheance);
        DateEcheance.set(Calendar.DAY_OF_MONTH, dayOfMonthEcheance);

        long millis1 = now.getTimeInMillis();
        long millis2 = DateEcheance.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = millis2 - millis1;
        differenceDay = (int) (diff / (24 * 60 * 60 * 1000));

        reglageday = pref.getInt("key_duree_rappel_echeance", 0);
        transform(reglageday);

        if((differenceDay <= reglageday2) && (differenceDay >= 0)){
            return true;
        } else {
            return false;
        }
    }

    private void transform(int reglageday) {
        switch(reglageday){
            case 0:
                reglageday2 = 90;
                return;
            case 1:
                reglageday2 = 60;
                return;
            case 2:
                reglageday2 = 45;
                return;
            case 3:
                reglageday2 = 30;
                return;
            case 4:
                reglageday2 = 15;
        }
    }

    private boolean testDateVidage() {
        Calendar now = Calendar.getInstance();
        Calendar DateVidage = Calendar.getInstance();
        yearVidage = pref.getInt("key_dechargement_Year", 0);
        monthVidage = pref.getInt("key_dechargement_Month", 0);
        dayOfMonthVidage = pref.getInt("key_dechargement_Day", 0);

        DateVidage.set(Calendar.YEAR, yearVidage);
        DateVidage.set(Calendar.MONTH, monthVidage);
        DateVidage.set(Calendar.DAY_OF_MONTH, dayOfMonthVidage);
        DateVidage.add(Calendar.DAY_OF_MONTH, 28);

        yearProchainVidage = DateVidage.get(Calendar.YEAR);
        monthProchainVidage = DateVidage.get(Calendar.MONTH);
        dayOfMonthProchainVidage = DateVidage.get(Calendar.DAY_OF_MONTH);

        long millis1 = now.getTimeInMillis();
        long millis2 = DateVidage.getTimeInMillis();

        long diff = millis2 - millis1;
        differenceDayVidage = (int) (diff / (24 * 60 * 60 * 1000));

        reglagedayVidage = pref.getInt("key_duree_rappel_vidage", 0);
        transform2(reglagedayVidage);

        if((differenceDayVidage <= reglagedayVidage2) && (differenceDayVidage >= 0)){
            return true;
        } else {
            return  false;
        }
    }


    private void transform2(int reglagedayVidage) {
        switch(reglagedayVidage){
            case 0:
                reglagedayVidage2 = 21;
                return;
            case 1:
                reglagedayVidage2 = 14;
                return;
            case 2:
                reglagedayVidage2 = 7;
                return;
            case 3:
                reglagedayVidage2 = 3;
                return;
            case 4:
                reglagedayVidage2 = 2;
        }
    }

    public void sendNoti(Context context, int notiID) {

        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //---PendingIntent to launch activity if the user selects
        // the notification---
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("fragment", "carte");
        //notificationIntent.putExtra("mText", info);

        PendingIntent contentIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            contentIntent = PendingIntent.getActivity(context, notiID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        } else{
            contentIntent = PendingIntent.getActivity(context, notiID, notificationIntent, 0);
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //create the notification
        Notification notif = new NotificationCompat.Builder(context, CarteFragment.id2)
                .setSmallIcon(R.drawable.ic_baseline_clear_24)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_baseline_access_time_24))
                .setWhen(System.currentTimeMillis()) //When the event occurred, now, since noti are stored by time.
                .setContentTitle("GoodDriver - Validité de carte") //Title message top row.
                .setContentText("Votre carte tachygraphe va le expirer "+ twoDigitString(dayOfMonthEcheance)+ "/" + twoDigitString(monthEcheance+1) + "/"+ twoDigitString(yearEcheance)) //message when looking at the notification, second row
                .setContentIntent(contentIntent) //what activity to open.
                .setChannelId(CarteFragment.id2)
                .setSound(alarmSound)
                .setAutoCancel(true) //allow auto cancel when pressed.
                .build(); //finally build and return a Notification.
        //Show the notification
        mManager.notify(100, notif);  //and if we want different notifications, use notiID here instead of 1.
    }

    public void sendNoti2(Context context, int notiID2) {

        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //---PendingIntent to launch activity if the user selects
        // the notification---
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("fragment", "carte");
        //notificationIntent.putExtra("mText", info);

        PendingIntent contentIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            contentIntent = PendingIntent.getActivity(context, notiID2, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        } else{
            contentIntent = PendingIntent.getActivity(context, notiID2, notificationIntent, 0);
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //create the notification
        Notification notif = new NotificationCompat.Builder(context, CarteFragment.id3)
                .setSmallIcon(android.R.drawable.stat_sys_upload_done)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_baseline_access_time_24))
                .setWhen(System.currentTimeMillis()) //When the event occurred, now, since noti are stored by time.
                .setContentTitle("GoodDriver - Vidage de carte") //Title message top row.
                .setContentText("Déchargez votre carte avant le : "+ twoDigitString(dayOfMonthProchainVidage)+ "/" + twoDigitString(monthProchainVidage + 1) + "/"+ twoDigitString(yearProchainVidage)) //message when looking at the notification, second row
                .setContentIntent(contentIntent) //what activity to open.
                .setChannelId(CarteFragment.id3)
                .setSound(alarmSound)
                .setAutoCancel(true) //allow auto cancel when pressed.
                .build(); //finally build and return a Notification.
        //Show the notification
        mManager.notify(200, notif);  //and if we want different notifications, use notiID here instead of 1.

    }

    private String twoDigitString(long number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

}
