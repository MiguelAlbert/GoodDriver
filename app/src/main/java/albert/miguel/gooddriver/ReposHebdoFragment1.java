package albert.miguel.gooddriver;



import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class ReposHebdoFragment1 extends Fragment {

    ImageButton imageButtonDateDebut,imageButtonHeureDebut,imageButtonDeleteDebut;
    static Context context;
    static TextView tvDateReposReduit;
    static TextView tvHeureReposReduit;
    static TextView tvTempsRestantReduit;
    static TextView tvDateReposNormal;
    static TextView tvHeureReposNormal;
    static TextView tvTempsRestantNormal;
    static TextView tVDateDebut;
    static TextView tvHeureDebut;
    static PieChartView pieChartView24;
    static PieChartView pieChartView45;
    Calendar now;
    static Calendar debut;
    static Calendar debutAdd24;
    static Calendar debutAdd45;
    Calendar calFin;
    private Timer timer;
    static SharedPreferences.Editor editorReposHebdo;
    private AdView mPublisherAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = Objects.requireNonNull(container).getContext();
        SharedPreferences pref = context.getSharedPreferences("PrefReposHebdo", MODE_PRIVATE);
        editorReposHebdo = pref.edit();

        View v = inflater.inflate(R.layout.fragment_repos_hebdo1,container,false);
        mPublisherAdView = v.findViewById(R.id.publisherAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);

        debut = Calendar.getInstance();
        calFin = Calendar.getInstance();
        debutAdd24 = Calendar.getInstance();
        debutAdd45 = Calendar.getInstance();

        timerGetHeure();

        pieChartView24 = v.findViewById(R.id.chart24);
        pieChartView45 = v.findViewById(R.id.chart45);

        tvDateReposReduit = (TextView) v.findViewById(R.id.tvReposPalettes);
        tvHeureReposReduit = (TextView) v.findViewById(R.id.tvHeureReposReduit);
        tvTempsRestantReduit = (TextView) v.findViewById(R.id.tvTempsRestantReduit);
        tvDateReposNormal = (TextView) v.findViewById(R.id.tvDateReposNormal);
        tvHeureReposNormal = (TextView) v.findViewById(R.id.tvHeureReposNormal);
        tvTempsRestantNormal = (TextView) v.findViewById(R.id.tvTempsRestantNormal);
        tVDateDebut = (TextView) v.findViewById(R.id.tvDateDebut);
        tvHeureDebut = (TextView) v.findViewById(R.id.tvHeureDebut);

        imageButtonDateDebut = (ImageButton) v.findViewById(R.id.imageButtonDateDebut);
        imageButtonHeureDebut = (ImageButton) v.findViewById(R.id.imageButtonHeureDebut);
        imageButtonDeleteDebut = (ImageButton) v.findViewById(R.id.imageButtonDeleteDebut);
        imageButtonDateDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDateDebut();
            }
        });
        imageButtonHeureDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeureDebut();
            }
        });
        imageButtonDeleteDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDateDebut();
            }
        });
        tVDateDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDateDebut();
            }
        });
        tvHeureDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeureDebut();
            }
        });
        testSiDonneeEnregistrees();
        return v;
    }


    @Override
    public void setMenuVisibility(boolean isvisible) {
        super.setMenuVisibility(isvisible);
        if (isvisible){
            Log.d("ReposHebdoFragment1", "ReposHebdoFragment1 is visible ");
            //testSiDonneeEnregistrees();
            //Toast.makeText( getView().getContext(), "ReposHebdoFragment1 is visible", Toast.LENGTH_SHORT).show();
        }else {
            Log.d("ReposHebdoFragment1", "ReposHebdoFragment1 is not visible ");
            //testSiDonneeEnregistrees();
            //Toast.makeText(getContext(), "ReposHebdoFragment1 is not visible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        testSiDonneeEnregistrees();
        updateWidget();
        Log.e("Frontales","resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        updateWidget();
        Log.e("Frontales", "Pause");
    }

    public void updateWidget(){
        Intent intent = new Intent(context, SimpleWidgetProvider2.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getActivity().getApplication())
                .getAppWidgetIds(new ComponentName(getActivity().getApplication(), SimpleWidgetProvider2.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

    public static void testSiDonneeEnregistrees() {
        SharedPreferences pref = context.getSharedPreferences("PrefReposHebdo", MODE_PRIVATE);
        editorReposHebdo = pref.edit();
        int monthDebut = pref.getInt("key_Debut_Month", 0);
        int yearDebut = pref.getInt("key_Debut_Year", 0);
        int dayOfMonthDebut = pref.getInt("key_Debut_Day", 0);
        int heureDebut = pref.getInt("key_Debut_Hour", 0);
        int minuteDebut = pref.getInt("key_Debut_Minute", 0);
        if(monthDebut == 0 && yearDebut == 0 && dayOfMonthDebut == 0 && heureDebut == 0 && minuteDebut == 0) {
            tvHeureDebut.setText("");
            tVDateDebut.setText("");
            tvDateReposNormal.setText("");
            tvHeureReposNormal.setText("");
            tvDateReposReduit.setText("");
            tvHeureReposReduit.setText("");
        } else {
            debut.set(Calendar.MONTH, monthDebut);
            debut.set(Calendar.YEAR, yearDebut);
            debut.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            debut.set(Calendar.HOUR_OF_DAY, heureDebut);
            debut.set(Calendar.MINUTE, minuteDebut);
            int NomduJourdebut = debut.get(Calendar.DAY_OF_WEEK);
            tvHeureDebut.setText(String.format("%02d:%02d",heureDebut,minuteDebut));
            tVDateDebut.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut ,(monthDebut + 1), yearDebut));
            add24hour();
            add45hour();
        }
    }

    private void selectDateDebut() {
        now = Calendar.getInstance();
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        if(tVDateDebut.getText().toString()==""){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        } else {
            yearDebut = debut.get(Calendar.YEAR);
            monthDebut = debut.get(Calendar.MONTH);
            dayOfMonthDebut = debut.get(Calendar.DAY_OF_MONTH);
            HourDebut = debut.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = debut.get(Calendar.MINUTE);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        debut.set(Calendar.HOUR_OF_DAY, HourDebut);
                        debut.set(Calendar.MINUTE, MinuteDebut);
                        debut.set(Calendar.YEAR, year);
                        debut.set(Calendar.MONTH, month);
                        debut.set(Calendar.DAY_OF_MONTH, day);
                        debut.set(Calendar.SECOND, 0);
                        debut.set(Calendar.MILLISECOND, 0);
                        int NomduJour = debut.get(Calendar.DAY_OF_WEEK);
                        tvHeureDebut.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
                        tVDateDebut.setText(getDayName(NomduJour-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
                        editorReposHebdo.putInt("key_Debut_Hour",HourDebut );
                        editorReposHebdo.putInt("key_Debut_Minute",MinuteDebut );
                        editorReposHebdo.putInt("key_Debut_Year",year );
                        editorReposHebdo.putInt("key_Debut_Month",month );
                        editorReposHebdo.putInt("key_Debut_Day",day );
                        editorReposHebdo.putBoolean("key_widget_on", true);
                        editorReposHebdo.apply(); // commit changes
                        add24hour();
                        add45hour();
                        updateWidget();
                    }
                }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();

    }
    private void selectHeureDebut() {
        now = Calendar.getInstance();
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        if(tvHeureDebut.getText().toString()==""){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        } else {
            yearDebut = debut.get(Calendar.YEAR);
            monthDebut = debut.get(Calendar.MONTH);
            dayOfMonthDebut = debut.get(Calendar.DAY_OF_MONTH);
            HourDebut = debut.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = debut.get(Calendar.MINUTE);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                debut.set(Calendar.HOUR_OF_DAY, hourOfDay);
                debut.set(Calendar.MINUTE, minutes);
                debut.set(Calendar.YEAR, yearDebut);
                debut.set(Calendar.MONTH, monthDebut);
                debut.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                debut.set(Calendar.SECOND, 0);
                debut.set(Calendar.MILLISECOND, 0);
                int NomduJour = debut.get(Calendar.DAY_OF_WEEK);
                tvHeureDebut.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tVDateDebut.setText(getDayName(NomduJour-1) + "\n" + String.format("%02d/%02d/%02d", dayOfMonthDebut, (monthDebut + 1), yearDebut));
                editorReposHebdo.putInt("key_Debut_Hour",hourOfDay );  // Saving int
                editorReposHebdo.putInt("key_Debut_Minute",minutes );
                editorReposHebdo.putInt("key_Debut_Year",yearDebut );  // Saving int
                editorReposHebdo.putInt("key_Debut_Month",monthDebut );  // Saving int
                editorReposHebdo.putInt("key_Debut_Day",dayOfMonthDebut );  // Saving int// Saving int
                editorReposHebdo.putBoolean("key_widget_on", true);
                editorReposHebdo.apply();// commit changes
                add24hour();
                add45hour();
                updateWidget();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();

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

    private static String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) );
        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }
    private static int formatMilliSecondsToHours(long milliseconds) {

        int hours = (int) ((milliseconds / (1000 * 60 * 60)) );
        return hours ;
    }
    private static String formatMilliSecondsToTimeMinute(long milliseconds) {

        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) );
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

    private void timerGetHeure() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        refreshUI();
                    }
                });
            }
        },0,1000);
    }

    private static void refreshUI() {
        pieChart24(0, 0);
        pieChart45(0, 0);
        Calendar maintenant = Calendar.getInstance();
        long milliSeconds1 = debutAdd24.getTimeInMillis();
        long milliSeconds2 = maintenant.getTimeInMillis();
        long milliSeconds3 = milliSeconds1 + 21*60*60*1000;
        long time = (milliSeconds1 - milliSeconds2);
        long time2 = (milliSeconds3 - milliSeconds2);

        if(tvHeureDebut.getText() == ""){
            tvTempsRestantReduit.setText("");
            tvTempsRestantNormal.setText("");
        }else {
            if(debutAdd24.compareTo(maintenant) < 0){
                tvTempsRestantReduit.setText("Repos réduit terminé");
                pieChart24Termine();
            } else if(debut.compareTo(maintenant) > 0){
                tvTempsRestantReduit.setText("Repos non débuté");
                pieChart24AFaire();
            } else{
                tvTempsRestantReduit.setText(formatMilliSecondsToTime(time));
                pieChart24(24-formatMilliSecondsToHours(time)-1, time);
            }
            if(debutAdd45.compareTo(maintenant) < 0){
                tvTempsRestantNormal.setText("Repos normal terminé");
                pieChart45Termine();
            } else if(debut.compareTo(maintenant) > 0){
                tvTempsRestantNormal.setText("Repos non débuté");
                pieChart45AFaire();
            } else{
                tvTempsRestantNormal.setText(formatMilliSecondsToTime(time2));
                pieChart45(45-formatMilliSecondsToHours(time2)-1, time2);
            }
        }

        //Toast.makeText(context, "Toutes les 5 secondes", Toast.LENGTH_SHORT).show();
    }

    private static void pieChart24AFaire() {

        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(0, Color.GRAY).setLabel("Fait : 00:00"));
        pieData.add(new SliceValue(24, ResourcesCompat.getColor(context.getResources(), R.color.red600, null)).setLabel("Restant : 24:00"));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.5f).setCenterText1("24h").setCenterText1FontSize(14).setCenterText1Color(Color.parseColor("#000000"));;
        pieChartView24.setPieChartData(pieChartData);
    }

    private static void pieChart24Termine() {

        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(24, Color.GRAY).setLabel("Fait : 24:00"));
        pieData.add(new SliceValue(0, ResourcesCompat.getColor(context.getResources(), R.color.red600, null)).setLabel("Restant : 00:00"));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.5f).setCenterText1("24h").setCenterText1FontSize(14).setCenterText1Color(Color.parseColor("#000000"));;
        pieChartView24.setPieChartData(pieChartData);
    }

    private static void pieChart24(int i, long time) {
        int a = i ;
        int b = 24  - a ;
        long timeRestant = (24 * 60 * 60 * 1000)-(time) ;
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(a, Color.GRAY).setLabel("Fait : " + formatMilliSecondsToTimeMinute(timeRestant)));
        pieData.add(new SliceValue(b, ResourcesCompat.getColor(context.getResources(), R.color.red600, null)).setLabel("Restant : " + formatMilliSecondsToTimeMinute(time)));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.5f).setCenterText1("24h").setCenterText1FontSize(14).setCenterText1Color(Color.parseColor("#000000"));;
        pieChartView24.setPieChartData(pieChartData);
    }

    private static void pieChart45AFaire() {

        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(0, Color.GRAY).setLabel("Fait : 00:00"));
        pieData.add(new SliceValue(45, ResourcesCompat.getColor(context.getResources(), R.color.red600, null)).setLabel("Restant : 45:00"));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.5f).setCenterText1("45h").setCenterText1FontSize(14).setCenterText1Color(Color.parseColor("#000000"));;
        pieChartView45.setPieChartData(pieChartData);
    }

    private static void pieChart45Termine() {

        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(45, Color.GRAY).setLabel("Fait : 45:00"));
        pieData.add(new SliceValue(0, ResourcesCompat.getColor(context.getResources(), R.color.red600, null)).setLabel("Restant : 00:00"));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.5f).setCenterText1("45h").setCenterText1FontSize(14).setCenterText1Color(Color.parseColor("#000000"));;
        pieChartView45.setPieChartData(pieChartData);
    }
    private static void pieChart45(int i, long time2) {
        int a = i ;
        int b = 45  - a ;
        long timeRestant = (45 * 60 * 60 * 1000)-(time2) ;
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(a, Color.GRAY).setLabel("Fait : " + formatMilliSecondsToTimeMinute(timeRestant)));
        pieData.add(new SliceValue(b, ResourcesCompat.getColor(context.getResources(), R.color.red600, null)).setLabel("Restant : "+ formatMilliSecondsToTimeMinute(time2)));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.5f).setCenterText1("45h").setCenterText1FontSize(14).setCenterText1Color(Color.parseColor("#000000"));;
        pieChartView45.setPieChartData(pieChartData);
    }

    @Override
    public void onDestroyView() {
        timer.cancel();
        super.onDestroyView();
        Log.d("Tag", "FragmentA.onDestroyView() has been called.");
    }

    private static void add45hour() {
        //Calendar maintenant = Calendar.getInstance();
        debutAdd45 = (Calendar) debut.clone();
        debutAdd45.add(debut.HOUR_OF_DAY, 45);
        debutAdd45.set(Calendar.SECOND, 0);
        debutAdd45.set(Calendar.MILLISECOND, 0);
        int NomduJour = debutAdd45.get(Calendar.DAY_OF_WEEK);
        tvDateReposNormal.setText(String.format(getDayName(NomduJour-1) + "\n" +"%02d/%02d/%02d",debutAdd45.get(Calendar.DAY_OF_MONTH) ,(debutAdd45.get(Calendar.MONTH) +1) , debutAdd45.get(Calendar.YEAR)));
        tvHeureReposNormal.setText(String.format("%02d:%02d", debutAdd45.get(Calendar.HOUR_OF_DAY), debutAdd45.get(Calendar.MINUTE)));
        refreshUI();
    }

    private static void add24hour() {
        //Calendar maintenant = Calendar.getInstance();
        //maintenant.set(Calendar.SECOND, 1);
        debutAdd24 = (Calendar) debut.clone();
        debutAdd24.add(debut.HOUR_OF_DAY, 24);
        debutAdd24.set(Calendar.SECOND, 0);
        debutAdd24.set(Calendar.MILLISECOND, 0);
        int NomduJour = debutAdd24.get(Calendar.DAY_OF_WEEK);
        tvDateReposReduit.setText(String.format(getDayName(NomduJour-1) + "\n" +"%02d/%02d/%02d",debutAdd24.get(Calendar.DAY_OF_MONTH) ,(debutAdd24.get(Calendar.MONTH) +1) , debutAdd24.get(Calendar.YEAR)));
        tvHeureReposReduit.setText(String.format("%02d:%02d",debutAdd24.get(Calendar.HOUR_OF_DAY) ,(debutAdd24.get(Calendar.MINUTE))));
        refreshUI();
    }


    static void deleteDateDebut() {
        pieChart24(0, 0);
        pieChart45(0, 0);
        tVDateDebut.setText("");
        tvHeureDebut.setText("");
        tvDateReposReduit.setText("");
        tvHeureReposReduit.setText("");
        tvTempsRestantReduit.setText("");
        tvDateReposNormal.setText("");
        tvHeureReposNormal.setText("");
        tvTempsRestantNormal.setText("");
        editorReposHebdo.putInt("key_Debut_Hour",0 );  // Saving int
        editorReposHebdo.putInt("key_Debut_Minute",0 );
        editorReposHebdo.putInt("key_Debut_Year",0 );  // Saving int
        editorReposHebdo.putInt("key_Debut_Month",0 );  // Saving int
        editorReposHebdo.putInt("key_Debut_Day",0 );  // Saving int// Saving int
        editorReposHebdo.putBoolean("key_widget_on", false);
        editorReposHebdo.apply();// commit changes
    }
}
