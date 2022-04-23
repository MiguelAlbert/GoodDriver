package albert.miguel.gooddriver;



import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class ReposHebdoFragment1 extends Fragment {

    ImageButton imageButtonDateDebut,imageButtonHeureDebut,imageButtonDeleteDebut;
    Context context;
    TextView tvDateReposReduit, tvHeureReposReduit, tvTempsRestantReduit, tvDateReposNormal,
            tvHeureReposNormal, tvTempsRestantNormal,tVDateDebut,
            tvHeureDebut;
    ProgressBar progressBar24, progressBar45;
    Calendar now, debut, debutAdd24, debutAdd45, calFin;
    private Timer timer;
    SharedPreferences.Editor editorReposHebdo;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        SharedPreferences pref = context.getSharedPreferences("PrefReposHebdo", MODE_PRIVATE);
        editorReposHebdo = pref.edit();
        debut = Calendar.getInstance();
        calFin = Calendar.getInstance();
        debutAdd24 = Calendar.getInstance();
        debutAdd45 = Calendar.getInstance();

        timerGetHeure();
        View v = inflater.inflate(R.layout.fragment_repos_hebdo1,container,false);

        progressBar24 = (ProgressBar) v.findViewById(R.id.progressBar24);
        progressBar45 = (ProgressBar) v.findViewById(R.id.progressBar45);

        tvDateReposReduit = (TextView) v.findViewById(R.id.tvDateReposReduit);
        tvHeureReposReduit = (TextView) v.findViewById(R.id.tvHeureReposReduit);
        tvTempsRestantReduit = (TextView) v.findViewById(R.id.tvTempsRestantReduit);
        tvDateReposNormal = (TextView) v.findViewById(R.id.tvDateReposNormal);
        tvHeureReposNormal = (TextView) v.findViewById(R.id.tvHeureReposNormal);
        tvTempsRestantNormal = (TextView) v.findViewById(R.id.tvTempsRestantNormal);
        tVDateDebut = (TextView) v.findViewById(R.id.tVDateDebut);
        tvHeureDebut = (TextView) v.findViewById(R.id.tvHeure1);

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

    private void testSiDonneeEnregistrees() {
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
                        editorReposHebdo.apply(); // commit changes
                        add24hour();
                        add45hour();
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
                editorReposHebdo.apply();// commit changes
                add24hour();
                add45hour();
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

    private String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) );
        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }
    private int formatMilliSecondsToHours(long milliseconds) {

        int hours = (int) ((milliseconds / (1000 * 60 * 60)) );
        return hours ;
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

    private void refreshUI() {
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
                progressBar24.setProgress(24);
            } else if(debut.compareTo(maintenant) > 0){
                tvTempsRestantReduit.setText("Repos non débuté");
                progressBar24.setProgress(0);
            } else{
                tvTempsRestantReduit.setText(formatMilliSecondsToTime(time));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar24.setProgress(24-formatMilliSecondsToHours(time)-1,true);
                } else{
                    progressBar24.setProgress(24-formatMilliSecondsToHours(time)-1);
                }
            }
            if(debutAdd45.compareTo(maintenant) < 0){
                tvTempsRestantNormal.setText("Repos normal terminé");
                progressBar45.setProgress(45);
            } else if(debut.compareTo(maintenant) > 0){
                tvTempsRestantNormal.setText("Repos non débuté");
                progressBar45.setProgress(0);
            } else{
                tvTempsRestantNormal.setText(formatMilliSecondsToTime(time2));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar45.setProgress(45-formatMilliSecondsToHours(time2)-1,true);
                }else {
                    progressBar45.setProgress(45-formatMilliSecondsToHours(time2)-1);
                }
            }
        }

        //Toast.makeText(context, "Toutes les 5 secondes", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        timer.cancel();
        super.onDestroyView();
        Log.d("Tag", "FragmentA.onDestroyView() has been called.");
    }

    private void add45hour() {
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

    private void add24hour() {
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


    private void deleteDateDebut() {
        tVDateDebut.setText("");
        tvHeureDebut.setText("");
        tvDateReposReduit.setText("");
        tvHeureReposReduit.setText("");
        tvTempsRestantReduit.setText("");
        tvDateReposNormal.setText("");
        tvHeureReposNormal.setText("");
        tvTempsRestantNormal.setText("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar24.setProgress(0,true);
            progressBar45.setProgress(0,true);
        } else {
            progressBar24.setProgress(0);
            progressBar45.setProgress(0);
        }
        editorReposHebdo.putInt("key_Debut_Hour",0 );  // Saving int
        editorReposHebdo.putInt("key_Debut_Minute",0 );
        editorReposHebdo.putInt("key_Debut_Year",0 );  // Saving int
        editorReposHebdo.putInt("key_Debut_Month",0 );  // Saving int
        editorReposHebdo.putInt("key_Debut_Day",0 );  // Saving int// Saving int
        editorReposHebdo.apply();// commit changes
    }
}
