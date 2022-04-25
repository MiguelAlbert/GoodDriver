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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class CalculPalettes extends Fragment {

    Context context;
    ImageButton imageButtonDelete, imageButtonCalcul;
    EditText etLongueurDispo;
    TextView tvNombrePalettes;
    RadioButton radioButton11, radioButton9;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();

        View v = inflater.inflate(R.layout.fragment_palettes,container,false);
        etLongueurDispo = (EditText) v.findViewById(R.id.etLongueurDispo);
        radioButton11 = (RadioButton) v.findViewById(R.id.radioButton11);
        radioButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paletteChange(v);
            }
        });
        radioButton9 = (RadioButton) v.findViewById(R.id.radioButton9);
        radioButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paletteChange(v);
            }
        });

        tvNombrePalettes = (TextView) v.findViewById(R.id.tvNombrePalettes);
        imageButtonDelete = (ImageButton) v.findViewById(R.id.imageButtonDelete);
        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        imageButtonCalcul = (ImageButton) v.findViewById(R.id.imageButtonCalcul);
        imageButtonCalcul.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                calcul();
            }
        });
        etLongueurDispo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String str = etLongueurDispo.getText().toString();
                if (str.isEmpty()) return;
                String str2 = PerfectDecimal(str, 4, 2);

                if (!str2.equals(str)) {
                    etLongueurDispo.setText(str2);
                    etLongueurDispo.setSelection(str2.length());
                }
                if (etLongueurDispo.getText().toString().equals("")){
                } else {
                    editor.putFloat("key_longueur", Float.parseFloat(etLongueurDispo.getText().toString()));
                    editor.apply();
                }
            }
        });
        testSiValeursEnregistrees();
        return v;
    }

    private void paletteChange(View v) {
        if(radioButton9.isChecked()){
            editor.putBoolean("key_taille", false);
            editor.apply();
            calcul();
        }
        if(radioButton11.isChecked()){
            editor.putBoolean("key_taille", true);
            editor.apply();
            calcul();
        }
    }

    private void testSiValeursEnregistrees() {

        SharedPreferences pref = context.getSharedPreferences("PrefPalettes", MODE_PRIVATE);
        editor = pref.edit();

        double longueur = pref.getFloat("key_longueur", 0.0F);
        if(longueur == 0.0 ){
            etLongueurDispo.setText("");
        } else {
            longueur = Math.round(longueur * 100);
            longueur = longueur / 100;
            etLongueurDispo.setText(String.valueOf(longueur));
        }

        int palettes = pref.getInt("key_palettes", 0);
        if(palettes == 0 ){
            tvNombrePalettes.setText("");
        } else {
            tvNombrePalettes.setText(String.valueOf(palettes));
        }
        boolean taille = pref.getBoolean("key_taille",true);
        if(taille){
            radioButton11.toggle();
        } else{
            radioButton9.toggle();
        }

    }

    private void calcul() {

        if(!etLongueurDispo.getText().toString().equals("")){
            if (radioButton11.isChecked()){
                int pal = (int) (Double.parseDouble(etLongueurDispo.getText().toString())/0.4);
                tvNombrePalettes.setText(String.valueOf(pal));
                editor.putBoolean("key_taille", true);
                editor.putFloat("key_longueur", Float.parseFloat(etLongueurDispo.getText().toString()));
                editor.putInt("key_palettes", Integer.parseInt(tvNombrePalettes.getText().toString()));
                editor.apply();
            }
        }
        if(!etLongueurDispo.getText().toString().equals("")){
            if (radioButton9.isChecked()){
                editor.putBoolean("key_taille", false);
                int pal = (int) (Double.parseDouble(etLongueurDispo.getText().toString())/0.5);
                if ((pal % 2) == 0) {
                    tvNombrePalettes.setText(String.valueOf(pal));
                    editor.putFloat("key_longueur", Float.parseFloat(etLongueurDispo.getText().toString()));
                    editor.putInt("key_palettes", Integer.parseInt(tvNombrePalettes.getText().toString()));
                } else {
                    tvNombrePalettes.setText(String.valueOf(pal - 1));
                    editor.putFloat("key_longueur", Float.parseFloat(etLongueurDispo.getText().toString()));
                    editor.putInt("key_palettes", Integer.parseInt(tvNombrePalettes.getText().toString()));
                }
                editor.apply();
            }
        }
    }

    private void delete() {
        etLongueurDispo.setText("");
        tvNombrePalettes.setText("");
        editor.remove("key_longueur");
        editor.remove("key_palettes");
        radioButton11.toggle();
        editor.apply();

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
