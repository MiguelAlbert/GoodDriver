package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;
import java.util.Random;

public class WorkerWidget extends Worker {

    Context context;
    SharedPreferences.Editor editor;

    public WorkerWidget(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;

    }

    @NonNull
    @Override
    public Result doWork() {
        Toast.makeText(context, "Worker", Toast.LENGTH_SHORT).show();

        // test3
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, SimpleWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int i=0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent3 = new Intent(context, SimpleWidgetProvider.class);
            intent3.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent3.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            context.sendBroadcast(intent3);
        }

        return Result.success();
    }

}
