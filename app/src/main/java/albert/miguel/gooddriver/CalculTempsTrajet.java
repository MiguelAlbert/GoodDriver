package albert.miguel.gooddriver;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class CalculTempsTrajet extends Fragment {

    Context context;
    EditText etKilometres,etVitesseMoyenne;
    ImageButton imageButtonDelete, imageButtonDelete2, imageButtonCalcul;
    TextView tvHeure, tvMinute;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        View v = inflater.inflate(R.layout.fragment_temps_trajet,container,false);

        etKilometres = (EditText) v.findViewById(R.id.etKilometres);
        etKilometres.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            public void afterTextChanged(Editable arg0) {
                if (etKilometres.getText().toString().equals("")){
                } else {
                    editor.putInt("key_km", Integer.parseInt(etKilometres.getText().toString()));
                    editor.apply();
                }
            }
        });
        etVitesseMoyenne = (EditText) v.findViewById(R.id.etVitesseMoyenne);
        etVitesseMoyenne.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            public void afterTextChanged(Editable arg0) {
                String str = etVitesseMoyenne.getText().toString();
                if (str.isEmpty()) return;
                String str2 = PerfectDecimal(str, 3, 2);

                if (!str2.equals(str)) {
                    etVitesseMoyenne.setText(str2);
                    etVitesseMoyenne.setSelection(str2.length());
                }

                if (etVitesseMoyenne.getText().toString().equals("")){
                } else {
                    editor.putFloat("key_vitesse", Float.parseFloat(etVitesseMoyenne.getText().toString()));
                    editor.apply();
                }
            }

        });
        tvHeure = (TextView) v.findViewById(R.id.tvHeure);
        tvMinute = (TextView) v.findViewById(R.id.tvMinute);
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
        imageButtonCalcul = (ImageButton) v.findViewById(R.id.imageButtonCalcul);
        imageButtonCalcul.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                calcul();
            }
        });
        testSiValeursEnregistrees();
        return v;
    }

    private void testSiValeursEnregistrees() {
        SharedPreferences pref = context.getSharedPreferences("PrefTempsDeTrajet", MODE_PRIVATE);
        editor = pref.edit();
        int km = pref.getInt("key_km", 0);
        if(km == 0 ){
            etKilometres.setText("");
        } else {
            etKilometres.setText(String.valueOf(km));
        }

        double vitesse = pref.getFloat("key_vitesse", 0.0F);
        if(vitesse == 0.0 ){
            etVitesseMoyenne.setText("");
        } else {
            etVitesseMoyenne.setText(String.valueOf(vitesse));
        }

        int heures = pref.getInt("key_heures", 0);
        int minutes = pref.getInt("key_minutes", 0);
        if(heures == 0 && minutes == 0 ){
            tvHeure.setText("");
            tvMinute.setText("");
        } else {
            tvHeure.setText(twoDigitString(heures));
            tvMinute.setText(twoDigitString(minutes));
        }
    }

    private void calcul() {
        if (etKilometres.getText().toString().equals("") || etVitesseMoyenne.getText().toString().equals("")) {
            Toast.makeText(context, "valeurs vides", Toast.LENGTH_SHORT).show();
        } else {
            int km = Integer.parseInt(etKilometres.getText().toString());
            double vitesse = Double.parseDouble(etVitesseMoyenne.getText().toString());
            if (km == 0 || vitesse == 0) {
                Toast.makeText(context, "valeurs 0", Toast.LENGTH_SHORT).show();
            } else {
                double dureeminutes = km/(vitesse/60);
                int heures = (int) (dureeminutes/60);
                int minutes = (int) (dureeminutes - (heures*60));
                tvHeure.setText(twoDigitString(heures));
                tvMinute.setText(twoDigitString(minutes));
                editor.putInt("key_heures",heures);
                editor.putInt("key_minutes",minutes);
                editor.putFloat("key_vitesse", (float) vitesse);
                editor.putInt("key_km",km);
                editor.apply();
            }
        }
    }

    private void delete1() {
        tvHeure.setText("");
        editor.remove("key_heures");
        tvMinute.setText("");
        editor.remove("key_minutes");
        etKilometres.setText("");
        editor.remove("key_km");
        editor.apply();
    }

    private void delete2() {
        tvHeure.setText("");
        editor.remove("key_heures");
        tvMinute.setText("");
        editor.remove("key_minutes");
        etVitesseMoyenne.setText("");
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
