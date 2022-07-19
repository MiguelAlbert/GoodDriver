package albert.miguel.gooddriver;


import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.POWER_SERVICE;
import static android.os.Build.VERSION.SDK_INT;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class AmplitudeFragment extends Fragment {

    static Context context;
    static TextView tVDateDebut;
    static TextView tvHeureDebut;
    static TextView tvTempsServiveNuit;
    static TextView tvNotificationTimeBefore;
    static TextView tvTempsRestant;
    static TextView tvPeriodeRisque;
    static TextView tvPeriodeRisque2;
    static TextView tvDateFinAmplitude;
    static TextView tvHeureFinAmplitude;
    static PieChartView pieChartView;
    static RadioButton radioButton11;
    static RadioButton radioButton9;
    static SharedPreferences.Editor editor;
    ImageButton imageButtonHeure, imageButtonDate, imageButtonDelete;
    static Calendar now = Calendar.getInstance();
    static Calendar calendardebut = now;
    static Calendar calendarfin = now;
    Switch switchAlarm;
    boolean booleanalarm;

    private Timer timer;

    public static final int requestCode = 9999;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public static String id1 = "test_channel_01";
    public static String id = "test_channel_boot";
    public static final String ACTION = "miguel.albert.AlarmManager.myAction";
    final int[] checkedItem = {-1};
    private static final String TAG_MY_FRAGMENT = "myFragment";

    private AdView mPublisherAdView;

    static final String[] listItems = new String[]{"15 mn", "30 mn", "45 mn", "1h", "1h30"};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = Objects.requireNonNull(container).getContext();

        timerGetHeure();

        View v = inflater.inflate(R.layout.fragment_amplitude,container,false);

        mPublisherAdView = v.findViewById(R.id.publisherAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);

        imageButtonHeure = (ImageButton) v.findViewById(R.id.imageButtonCalcul1);
        imageButtonHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeure();
            }
        });
        imageButtonDate = (ImageButton) v.findViewById(R.id.imageButtonHeureDebutJ5);
        imageButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        imageButtonDelete = (ImageButton) v.findViewById(R.id.imageButtonDelete);
        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        radioButton11 = (RadioButton) v.findViewById(R.id.radioButton11);
        radioButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reposChange();
            }
        });
        radioButton9 = (RadioButton) v.findViewById(R.id.radioButton9);
        radioButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reposChange();
            }
        });
        tvTempsServiveNuit = (TextView) v.findViewById(R.id.tvTempsServiveNuit);
        tvPeriodeRisque = (TextView) v.findViewById(R.id.tvPeriodeRisque);
        tvPeriodeRisque2 = (TextView) v.findViewById(R.id.tvPeriodeRisque2);
        tVDateDebut = (TextView) v.findViewById(R.id.tvDateDebut);
        tVDateDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        tvHeureDebut = (TextView) v.findViewById(R.id.tvHeureDebutJ2);
        tvHeureDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeure();
            }
        });
        tvHeureFinAmplitude = (TextView) v.findViewById(R.id.tvHeureReposReduit);
        tvDateFinAmplitude = (TextView) v.findViewById(R.id.tvReposPalettes);
        tvTempsRestant = (TextView) v.findViewById(R.id.tvTempsRestantReduit);
        tvNotificationTimeBefore = (TextView) v.findViewById(R.id.tvNotificationTimeBefore);
        tvNotificationTimeBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTimeRappel();
            }
        });
        pieChartView = v.findViewById(R.id.chart);

        createchannel();
        testSiValeursEnregistrees();

        switchAlarm = (Switch) v.findViewById(R.id.switchAlarm);
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (tvHeureDebut.getText() == "" || tVDateDebut.getText() == ""){
                        Toast.makeText(context, "Sélectionnez une heure de début", Toast.LENGTH_SHORT).show();
                        switchAlarm.setChecked(false);
                    } else{
                        if(now.compareTo(calendarfin) > 0){
                            Toast.makeText(context, "Amplitude terminée", Toast.LENGTH_SHORT).show();
                            switchAlarm.setChecked(false);
                        } else{
                            setAlarm();
                            disableSelected(false);
                        }

                    }

                } else {
                    disableSelected(true);
                    cancelAlarm();
                }
            }
        });
        return v;
    }

    private void timerGetHeure() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        affichageTempsRestant();
                    }
                });
            }
        },0,1000);
        //clearNotification();
    }

    @Override
    public void onResume() {
        createchannel();
        updateWidget();
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        booleanalarm = pref.getBoolean("Key_alarm",false);
        //Toast.makeText(context, "Alarm activée " + booleanalarm, Toast.LENGTH_SHORT).show();
        switchAlarm.setChecked(booleanalarm);
        NavigationView navigationView = (NavigationView) requireActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_amplitude).setChecked(true);
        super.onResume();
    }

    @Override
    public void onPause() {
        updateWidget();
        super.onPause();
    }
    @Override
    public void onStop() {
        updateWidget();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        timer.cancel();
        super.onDestroyView();
        Log.d("Tag", "FragmentA.onDestroyView() has been called.");
    }

    private static void createPieChart() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        int c = pref.getInt("key_Amplitude_Journaliere", 13);
        if (c == 13){c = 11;} else { c = 9;}
        long milliSeconds1 = calendarfin.getTimeInMillis();
        long milliSeconds2 = now.getTimeInMillis();
        long time = (milliSeconds1 - milliSeconds2);
        if(time <=0){
            int a = 24 - c;
            List<SliceValue> pieData = new ArrayList<>();
            pieData.add(new SliceValue(c, Color.GRAY).setLabel("Repos : "+ c + ":00"));
            pieData.add(new SliceValue(a, ResourcesCompat.getColor(context.getResources(), R.color.red600, null)).setLabel("Amplitude : "+ a + ":00"));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(10);
            pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.4f).setCenterText1("24:00").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#000000"));;
            pieChartView.setPieChartData(pieChartData);
        } else if (c == 11 && time >= 13*60*60*1000) {
            int a = 24 - c;
            List<SliceValue> pieData = new ArrayList<>();
            pieData.add(new SliceValue(c, Color.GRAY).setLabel("Repos : "+ c + ":00"));
            pieData.add(new SliceValue(a, ResourcesCompat.getColor(context.getResources(), R.color.red600, null)).setLabel("Amplitude : "+ a + ":00"));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(10);
            pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.4f).setCenterText1("24:00").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#000000"));;
            pieChartView.setPieChartData(pieChartData);
        } else if(c == 9 && time >= 15*60*60*1000){
            int a = 24 - c;
            List<SliceValue> pieData = new ArrayList<>();
            pieData.add(new SliceValue(c, Color.GRAY).setLabel("Repos : "+ c + ":00"));
            pieData.add(new SliceValue(a, ResourcesCompat.getColor(context.getResources(), R.color.red600, null)).setLabel("Amplitude : "+ a + ":00"));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(10);
            pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.4f).setCenterText1("24:00").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#000000"));;
            pieChartView.setPieChartData(pieChartData);
        } else {
            int minutes = (int) ((time / (1000*60)) % 60) ;
            int hours   = (int) ((time / (1000*60*60)) % 24);
            // time to heure minute
            int minutesfaites = 60 - minutes - 1;
            int hoursfaites = 24 - c - hours;
            if(minutesfaites == 59){
                minutesfaites = -1;
                hoursfaites++;
            }
            float a = 24 - c - Float.parseFloat(hours + "." + (minutes));
            float b = 24 - c - a ;
            List<SliceValue> pieData = new ArrayList<>();
            pieData.add(new SliceValue(c, Color.GRAY).setLabel("Repos : "+ c + ":00"));
            pieData.add(new SliceValue(a, ResourcesCompat.getColor(context.getResources(), R.color.red200, null)).setLabel("Fait "+ (hoursfaites-1) +":"+ twoDigitString(minutesfaites+1)));
            pieData.add(new SliceValue(b, ResourcesCompat.getColor(context.getResources(), R.color.red600, null)).setLabel("Restant "+ hours +":"+ (twoDigitString(minutes))));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(10);
            pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.4f).setCenterText1("24:00").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#000000"));;
            pieChartView.setPieChartData(pieChartData);
        }
    }

    private void disableSelected(boolean b) {
        tvHeureDebut.setClickable(b);
        tVDateDebut.setClickable(b);
        radioButton9.setClickable(b);
        radioButton11.setClickable(b);
        imageButtonHeure.setClickable(b);
        imageButtonDate.setClickable(b);
        imageButtonDelete.setClickable(b);
        tvNotificationTimeBefore.setClickable(b);
    }

    public static void testSiValeursEnregistrees() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        int tempsAmplitude = pref.getInt("key_Amplitude_Journaliere", 13);
        if(tempsAmplitude == 13){
            radioButton11.setChecked(true);
        }else{
            radioButton9.setChecked(true);
        }
        int monthDebut = pref.getInt("key_Debut_Month", 0);
        int yearDebut = pref.getInt("key_Debut_Year", 0);
        int dayOfMonthDebut = pref.getInt("key_Debut_Day", 0);
        int heureDebut = pref.getInt("key_Debut_Hour", 0);
        int minuteDebut = pref.getInt("key_Debut_Minute", 0);
        int dureeRappel = pref.getInt("key_duree_rappel", 0);
        tvNotificationTimeBefore.setText(listItems[dureeRappel] + " avant.");
        if(monthDebut == 0 && yearDebut == 0 && dayOfMonthDebut == 0 && heureDebut == 0 && minuteDebut == 0) {
            tvHeureDebut.setText("");
            tVDateDebut.setText("");
        }else {
            calendardebut.set(Calendar.MONTH, monthDebut);
            calendardebut.set(Calendar.YEAR, yearDebut);
            calendardebut.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendardebut.set(Calendar.HOUR_OF_DAY, heureDebut);
            calendardebut.set(Calendar.MINUTE, minuteDebut);
            calendardebut.set(Calendar.SECOND, 0);
            calendardebut.set(Calendar.MILLISECOND, 0);
            int NomduJourdebut = calendardebut.get(Calendar.DAY_OF_WEEK);
            tvHeureDebut.setText(String.format("%02d:%02d",heureDebut,minuteDebut));
            tVDateDebut.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut ,(monthDebut + 1), yearDebut));
            calendarfin = (Calendar) calendardebut.clone();
            calendarfin.add(calendardebut.HOUR_OF_DAY, tempsAmplitude);
            calendarfin.set(Calendar.SECOND, 0);
            calendarfin.set(Calendar.MILLISECOND, 0);
            int HourFin = calendarfin.get(Calendar.HOUR_OF_DAY);
            int MinuteFin = calendarfin.get(Calendar.MINUTE);
            int dayOfMonthFin = calendarfin.get(Calendar.DAY_OF_MONTH);
            int monthFin = calendarfin.get(Calendar.MONTH);
            int yearFin = calendarfin.get(Calendar.YEAR);
            int NomduJour = calendarfin.get(Calendar.DAY_OF_WEEK);
            tvHeureFinAmplitude.setText(String.format("%02d:%02d", HourFin, MinuteFin));
            tvDateFinAmplitude.setText(getDayName(NomduJour-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFin,(monthFin+1),yearFin));
            affichageTempsRestant();
        }
    }

    private static void affichageTempsRestant() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        now = Calendar.getInstance();
        calendardebut = Calendar.getInstance();
        int tempsAmplitude = pref.getInt("key_Amplitude_Journaliere", 13);
        int monthDebut = pref.getInt("key_Debut_Month", 0);
        int yearDebut = pref.getInt("key_Debut_Year", 0);
        int dayOfMonthDebut = pref.getInt("key_Debut_Day", 0);
        int heureDebut = pref.getInt("key_Debut_Hour", 0);
        int minuteDebut = pref.getInt("key_Debut_Minute", 0);
        calendardebut.set(Calendar.MONTH, monthDebut);
        calendardebut.set(Calendar.YEAR, yearDebut);
        calendardebut.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
        calendardebut.set(Calendar.HOUR_OF_DAY, heureDebut);
        calendardebut.set(Calendar.MINUTE, minuteDebut);
        calendardebut.set(Calendar.SECOND, 0);
        calendardebut.set(Calendar.MILLISECOND, 0);
        if(tVDateDebut.getText() =="" || tvHeureDebut.getText() ==""){
            tvTempsRestant.setText("");
        } else{
            long milliSeconds1 = calendarfin.getTimeInMillis();
            long milliSeconds2 = now.getTimeInMillis();
            long time = (milliSeconds1 - milliSeconds2);
            if(calendardebut.compareTo(now) > 0){
                tvTempsRestant.setText("Amplitude non débutée");
            } else if(now.compareTo(calendarfin) > 0){
                tvTempsRestant.setText("Amplitude terminée");
            } else{
                tvTempsRestant.setText(formatMilliSecondsToTime(time));
            }
        }
        if (tvHeureDebut.getText() == ""||tVDateDebut.getText() == "") {
            tvTempsServiveNuit.setText("");
            tvPeriodeRisque.setText("");
            tvPeriodeRisque2.setText("");
        } else{
            int testheuredebut = calendardebut.get(Calendar.HOUR_OF_DAY);
            int testminutedebut = calendardebut.get(Calendar.MINUTE);
            if(tempsAmplitude == 13){
                if(testheuredebut < 5){
                    tvPeriodeRisque.setText("2h à 5h");
                    tvPeriodeRisque.setTypeface(null, Typeface.BOLD);
                    tvPeriodeRisque.setTextColor(context.getResources().getColor(R.color.blue));
                }else if(testheuredebut > 13 ) {
                    tvPeriodeRisque.setText("2h à 5h");
                    tvPeriodeRisque.setTypeface(null, Typeface.BOLD);
                    tvPeriodeRisque.setTextColor(context.getResources().getColor(R.color.blue));
                }else {
                    tvPeriodeRisque.setText("");
                }

                if((testheuredebut < 15) && (testheuredebut >= 1) ){
                    tvPeriodeRisque2.setText("13h à 15h");
                    tvPeriodeRisque2.setTypeface(null, Typeface.BOLD);
                    tvPeriodeRisque2.setTextColor(context.getResources().getColor(R.color.blue));
                }else if ((testheuredebut == 0)&& (testminutedebut > 0) ) {
                    tvPeriodeRisque2.setText("13h à 15h");
                    tvPeriodeRisque2.setTypeface(null, Typeface.BOLD);
                    tvPeriodeRisque2.setTextColor(context.getResources().getColor(R.color.blue));
                }else if (testheuredebut == 0 ) {
                    tvPeriodeRisque2.setText("");
                }else {
                    tvPeriodeRisque2.setText("");
                }

                if(testheuredebut < 5 || testheuredebut >= 11){
                    tvTempsServiveNuit.setText("Temps de service maxi : 10h00");
                    tvTempsServiveNuit.setTypeface(null, Typeface.BOLD);
                    tvTempsServiveNuit.setTextColor(context.getResources().getColor(R.color.red600));
                }else {
                    tvTempsServiveNuit.setText("Temps de service maxi : 12h00");
                    tvTempsServiveNuit.setTypeface(null, Typeface.NORMAL);
                    tvTempsServiveNuit.setTextColor(context.getResources().getColor(R.color.black));
                }
                if(testheuredebut == 11 && testminutedebut == 0){
                    tvTempsServiveNuit.setText("Temps de service maxi : 12h00");
                    tvTempsServiveNuit.setTypeface(null, Typeface.NORMAL);
                    tvTempsServiveNuit.setTextColor(context.getResources().getColor(R.color.black));
                }
            }

            if(tempsAmplitude == 15){
                if( testheuredebut < 5){
                    tvPeriodeRisque.setText("2h à 5h");
                    tvPeriodeRisque.setTypeface(null, Typeface.BOLD);
                    tvPeriodeRisque.setTextColor(context.getResources().getColor(R.color.blue));
                }else if(testheuredebut > 11 || (testheuredebut == 11 && testminutedebut > 0 )) {
                    tvPeriodeRisque.setText("2h à 5h");
                    tvPeriodeRisque.setTypeface(null, Typeface.BOLD);
                    tvPeriodeRisque.setTextColor(context.getResources().getColor(R.color.blue));
                }
                else {
                    tvPeriodeRisque.setText("");
                }

                if(testheuredebut < 15){
                    tvPeriodeRisque2.setText("13h à 15h");
                    tvPeriodeRisque2.setTypeface(null, Typeface.BOLD);
                    tvPeriodeRisque2.setTextColor(context.getResources().getColor(R.color.blue));
                }else if(testheuredebut > 22 || (testheuredebut == 22 && testminutedebut > 0 )) {
                    tvPeriodeRisque2.setText("13h à 15h");
                    tvPeriodeRisque2.setTypeface(null, Typeface.BOLD);
                    tvPeriodeRisque2.setTextColor(context.getResources().getColor(R.color.blue));
                }
                else {
                    tvPeriodeRisque2.setText("");
                }

                if(testheuredebut < 5 || testheuredebut >= 9){
                    tvTempsServiveNuit.setText("Temps de service maxi : 10h00");
                    tvTempsServiveNuit.setTypeface(null, Typeface.BOLD);
                    tvTempsServiveNuit.setTextColor(context.getResources().getColor(R.color.red600));
                }else {
                    tvTempsServiveNuit.setText("Temps de service maxi : 12h00");
                    tvTempsServiveNuit.setTypeface(null, Typeface.NORMAL);
                    tvTempsServiveNuit.setTextColor(context.getResources().getColor(R.color.black));
                }
                if(testheuredebut == 9 && testminutedebut == 0){
                    tvTempsServiveNuit.setText("Temps de service maxi : 12h00");
                    tvTempsServiveNuit.setTypeface(null, Typeface.NORMAL);
                    tvTempsServiveNuit.setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        }
        createPieChart();
    }

    private static String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
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

    public void selectDate() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        int tempsAmplitude = pref.getInt("key_Amplitude_Journaliere", 13);
        if(tvHeureDebut.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_Month", 0);
            yearDebut = pref.getInt("key_Debut_Year", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_Day", 0);
            HourDebut = pref.getInt("key_Debut_Hour", 0);
            MinuteDebut = pref.getInt("key_Debut_Minute", 0);
            calendardebut.set(Calendar.MONTH, monthDebut);
            calendardebut.set(Calendar.YEAR, yearDebut);
            calendardebut.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendardebut.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendardebut.set(Calendar.MINUTE, MinuteDebut);
            calendardebut.set(Calendar.SECOND, 0);
            calendardebut.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendardebut.set(Calendar.HOUR_OF_DAY, HourDebut);
                        calendardebut.set(Calendar.MINUTE, MinuteDebut);
                        calendardebut.set(Calendar.MONTH, month);
                        calendardebut.set(Calendar.YEAR, year);
                        calendardebut.set(Calendar.DAY_OF_MONTH, day);
                        calendardebut.set(Calendar.SECOND, 0);
                        calendardebut.set(Calendar.MILLISECOND, 0);
                        int NomduJourdebut = calendardebut.get(Calendar.DAY_OF_WEEK);
                        editor.putInt("key_Debut_Hour",HourDebut );
                        editor.putInt("key_Debut_Minute",MinuteDebut );
                        editor.putInt("key_Debut_Year",year );
                        editor.putInt("key_Debut_Month",month );
                        editor.putInt("key_Debut_Day",day );
                        editor.putBoolean("key_widget_on", true);
                        editor.apply(); // commit changes
                        tvHeureDebut.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
                        tVDateDebut.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
                        calendarfin = (Calendar) calendardebut.clone();
                        calendarfin.add(calendardebut.HOUR_OF_DAY, tempsAmplitude);
                        calendarfin.set(Calendar.SECOND, 0);
                        calendarfin.set(Calendar.MILLISECOND, 0);
                        int HourFin = calendarfin.get(Calendar.HOUR_OF_DAY);
                        int MinuteFin = calendarfin.get(Calendar.MINUTE);
                        int dayOfMonthFin = calendarfin.get(Calendar.DAY_OF_MONTH);
                        int monthFin = calendarfin.get(Calendar.MONTH);
                        int yearFin = calendarfin.get(Calendar.YEAR);
                        int NomduJour = calendarfin.get(Calendar.DAY_OF_WEEK);
                        tvHeureFinAmplitude.setText(String.format("%02d:%02d", HourFin, MinuteFin));
                        tvDateFinAmplitude.setText(getDayName(NomduJour-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFin,(monthFin+1),yearFin));

                        affichageTempsRestant();
                    }
                }, yearDebut, monthDebut, dayOfMonthDebut);
        updateWidget();
        datePickerDialog.show();
    }


    public void selectHeure() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        int tempsAmplitude = pref.getInt("key_Amplitude_Journaliere", 13);
        if(tVDateDebut.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_Month", 0);
            yearDebut = pref.getInt("key_Debut_Year", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_Day", 0);
            HourDebut = pref.getInt("key_Debut_Hour", 0);
            MinuteDebut = pref.getInt("key_Debut_Minute", 0);
            calendardebut.set(Calendar.MONTH, monthDebut);
            calendardebut.set(Calendar.YEAR, yearDebut);
            calendardebut.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendardebut.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendardebut.set(Calendar.MINUTE, MinuteDebut);
            calendardebut.set(Calendar.SECOND, 0);
            calendardebut.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendardebut.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendardebut.set(Calendar.MINUTE, minutes);
                calendardebut.set(Calendar.MONTH, monthDebut);
                calendardebut.set(Calendar.YEAR, yearDebut);
                calendardebut.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendardebut.set(Calendar.SECOND, 0);
                calendardebut.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendardebut.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Debut_Hour",hourOfDay );  // Saving int
                editor.putInt("key_Debut_Minute",minutes );
                editor.putInt("key_Debut_Year",yearDebut );  // Saving int
                editor.putInt("key_Debut_Month",monthDebut );  // Saving int
                editor.putInt("key_Debut_Day",dayOfMonthDebut );  // Saving int// Saving int
                editor.putBoolean("key_widget_on", true);
                editor.apply();// commit changes
                tvHeureDebut.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tVDateDebut.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calendarfin = (Calendar) calendardebut.clone();
                calendarfin.add(calendardebut.HOUR_OF_DAY, tempsAmplitude);
                calendarfin.set(Calendar.SECOND, 0);
                calendarfin.set(Calendar.MILLISECOND, 0);
                int HourFin = calendarfin.get(Calendar.HOUR_OF_DAY);
                int MinuteFin = calendarfin.get(Calendar.MINUTE);
                int dayOfMonthFin = calendarfin.get(Calendar.DAY_OF_MONTH);
                int monthFin = calendarfin.get(Calendar.MONTH);
                int yearFin = calendarfin.get(Calendar.YEAR);
                int NomduJour = calendarfin.get(Calendar.DAY_OF_WEEK);
                tvHeureFinAmplitude.setText(String.format("%02d:%02d", HourFin, MinuteFin));
                tvDateFinAmplitude.setText(getDayName(NomduJour-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFin,(monthFin+1),yearFin));
                updateWidget();
                affichageTempsRestant();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }

    public void updateWidget(){
        Intent intent = new Intent(context, SimpleWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getActivity().getApplication())
                .getAppWidgetIds(new ComponentName(getActivity().getApplication(), SimpleWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

    public void selectTimeRappel() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        int dureeRappel = pref.getInt("key_duree_rappel", 0);
        // AlertDialog builder instance to build the alert dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // set the custom icon to the alert dialog
        alertDialog.setIcon(R.drawable.ic_baseline_more_time_24);

        // title of the alert dialog
        alertDialog.setTitle("Sélectionnez une durée");

        // list of the items to be displayed to
        // the user in the form of list
        // so that user can select the item from
        //final String[] listItems = new String[]{"15 mn", "30 mn", "45 mn", "1h", "1h30"};

        // the function setSingleChoiceItems is the function which builds
        // the alert dialog with the single item selection
        alertDialog.setSingleChoiceItems(listItems, dureeRappel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // update the selected item which is selected by the user
                // so that it should be selected when user opens the dialog next time
                // and pass the instance to setSingleChoiceItems method
                checkedItem[0] = which;

                // now also update the TextView which previews the selected item
                tvNotificationTimeBefore.setText(listItems[which] + " avant.");
                editor.putInt("key_duree_rappel",which );
                editor.apply(); // commit changes
                // when selected an item the dialog should be closed with the dismiss method
                dialog.dismiss();
            }
        });

        // set the negative button if the user
        // is not interested to select or change
        // already selected item
        /*
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

         */

        // create and build the AlertDialog instance
        // with the AlertDialog builder instance
        AlertDialog customAlertDialog = alertDialog.create();

        // show the alert dialog when the button is clicked
        customAlertDialog.show();
    }

    public static void delete() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        tvTempsServiveNuit.setText("");
        tvPeriodeRisque.setText("");
        tVDateDebut.setText("");
        tvHeureDebut.setText("");
        tvHeureFinAmplitude.setText("");
        tvDateFinAmplitude.setText("");
        tvTempsRestant.setText("");
        //Clear only one sharedpreferences
        //editor.remove("key_Date"); // will delete key key_name4
        //editor.apply();
        // Clear all sharedpreferences
        editor.clear();
        editor.apply();
        editor.putInt("key_Amplitude_Journaliere",13 );
        editor.putBoolean("key_widget_on", false);
        editor.apply();// commit changes
        radioButton11.toggle();
        now.clear();
        calendarfin.clear();
        createPieChart();
        //clearWidget();
    }

    private static void clearWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget);
        ComponentName thisWidget = new ComponentName(context, SimpleWidgetProvider.class);
        remoteViews.setTextViewText(R.id.textView, "00:00");
        remoteViews.setTextViewText(R.id.tvRepos11hRestant, " ");
        remoteViews.setTextViewText(R.id.tvRepos9hRestant, " ");
        remoteViews.setTextViewText(R.id.textView3, " ");
        remoteViews.setTextViewText(R.id.tvRepos9hFin, " ");
        remoteViews.setTextViewText(R.id.tvRepos11hFin, " ");
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    public void reposChange() {
        if(radioButton9.isChecked()){
            SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
            editor = pref.edit();
            editor.putInt("key_Amplitude_Journaliere",15 );
            editor.apply();// commit changes
            affichageTempsRestant();
            createPieChart();
        }
        if(radioButton11.isChecked()){
            SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
            editor = pref.edit();
            editor.putInt("key_Amplitude_Journaliere",13 );
            editor.apply();// commit changes
            affichageTempsRestant();
            createPieChart();
        }
        testSiValeursEnregistrees();

    }

    private void setAlarm() {
        testBatterieOptimisation();
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Key_alarm", true);
        editor.apply();
        int minutesEnMoins;
        int dureeRappel = pref.getInt("key_duree_rappel", 0);
        //final String[] listItems = new String[]{"15 mn", "30 mn", "45 mn", "1h", "1h30"};
        switch(dureeRappel) {
            case 0:
                minutesEnMoins = 15;
                break;
            case 1:
                minutesEnMoins = 30;
                break;
            case 2:
                minutesEnMoins = 45;
                break;
            case 3:
                minutesEnMoins = 60;
                break;
            case 4:
                minutesEnMoins = 90;
                break;
            default:
                minutesEnMoins = 0;
                break;
        }
        int tempsAmplitude = pref.getInt("key_Amplitude_Journaliere", 13);
        Calendar calendarAlarm = (Calendar) calendardebut.clone();
        calendarAlarm.add(Calendar.MINUTE, (tempsAmplitude*60) - minutesEnMoins);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("SomeAction");
        //String dateFin = tvDateFinAmplitude.getText().toString() + " " + tvHeureFinAmplitude.getText().toString();
        intent.putExtra("int_value", minutesEnMoins);

        //sendBroadcast(intent);
        if (SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE |PendingIntent.FLAG_UPDATE_CURRENT);
            if (alarmManager.canScheduleExactAlarms()){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendarAlarm.getTimeInMillis(), pendingIntent);
                Toast.makeText(context, "Notification activée", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent();
                if (SDK_INT >= Build.VERSION_CODES.O) {
                    i.setAction(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                    i.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                } else if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    i.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                    i.putExtra("app_package", context.getPackageName());
                    i.putExtra("app_uid", context.getApplicationInfo().uid);
                } else {
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:" + context.getPackageName()));
                }
                startActivity(i);
            }
        } else{
            pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendarAlarm.getTimeInMillis(), pendingIntent);
            }
            Toast.makeText(context, "Notification activée", Toast.LENGTH_SHORT).show();
        }

    }

    private void cancelAlarm() {
        editor.putBoolean("Key_alarm", false);
        editor.apply();
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("SomeAction");

        if (SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_NO_CREATE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent,  PendingIntent.FLAG_NO_CREATE);
        }
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if(pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(context, "Notification supprimée", Toast.LENGTH_SHORT).show();
        }
    }

    private void createchannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(id1,
                    getString(R.string.channel_name),  //name of the channel
                    NotificationManager.IMPORTANCE_DEFAULT);   //importance level
            //important level: default is is high on the phone.  high is urgent on the phone.  low is medium, so none is low?
            // Configure the notification channel.
            mChannel.setDescription(getString(R.string.channel_description));
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            nm.createNotificationChannel(mChannel);

        }
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

    private void testBatterieOptimisation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }
    }

}
