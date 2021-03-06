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


public class TempsCentiemes extends Fragment {
    ImageButton imageButtonDelete1, imageButtonDelete2;
    EditText etHeure1, etMinute1, etHeureCentieme2;
    TextView tVResultat1, tvResultatHeure2,tvResultatMinute2;
    Context context;
    SharedPreferences.Editor editor;
    private AdView mPublisherAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = Objects.requireNonNull(container).getContext();

        View v = inflater.inflate(R.layout.fragment_temps_centiemes,container,false);

        mPublisherAdView = v.findViewById(R.id.publisherAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);

        tVResultat1 = (TextView) v.findViewById(R.id.etKilometres);
        tvResultatHeure2 = (TextView) v.findViewById(R.id.tvResultatHeure2);
        tvResultatMinute2 = (TextView) v.findViewById(R.id.tvResultatMinute2);
        imageButtonDelete1 = (ImageButton) v.findViewById(R.id.imageButtonDelete);
        imageButtonDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteligne1();
            }
        });
        imageButtonDelete2 = (ImageButton) v.findViewById(R.id.imageButtonDelete2);
        imageButtonDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteligne2();
            }
        });
        etHeure1 = (EditText) v.findViewById(R.id.etHeure1);
        etHeure1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        etHeure1.setShowSoftInputOnFocus(true);
        etHeure1.setInputType(InputType.TYPE_CLASS_NUMBER);
        etHeure1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etHeure1.getText().toString().equals("")){
                } else {
                    editor.putInt("key_heures1", Integer.parseInt(etHeure1.getText().toString()));
                    editor.apply();
                }
                calculCentieme();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        etMinute1 = (EditText) v.findViewById(R.id.etMinute1);
        etMinute1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        etMinute1.setInputType(InputType.TYPE_CLASS_NUMBER);
        etMinute1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){
                    int n = Integer.parseInt(String.valueOf(s));
                    if(n > 59){
                        Toast.makeText(context,"Maxi 59 minutes",Toast.LENGTH_SHORT).show();
                        etMinute1.setText("");
                    }
                }
                if (etMinute1.getText().toString().equals("")){
                } else {
                    editor.putInt("key_minutes1", Integer.parseInt(etMinute1.getText().toString()));
                    editor.apply();
                }
                calculCentieme();

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        etHeureCentieme2 = (EditText) v.findViewById(R.id.etHeureCentieme2);
        etHeureCentieme2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                String str = etHeureCentieme2.getText().toString();
                if (etHeureCentieme2.getText().toString().equals("") || etHeureCentieme2.getText().toString().equals("0") || etHeureCentieme2.getText().toString().equals("00")){
                    tvResultatMinute2.setText("");
                    tvResultatHeure2.setText("");
                } else {
                    editor.putFloat("key_heurescentiemes", Float.parseFloat(etHeureCentieme2.getText().toString()));
                    editor.apply();
                }
                calculHeureMinute();
                if (str.isEmpty()) return;
                String str2 = PerfectDecimal(str, 4, 2);

                if (!str2.equals(str)) {
                    etHeureCentieme2.setText(str2);
                    etHeureCentieme2.setSelection(str2.length());
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            public void afterTextChanged(Editable arg0) {}
        });
        testSiValeursEnregistrees();
        return v;
    }

    private void testSiValeursEnregistrees() {
        SharedPreferences pref = context.getSharedPreferences("PrefTempsCentieme", MODE_PRIVATE);
        editor = pref.edit();
        //Ligne1
        int heures1 = pref.getInt("key_heures1", 0);
        int minutes1 = pref.getInt("key_minutes1", 0);
        if(heures1 == 0 && minutes1 == 0 ){
            etHeure1.setText("");
            etMinute1.setText("");
        } else {
            etHeure1.setText(twoDigitString(heures1));
            etMinute1.setText(twoDigitString(minutes1));
        }
        double vitesse = pref.getFloat("key_resultat1", 0.00F);
        vitesse = Math.round(vitesse * 100);
        vitesse = vitesse / 100;
        if(vitesse == 0.0 ){
            tVResultat1.setText("");
        } else {
            vitesse = Math.round(vitesse * 100);
            vitesse = vitesse / 100;
            tVResultat1.setText(String.valueOf(vitesse));
        }

        //ligne2
        double heureCentieme2 = pref.getFloat("key_heurescentiemes", 0.00F);
        heureCentieme2 = Math.round(heureCentieme2 * 100);
        heureCentieme2 = heureCentieme2 / 100;
        if(heureCentieme2 == 0.0 ){
            etHeureCentieme2.setText("");
        } else {
            heureCentieme2 = Math.round(heureCentieme2 * 100);
            heureCentieme2 = heureCentieme2 / 100;
            etHeureCentieme2.setText(String.valueOf(heureCentieme2));
        }
        int heures2 = pref.getInt("key_heures2", 0);
        int minutes2 = pref.getInt("key_minutes2", 0);
        if(heures2 == 0 && minutes2 == 0 ){
            tvResultatHeure2.setText("");
            tvResultatMinute2.setText("");
        } else {
            tvResultatHeure2.setText(twoDigitString(heures2));
            tvResultatMinute2.setText(twoDigitString(minutes2));
        }
    }

    private void calculHeureMinute() {
        if(etHeureCentieme2.getText().toString().equals("") || etHeureCentieme2.getText().toString().equals("0") || etHeureCentieme2.getText().toString().equals("00")){
            tvResultatMinute2.setText("");
            tvResultatHeure2.setText("");
            //Toast.makeText(context, "valeurs vides", Toast.LENGTH_SHORT).show();
        }
        //
        if(!etHeureCentieme2.getText().toString().equals("")){
            float finalBuildTime = Float.parseFloat(etHeureCentieme2.getText().toString());
            finalBuildTime = finalBuildTime + Float.parseFloat(String.valueOf(0.006666666));
            int hours = (int) finalBuildTime;
            int minutes = (int) (finalBuildTime * 60) % 60;
            int secondes = (int) (finalBuildTime * 60) % 60 % 60;

            tvResultatMinute2.setText(String.valueOf(twoDigitString(minutes)));
            tvResultatHeure2.setText(String.valueOf(twoDigitString(hours)));
            editor.putInt("key_minutes2", Integer.parseInt(tvResultatMinute2.getText().toString()));
            editor.putInt("key_heures2", Integer.parseInt(tvResultatHeure2.getText().toString()));
            editor.apply();
        }
    }

    private void calculCentieme() {
        if(etHeure1.getText().toString().equals("") || etMinute1.getText().toString().equals("")){
            //Toast.makeText(context, "valeurs vides", Toast.LENGTH_SHORT).show();
        }
        if(!etHeure1.getText().toString().equals("") && !etMinute1.getText().toString().equals("")){
            int heures =  Integer.parseInt(String.valueOf(etHeure1.getText()));
            double minutes = (Double.parseDouble(String.valueOf(etMinute1.getText()))*100.00f/60.00f)/100.00f;
            double number = heures + minutes;
            number = Math.round(number * 100.00f);
            number = number / 100.00f;
            tVResultat1.setText(String.valueOf(number));
            editor.putFloat("key_resultat1", (float) number);
            editor.apply();
        }
        if(!etHeure1.getText().toString().equals("") && etMinute1.getText().toString().equals("") && ( etMinute1.getText().toString().equals("") || etMinute1.getText().toString().equals("0")|| etMinute1.getText().toString().equals("00"))){
            int heures =  Integer.parseInt(String.valueOf(etHeure1.getText()));
            //double minutes = (Double.parseDouble(String.valueOf(etMinute1.getText()))*100/60)/100;
            double number = heures ; //+ minutes;
            number = Math.round(number * 100.00f);
            number = number / 100.00f;
            tVResultat1.setText(String.valueOf(number));
            editor.putFloat("key_resultat1", (float) number);
            editor.apply();
        }
        if((etHeure1.getText().toString().equals("") ||etHeure1.getText().toString().equals("0") ||etHeure1.getText().toString().equals("00")) && !etMinute1.getText().toString().equals("")){
            //int heures =  Integer.parseInt(String.valueOf(etHeure1.getText()));
            double minutes = (Double.parseDouble(String.valueOf(etMinute1.getText()))*100.00f/60.00f)/100.00f;
            double number = minutes;
            number = Math.round(number * 100.00f);
            number = number / 100.00f;
            tVResultat1.setText(String.valueOf(number));
            editor.putFloat("key_resultat1", (float) number);
            editor.apply();
        }
    }

    private void deleteligne1() {
        etHeure1.setText("");
        etMinute1.setText("");
        tVResultat1.setText("");
        editor.remove("key_heures1");
        editor.remove("key_minutes1");
        editor.remove("key_resultat1");
        editor.apply();
    }

    private void deleteligne2() {
        etHeureCentieme2.setText("");
        tvResultatHeure2.setText("");
        tvResultatMinute2.setText("");
        editor.remove("key_heurescentiemes");
        editor.remove("key_heures2");
        editor.remove("key_minutes2");
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

    public String PerfectDecimal(String str, int MAX_BEFORE_POINT, int MAX_DECIMAL){
        if(str.charAt(0) == '.') str = "0"+str;
        int max = str.length();

        String rFinal = "";
        boolean after = false;
        int i = 0, up = 0, decimal = 0; char t;
        while(i < max){
            t = str.charAt(i);
            if(t != '.' && after == false){
                up++;
                if(up > MAX_BEFORE_POINT) return rFinal;
            }else if(t == '.'){
                after = true;
            }else{
                decimal++;
                if(decimal > MAX_DECIMAL)
                    return rFinal;
            }
            rFinal = rFinal + t;
            i++;
        }return rFinal;
    }

}
