package albert.miguel.gooddriver;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION.SDK_INT;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Objects;


public class CarteFragment extends Fragment {

    Context context;
    SharedPreferences.Editor editor;
    EditText etNumeroCarte;
    ImageButton imageButtonPhoto, imageButtonDelete, imageButtonDate1, imageButtonDate2;
    TextView tvDateEcheance,tvDateDechargement;
    public static String id = "test_channel_02";
    Switch switchAlarm, switchAlarm2;
    boolean booleanalarm;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    public static int ALARM_TYPE_RTC = 100;

    public static final int requestCode = 9998;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = Objects.requireNonNull(container).getContext();

        View v = inflater.inflate(R.layout.fragment_carte,container,false);
        tvDateEcheance = (TextView) v.findViewById(R.id.tvDateEcheance);
        tvDateDechargement = (TextView) v.findViewById(R.id.tvDateDechargement);
        imageButtonDate1 = (ImageButton) v.findViewById(R.id.imageButtonDate1);
        imageButtonDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate1();
            }
        });
        imageButtonDate2 = (ImageButton) v.findViewById(R.id.imageButtonDate2);
        imageButtonDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate2();
            }
        });
        tvDateDechargement = (TextView) v.findViewById(R.id.tvDateDechargement);
        switchAlarm = (Switch) v.findViewById(R.id.switchAlarm);
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setAlarmEcheance();
                    disableSelected(false);
                    /*
                    if (tvDateDechargement.getText() == "" ){
                        Toast.makeText(context, "Sélectionnez date d'échéance", Toast.LENGTH_SHORT).show();
                        switchAlarm.setChecked(false);
                    } else{

                        }*/
                } else {
                    disableSelected(true);
                    //Toast.makeText(context, "switch false", Toast.LENGTH_SHORT).show();
                    cancelAlarmEcheance();
                }
            }
        });
        testSiValeursEnregistrees();
        createchannel();
        return v;
    }

    private void testSiValeursEnregistrees() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        int yearDebut = pref.getInt("key_Echeance_Year", 0);
        int monthDebut = pref.getInt("key_Echeance_Month", 0);
        int dayOfMonthDebut = pref.getInt("key_Echeance_Day", 0);
        if(monthDebut == 0 && yearDebut == 0 && dayOfMonthDebut == 0) {
            tvDateEcheance.setText("");
        }else {
            Calendar finEcheance = Calendar.getInstance();
            finEcheance.set(Calendar.MONTH, monthDebut);
            finEcheance.set(Calendar.YEAR, yearDebut);
            finEcheance.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            int NomduJourdebut = finEcheance.get(Calendar.DAY_OF_WEEK);
            tvDateEcheance.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut ,(monthDebut + 1), yearDebut));
        }
        int yearDebut2 = pref.getInt("key_dechargement_Year", 0);
        int monthDebut2 = pref.getInt("key_dechargement_Month", 0);
        int dayOfMonthDebut2 = pref.getInt("key_dechargement_Day", 0);
        if(monthDebut2 == 0 && yearDebut2 == 0 && dayOfMonthDebut2 == 0) {
            tvDateDechargement.setText("");
        }else {
            Calendar dateVidage = Calendar.getInstance();
            dateVidage.set(Calendar.MONTH, monthDebut2);
            dateVidage.set(Calendar.YEAR, yearDebut2);
            dateVidage.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut2);
            int NomduJourdebut2 = dateVidage.get(Calendar.DAY_OF_WEEK);
            tvDateDechargement.setText(getDayName(NomduJourdebut2-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut2 ,(monthDebut2 + 1), yearDebut2));
        }
    }



    @Override
    public void onResume() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        booleanalarm = pref.getBoolean("Key_alarm_echeance",false);
        //Toast.makeText(context, "Alarm activée " + booleanalarm, Toast.LENGTH_SHORT).show();
        switchAlarm.setChecked(booleanalarm);
        super.onResume();
    }

    private void createchannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(id,
                    getString(R.string.channel_name2),  //name of the channel
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

    private void disableSelected(boolean b) {

        //imageButtonDelete.setClickable(b);

    }


    private void setAlarmEcheance() {
        //get calendar instance to be able to select what time notification should be scheduled
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //Setting time of the day (8am here) when notification will be sent every day (default)
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.add(calendar.DAY_OF_MONTH, 1);


        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction("SomeAction");
        intent.putExtra("int_value", 10);
        //Setting intent to class where Alarm broadcast message will be handled

        if (SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE |PendingIntent.FLAG_UPDATE_CURRENT);
            if (alarmManager.canScheduleExactAlarms()){
                alarmManager.setInexactRepeating(AlarmManager.RTC,
                        calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
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
                alarmManager.setInexactRepeating(AlarmManager.RTC,
                        calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
            //Toast.makeText(context, "Notification activée", Toast.LENGTH_SHORT).show();
        }



        if (SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_IMMUTABLE |PendingIntent.FLAG_UPDATE_CURRENT);
        } else{
            pendingIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        alarmManager.setInexactRepeating(AlarmManager.RTC,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "Notification activée", Toast.LENGTH_SHORT).show();
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Key_alarm_echeance", true);
        editor.apply();
    }

    private void cancelAlarmEcheance() {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction("SomeAction");

        if (SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_NO_CREATE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent,  PendingIntent.FLAG_NO_CREATE);
        }
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if(pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(context, "Notification supprimée", Toast.LENGTH_SHORT).show();
        }
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Key_alarm_echeance", false);
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


    private void selectDate1() {
        Calendar now = Calendar.getInstance();
        Calendar finEcheance = Calendar.getInstance();
        int yearDebut,monthDebut,dayOfMonthDebut;
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateEcheance.getText().toString()==""){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
        }else{
            yearDebut = pref.getInt("key_Echeance_Year", 0);
            monthDebut = pref.getInt("key_Echeance_Month", 0);
            dayOfMonthDebut = pref.getInt("key_Echeance_Day", 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        finEcheance.set(Calendar.YEAR, yearDebut);
                        finEcheance.set(Calendar.MONTH, monthDebut);
                        finEcheance.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                        int NomduJour = finEcheance.get(Calendar.DAY_OF_WEEK);
                        editor.putInt("key_Echeance_Year",year );  // Saving int
                        editor.putInt("key_Echeance_Month",month );  // Saving int
                        editor.putInt("key_Echeance_Day",day );  // Saving int// Saving int
                        editor.apply();// commit changes
                        tvDateEcheance.setText(getDayName(NomduJour-1) + "\n" + String.format("%02d/%02d/%02d",day,(month+1),year));
                    }
                }, yearDebut, monthDebut, dayOfMonthDebut);

        datePickerDialog.show();
    }

    private void selectDate2() {
        Calendar now = Calendar.getInstance();
        Calendar dateDechargement = Calendar.getInstance();
        int yearDebut,monthDebut,dayOfMonthDebut;
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();

        if(tvDateDechargement.getText().toString()==""){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
        }else{
            yearDebut = pref.getInt("key_dechargement_Year", 0);
            monthDebut = pref.getInt("key_dechargement_Month", 0);
            dayOfMonthDebut = pref.getInt("key_dechargement_Day", 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dateDechargement.set(Calendar.MONTH, month);
                        dateDechargement.set(Calendar.YEAR, year);
                        dateDechargement.set(Calendar.DAY_OF_MONTH, day);
                        int NomduJour = dateDechargement.get(Calendar.DAY_OF_WEEK);
                        editor.putInt("key_dechargement_Year",year );  // Saving int
                        editor.putInt("key_dechargement_Month",month );  // Saving int
                        editor.putInt("key_dechargement_Day",day );  // Saving int// Saving int
                        editor.apply();// commit changes
                        tvDateDechargement.setText(getDayName(NomduJour-1) + "\n" + String.format("%02d/%02d/%02d",day,(month+1),year));
                    }
                }, yearDebut, monthDebut, dayOfMonthDebut);

        datePickerDialog.show();
    }

}
