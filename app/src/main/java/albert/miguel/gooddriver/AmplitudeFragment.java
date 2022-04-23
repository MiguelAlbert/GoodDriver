package albert.miguel.gooddriver;


import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class AmplitudeFragment extends Fragment {

    Context context;
    TextView tVDateDebut,tvHeureDebut, tvTempsServiveNuit, tvNotificationTimeBefore,tvTempsRestant,tvHeureJour,tvDateDuJour,tvDateFinAmplitude,tvHeureFinAmplitude;
    PieChartView pieChartView;
    String[] selectedoption;
    RadioButton radioButton11, radioButton9;
    SharedPreferences.Editor editor;
    ImageButton imageButtonHeure, imageButtonDate, imageButtonDelete;
    Calendar now = Calendar.getInstance();
    Calendar calendardebut = now;
    Calendar calendarfin = now;
    Switch switchAlarm;

    private Timer timer;

    public static final int requestCode = 9999;

    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public static String id = "test_channel_01";
    public static final String ACTION = "miguel.albert.AlarmManager.myAction";
    final int[] checkedItem = {-1};
    private static final String TAG_MY_FRAGMENT = "myFragment";
    MainFragment mFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = container.getContext();

        timerGetHeure();

        View v = inflater.inflate(R.layout.fragment_amplitude,container,false);
        imageButtonHeure = (ImageButton) v.findViewById(R.id.imageButtonCalcul1);
        imageButtonHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeure(v);
            }
        });
        imageButtonDate = (ImageButton) v.findViewById(R.id.imageButtonDate);
        imageButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
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
                reposChange(v);
            }
        });
        radioButton9 = (RadioButton) v.findViewById(R.id.radioButton9);
        radioButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reposChange(v);
            }
        });
        tvTempsServiveNuit = (TextView) v.findViewById(R.id.tvTempsServiveNuit);
        tVDateDebut = (TextView) v.findViewById(R.id.tVDateDebut);
        tVDateDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
            }
        });
        tvHeureDebut = (TextView) v.findViewById(R.id.tvHeure1);
        tvHeureDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeure(v);
            }
        });
        tvHeureFinAmplitude = (TextView) v.findViewById(R.id.tvHeureReposReduit);
        tvDateFinAmplitude = (TextView) v.findViewById(R.id.tvDateReposReduit);
        //tvHeureJour = (TextView) v.findViewById(R.id.tvHeureJour);
        //tvDateDuJour = (TextView) v.findViewById(R.id.tvDateDuJour);
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
        testSiDateDebutEtFinEnregistree();

        switchAlarm = (Switch) v.findViewById(R.id.switchAlarm);
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (tvHeureDebut.getText() == "" || tVDateDebut.getText() == ""){
                        Toast.makeText(context, "Vous devez sélectionner une heure", Toast.LENGTH_SHORT).show();
                        switchAlarm.setChecked(false);
                    } else{
                        //Toast.makeText(context, "Heure paramétrée", Toast.LENGTH_SHORT).show();
                        setAlarm();
                        disableSelected(false);
                    }

                } else {
                    disableSelected(true);
                    //Toast.makeText(context, "switch false", Toast.LENGTH_SHORT).show();
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
        clearNotification();
        //testSiDateDebutEtFinEnregistree();
    }

    @Override
    public void onDestroyView() {
        timer.cancel();
        super.onDestroyView();
        Log.d("Tag", "FragmentA.onDestroyView() has been called.");
    }

    private void createPieChart() {
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
            pieData.add(new SliceValue(c, Color.GRAY).setLabel("Repos : "+ c + "h00"));
            pieData.add(new SliceValue(a, ResourcesCompat.getColor(getResources(), R.color.red600, null)).setLabel("Amplitude : "+ a + "h00"));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(10);
            pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.6f).setCenterText1("24h").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#000000"));;
            pieChartView.setPieChartData(pieChartData);
        } else if (c == 11 && time >= 13*60*60*1000) {
            int a = 24 - c;
            List<SliceValue> pieData = new ArrayList<>();
            pieData.add(new SliceValue(c, Color.GRAY).setLabel("Repos : "+ c + "h00"));
            pieData.add(new SliceValue(a, ResourcesCompat.getColor(getResources(), R.color.red600, null)).setLabel("Amplitude : "+ a + "h00"));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(10);
            pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.6f).setCenterText1("24h").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#000000"));;
            pieChartView.setPieChartData(pieChartData);
        } else if(c == 9 && time >= 15*60*60*1000){
            int a = 24 - c;
            List<SliceValue> pieData = new ArrayList<>();
            pieData.add(new SliceValue(c, Color.GRAY).setLabel("Repos : "+ c + "h00"));
            pieData.add(new SliceValue(a, ResourcesCompat.getColor(getResources(), R.color.red600, null)).setLabel("Amplitude : "+ a + "h00"));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(10);
            pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.6f).setCenterText1("24h").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#000000"));;
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
            pieData.add(new SliceValue(c, Color.GRAY).setLabel("Repos : "+ c + "h00"));
            pieData.add(new SliceValue(a, ResourcesCompat.getColor(getResources(), R.color.red200, null)).setLabel("Fait "+ (hoursfaites-1) +"h"+ twoDigitString(minutesfaites+1)));
            pieData.add(new SliceValue(b, ResourcesCompat.getColor(getResources(), R.color.red600, null)).setLabel("Restant "+ hours +"h"+ (twoDigitString(minutes))));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(10);
            pieChartData.setCenterCircleColor(Color.WHITE).setHasCenterCircle(true).setCenterCircleScale(0.6f).setCenterText1("24h").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#000000"));;
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
        tvNotificationTimeBefore.setClickable(b);
    }


    private void testSiDateDebutEtFinEnregistree() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        int tempsAmplitude = pref.getInt("key_Amplitude_Journaliere", 13);
        int monthDebut = pref.getInt("key_Debut_Month", 0);
        int yearDebut = pref.getInt("key_Debut_Year", 0);
        int dayOfMonthDebut = pref.getInt("key_Debut_Day", 0);
        int heureDebut = pref.getInt("key_Debut_Hour", 0);
        int minuteDebut = pref.getInt("key_Debut_Minute", 0);
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


    private void affichageTempsRestant() {
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
        } else{
            int testheuredebut = calendardebut.get(Calendar.HOUR_OF_DAY);
            int testminutedebut = calendardebut.get(Calendar.MINUTE);
            if(tempsAmplitude == 13){
                if(testheuredebut < 5 || testheuredebut >= 11){
                    tvTempsServiveNuit.setText("Attention !! Temps de service limité à 10h00");
                }else {
                    tvTempsServiveNuit.setText("Temps de service maxi de 12h00");
                }
                if(testheuredebut == 11 && testminutedebut == 0){
                    tvTempsServiveNuit.setText("Temps de service maxi de 12h00");
                }
            }

            if(tempsAmplitude == 15){
                if(testheuredebut < 5 || testheuredebut >= 9){
                    tvTempsServiveNuit.setText("Attention !! Temps de service limité à 10h00");
                }else {
                    tvTempsServiveNuit.setText("Temps de service maxi de 12h00");
                }
                if(testheuredebut == 9 && testminutedebut == 0){
                    tvTempsServiveNuit.setText("Temps de service maxi de 12h00");
                }
            }
        }
        createPieChart();
    }

    private String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
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




    public void selectDate(View v) {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        int tempsAmplitude = pref.getInt("key_Amplitude_Journaliere", 13);
        if(tvHeureDebut.getText().toString()==""){
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

        datePickerDialog.show();
    }


    public void selectHeure(View view) {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        int tempsAmplitude = pref.getInt("key_Amplitude_Journaliere", 13);
        if(tVDateDebut.getText().toString()==""){
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

                affichageTempsRestant();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }

    public void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
    }

    public void selectTimeRappel() {
        // AlertDialog builder instance to build the alert dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // set the custom icon to the alert dialog
        alertDialog.setIcon(R.drawable.ic_baseline_more_time_24);

        // title of the alert dialog
        alertDialog.setTitle("Sélectionnez une durée");

        // list of the items to be displayed to
        // the user in the form of list
        // so that user can select the item from
        final String[] listItems = new String[]{"15 mn", "30 mn", "45 mn", "1h", "1h30"};

        // the function setSingleChoiceItems is the function which builds
        // the alert dialog with the single item selection
        alertDialog.setSingleChoiceItems(listItems, checkedItem[0], new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // update the selected item which is selected by the user
                // so that it should be selected when user opens the dialog next time
                // and pass the instance to setSingleChoiceItems method
                checkedItem[0] = which;

                // now also update the TextView which previews the selected item
                tvNotificationTimeBefore.setText(listItems[which] + " avant.");

                // when selected an item the dialog should be closed with the dismiss method
                dialog.dismiss();
            }
        });

        // set the negative button if the user
        // is not interested to select or change
        // already selected item
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // create and build the AlertDialog instance
        // with the AlertDialog builder instance
        AlertDialog customAlertDialog = alertDialog.create();

        // show the alert dialog when the button is clicked
        customAlertDialog.show();
    }



    public void delete() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
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
        editor.apply();// commit changes
        radioButton11.toggle();
        now.clear();
        calendarfin.clear();
        createPieChart();
    }

    public void reposChange(View view) {
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
        testSiDateDebutEtFinEnregistree();

    }

    private void setAlarm() {
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,2);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("SomeAction");
        intent.putExtra("int_value", 10);
        //sendBroadcast(intent);
        if (SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE |PendingIntent.FLAG_UPDATE_CURRENT);
            if (alarmManager.canScheduleExactAlarms()){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(context, "Notification activée", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    i.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    i.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
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
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
            Toast.makeText(context, "Notification activée", Toast.LENGTH_SHORT).show();
        }

    }

    private void cancelAlarm() {

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
            NotificationChannel mChannel = new NotificationChannel(id,
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

}
