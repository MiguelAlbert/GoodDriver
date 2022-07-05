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


public class AmplitudeFragment2 extends Fragment {

    static Context context;
    static TextView tVDateDebut, tVDateFin;
    static TextView tvHeureDebut, tvHeureFin;
    static TextView tvDateRepriseNormal, tvDateRepriseReduit,tvHeureRepriseNormal, tvHeureRepriseReduit;
    static SharedPreferences.Editor editor;
    ImageButton imageButtonHeure, imageButtonHeureFin, imageButtonDate,imageButtonDateFin, imageButtonDelete, imageButtonDeleteFin;
    static Calendar calendardebut = Calendar.getInstance();
    static Calendar calendarFin = Calendar.getInstance();
    private AdView mPublisherAdView;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = Objects.requireNonNull(container).getContext();


        View v = inflater.inflate(R.layout.fragment_amplitude2,container,false);

        mPublisherAdView = v.findViewById(R.id.publisherAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);
        imageButtonHeureFin = (ImageButton) v.findViewById(R.id.imageButtonHeureFin);
        imageButtonHeureFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeureFin(v);
            }
        });
        imageButtonHeure = (ImageButton) v.findViewById(R.id.imageButtonCalcul1);
        imageButtonHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeure(v);
            }
        });
        imageButtonDate = (ImageButton) v.findViewById(R.id.imageButtonCalcul);
        imageButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
            }
        });
        imageButtonDateFin = (ImageButton) v.findViewById(R.id.imageButtonDateFin);
        imageButtonDateFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDateFin(v);
            }
        });
        imageButtonDelete = (ImageButton) v.findViewById(R.id.imageButtonDelete);
        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmplitudeFragment.delete();
                delete();
            }
        });
        imageButtonDeleteFin = (ImageButton) v.findViewById(R.id.imageButtonDeleteFin);
        imageButtonDeleteFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFin();
            }
        });
        tVDateDebut = (TextView) v.findViewById(R.id.tvDateDebut);
        tVDateDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
            }
        });
        tvHeureDebut = (TextView) v.findViewById(R.id.tvHeureDebut);
        tvHeureDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeure(v);
            }
        });
        tVDateFin = (TextView) v.findViewById(R.id.tvDateFin);
        tVDateFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDateFin(v);
            }
        });
        tvHeureFin = (TextView) v.findViewById(R.id.tvHeureFin);
        tvHeureFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeureFin(v);
            }
        });
        tvDateRepriseNormal = (TextView) v.findViewById(R.id.tvDateRepriseNormal);
        tvDateRepriseReduit = (TextView) v.findViewById(R.id.tvDateRepriseReduit);
        tvHeureRepriseNormal = (TextView) v.findViewById(R.id.tvHeureRepriseNormal);
        tvHeureRepriseReduit = (TextView) v.findViewById(R.id.tvHeureRepriseReduit);
        testSiValeursEnregistrees();
        return v;
    }

    @Override
    public void onResume() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        testSiValeursEnregistrees();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag", "FragmentA.onDestroyView() has been called.");
    }


    private void disableSelected(boolean b) {
        tvHeureDebut.setClickable(b);
        tVDateDebut.setClickable(b);
        imageButtonHeure.setClickable(b);
        imageButtonDate.setClickable(b);
        imageButtonDelete.setClickable(b);
    }

    public static void testSiValeursEnregistrees() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
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
        }
        int monthFin = pref.getInt("key_Fin_Month", 0);
        int yearFin = pref.getInt("key_Fin_Year", 0);
        int dayOfMonthFin = pref.getInt("key_Fin_Day", 0);
        int heureFin = pref.getInt("key_Fin_Hour", 0);
        int minuteFin = pref.getInt("key_Fin_Minute", 0);
        if(monthFin == 0 && yearFin == 0 && dayOfMonthFin == 0 && heureFin == 0 && minuteFin == 0) {
            tvHeureFin.setText("");
            tVDateFin.setText("");
        }else {
            calendarFin.set(Calendar.MONTH, monthFin);
            calendarFin.set(Calendar.YEAR, yearFin);
            calendarFin.set(Calendar.DAY_OF_MONTH, dayOfMonthFin);
            calendarFin.set(Calendar.HOUR_OF_DAY, heureFin);
            calendarFin.set(Calendar.MINUTE, minuteFin);
            calendarFin.set(Calendar.SECOND, 0);
            calendarFin.set(Calendar.MILLISECOND, 0);
            int NomduJourfin = calendarFin.get(Calendar.DAY_OF_WEEK);
            tvHeureFin.setText(String.format("%02d:%02d",heureFin,minuteFin));
            tVDateFin.setText(getDayName(NomduJourfin-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFin ,(monthFin + 1), yearFin));
        }
        CalculHeureReprise();
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
        Calendar now = Calendar.getInstance();
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
                        editor.putBoolean("key_widget_on", true);
                        editor.apply(); // commit changes
                        tvHeureDebut.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
                        tVDateDebut.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
                        CalculHeureReprise();
                    }
                }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }

    public void selectHeure(View view) {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        Calendar now = Calendar.getInstance();
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
                editor.putBoolean("key_widget_on", true);
                editor.apply();// commit changes
                tvHeureDebut.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tVDateDebut.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                CalculHeureReprise();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }

    public void selectHeureFin(View view) {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        Calendar now = Calendar.getInstance();
        if(tvHeureFin.getText().toString()==""){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_Month", 0);
            yearDebut = pref.getInt("key_Fin_Year", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_Day", 0);
            HourDebut = pref.getInt("key_Fin_Hour", 0);
            MinuteDebut = pref.getInt("key_Fin_Minute", 0);
            calendarFin.set(Calendar.MONTH, monthDebut);
            calendarFin.set(Calendar.YEAR, yearDebut);
            calendarFin.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFin.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFin.set(Calendar.MINUTE, MinuteDebut);
            calendarFin.set(Calendar.SECOND, 0);
            calendarFin.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarFin.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarFin.set(Calendar.MINUTE, minutes);
                calendarFin.set(Calendar.MONTH, monthDebut);
                calendarFin.set(Calendar.YEAR, yearDebut);
                calendarFin.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarFin.set(Calendar.SECOND, 0);
                calendarFin.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarFin.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Fin_Hour",hourOfDay );  // Saving int
                editor.putInt("key_Fin_Minute",minutes );
                editor.putInt("key_Fin_Year",yearDebut );  // Saving int
                editor.putInt("key_Fin_Month",monthDebut );  // Saving int
                editor.putInt("key_Fin_Day",dayOfMonthDebut );  // Saving int// Saving int
                editor.apply();// commit changes
                tvHeureFin.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tVDateFin.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                CalculHeureReprise();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }

    public void selectDateFin(View v) {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        Calendar now = Calendar.getInstance();
        if(tVDateFin.getText().toString()==""){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_Month", 0);
            yearDebut = pref.getInt("key_Fin_Year", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_Day", 0);
            HourDebut = pref.getInt("key_Fin_Hour", 0);
            MinuteDebut = pref.getInt("key_Fin_Minute", 0);
            calendarFin.set(Calendar.MONTH, monthDebut);
            calendarFin.set(Calendar.YEAR, yearDebut);
            calendarFin.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFin.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFin.set(Calendar.MINUTE, MinuteDebut);
            calendarFin.set(Calendar.SECOND, 0);
            calendarFin.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendarFin.set(Calendar.HOUR_OF_DAY, HourDebut);
                        calendarFin.set(Calendar.MINUTE, MinuteDebut);
                        calendarFin.set(Calendar.MONTH, month);
                        calendarFin.set(Calendar.YEAR, year);
                        calendarFin.set(Calendar.DAY_OF_MONTH, day);
                        calendarFin.set(Calendar.SECOND, 0);
                        calendarFin.set(Calendar.MILLISECOND, 0);
                        int NomduJourdebut = calendarFin.get(Calendar.DAY_OF_WEEK);
                        editor.putInt("key_Fin_Hour",HourDebut );
                        editor.putInt("key_Fin_Minute",MinuteDebut );
                        editor.putInt("key_Fin_Year",year );
                        editor.putInt("key_Fin_Month",month );
                        editor.putInt("key_Fin_Day",day );
                        editor.apply(); // commit changes
                        tvHeureFin.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
                        tVDateFin.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
                        CalculHeureReprise();
                    }
                }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }

    private static void CalculHeureReprise() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        Calendar calendarDebutAmplitude = Calendar.getInstance();
        int monthDebutAmplitude = pref.getInt("key_Debut_Month", 0);
        int yearDebutAmplitude = pref.getInt("key_Debut_Year", 0);
        int dayOfMonthDebutAmplitude = pref.getInt("key_Debut_Day", 0);
        int HourDebutAmplitude = pref.getInt("key_Debut_Hour", 0);
        int MinuteDebutAmplitude = pref.getInt("key_Debut_Minute", 0);
        calendarDebutAmplitude.set(Calendar.MONTH, monthDebutAmplitude);
        calendarDebutAmplitude.set(Calendar.YEAR, yearDebutAmplitude);
        calendarDebutAmplitude.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutAmplitude);
        calendarDebutAmplitude.set(Calendar.HOUR_OF_DAY, HourDebutAmplitude);
        calendarDebutAmplitude.set(Calendar.MINUTE, MinuteDebutAmplitude);
        calendarDebutAmplitude.set(Calendar.SECOND, 0);
        calendarDebutAmplitude.set(Calendar.MILLISECOND, 0);

        Calendar calendarFinAmplitude = Calendar.getInstance();
        int monthFinAmplitude = pref.getInt("key_Fin_Month", 0);
        int yearFinAmplitude = pref.getInt("key_Fin_Year", 0);
        int dayOfMonthFinAmplitude = pref.getInt("key_Fin_Day", 0);
        int HourFinAmplitude = pref.getInt("key_Fin_Hour", 0);
        int MinuteFinAmplitude = pref.getInt("key_Fin_Minute", 0);
        calendarFinAmplitude.set(Calendar.MONTH, monthFinAmplitude);
        calendarFinAmplitude.set(Calendar.YEAR, yearFinAmplitude);
        calendarFinAmplitude.set(Calendar.DAY_OF_MONTH, dayOfMonthFinAmplitude);
        calendarFinAmplitude.set(Calendar.HOUR_OF_DAY, HourFinAmplitude);
        calendarFinAmplitude.set(Calendar.MINUTE, MinuteFinAmplitude);
        calendarFinAmplitude.set(Calendar.SECOND, 0);
        calendarFinAmplitude.set(Calendar.MILLISECOND, 0);

        Calendar calendarRepos11h = (Calendar) calendarFinAmplitude.clone();
        calendarRepos11h.add(Calendar.HOUR_OF_DAY, 11);
        int HourFin11h = calendarRepos11h.get(Calendar.HOUR_OF_DAY);
        int MinuteFin11h = calendarRepos11h.get(Calendar.MINUTE);
        int dayOfMonthFin11h = calendarRepos11h.get(Calendar.DAY_OF_MONTH);
        int monthFin11h = calendarRepos11h.get(Calendar.MONTH);
        int yearFin11h = calendarRepos11h.get(Calendar.YEAR);
        int NomduJour11h = calendarRepos11h.get(Calendar.DAY_OF_WEEK);
        tvHeureRepriseNormal.setText(String.format("%02d:%02d", HourFin11h, MinuteFin11h));
        tvDateRepriseNormal.setText(getDayName(NomduJour11h-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFin11h,(monthFin11h +1),yearFin11h));

        Calendar calendarRepos9h = (Calendar) calendarFinAmplitude.clone();
        calendarRepos9h.add(Calendar.HOUR_OF_DAY, 9);
        int HourFin9h = calendarRepos9h.get(Calendar.HOUR_OF_DAY);
        int MinuteFin9h = calendarRepos9h.get(Calendar.MINUTE);
        int dayOfMonthFin9h = calendarRepos9h.get(Calendar.DAY_OF_MONTH);
        int monthFin9h = calendarRepos9h.get(Calendar.MONTH);
        int yearFin9h = calendarRepos9h.get(Calendar.YEAR);
        int NomduJour9h = calendarRepos9h.get(Calendar.DAY_OF_WEEK);
        tvHeureRepriseReduit.setText(String.format("%02d:%02d", HourFin9h, MinuteFin9h));
        tvDateRepriseReduit.setText(getDayName(NomduJour9h-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFin9h,(monthFin9h +1),yearFin9h));

        Calendar calendar24h = (Calendar) calendarDebutAmplitude.clone();
        calendar24h.add(Calendar.HOUR_OF_DAY, 24);
        long millis1 = calendar24h.getTimeInMillis();
        long millis2 = calendarRepos9h.getTimeInMillis();
        long millis3 = calendarRepos11h.getTimeInMillis();
        long millis4 = calendarDebutAmplitude.getTimeInMillis();
        long millis5 = calendarFinAmplitude.getTimeInMillis();
        if(tvHeureDebut.getText() == "" || tVDateDebut.getText() == "" || tvHeureFin.getText() =="" || tVDateFin.getText() == ""){
            tvHeureRepriseNormal.setText("");
            tvDateRepriseNormal.setText("");
            tvHeureRepriseReduit.setText("");
            tvDateRepriseReduit.setText("");
        } else {
            if (millis4 >= millis5){
                tvDateRepriseNormal.setText("La date de fin doit être supérieure à la date de début");
                tvDateRepriseReduit.setText("La date de fin doit être supérieure à la date de début");
                tvHeureRepriseNormal.setText("");
                tvHeureRepriseReduit.setText("");
            }else {
                if(millis3 > millis1){
                    tvHeureRepriseNormal.setText("");
                    tvDateRepriseNormal.setText("Repos de 11 heures impossible\nAmplitude trop longue");
                }
                if(millis2 > millis1){
                    tvHeureRepriseReduit.setText("");
                    tvDateRepriseReduit.setText("Repos de 9 heures impossible\nAmplitude trop longue");
                }
            }
        }
    }

    public void delete() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        tVDateDebut.setText("");
        tvHeureDebut.setText("");
        tvDateRepriseNormal.setText("");
        tvHeureRepriseNormal.setText("");
        tvDateRepriseReduit.setText("");
        tvHeureRepriseReduit.setText("");
        editor.remove("key_Debut_Hour");
        editor.remove("key_Debut_Minute");
        editor.remove("key_Debut_Year");
        editor.remove("key_Debut_Month");
        editor.remove("key_Debut_Day");
        editor.apply();
    }

    private void deleteFin() {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        tVDateFin.setText("");
        tvHeureFin.setText("");
        tvDateRepriseNormal.setText("");
        tvHeureRepriseNormal.setText("");
        tvDateRepriseReduit.setText("");
        tvHeureRepriseReduit.setText("");
        editor.remove("key_Fin_Hour");  // Saving int
        editor.remove("key_Fin_Minute");
        editor.remove("key_Fin_Year");  // Saving int
        editor.remove("key_Fin_Month");  // Saving int
        editor.remove("key_Fin_Day");
        editor.apply();
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
