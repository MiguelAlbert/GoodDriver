package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class SimpleWidgetProvider extends AppWidgetProvider {

    SharedPreferences.Editor editor;
    public static final String EXTRA_CLICKED_FILE = "EXTRA_CLICKED_FILE";
    public static final String ACTION_WIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    private static final String LIST_ITEM_CLICKED_ACTION = "LIST_ITEM_CLICKED_ACTION";
    private static final String REFRESH_WIDGET_ACTION = "REFRESH_WIDGET_ACTION";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //Toast.makeText(context, "Update",Toast.LENGTH_SHORT).show();
        // Perform this loop procedure for each widget that belongs to this
        // provider.
        for (int i=0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            // Create an Intent to launch ExampleActivity
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.simple_widget);
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                pendingIntent = PendingIntent.getActivity(context, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            } else{
                pendingIntent = PendingIntent.getActivity(context, 10, intent,0);
            }

            Intent intent2 = new Intent(context.getApplicationContext(), MainActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent2.putExtra("fragment", "amplitude");
            PendingIntent pendingIntent2;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                pendingIntent2 = PendingIntent.getActivity(context, 20, intent2, PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            } else{
                pendingIntent2 = PendingIntent.getActivity(context, 20, intent2,0);
            }

            Intent refreshIntent = new Intent(context, SimpleWidgetProvider.class);
            refreshIntent.setAction(SimpleWidgetProvider.REFRESH_WIDGET_ACTION);
            refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            refreshIntent.setData(Uri.parse(refreshIntent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 30, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

            SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
            editor = pref.edit();
            int monthDebut = pref.getInt("key_Debut_Month", 0);
            int yearDebut = pref.getInt("key_Debut_Year", 0);
            int dayOfMonthDebut = pref.getInt("key_Debut_Day", 0);
            int heureDebut = pref.getInt("key_Debut_Hour", 0);
            int minuteDebut = pref.getInt("key_Debut_Minute", 0);
            Calendar calendardebut = Calendar.getInstance();
            calendardebut.set(Calendar.MONTH, monthDebut);
            calendardebut.set(Calendar.YEAR, yearDebut);
            calendardebut.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendardebut.set(Calendar.HOUR_OF_DAY, heureDebut);
            calendardebut.set(Calendar.MINUTE, minuteDebut);
            calendardebut.set(Calendar.SECOND, 0);
            calendardebut.set(Calendar.MILLISECOND, 0);
            Calendar calendarfin13 = (Calendar) calendardebut.clone();
            calendarfin13.add(Calendar.HOUR_OF_DAY, 13);
            Calendar calendarfin15 = (Calendar) calendardebut.clone();
            calendarfin15.add(Calendar.HOUR_OF_DAY, 15);
            int NomduJourDebut = calendardebut.get(Calendar.DAY_OF_WEEK);
            int NomduJourFin13 = calendarfin13.get(Calendar.DAY_OF_WEEK);
            int NomduJourFin15 = calendarfin15.get(Calendar.DAY_OF_WEEK);

            int Hour13 = calendarfin13.get(Calendar.HOUR_OF_DAY);
            int Minute13 = calendarfin13.get(Calendar.MINUTE);
            int Hour15 = calendarfin15.get(Calendar.HOUR_OF_DAY);
            int Minute15 = calendarfin15.get(Calendar.MINUTE);

            Calendar now = Calendar.getInstance();
            int HourUpdate = now.get(Calendar.HOUR_OF_DAY);
            int MinuteUpdate = now.get(Calendar.MINUTE);

            long milliSeconds1 = calendarfin15.getTimeInMillis();
            long milliSeconds2 = now.getTimeInMillis();
            long time15 = (milliSeconds1 - milliSeconds2);

            long milliSeconds3 = calendarfin13.getTimeInMillis();
            long milliSeconds4 = now.getTimeInMillis();
            long time13 = (milliSeconds3 - milliSeconds4);

            // Get the layout for the widget and attach an on-click listener
            // to the button.

            if(calendardebut.compareTo(now) > 0){
                views.setTextViewText(R.id.tvRepos9hRestant, "Non\ndébutée");
            } else if(now.compareTo(calendarfin15) > 0){
                views.setTextViewText(R.id.tvRepos9hRestant, "Fin");
            } else{
                views.setTextViewText(R.id.tvRepos9hRestant, formatMilliSecondsToTime(time15));
            }
            if(calendardebut.compareTo(now) > 0){
                views.setTextViewText(R.id.tvRepos11hRestant, "Non\ndébutée");
            } else if(now.compareTo(calendarfin13) > 0){
                views.setTextViewText(R.id.tvRepos11hRestant, "Fin");
            } else{
                views.setTextViewText(R.id.tvRepos11hRestant, formatMilliSecondsToTime(time13));
            }

            views.setTextViewText(R.id.textView3,String.format("Update\n" + "%02d:%02d", HourUpdate, MinuteUpdate));
            views.setTextViewText(R.id.textView,getDayName(NomduJourDebut-1) + " " + String.format("%02d:%02d", heureDebut, minuteDebut));
            views.setTextViewText(R.id.tvRepos11hFin,getDayName(NomduJourFin13-1) + "\n" + String.format("%02d:%02d", Hour13, Minute13));
            views.setTextViewText(R.id.tvRepos9hFin,getDayName(NomduJourFin15-1) + "\n" + String.format("%02d:%02d", Hour15, Minute15));
            views.setOnClickPendingIntent(R.id.imageButton, refreshPendingIntent);
            views.setOnClickPendingIntent(R.id.imageView3, pendingIntent2);
            views.setOnClickPendingIntent(R.id.textView65, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget.
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        switch (intent.getAction()) {
            case LIST_ITEM_CLICKED_ACTION:

                break;
            case REFRESH_WIDGET_ACTION:
                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                //AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                onUpdate(context,appWidgetManager, new int[]{appWidgetId});
                break;
            case ACTION_WIDGET_UPDATE:

                break;
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context ctxt,
                                          AppWidgetManager mgr,
                                          int appWidgetId,
                                          Bundle newOptions) {
        RemoteViews views = new RemoteViews(ctxt.getPackageName(), R.layout.simple_widget);

        if(newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)> 300){
            views.setTextViewTextSize(R.id.textView, TypedValue.COMPLEX_UNIT_SP, 17);
            views.setTextViewTextSize(R.id.tvRepos11hFin, TypedValue.COMPLEX_UNIT_SP, 17);
            views.setTextViewTextSize(R.id.tvRepos9hFin, TypedValue.COMPLEX_UNIT_SP, 17);
            views.setTextViewTextSize(R.id.tvRepos11hRestant, TypedValue.COMPLEX_UNIT_SP, 17);
            views.setTextViewTextSize(R.id.tvRepos9hRestant, TypedValue.COMPLEX_UNIT_SP, 17);
            views.setTextViewTextSize(R.id.textView65, TypedValue.COMPLEX_UNIT_SP, 17);
            views.setTextViewTextSize(R.id.textView169, TypedValue.COMPLEX_UNIT_SP, 17);
            views.setTextViewTextSize(R.id.textView69, TypedValue.COMPLEX_UNIT_SP, 17);
            mgr.updateAppWidget(appWidgetId, views);
            onUpdate(ctxt,mgr, new int[]{appWidgetId});
            //Toast.makeText(ctxt, "Taille",Toast.LENGTH_SHORT).show();
        }
        if(newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)<= 300){
            views.setTextViewTextSize(R.id.textView, TypedValue.COMPLEX_UNIT_SP, 13);
            views.setTextViewTextSize(R.id.tvRepos11hFin, TypedValue.COMPLEX_UNIT_SP, 13);
            views.setTextViewTextSize(R.id.tvRepos9hFin, TypedValue.COMPLEX_UNIT_SP, 13);
            views.setTextViewTextSize(R.id.tvRepos11hRestant, TypedValue.COMPLEX_UNIT_SP, 13);
            views.setTextViewTextSize(R.id.tvRepos9hRestant, TypedValue.COMPLEX_UNIT_SP, 13);
            views.setTextViewTextSize(R.id.textView65, TypedValue.COMPLEX_UNIT_SP, 13);
            views.setTextViewTextSize(R.id.textView169, TypedValue.COMPLEX_UNIT_SP, 13);
            views.setTextViewTextSize(R.id.textView69, TypedValue.COMPLEX_UNIT_SP, 13);
            mgr.updateAppWidget(appWidgetId, views);
            onUpdate(ctxt,mgr, new int[]{appWidgetId});
            //Toast.makeText(ctxt, "Taille",Toast.LENGTH_SHORT).show();
        }
    }

    private static String formatMilliSecondsToTime(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60) +1;
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        if(minutes == 60){
            minutes = 0;
            hours ++ ;
        }
        return twoDigitString(hours) + ":" + twoDigitString(minutes) ;
    }
    private static String twoDigitString(long number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public static String getDayName(int day){
        switch(day){
            case 0:
                return "Dimanche";
            case 1:
                return "Lundi";
            case 2:
                return "Mardi";
            case 3:
                return "Mercredi";
            case 4:
                return "Jeudi";
            case 5:
                return  "Vendredi";
            case 6:
                return "Samedi";
        }
        return "Worng Day";
    }
}

