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

public class DailyWorkerEcheanceCarte extends Worker {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int reglageday, reglageday2, differenceDay;
    int yearEcheance, monthEcheance, dayOfMonthEcheance;
    int NotiID = 2;
    Context context;

    public DailyWorkerEcheanceCarte(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
    }

    @NonNull
    @Override
    public Result doWork() {

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
            sendNoti(context, NotiID);
        }

        return Result.success();
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
                //.setSmallIcon(R.drawable.ic_logo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo))
                .setWhen(System.currentTimeMillis()) //When the event occurred, now, since noti are stored by time.
                .setContentTitle("GoodDriver - Validité de carte") //Title message top row.
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Votre carte tachygraphe va le expirer "+ twoDigitString(dayOfMonthEcheance)+ "/" + twoDigitString(monthEcheance+1) + "/"+ twoDigitString(yearEcheance) +"\n" + differenceDay + " jour(s) restant(s)"))
                //.setContentText("Votre carte tachygraphe va le expirer "+ twoDigitString(dayOfMonthEcheance)+ "/" + twoDigitString(monthEcheance+1) + "/"+ twoDigitString(yearEcheance)) //message when looking at the notification, second row
                .setContentIntent(contentIntent) //what activity to open.
                .setChannelId(CarteFragment.id2)
                .setSound(alarmSound)
                .setAutoCancel(true) //allow auto cancel when pressed.
                .build(); //finally build and return a Notification.
        //Show the notification
        mManager.notify(100, notif);  //and if we want different notifications, use notiID here instead of 1.
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
}
