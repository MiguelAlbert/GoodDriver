package albert.miguel.gooddriver;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Objects;


public class CalculVitesseMoyenne extends Fragment {

    Context context;
    ImageButton imageButtonDelete, imageButtonDelete2;
    EditText etKilometres,etHeure1, etMinute1;
    TextView tvResultat;
    SharedPreferences.Editor editor;
    private AdView mPublisherAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = Objects.requireNonNull(container).getContext();

        View v = inflater.inflate(R.layout.fragment_vitesse_moyenne,container,false);

        mPublisherAdView = v.findViewById(R.id.publisherAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);

        etKilometres = (EditText) v.findViewById(R.id.etKilometres);
        etHeure1 = (EditText) v.findViewById(R.id.etHeure1);
        etMinute1 = (EditText) v.findViewById(R.id.etMinute1);
        tvResultat = (TextView) v.findViewById(R.id.tvResultat);
        imageButtonDelete = (ImageButton) v.findViewById(R.id.imageButtonDelete);
        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete1();
            }
        });
        imageButtonDelete2 = (ImageButton) v.findViewById(R.id.imageButtonDelete2);
        imageButtonDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete2();
            }
        });
        etHeure1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        etHeure1.setShowSoftInputOnFocus(true);
        etHeure1.setInputType(InputType.TYPE_CLASS_NUMBER);
        etHeure1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (etHeure1.getText().toString().equals("")){
                    editor.putInt("key_heures", 0);
                    editor.apply();
                    calcul();
                } else {
                    editor.putInt("key_heures", Integer.parseInt(etHeure1.getText().toString()));
                    editor.apply();
                    calcul();
                }
            }
        });
        etMinute1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        etMinute1.setInputType(InputType.TYPE_CLASS_NUMBER);
        etMinute1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    int n = Integer.parseInt(String.valueOf(s));
                    if(n > 59){
                        Toast.makeText(context,"Maxi 59 minutes",Toast.LENGTH_SHORT).show();
                        etMinute1.setText("");
                    }
                }
                if (etMinute1.getText().toString().equals("")){
                    editor.putInt("key_minutes", 0);
                    editor.apply();
                    calcul();
                } else {
                    editor.putInt("key_minutes", Integer.parseInt(etMinute1.getText().toString()));
                    editor.apply();
                    calcul();
                }
            }
        });
        etKilometres.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (etKilometres.getText().toString().equals("")){
                    editor.putInt("key_km", 0);
                    editor.apply();
                    calcul();
                } else {
                    editor.putInt("key_km", Integer.parseInt(etKilometres.getText().toString()));
                    editor.apply();
                    calcul();
                }
            }
        });
        testSiValeursEnregistrees();
        return v;
    }

    private void testSiValeursEnregistrees() {
        SharedPreferences pref = context.getSharedPreferences("PrefVitesseMoyenne", MODE_PRIVATE);
        editor = pref.edit();
        int km = pref.getInt("key_km", 0);
        if(km == 0 ){
            etKilometres.setText("");
        } else {
            etKilometres.setText(String.valueOf(km));
        }
        double vitesse = pref.getFloat("key_vitesse", 0.0F);
        if(vitesse == 0.0 ){
            tvResultat.setText("");
        } else {
            vitesse = Math.round(vitesse * 100);
            vitesse = vitesse / 100;
            tvResultat.setText(String.valueOf(vitesse));
        }
        int heures = pref.getInt("key_heures", 0);
        int minutes = pref.getInt("key_minutes", 0);
        if(heures == 0 && minutes == 0 ){
            etHeure1.setText("");
            etMinute1.setText("");
        } else {
            etHeure1.setText(twoDigitString(heures));
            etMinute1.setText(twoDigitString(minutes));
        }
    }

    private void calcul() {
        double minutes;
        if(!etHeure1.getText().toString().equals("") && !etKilometres.getText().toString().equals("")){
            if(!etMinute1.getText().toString().equals("")){
                minutes = Integer.parseInt(String.valueOf(etHeure1.getText()))*60 + Integer.parseInt(String.valueOf(etMinute1.getText()));
            } else {
                minutes = Integer.parseInt(String.valueOf(etHeure1.getText()))*60;
            }
            double km = Integer.parseInt(String.valueOf(etKilometres.getText()));
            double result = km * 60/minutes;
            result = Math.round(result * 100);
            result = result / 100;
            editor.putFloat("key_vitesse", (float) result);
            editor.apply();
            tvResultat.setText(""+result);
        } else {
            tvResultat.setText("");
        }
        if((etHeure1.getText().toString().equals("0") || etHeure1.getText().toString().equals("00") )&& !etKilometres.getText().toString().equals("")){
            tvResultat.setText("");
        }
        if((etHeure1.getText().toString().equals("0") || etHeure1.getText().toString().equals("00") || etHeure1.getText().toString().equals("")) && !etKilometres.getText().toString().equals("") && !etMinute1.getText().toString().equals("")){
            minutes = Integer.parseInt(String.valueOf(etMinute1.getText()));
            double km = Integer.parseInt(String.valueOf(etKilometres.getText()));
            double result = km * 60/minutes;
            result = Math.round(result * 100);
            result = result / 100;
            editor.putFloat("key_vitesse", (float) result);
            editor.apply();
            tvResultat.setText(""+result);
        }
        if((etHeure1.getText().toString().equals("0") || etHeure1.getText().toString().equals("00") || etHeure1.getText().toString().equals("") )&& (etMinute1.getText().toString().equals("0") || etMinute1.getText().toString().equals("00") || etMinute1.getText().toString().equals(""))){
            tvResultat.setText("");
        }
    }

    private void delete1() {
        etHeure1.setText("");
        etMinute1.setText("");
        tvResultat.setText("");
        editor.remove("key_heures");
        editor.remove("key_minutes");
        editor.remove("key_vitesse");
        editor.apply();
    }

    private void delete2() {
        etKilometres.setText("");
        tvResultat.setText("");
        editor.remove("key_km");
        editor.remove("key_vitesse");
        editor.apply();
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
}
