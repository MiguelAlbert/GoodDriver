package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Calendar;

public class SimpleWidgetProvider2 extends AppWidgetProvider {

    SharedPreferences.Editor editor;
    private static final String REFRESH_WIDGET_ACTION2 = "REFRESH_WIDGET_ACTION";
    private static final String ACTION_APPWIDGET_ENABLED2 = "android.appwidget.action.ACTION_APPWIDGET_ENABLED";
    RemoteViews views;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        views = new RemoteViews(context.getPackageName(), R.layout.simple_widget2);

        for (int i=0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            // Create an Intent to launch ExampleActivity

            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            } else{
                pendingIntent = PendingIntent.getActivity(context, 0, intent,0);
            }

            Intent intent2 = new Intent(context.getApplicationContext(), MainActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent2.putExtra("fragment", "hebdo");
            PendingIntent pendingIntent2;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                pendingIntent2 = PendingIntent.getActivity(context, 100, intent2, PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            } else{
                pendingIntent2 = PendingIntent.getActivity(context, 100, intent2,0);
            }

            Intent refreshIntent = new Intent(context, SimpleWidgetProvider2.class);
            refreshIntent.setAction(SimpleWidgetProvider2.REFRESH_WIDGET_ACTION2);
            refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            refreshIntent.setData(Uri.parse(refreshIntent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 200, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

            SharedPreferences pref = context.getSharedPreferences("PrefReposHebdo", MODE_PRIVATE);
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
            Calendar calendarfin24 = (Calendar) calendardebut.clone();
            calendarfin24.add(Calendar.HOUR_OF_DAY, 24);
            Calendar calendarfin45 = (Calendar) calendardebut.clone();
            calendarfin45.add(Calendar.HOUR_OF_DAY, 45);
            int NomduJourDebut = calendardebut.get(Calendar.DAY_OF_WEEK);
            int NomduJourFin24 = calendarfin24.get(Calendar.DAY_OF_WEEK);
            int NomduJourFin45 = calendarfin45.get(Calendar.DAY_OF_WEEK);

            int Hour13 = calendarfin24.get(Calendar.HOUR_OF_DAY);
            int Minute13 = calendarfin24.get(Calendar.MINUTE);
            int Hour15 = calendarfin45.get(Calendar.HOUR_OF_DAY);
            int Minute15 = calendarfin45.get(Calendar.MINUTE);

            Calendar now = Calendar.getInstance();
            int HourUpdate = now.get(Calendar.HOUR_OF_DAY);
            int MinuteUpdate = now.get(Calendar.MINUTE);

            long milliSeconds1 = calendarfin45.getTimeInMillis();
            long milliSeconds2 = now.getTimeInMillis();
            long time15 = (milliSeconds1 - milliSeconds2);

            long milliSeconds3 = calendarfin24.getTimeInMillis();
            long milliSeconds4 = now.getTimeInMillis();
            long time13 = (milliSeconds3 - milliSeconds4);

            // Get the layout for the widget and attach an on-click listener
            // to the button.

            boolean reposHebdoOn = pref.getBoolean("key_widget_on", false);

            if(reposHebdoOn) {
                if (calendardebut.compareTo(now) > 0) {
                    views.setTextViewText(R.id.tvRepos9hRestant, "Non\nd??but??e");
                } else if (now.compareTo(calendarfin45) > 0) {
                    views.setTextViewText(R.id.tvRepos9hRestant, "Fin");
                } else {
                    views.setTextViewText(R.id.tvRepos9hRestant, formatMilliSecondsToTime(time15));
                }
                if (calendardebut.compareTo(now) > 0) {
                    views.setTextViewText(R.id.tvRepos11hRestant, "Non\nd??but??e");
                } else if (now.compareTo(calendarfin24) > 0) {
                    views.setTextViewText(R.id.tvRepos11hRestant, "Fin");
                } else {
                    views.setTextViewText(R.id.tvRepos11hRestant, formatMilliSecondsToTime(time13));
                }
                views.setTextViewText(R.id.textView3, String.format("Update\n" + "%02d:%02d", HourUpdate, MinuteUpdate));
                views.setTextViewText(R.id.textView, getDayName(NomduJourDebut - 1) + " " + String.format("%02d:%02d", heureDebut, minuteDebut));
                views.setTextViewText(R.id.tvRepos11hFin, getDayName(NomduJourFin24 - 1) + "\n" + String.format("%02d:%02d", Hour13, Minute13));
                views.setTextViewText(R.id.tvRepos9hFin, getDayName(NomduJourFin45 - 1) + "\n" + String.format("%02d:%02d", Hour15, Minute15));
                views.setOnClickPendingIntent(R.id.imageButton, refreshPendingIntent);
                views.setOnClickPendingIntent(R.id.imageView3, pendingIntent2);
                views.setOnClickPendingIntent(R.id.textView65, pendingIntent);
            } else{
                views.setTextViewText(R.id.textView3,"");
                views.setTextViewText(R.id.textView,"");
                views.setTextViewText(R.id.tvRepos11hFin,"");
                views.setTextViewText(R.id.tvRepos9hFin,"");
                views.setTextViewText(R.id.tvRepos9hRestant, "");
                views.setTextViewText(R.id.tvRepos11hRestant, "");
            }

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

            case REFRESH_WIDGET_ACTION2:
                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                //AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                onUpdate(context,appWidgetManager, new int[]{appWidgetId});
                break;
            case ACTION_APPWIDGET_ENABLED2:
                int appWidgetId2 = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                //AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
                AppWidgetManager appWidgetManager2 = AppWidgetManager.getInstance(context);
                onUpdate(context,appWidgetManager2, new int[]{appWidgetId2});
                break;
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context ctxt,
                                          AppWidgetManager mgr,
                                          int appWidgetId,
                                          Bundle newOptions) {
        RemoteViews views = new RemoteViews(ctxt.getPackageName(), R.layout.simple_widget2);

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
        int hours = (int) (milliseconds / (1000 * 60 * 60)) ;
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

