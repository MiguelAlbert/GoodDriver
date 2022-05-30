package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DailyTaskCarte extends Worker {

    private SharedPreferences mPreferences;

    private static final String mPreferncesName = "MyPerfernces";

    Context context;

    public DailyTaskCarte(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        mPreferences = context.getSharedPreferences(mPreferncesName, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Result doWork() {


        String getstatus = mPreferences.getString("Status1","No Task Perform");
        if (!getstatus.equalsIgnoreCase("Frist")){

            mPreferences = context.getSharedPreferences(mPreferncesName, MODE_PRIVATE);
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.apply();
            sendNotification("Daily Notification", "Second ");
            Log.e("currentTime", "");

        }else {
            Log.e("chack","Not Frist");
            sendNotification("Notification First time","Nothing is save in SharedPreferences");
        }


        return Result.success();
    }

    private void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }
}
