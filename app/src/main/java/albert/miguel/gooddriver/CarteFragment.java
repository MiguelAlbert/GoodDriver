package albert.miguel.gooddriver;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.navigation.NavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class CarteFragment extends Fragment {

    Context context;
    SharedPreferences.Editor editor;
    EditText etNumeroCarte;
    ImageButton imageButtonPhoto, imageButtonDelete, imageButtonDeleteEcheance, imageButtonDeleteVidage, imageButtonDate1, imageButtonDate2;
    TextView tvDateEcheance,tvDateDechargement, tvNotificationTimeBefore, tvNotificationTimeBeforeEnd, tvDateProchainDechargement;
    TextView tvValiditeJoursRestants, tvDechargementJoursRestants;
    public static String id2 = "test_channel_02";
    public static String id3 = "test_channel_03";
    SwitchCompat switchAlarm, switchAlarm2;
    boolean booleanalarm, booleanalarm2;
    final int[] checkedItem = {-1};
    final String[] listItems = new String[]{"90 jours", "60 jours", "45 jours", "30 jours", "15 jours"};
    final int[] checkedItem2 = {-1};
    final String[] listItems2 = new String[]{"21 jours", "14 jours", "7 jours", "3 jours", "2 jours"};

    private ImageView mPreviewIv;
    PeriodicWorkRequest periodicWorkRequest, periodicWorkRequest2;

    //Permission Code
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 2001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    UUID getId, getId2;
    private AdView mPublisherAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = Objects.requireNonNull(container).getContext();
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        View v = inflater.inflate(R.layout.fragment_carte,container,false);
        mPublisherAdView = v.findViewById(R.id.publisherAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);

        tvDateEcheance = (TextView) v.findViewById(R.id.tvDateEcheance);
        tvDateEcheance.setOnClickListener(v110 -> selectDate1());
        imageButtonDeleteVidage = (ImageButton) v.findViewById(R.id.imageButtonDeleteVidage);
        imageButtonDeleteVidage.setOnClickListener(v19 -> deleteDateVidage());
        imageButtonDelete = (ImageButton) v.findViewById(R.id.imageButtonDelete);
        imageButtonDelete.setOnClickListener(v18 -> deleteNumeroCarte());
        imageButtonPhoto = (ImageButton) v.findViewById(R.id.imageButtonPhoto);
        imageButtonPhoto.setOnClickListener(v17 -> showImageImportDialog());
        tvDateProchainDechargement = (TextView) v.findViewById(R.id.tvDateProchainDechargement);
        tvDateDechargement = (TextView) v.findViewById(R.id.tvDateDechargement);
        tvValiditeJoursRestants = (TextView) v.findViewById(R.id.tvValiditeJoursRestants);
        tvDechargementJoursRestants = (TextView) v.findViewById(R.id.tvDechargementJoursRestants);
        tvNotificationTimeBefore = (TextView) v.findViewById(R.id.tvNotificationTimeBefore);
        tvNotificationTimeBefore.setOnClickListener(v1 -> selectTimeRappelValidite());
        tvNotificationTimeBeforeEnd = (TextView) v.findViewById(R.id.tvNotificationTimeBeforeEnd);
        tvNotificationTimeBeforeEnd.setOnClickListener(v12 -> selectTimeRappelVidage());
        imageButtonDate1 = (ImageButton) v.findViewById(R.id.imageButtonDate1);
        imageButtonDate1.setOnClickListener(v13 -> selectDate1());
        imageButtonDeleteEcheance = (ImageButton) v.findViewById(R.id.imageButtonDeleteEcheance);
        imageButtonDeleteEcheance.setOnClickListener(v14 -> deleteDateEcheance());
        imageButtonDate2 = (ImageButton) v.findViewById(R.id.imageButtonDate2);
        imageButtonDate2.setOnClickListener(v15 -> selectDate2());
        tvDateDechargement = (TextView) v.findViewById(R.id.tvDateDechargement);
        tvDateDechargement.setOnClickListener(v16 -> selectDate2());
        switchAlarm = (SwitchCompat) v.findViewById(R.id.switchAlarm);
        switchAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                SharedPreferences pref1 = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
                editor = pref1.edit();
                booleanalarm = pref1.getBoolean("Key_alarm_echeance",false);
                if (tvDateEcheance.getText() == ""){
                    Toast.makeText(context, "Sélectionnez une date d'échéance", Toast.LENGTH_SHORT).show();
                    switchAlarm.setChecked(false);
                }else {
                    if(!booleanalarm){
                        setAlarmEcheance();
                        disableSelected(false);
                    }
                }
            } else {
                SharedPreferences pref1 = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
                editor = pref1.edit();
                booleanalarm = pref1.getBoolean("Key_alarm_echeance",false);
                if(booleanalarm) {
                    //Toast.makeText(context, "switch false", Toast.LENGTH_SHORT).show();
                    cancelAlarmEcheance();
                    disableSelected(true);
                }
            }
        });
        switchAlarm2 = (SwitchCompat) v.findViewById(R.id.switchAlarm2);
        switchAlarm2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                SharedPreferences pref12 = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
                editor = pref12.edit();
                booleanalarm2 = pref12.getBoolean("Key_alarm_vidage",false);
                if (tvDateDechargement.getText() == ""){
                    Toast.makeText(context, "Sélectionnez une date de dernier déchargement", Toast.LENGTH_SHORT).show();
                    switchAlarm2.setChecked(false);
                }else {
                    if(!booleanalarm2){
                        setAlarmDechargement();
                        disableSelected2(false);
                    }
                }
            } else {
                SharedPreferences pref12 = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
                editor = pref12.edit();
                booleanalarm2 = pref12.getBoolean("Key_alarm_vidage",false);
                if(booleanalarm2) {
                    //Toast.makeText(context, "switch false", Toast.LENGTH_SHORT).show();
                    cancelAlarmDechargement();
                    disableSelected2(true);
                }
            }
        });
        etNumeroCarte = (EditText) v.findViewById(R.id.etNumeroCarte);
        etNumeroCarte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String str = etNumeroCarte.getText().toString();
                if (str.isEmpty()) return;
                //String str2 = PerfectDecimal(str, 16, 0);
                editor.putString("key_Numero_Carte", str);
                editor.apply();
                }
        });
        testSiValeursEnregistrees();
        createchannel();
        createchannel2();

        mPreviewIv  = v.findViewById(R.id.imageIv);
        //camera permission
        cameraPermission = new String[] {Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //storage permission
        storagePermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        return v;
    }

    private void showImageImportDialog() {
        String[] items = {"Appareil photo", "Galerie"};
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);

        dialog.setTitle("Selectionnez une image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        //camera permission not allowed, request it
                        requestCameraPermission();
                    } else {
                        //permission allowed, take picture
                        pickCamera();
                    }
                }

                if (which == 1) {
                    if (!checkStoragePermission()) {
                        //storage permission not allowed, request it
                        requestStoragePermission();
                    } else {
                        //permission allowed, take picture
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show();
    }

    private void pickGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        //set intent type to image
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        //intent to take image from camera, it will also be save to storage to get high quality image
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPick"); //title of the picture
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text"); //title of the picture
        image_uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(requireActivity(), storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(), cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    //handle image result
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //got image from gallery now crop it
                CropImage.activity(Objects.requireNonNull(data).getData())
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guid lines
                        .start(requireActivity(), this);
            }

            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //got image from camera now crop it
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guid lines
                        .start(requireActivity(), this);
            }
        }

        //get cropped image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri(); //get image uri
                //set image to image view

                mPreviewIv.setImageURI(resultUri);

                //get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(context.getApplicationContext()).build();

                if (!recognizer.isOperational()) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    //get text from sb until there is no text
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append(" ");
                    }

                    //set text to edit text
                    etNumeroCarte.setText(sb.toString());
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //if there is any error show it
                Exception error = result.getError();
                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void selectTimeRappelValidite() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        int dureeRappel = pref.getInt("key_duree_rappel_echeance", 0);
        // AlertDialog builder instance to build the alert dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // set the custom icon to the alert dialog
        alertDialog.setIcon(R.drawable.ic_baseline_more_time_24);
        // title of the alert dialog
        alertDialog.setTitle("Sélectionnez une durée");
        alertDialog.setSingleChoiceItems(listItems, dureeRappel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItem[0] = which;
                tvNotificationTimeBefore.setText(listItems[which] + " avant.");
                editor.putInt("key_duree_rappel_echeance",which );
                editor.apply(); // commit changes
                // when selected an item the dialog should be closed with the dismiss method
                dialog.dismiss();
            }
        });
        AlertDialog customAlertDialog = alertDialog.create();
        customAlertDialog.show();
    }

    private void selectTimeRappelVidage() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        int dureeRappel = pref.getInt("key_duree_rappel_vidage", 2);
        // AlertDialog builder instance to build the alert dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // set the custom icon to the alert dialog
        alertDialog.setIcon(R.drawable.ic_baseline_more_time_24);
        // title of the alert dialog
        alertDialog.setTitle("Sélectionnez une durée");
        alertDialog.setSingleChoiceItems(listItems2, dureeRappel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItem2[0] = which;
                tvNotificationTimeBeforeEnd.setText(listItems2[which] + " avant.");
                editor.putInt("key_duree_rappel_vidage",which );
                editor.apply(); // commit changes
                // when selected an item the dialog should be closed with the dismiss method
                dialog.dismiss();
            }
        });
        AlertDialog customAlertDialog = alertDialog.create();
        customAlertDialog.show();
    }

    private void testSiValeursEnregistrees() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        int yearDebut = pref.getInt("key_Echeance_Year", 0);
        int monthDebut = pref.getInt("key_Echeance_Month", 0);
        int dayOfMonthDebut = pref.getInt("key_Echeance_Day", 0);
        int dureeRappel = pref.getInt("key_duree_rappel_echeance", 0);
        tvNotificationTimeBefore.setText(listItems[dureeRappel] + " avant.");
        int dureeRappel2 = pref.getInt("key_duree_rappel_vidage", 2);
        tvNotificationTimeBeforeEnd.setText(listItems2[dureeRappel2] + " avant.");
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

        String numeroCarte = pref.getString("key_Numero_Carte", "");
        if(numeroCarte.equals("")){
            etNumeroCarte.setText("");
        } else {
            etNumeroCarte.setText(""+numeroCarte);
        }

        int yearFinD = pref.getInt("key_fin_dechargement_Year", 0);
        int monthFinD = pref.getInt("key_fin_dechargement_Month", 0);
        int dayofmonthFinD = pref.getInt("key_fin_dechargement_Day", 0);
        if(yearFinD == 0 && monthFinD == 0 && dayofmonthFinD == 0) {
            tvDateProchainDechargement.setText("");
        }else {
            Calendar dateProchainVidage = Calendar.getInstance();
            dateProchainVidage.set(Calendar.MONTH, monthFinD);
            dateProchainVidage.set(Calendar.YEAR, yearFinD);
            dateProchainVidage.set(Calendar.DAY_OF_MONTH, dayofmonthFinD);
            int nomdujourFinD = dateProchainVidage.get(Calendar.DAY_OF_WEEK);
            tvDateProchainDechargement.setText(getDayName(nomdujourFinD-1) + "\n" + String.format("%02d/%02d/%02d",dayofmonthFinD,(monthFinD+1),yearFinD));
        }
        CalculJoursRestantsValidité();
        CalculJoursRestantsVidage();
    }

    private void CalculJoursRestantsValidité() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();

        if(tvDateEcheance.getText() == ""){
            tvValiditeJoursRestants.setText("");
        }else {
            Calendar now = Calendar.getInstance();
            Calendar DateEcheance = Calendar.getInstance();
            int yearEcheance = pref.getInt("key_Echeance_Year", 0);
            int monthEcheance = pref.getInt("key_Echeance_Month", 0);
            int dayOfMonthEcheance = pref.getInt("key_Echeance_Day", 0);
            DateEcheance.set(Calendar.YEAR, yearEcheance);
            DateEcheance.set(Calendar.MONTH, monthEcheance);
            DateEcheance.set(Calendar.DAY_OF_MONTH, dayOfMonthEcheance);

            long millis1 = now.getTimeInMillis();
            long millis2 = DateEcheance.getTimeInMillis();

            long diff = millis2 - millis1;
            int differenceDay = (int) (diff / (24 * 60 * 60 * 1000));

            LocalDate firstDate = null;
            LocalDate secondDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                firstDate = LocalDate.of(yearEcheance, monthEcheance, dayOfMonthEcheance);
                secondDate = LocalDate.of(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                Period period = Period.between(secondDate, firstDate);
                tvValiditeJoursRestants.setText(period.getYears() + " an(s) "
                        + period.getMonths() + " mois et "
                        + period.getDays() + " jour(s) restant(s)");

            } else{
                tvValiditeJoursRestants.setText(differenceDay + " jour(s) restant(s).");
            }
        }

    }

    private void CalculJoursRestantsVidage() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();

        if(tvDateDechargement.getText() == ""){
            tvDechargementJoursRestants.setText("");
        }else {
            Calendar now = Calendar.getInstance();
            Calendar DateVidage = Calendar.getInstance();
            int yearEcheance = pref.getInt("key_dechargement_Year", 0);
            int monthEcheance = pref.getInt("key_dechargement_Month", 0);
            int dayOfMonthEcheance = pref.getInt("key_dechargement_Day", 0);
            DateVidage.set(Calendar.YEAR, yearEcheance);
            DateVidage.set(Calendar.MONTH, monthEcheance);
            DateVidage.set(Calendar.DAY_OF_MONTH, dayOfMonthEcheance);
            DateVidage.add(Calendar.DAY_OF_MONTH, 28);

            long millis1 = now.getTimeInMillis();
            long millis2 = DateVidage.getTimeInMillis();

            long diff = millis2 - millis1;
            int differenceDay = (int) (diff / (24 * 60 * 60 * 1000));
            tvDechargementJoursRestants.setText(differenceDay + " jour(s) restant(s).");
        }

    }

    @Override
    public void onResume() {
        createchannel();
        createchannel2();
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        booleanalarm = pref.getBoolean("Key_alarm_echeance",false);
        //Toast.makeText(context, "Alarm activée " + booleanalarm, Toast.LENGTH_SHORT).show();
        switchAlarm.setChecked(booleanalarm);
        if(booleanalarm){
            disableSelected(false);
        } else {
            disableSelected(true);
        }
        booleanalarm2 = pref.getBoolean("Key_alarm_vidage",false);
        //Toast.makeText(context, "Alarm activée " + booleanalarm, Toast.LENGTH_SHORT).show();
        switchAlarm2.setChecked(booleanalarm2);
        if(booleanalarm2){
            disableSelected2(false);
        } else {
            disableSelected2(true);
        }
        NavigationView navigationView = (NavigationView) requireActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_carte).setChecked(true);
        super.onResume();
    }

    private void createchannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(id2,
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

    private void createchannel2() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(id3,
                    getString(R.string.channel_name3),  //name of the channel
                    NotificationManager.IMPORTANCE_DEFAULT);   //importance level
            //important level: default is is high on the phone.  high is urgent on the phone.  low is medium, so none is low?
            // Configure the notification channel.
            mChannel.setDescription(getString(R.string.channel_description));
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            nm.createNotificationChannel(mChannel);

        }
    }

    private void disableSelected(boolean b) {
        tvDateEcheance.setClickable(b);
        imageButtonDate1.setClickable(b);
        imageButtonDeleteEcheance.setClickable(b);
        tvNotificationTimeBefore.setClickable(b);
        //imageButtonDelete.setClickable(b);

    }

    private void disableSelected2(boolean b) {
        tvDateDechargement.setClickable(b);
        imageButtonDate2.setClickable(b);
        imageButtonDeleteVidage.setClickable(b);
        tvNotificationTimeBeforeEnd.setClickable(b);
        //imageButtonDelete.setClickable(b);

    }

    private void setAlarmEcheance() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Calendar now = Calendar.getInstance();
        long diffInMillisec = calendar.getTimeInMillis() - now.getTimeInMillis();
        long diffInMin = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec);

        periodicWorkRequest = new PeriodicWorkRequest.Builder(DailyWorkerEcheanceCarte.class,
                1 , TimeUnit.DAYS)
                .setInitialDelay(diffInMin, TimeUnit.MINUTES)
                .addTag("tagEcheance")
                .build();

        getId = periodicWorkRequest.getId();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("DailyTaskEcheance"
                , ExistingPeriodicWorkPolicy.REPLACE
                ,periodicWorkRequest);

        Toast.makeText(context, "Notification échéance activée", Toast.LENGTH_SHORT).show();
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Key_alarm_echeance", true);
        editor.putString("key_uuid", String.valueOf(getId));
        editor.apply();

    }

    private void cancelAlarmEcheance() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        String UUID_string = pref.getString("key_uuid",null);
        getId = UUID.fromString(UUID_string);
        WorkManager.getInstance(context).cancelWorkById(getId);
        editor = pref.edit();
        editor.putBoolean("Key_alarm_echeance", false);
        editor.apply();
        Toast.makeText(context, "Notification échéance supprimée", Toast.LENGTH_SHORT).show();
    }

    private void setAlarmDechargement() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Calendar now = Calendar.getInstance();
        long diffInMillisec = calendar.getTimeInMillis() - now.getTimeInMillis();
        long diffInMin = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec);

        periodicWorkRequest2 = new PeriodicWorkRequest.Builder(DailyWorkerVidageCarte.class,
                1 , TimeUnit.DAYS)
                .setInitialDelay(diffInMin, TimeUnit.MINUTES)
                .addTag("tagDechargement")
                .build();
        getId2 = periodicWorkRequest2.getId();
        WorkManager.getInstance(context).enqueueUniquePeriodicWork("DailyTaskDechargement"
                , ExistingPeriodicWorkPolicy.REPLACE
                ,periodicWorkRequest2);
        Toast.makeText(context, "Notification déchargement activée", Toast.LENGTH_SHORT).show();
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("key_uuid2", String.valueOf(getId2));
        editor.putBoolean("Key_alarm_vidage", true);
        editor.apply();
    }

    private void cancelAlarmDechargement() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        String UUID_string = pref.getString("key_uuid2",null);
        getId2 = UUID.fromString(UUID_string);
        WorkManager.getInstance(context).cancelWorkById(getId2);
        editor = pref.edit();
        editor.putBoolean("Key_alarm_vidage", false);
        editor.apply();
        Toast.makeText(context, "Notification déchargement supprimée", Toast.LENGTH_SHORT).show();
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
                        finEcheance.set(Calendar.YEAR, year);
                        finEcheance.set(Calendar.MONTH, month);
                        finEcheance.set(Calendar.DAY_OF_MONTH, day);
                        finEcheance.set(Calendar.HOUR_OF_DAY, 0);
                        finEcheance.set(Calendar.MINUTE, 0);
                        finEcheance.set(Calendar.SECOND, 0);
                        finEcheance.set(Calendar.MILLISECOND,0);
                        //initiation du calendrier du jour actuel
                        Calendar now = Calendar.getInstance();
                        now.set(Calendar.HOUR_OF_DAY, 0);
                        now.set(Calendar.MINUTE, 0);
                        now.set(Calendar.SECOND, 0);
                        now.set(Calendar.MILLISECOND,0);
                        if (finEcheance.compareTo(now) >= 0){
                            int NomduJour = finEcheance.get(Calendar.DAY_OF_WEEK);
                            editor.putInt("key_Echeance_Year",year );  // Saving int
                            editor.putInt("key_Echeance_Month",month );  // Saving int
                            editor.putInt("key_Echeance_Day",day );  // Saving int// Saving int
                            editor.apply();// commit changes
                            tvDateEcheance.setText(getDayName(NomduJour-1) + "\n" + String.format("%02d/%02d/%02d",day,(month+1),year));
                            CalculJoursRestantsValidité();
                        } else {
                            tvDateEcheance.setText("");
                            Toast.makeText(context, "La date de l'échéance doit être supérieure à la date du jour" + booleanalarm, Toast.LENGTH_SHORT).show();
                        }
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
                        dateDechargement.set(Calendar.HOUR_OF_DAY, 0);
                        dateDechargement.set(Calendar.MINUTE, 0);
                        dateDechargement.set(Calendar.SECOND, 0);
                        dateDechargement.set(Calendar.MILLISECOND, 0);
                        Calendar now = Calendar.getInstance();
                        now.set(Calendar.HOUR_OF_DAY, 0);
                        now.set(Calendar.MINUTE, 0);
                        now.set(Calendar.SECOND, 0);
                        now.set(Calendar.MILLISECOND,0);
                        if (dateDechargement.compareTo(now) <= 0) {
                            int NomduJour = dateDechargement.get(Calendar.DAY_OF_WEEK);
                            editor.putInt("key_dechargement_Year",year );  // Saving int
                            editor.putInt("key_dechargement_Month",month );  // Saving int
                            editor.putInt("key_dechargement_Day",day );  // Saving int// Saving int
                            editor.apply();// commit changes
                            tvDateDechargement.setText(getDayName(NomduJour-1) + "\n" + String.format("%02d/%02d/%02d",day,(month+1),year));
                            //échéance déchargement
                            dateDechargement.add(Calendar.DAY_OF_MONTH, 28);
                            int yearFinD = dateDechargement.get(Calendar.YEAR);
                            int monthFinD = dateDechargement.get(Calendar.MONTH);
                            int dayofmonthFinD = dateDechargement.get(Calendar.DAY_OF_MONTH);
                            int nomdujourFinD = dateDechargement.get(Calendar.DAY_OF_WEEK);
                            editor.putInt("key_fin_dechargement_Year",yearFinD );  // Saving int
                            editor.putInt("key_fin_dechargement_Month",monthFinD );  // Saving int
                            editor.putInt("key_fin_dechargement_Day",dayofmonthFinD );  // Saving int// Saving int
                            editor.apply();// commit changes
                            tvDateProchainDechargement.setText(getDayName(nomdujourFinD-1) + "\n" + String.format("%02d/%02d/%02d",dayofmonthFinD,(monthFinD+1),yearFinD));
                            CalculJoursRestantsVidage();
                        } else {
                            tvDateDechargement.setText("");
                            tvDateProchainDechargement.setText("");
                            Toast.makeText(context, "La date de déchargement doit être supérieure ou égale à la date du jour" + booleanalarm, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, yearDebut, monthDebut, dayOfMonthDebut);

        datePickerDialog.show();
    }

    public void deleteDateEcheance() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        tvDateEcheance.setText("");
        tvValiditeJoursRestants.setText("");
        //Clear only one sharedpreferences
        editor.remove("key_Echeance_Year");
        editor.remove("key_Echeance_Month");
        editor.remove("key_Echeance_Day");
        editor.apply();
    }

    private void deleteDateVidage() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        tvDateDechargement.setText("");
        tvDateProchainDechargement.setText("");
        tvDechargementJoursRestants.setText("");
        editor.remove("key_fin_dechargement_Year");
        editor.remove("key_fin_dechargement_Month");
        editor.remove("key_fin_dechargement_Day");
        editor.remove("key_dechargement_Year");
        editor.remove("key_dechargement_Month");
        editor.remove("key_dechargement_Day");
        editor.apply();
    }

    public void deleteNumeroCarte() {
        SharedPreferences pref = context.getSharedPreferences("Pref_Carte", MODE_PRIVATE);
        editor = pref.edit();
        etNumeroCarte.setText("");
        //Clear only one sharedpreferences
        editor.remove("key_Numero_Carte");
        editor.apply();
    }

}
