package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;

public class DailyWorkerVidageCarte extends Worker {


    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int NotiID2 = 3;
    int yearVidage, monthVidage, dayOfMonthVidage;
    int yearProchainVidage, monthProchainVidage, dayOfMonthProchainVidage;
    int reglagedayVidage, reglagedayVidage2, differenceDayVidage;

    public DailyWorkerVidageCarte(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();

    }

    @NonNull
    @Override
    public Result doWork() {
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

        reglagedayVidage = pref.getInt("key_duree_rappel_vidage", 2);
        transform2(reglagedayVidage);

        if((differenceDayVidage <= reglagedayVidage2) && (differenceDayVidage >= 0)){
            sendNoti2(context, NotiID2);
        }

        return Result.success();
    }

    public void sendNoti2(Context context, int notiID2) {

        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //---PendingIntent to launch activity if the user selects
        // the notification---
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationIntent.putExtra("fragment", "carte");
        //notificationIntent.putExtra("mText", info);

        PendingIntent contentIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            contentIntent = PendingIntent.getActivity(context, notiID2, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
        } else{
            contentIntent = PendingIntent.getActivity(context, notiID2, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT );
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //create the notification
        Notification notif = new NotificationCompat.Builder(context, CarteFragment.id3)
                //.setSmallIcon(R.drawable.ic_logo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo))
                .setWhen(System.currentTimeMillis()) //When the event occurred, now, since noti are stored by time.
                .setContentTitle("GoodDriver - Vidage de carte") //Title message top row.
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Déchargez votre carte avant le : "+ twoDigitString(dayOfMonthProchainVidage)+ "/" + twoDigitString(monthProchainVidage + 1) + "/"+ twoDigitString(yearProchainVidage) +"\n" + differenceDayVidage + " jour(s) restant(s)"))
                //.setContentText("Déchargez votre carte avant le : "+ twoDigitString(dayOfMonthProchainVidage)+ "/" + twoDigitString(monthProchainVidage + 1) + "/"+ twoDigitString(yearProchainVidage)) //message when looking at the notification, second row
                .setContentIntent(contentIntent) //what activity to open.
                .setChannelId(CarteFragment.id3)
                .setSound(alarmSound)
                .setAutoCancel(true) //allow auto cancel when pressed.
                .build(); //finally build and return a Notification.
        //Show the notification
        mManager.notify(200, notif);  //and if we want different notifications, use notiID here instead of 1.

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
