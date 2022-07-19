package albert.miguel.gooddriver;


import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.Objects;


public class SemaineFragment extends Fragment {

    static Context context;
    static View v;
    ImageButton imageButtonDateDebutJ1,imageButtonDateDebutJ2,imageButtonDateDebutJ3,imageButtonDateDebutJ4,imageButtonDateDebutJ5,imageButtonDateDebutJ6;
    ImageButton imageButtonDateFinJ1,imageButtonDateFinJ2,imageButtonDateFinJ3,imageButtonDateFinJ4,imageButtonDateFinJ5,imageButtonDateFinJ6;
    ImageButton imageButtonHeureDebutJ1,imageButtonHeureDebutJ2,imageButtonHeureDebutJ3,imageButtonHeureDebutJ4,imageButtonHeureDebutJ5,imageButtonHeureDebutJ6;
    ImageButton imageButtonHeureFinJ1,imageButtonHeureFinJ2,imageButtonHeureFinJ3,imageButtonHeureFinJ4,imageButtonHeureFinJ5,imageButtonHeureFinJ6;
    ImageButton imageButtonDeleteDebutJ1, imageButtonDeleteDebutJ2, imageButtonDeleteDebutJ3, imageButtonDeleteDebutJ4, imageButtonDeleteDebutJ5, imageButtonDeleteDebutJ6;
    ImageButton imageButtonDeleteFinJ1, imageButtonDeleteFinJ2, imageButtonDeleteFinJ3, imageButtonDeleteFinJ4, imageButtonDeleteFinJ5, imageButtonDeleteFinJ6;
    ImageButton imageButtonConduiteJ1,imageButtonConduiteJ2,imageButtonConduiteJ3,imageButtonConduiteJ4,imageButtonConduiteJ5,imageButtonConduiteJ6;
    ImageButton imageButtonTravailJ1,imageButtonTravailJ2,imageButtonTravailJ3,imageButtonTravailJ4,imageButtonTravailJ5,imageButtonTravailJ6;
    ImageButton imageButtonDispoJ1,imageButtonDispoJ2, imageButtonDispoJ3, imageButtonDispoJ4, imageButtonDispoJ5, imageButtonDispoJ6;
    static TextView tvHeureConduiteJ1,tvHeureConduiteJ2,tvHeureConduiteJ3, tvHeureConduiteJ4, tvHeureConduiteJ5,tvHeureConduiteJ6;
    static TextView tvHeureTravailJ1, tvHeureTravailJ2, tvHeureTravailJ3, tvHeureTravailJ4, tvHeureTravailJ5, tvHeureTravailJ6;
    static TextView tvHeureDispoJ1, tvHeureDispoJ2, tvHeureDispoJ3, tvHeureDispoJ4, tvHeureDispoJ5, tvHeureDispoJ6;
    static TextView tvDateDebutJ1, tvDateDebutJ2, tvDateDebutJ3, tvDateDebutJ4, tvDateDebutJ5, tvDateDebutJ6;
    static TextView tvDateFinJ1, tvDateFinJ2, tvDateFinJ3, tvDateFinJ4, tvDateFinJ5, tvDateFinJ6;
    static TextView tvHeureDebutJ1, tvHeureDebutJ2, tvHeureDebutJ3, tvHeureDebutJ4, tvHeureDebutJ5, tvHeureDebutJ6;
    static TextView tvHeureFinJ1, tvHeureFinJ2, tvHeureFinJ3, tvHeureFinJ4, tvHeureFinJ5, tvHeureFinJ6;
    static TextView tvTotalConduiteSemaine,tvTotalTravailSemaine, tvTotalDispoSemaine, tvTotalServiceSemaine;
    static TextView tvNbConduite, tvNbRepos;
    ImageButton imageButtonDeleteSemaine;
    LinearLayout LinearLayoutRH1, LinearLayoutRH2, LinearLayoutJ1,LinearLayoutJ2, LinearLayoutJ3, LinearLayoutJ4, LinearLayoutJ5, LinearLayoutJ6;
    static CheckBox checkBox3HeuresReposJ1;
    static CheckBox checkBox3HeuresReposJ2;
    static CheckBox checkBox3HeuresReposJ3;
    static CheckBox checkBox3HeuresReposJ4;
    static CheckBox checkBox3HeuresReposJ5;
    static TextView tvTempsServiceJ1,tvTempsServiceJ2, tvTempsServiceJ3, tvTempsServiceJ4, tvTempsServiceJ5, tvTempsServiceJ6;
    static TextView tvTempsReposJ1,tvTempsReposJ2, tvTempsReposJ3, tvTempsReposJ4, tvTempsReposJ5;
    static TextView tvTempsAmplitudeJ1, tvTempsAmplitudeJ2, tvTempsAmplitudeJ3, tvTempsAmplitudeJ4, tvTempsAmplitudeJ5, tvTempsAmplitudeJ6;
    ExpandableCardView reposHbefore, reposHend, amplitude1, amplitude2, amplitude3, amplitude4, amplitude5, amplitude6;
    static int nbRepos9h;
    static SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = Objects.requireNonNull(container).getContext();
        View v = inflater.inflate(R.layout.fragment_semaines,container,false);

        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();

        UI(v);
        return v;
    }

    private void UI(@NonNull View v) {

        //reposHbefore = v.findViewById(R.id.reposHbefore);
        amplitude1 = v.findViewById(R.id.amplitude1);
        amplitude2 = v.findViewById(R.id.amplitude2);
        amplitude3 = v.findViewById(R.id.amplitude3);
        amplitude4 = v.findViewById(R.id.amplitude4);
        amplitude5 = v.findViewById(R.id.amplitude5);
        amplitude6 = v.findViewById(R.id.amplitude6);
        //reposHend = v.findViewById(R.id.reposHend);

        checkBox3HeuresReposJ1 = v.findViewById(R.id.checkBox3HeuresReposJ1);
        checkBox3HeuresReposJ1.setOnClickListener(v126 -> {
            SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
            editor = pref.edit();
            if(checkBox3HeuresReposJ1.isChecked()){
                editor.putBoolean("key_CheckBoxRepos_J1",true);
                editor.apply();
            } else{
                editor.putBoolean("key_CheckBoxRepos_J1",false);
                editor.apply();
            }
            calculTempsRepos();
        } );
        checkBox3HeuresReposJ2 = v.findViewById(R.id.checkBox3HeuresReposJ2);
        checkBox3HeuresReposJ2.setOnClickListener(v127 -> {
            SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
            editor = pref.edit();
            if(checkBox3HeuresReposJ2.isChecked()){
                editor.putBoolean("key_CheckBoxRepos_J2",true);
                editor.apply();
            } else{
                editor.putBoolean("key_CheckBoxRepos_J2",false);
                editor.apply();
            }
            calculTempsRepos();
        } );
        checkBox3HeuresReposJ3 = v.findViewById(R.id.checkBox3HeuresReposJ3);
        checkBox3HeuresReposJ3.setOnClickListener(v128 -> {
            SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
            editor = pref.edit();
            if(checkBox3HeuresReposJ3.isChecked()){
                editor.putBoolean("key_CheckBoxRepos_J3",true);
                editor.apply();
            } else{
                editor.putBoolean("key_CheckBoxRepos_J3",false);
                editor.apply();
            }
            calculTempsRepos();
        } );
        checkBox3HeuresReposJ4 = v.findViewById(R.id.checkBox3HeuresReposJ4);
        checkBox3HeuresReposJ4.setOnClickListener(v129 -> {
            SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
            editor = pref.edit();
            if(checkBox3HeuresReposJ4.isChecked()){
                editor.putBoolean("key_CheckBoxRepos_J4",true);
                editor.apply();
            } else{
                editor.putBoolean("key_CheckBoxRepos_J4",false);
                editor.apply();
            }
            calculTempsRepos();
        } );
        checkBox3HeuresReposJ5 = v.findViewById(R.id.checkBox3HeuresReposJ5);
        checkBox3HeuresReposJ5.setOnClickListener(v130 -> {
            SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
            editor = pref.edit();
            if(checkBox3HeuresReposJ5.isChecked()){
                editor.putBoolean("key_CheckBoxRepos_J5",true);
                editor.apply();
            } else{
                editor.putBoolean("key_CheckBoxRepos_J5",false);
                editor.apply();
            }
            calculTempsRepos();
        } );
        /*
        LinearLayoutRH1 = v.findViewById(R.id.LinearLayoutRH1);
        LinearLayoutRH1.setOnClickListener(v132 -> {});
        LinearLayoutRH2 = v.findViewById(R.id.LinearLayoutRH2);
        LinearLayoutRH2.setOnClickListener(v133 -> {});
         */
        LinearLayoutJ1 = v.findViewById(R.id.LinearLayoutJ1);
        LinearLayoutJ1.setOnClickListener(v134 -> {});
        LinearLayoutJ2 = v.findViewById(R.id.LinearLayoutJ2);
        LinearLayoutJ2.setOnClickListener(v135 -> {});
        LinearLayoutJ3 = v.findViewById(R.id.LinearLayoutJ3);
        LinearLayoutJ3.setOnClickListener(v136 -> {});
        LinearLayoutJ4 = v.findViewById(R.id.LinearLayoutJ4);
        LinearLayoutJ4.setOnClickListener(v137 -> {});
        LinearLayoutJ5 = v.findViewById(R.id.LinearLayoutJ5);
        LinearLayoutJ5.setOnClickListener(v138 -> {});
        LinearLayoutJ6 = v.findViewById(R.id.LinearLayoutJ6);
        LinearLayoutJ6.setOnClickListener(v139 -> {});
        imageButtonDeleteSemaine = v.findViewById(R.id.imageButtonDeleteSemaine);
        imageButtonDeleteSemaine.setOnClickListener(v12 -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            // set title
            alertDialogBuilder.setTitle("Suppresion des données");
            // set dialog message
            alertDialogBuilder
                    .setMessage("êtes-vous sûr de vouloir supprimer les données de toute la semaine ?")
                    .setNegativeButton("Fermer",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            alertDialogBuilder
                    .setIcon(ContextCompat.getDrawable(context, R.drawable.ic_logo2))
                    .setPositiveButton("Supprimer",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            deleteSemaine();
                            dialog.cancel();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        });
        imageButtonDateDebutJ1 = v.findViewById(R.id.imageButtonDateDebutJ1);
        imageButtonDateDebutJ1.setOnClickListener(v140 -> selectDateDebutJ1());
        imageButtonDateDebutJ2 = v.findViewById(R.id.imageButtonDateDebutJ2);
        imageButtonDateDebutJ2.setOnClickListener(v1 -> selectDateDebutJ2());
        imageButtonDateDebutJ3 = v.findViewById(R.id.imageButtonDateDebutJ3);
        imageButtonDateDebutJ3.setOnClickListener(v141 -> selectDateDebutJ3());
        imageButtonDateDebutJ4 = v.findViewById(R.id.imageButtonDateDebutJ4);
        imageButtonDateDebutJ4.setOnClickListener(v142 -> {
            selectDateDebutJ4();
        });
        imageButtonDateDebutJ5 = v.findViewById(R.id.imageButtonDateDebutJ5);
        imageButtonDateDebutJ5.setOnClickListener(v143 -> {
            selectDateDebutJ5();
        });
        imageButtonDateDebutJ6 = v.findViewById(R.id.imageButtonDateDebutJ6);
        imageButtonDateDebutJ6.setOnClickListener(v144 -> {
            selectDateDebutJ6();
        });
        imageButtonDeleteDebutJ1 = v.findViewById(R.id.imageButtonDeleteDebutJ1);
        imageButtonDeleteDebutJ1.setOnClickListener(v145 -> {
            DeleteDateDebutJ1();
        });
        imageButtonDeleteDebutJ2 = v.findViewById(R.id.imageButtonDeleteDebutJ2);
        imageButtonDeleteDebutJ2.setOnClickListener(v146 -> {
            DeleteDateDebutJ2();
        });
        imageButtonDeleteDebutJ3 = v.findViewById(R.id.imageButtonDeleteDebutJ3);
        imageButtonDeleteDebutJ3.setOnClickListener(v147 -> {
            DeleteDateDebutJ3();
        });
        imageButtonDeleteDebutJ4 = v.findViewById(R.id.imageButtonDeleteDebutJ4);
        imageButtonDeleteDebutJ4.setOnClickListener(v148 -> {
            DeleteDateDebutJ4();
        });
        imageButtonDeleteDebutJ5 = v.findViewById(R.id.imageButtonDeleteDebutJ5);
        imageButtonDeleteDebutJ5.setOnClickListener(v149 -> {
            DeleteDateDebutJ5();
        });
        imageButtonDeleteDebutJ6 = v.findViewById(R.id.imageButtonDeleteDebutJ6);
        imageButtonDeleteDebutJ6.setOnClickListener(v150 -> {
            DeleteDateDebutJ6();
        });
        imageButtonDeleteFinJ1 = v.findViewById(R.id.imageButtonDeleteFinJ1);
        imageButtonDeleteFinJ1.setOnClickListener(v151 -> {
            DeleteDateFinJ1();
        });
        imageButtonDeleteFinJ2 = v.findViewById(R.id.imageButtonDeleteFinJ2);
        imageButtonDeleteFinJ2.setOnClickListener(v152 -> {
            DeleteDateFinJ2();
        });
        imageButtonDeleteFinJ3 = v.findViewById(R.id.imageButtonDeleteFinJ3);
        imageButtonDeleteFinJ3.setOnClickListener(v153 -> {
            DeleteDateFinJ3();
        });
        imageButtonDeleteFinJ4 = v.findViewById(R.id.imageButtonDeleteFinJ4);
        imageButtonDeleteFinJ4.setOnClickListener(v154 -> {
            DeleteDateFinJ4();
        });
        imageButtonDeleteFinJ5 = v.findViewById(R.id.imageButtonDeleteFinJ5);
        imageButtonDeleteFinJ5.setOnClickListener(v155 -> {
            DeleteDateFinJ5();
        });
        imageButtonDeleteFinJ6 = v.findViewById(R.id.imageButtonDeleteFinJ6);
        imageButtonDeleteFinJ6.setOnClickListener(v156 -> {
            DeleteDateFinJ6();
        });
        imageButtonHeureFinJ1 = v.findViewById(R.id.imageButtonHeureFinJ1);
        imageButtonHeureFinJ1.setOnClickListener(v112 -> {
            selectHeureFinJ1();
        });
        imageButtonHeureFinJ2 = v.findViewById(R.id.imageButtonHeureFinJ2);
        imageButtonHeureFinJ2.setOnClickListener(v1 -> {
            selectHeureFinJ2();
        });
        imageButtonHeureFinJ3 = v.findViewById(R.id.imageButtonHeureFinJ3);
        imageButtonHeureFinJ3.setOnClickListener(v111 -> {
            selectHeureFinJ3();
        });
        imageButtonHeureFinJ4 = v.findViewById(R.id.imageButtonHeureFinJ4);
        imageButtonHeureFinJ4.setOnClickListener(v113 -> {
            selectHeureFinJ4();
        });
        imageButtonHeureFinJ5 = v.findViewById(R.id.imageButtonHeureFinJ5);
        imageButtonHeureFinJ5.setOnClickListener(v114 -> {
            selectHeureFinJ5();
        });
        imageButtonHeureFinJ6 = v.findViewById(R.id.imageButtonHeureFinJ6);
        imageButtonHeureFinJ6.setOnClickListener(v115 -> {
            selectHeureFinJ6();
        });
        imageButtonHeureDebutJ1 = v.findViewById(R.id.imageButtonHeureDebutJ1);
        imageButtonHeureDebutJ1.setOnClickListener(v116 -> {
            selectHeureDebutJ1();
        });
        imageButtonHeureDebutJ2 = v.findViewById(R.id.imageButtonHeureDebutJ2);
        imageButtonHeureDebutJ2.setOnClickListener(v1 -> {
            selectHeureDebutJ2();
        });
        imageButtonHeureDebutJ3 = v.findViewById(R.id.imageButtonHeureDebutJ3);
        imageButtonHeureDebutJ3.setOnClickListener(v117 -> {
            selectHeureDebutJ3();
        });
        imageButtonHeureDebutJ4 = v.findViewById(R.id.imageButtonHeureDebutJ4);
        imageButtonHeureDebutJ4.setOnClickListener(v118 -> {
            selectHeureDebutJ4();
        });
        imageButtonHeureDebutJ5 = v.findViewById(R.id.imageButtonHeureDebutJ5);
        imageButtonHeureDebutJ5.setOnClickListener(v119 -> {
            selectHeureDebutJ5();
        });
        imageButtonHeureDebutJ6 = v.findViewById(R.id.imageButtonHeureDebutJ6);
        imageButtonHeureDebutJ6.setOnClickListener(v120 -> {
            selectHeureDebutJ6();
        });
        imageButtonDateFinJ1 = v.findViewById(R.id.imageButtonDateFinJ1);
        imageButtonDateFinJ1.setOnClickListener(v121 -> {
            selectDateFinJ1();
        });
        imageButtonDateFinJ2 = v.findViewById(R.id.imageButtonDateFinJ2);
        imageButtonDateFinJ2.setOnClickListener(v122 -> {
            selectDateFinJ2();
        });
        imageButtonDateFinJ3 = v.findViewById(R.id.imageButtonDateFinJ3);
        imageButtonDateFinJ3.setOnClickListener(v123 -> {
            selectDateFinJ3();
        });
        imageButtonDateFinJ4 = v.findViewById(R.id.imageButtonDateFinJ4);
        imageButtonDateFinJ4.setOnClickListener(v124 -> {
            selectDateFinJ4();
        });
        imageButtonDateFinJ5 = v.findViewById(R.id.imageButtonDateFinJ5);
        imageButtonDateFinJ5.setOnClickListener(v125 -> {
            selectDateFinJ5();
        });
        imageButtonDateFinJ6 = v.findViewById(R.id.imageButtonDateFinJ6);
        imageButtonDateFinJ6.setOnClickListener(v157 -> {
            selectDateFinJ6();
        });
        tvDateDebutJ1 = v.findViewById(R.id.tVDateDebutJ1);
        tvDateDebutJ1.setOnClickListener(v158 -> {
            selectDateDebutJ1();
        });
        tvDateDebutJ2 = v.findViewById(R.id.tVDateDebutJ2);
        tvDateDebutJ2.setOnClickListener(v159 -> {
            selectDateDebutJ2();
        });
        tvDateDebutJ3 = v.findViewById(R.id.tVDateDebutJ3);
        tvDateDebutJ3.setOnClickListener(v160 -> {
            selectDateDebutJ3();
        });
        tvDateDebutJ4 = v.findViewById(R.id.tVDateDebutJ4);
        tvDateDebutJ4.setOnClickListener(v161 -> {
            selectDateDebutJ4();
        });
        tvDateDebutJ5 = v.findViewById(R.id.tVDateDebutJ5);
        tvDateDebutJ5.setOnClickListener(v162 -> {
            selectDateDebutJ5();
        });
        tvDateDebutJ6 = v.findViewById(R.id.tVDateDebutJ6);
        tvDateDebutJ6.setOnClickListener(v163 -> {
            selectDateDebutJ6();
        });
        tvHeureDebutJ1 = v.findViewById(R.id.tvHeureDebutJ1);
        tvHeureDebutJ1.setOnClickListener(v164 -> {
            selectHeureDebutJ1();
        });
        tvHeureDebutJ2 = v.findViewById(R.id.tvHeureDebutJ2);
        tvHeureDebutJ2.setOnClickListener(v165 -> {
            selectHeureDebutJ2();
        });
        tvHeureDebutJ3 = v.findViewById(R.id.tvHeureDebutJ3);
        tvHeureDebutJ3.setOnClickListener(v166 -> {
            selectHeureDebutJ3();
        });
        tvHeureDebutJ4 = v.findViewById(R.id.tvHeureDebutJ4);
        tvHeureDebutJ4.setOnClickListener(v167 -> {
            selectHeureDebutJ4();
        });
        tvHeureDebutJ5 = v.findViewById(R.id.tvHeureDebutJ5);
        tvHeureDebutJ5.setOnClickListener(v168 -> {
            selectHeureDebutJ5();
        });
        tvHeureDebutJ6 = v.findViewById(R.id.tvHeureDebutJ6);
        tvHeureDebutJ6.setOnClickListener(v169 -> {
            selectHeureDebutJ6();
        });
        tvHeureFinJ1 = v.findViewById(R.id.tvHeureFinJ1);
        tvHeureFinJ1.setOnClickListener(v170 -> {
            selectHeureFinJ1();
        });
        tvHeureFinJ2 = v.findViewById(R.id.tvHeureFinJ2);
        tvHeureFinJ2.setOnClickListener(v171 -> {
            selectHeureFinJ2();
        });
        tvHeureFinJ3 = v.findViewById(R.id.tvHeureFinJ3);
        tvHeureFinJ3.setOnClickListener(v172 -> {
            selectHeureFinJ3();
        });
        tvHeureFinJ4 = v.findViewById(R.id.tvHeureFinJ4);
        tvHeureFinJ4.setOnClickListener(v173 -> {
            selectHeureFinJ4();
        });
        tvHeureFinJ5 = v.findViewById(R.id.tvHeureFinJ5);
        tvHeureFinJ5.setOnClickListener(v174 -> {
            selectHeureFinJ5();
        });
        tvHeureFinJ6 = v.findViewById(R.id.tvHeureFinJ6);
        tvHeureFinJ6.setOnClickListener(v175 -> {
            selectHeureFinJ6();
        });
        tvDateFinJ1 = v.findViewById(R.id.tvDateFinJ1);
        tvDateFinJ1.setOnClickListener(v176 -> {
            selectDateFinJ1();
        });
        tvDateFinJ2 = v.findViewById(R.id.tvDateFinJ2);
        tvDateFinJ2.setOnClickListener(v177 -> {
            selectDateFinJ2();
        });
        tvDateFinJ3 = v.findViewById(R.id.tvDateFinJ3);
        tvDateFinJ3.setOnClickListener(v178 -> {
            selectDateFinJ3();
        });
        tvDateFinJ4 = v.findViewById(R.id.tvDateFinJ4);
        tvDateFinJ4.setOnClickListener(v179 -> {
            selectDateFinJ4();
        });
        tvDateFinJ5 = v.findViewById(R.id.tvDateFinJ5);
        tvDateFinJ5.setOnClickListener(v180 -> {
            selectDateFinJ5();
        });
        imageButtonConduiteJ1 = v.findViewById(R.id.imageButtonConduiteJ1);
        imageButtonConduiteJ1.setOnClickListener(v121 -> {
            TempsConduiteJ1();
        });
        imageButtonConduiteJ2 = v.findViewById(R.id.imageButtonConduiteJ2);
        imageButtonConduiteJ2.setOnClickListener(v121 -> {
            TempsConduiteJ2();
        });
        imageButtonConduiteJ3 = v.findViewById(R.id.imageButtonConduiteJ3);
        imageButtonConduiteJ3.setOnClickListener(v121 -> {
            TempsConduiteJ3();
        });
        imageButtonConduiteJ4 = v.findViewById(R.id.imageButtonConduiteJ4);
        imageButtonConduiteJ4.setOnClickListener(v121 -> {
            TempsConduiteJ4();
        });
        imageButtonConduiteJ5 = v.findViewById(R.id.imageButtonConduiteJ5);
        imageButtonConduiteJ5.setOnClickListener(v121 -> {
            TempsConduiteJ5();
        });
        imageButtonConduiteJ6 = v.findViewById(R.id.imageButtonConduiteJ6);
        imageButtonConduiteJ6.setOnClickListener(v121 -> {
            TempsConduiteJ6();
        });
        imageButtonTravailJ1 = v.findViewById(R.id.imageButtonTravailJ1);
        imageButtonTravailJ1.setOnClickListener(v121 -> {
            TempsTravailJ1();
        });
        imageButtonTravailJ2 = v.findViewById(R.id.imageButtonTravailJ2);
        imageButtonTravailJ2.setOnClickListener(v121 -> {
            TempsTravailJ2();
        });
        imageButtonTravailJ3 = v.findViewById(R.id.imageButtonTravailJ3);
        imageButtonTravailJ3.setOnClickListener(v121 -> {
            TempsTravailJ3();
        });
        imageButtonTravailJ4 = v.findViewById(R.id.imageButtonTravailJ4);
        imageButtonTravailJ4.setOnClickListener(v121 -> {
            TempsTravailJ4();
        });
        imageButtonTravailJ5 = v.findViewById(R.id.imageButtonTravailJ5);
        imageButtonTravailJ5.setOnClickListener(v121 -> {
            TempsTravailJ5();
        });
        imageButtonTravailJ6 = v.findViewById(R.id.imageButtonTravailJ6);
        imageButtonTravailJ6.setOnClickListener(v121 -> {
            TempsTravailJ6();
        });
        imageButtonDispoJ1 = v.findViewById(R.id.imageButtonDispoJ1);
        imageButtonDispoJ1.setOnClickListener(v121 -> {
            TempsDispoJ1();
        });
        imageButtonDispoJ2 = v.findViewById(R.id.imageButtonDispoJ2);
        imageButtonDispoJ2.setOnClickListener(v121 -> {
            TempsDispoJ2();
        });
        imageButtonDispoJ3 = v.findViewById(R.id.imageButtonDispoJ3);
        imageButtonDispoJ3.setOnClickListener(v121 -> {
            TempsDispoJ3();
        });
        imageButtonDispoJ4 = v.findViewById(R.id.imageButtonDispoJ4);
        imageButtonDispoJ4.setOnClickListener(v121 -> {
            TempsDispoJ4();
        });
        imageButtonDispoJ5 = v.findViewById(R.id.imageButtonDispoJ5);
        imageButtonDispoJ5.setOnClickListener(v121 -> {
            TempsDispoJ5();
        });
        imageButtonDispoJ6 = v.findViewById(R.id.imageButtonDispoJ6);
        imageButtonDispoJ6.setOnClickListener(v121 -> {
            TempsDispoJ6();
        });
        tvHeureConduiteJ1 = v.findViewById(R.id.tvHeureConduiteJ1);
        tvHeureConduiteJ1.setOnClickListener(v181 -> {
            TempsConduiteJ1();
        });
        tvHeureConduiteJ2 = v.findViewById(R.id.tvHeureConduiteJ2);
        tvHeureConduiteJ2.setOnClickListener(v182 -> {
            TempsConduiteJ2();
        });
        tvHeureConduiteJ3 = v.findViewById(R.id.tvHeureConduiteJ3);
        tvHeureConduiteJ3.setOnClickListener(v183 -> {
            TempsConduiteJ3();
        });
        tvHeureConduiteJ4 = v.findViewById(R.id.tvHeureConduiteJ4);
        tvHeureConduiteJ4.setOnClickListener(v184 -> {
            TempsConduiteJ4();
        });
        tvHeureConduiteJ5 = v.findViewById(R.id.tvHeureConduiteJ5);
        tvHeureConduiteJ5.setOnClickListener(v198 -> {
            TempsConduiteJ5();
        });
        tvHeureConduiteJ6 = v.findViewById(R.id.tvHeureConduiteJ6);
        tvHeureConduiteJ6.setOnClickListener(v197 -> {
            TempsConduiteJ6();
        });
        tvHeureTravailJ1 = v.findViewById(R.id.tvHeureTravailJ1);
        tvHeureTravailJ1.setOnClickListener(v196 -> {
            TempsTravailJ1();
        });
        tvHeureTravailJ2 = v.findViewById(R.id.tvHeureTravailJ2);
        tvHeureTravailJ2.setOnClickListener(v195 -> {
            TempsTravailJ2();
        });
        tvHeureTravailJ3 = v.findViewById(R.id.tvHeureTravailJ3);
        tvHeureTravailJ3.setOnClickListener(v194 -> {
            TempsTravailJ3();
        });
        tvHeureTravailJ4 = v.findViewById(R.id.tvHeureTravailJ4);
        tvHeureTravailJ4.setOnClickListener(v193 -> {
            TempsTravailJ4();
        });
        tvHeureTravailJ5 = v.findViewById(R.id.tvHeureTravailJ5);
        tvHeureTravailJ5.setOnClickListener(v192 -> {
            TempsTravailJ5();
        });
        tvHeureTravailJ6 = v.findViewById(R.id.tvHeureTravailJ6);
        tvHeureTravailJ6.setOnClickListener(v191 -> {
            TempsTravailJ6();
        });
        tvHeureDispoJ1 = v.findViewById(R.id.tvHeureDispoJ1);
        tvHeureDispoJ1.setOnClickListener(v190 -> {
            TempsDispoJ1();
        });
        tvHeureDispoJ2 = v.findViewById(R.id.tvHeureDispoJ2);
        tvHeureDispoJ2.setOnClickListener(v189 -> {
            TempsDispoJ2();
        });
        tvHeureDispoJ3 = v.findViewById(R.id.tvHeureDispoJ3);
        tvHeureDispoJ3.setOnClickListener(v188 -> {
            TempsDispoJ3();
        });
        tvHeureDispoJ4 = v.findViewById(R.id.tvHeureDispoJ4);
        tvHeureDispoJ4.setOnClickListener(v187 -> {
            TempsDispoJ4();
        });
        tvHeureDispoJ5 = v.findViewById(R.id.tvHeureDispoJ5);
        tvHeureDispoJ5.setOnClickListener(v186 -> {
            TempsDispoJ5();
        });
        tvHeureDispoJ6 = v.findViewById(R.id.tvHeureDispoJ6);
        tvHeureDispoJ6.setOnClickListener(v185 -> {
            TempsDispoJ6();
        });
        tvDateFinJ6 = v.findViewById(R.id.tvDateFinJ6);
        tvDateFinJ6.setOnClickListener(v17 -> {
            selectDateFinJ6();
        });
        tvTempsServiceJ1 = v.findViewById(R.id.tvTempsServiceJ1);
        tvTempsServiceJ2 = v.findViewById(R.id.tvTempsServiceJ2);
        tvTempsServiceJ3 = v.findViewById(R.id.tvTempsServiceJ3);
        tvTempsServiceJ4 = v.findViewById(R.id.tvTempsServiceJ4);
        tvTempsServiceJ5 = v.findViewById(R.id.tvTempsServiceJ5);
        tvTempsServiceJ6 = v.findViewById(R.id.tvTempsServiceJ6);

        tvTotalConduiteSemaine = v.findViewById(R.id.tvTotalConduiteSemaine);
        tvTotalTravailSemaine = v.findViewById(R.id.tvTotalTravailSemaine);
        tvTotalDispoSemaine = v.findViewById(R.id.tvTotalDispoSemaine);
        tvTotalServiceSemaine = v.findViewById(R.id.tvTotalServiceSemaine);
        tvNbConduite = v.findViewById(R.id.tvNbConduite);
        tvNbRepos = v.findViewById(R.id.tvNbRepos);

        tvTempsReposJ1 = v.findViewById(R.id.tvTempsReposJ1);
        tvTempsReposJ2 = v.findViewById(R.id.tvTempsReposJ2);
        tvTempsReposJ3 = v.findViewById(R.id.tvTempsReposJ3);
        tvTempsReposJ4 = v.findViewById(R.id.tvTempsReposJ4);
        tvTempsReposJ5 = v.findViewById(R.id.tvTempsReposJ5);

        tvTempsAmplitudeJ1 = v.findViewById(R.id.tvTempsAmplitudeJ1);
        tvTempsAmplitudeJ2 = v.findViewById(R.id.tvTempsAmplitudeJ2);
        tvTempsAmplitudeJ3 = v.findViewById(R.id.tvTempsAmplitudeJ3);
        tvTempsAmplitudeJ4 = v.findViewById(R.id.tvTempsAmplitudeJ4);
        tvTempsAmplitudeJ5 = v.findViewById(R.id.tvTempsAmplitudeJ5);
        tvTempsAmplitudeJ6 = v.findViewById(R.id.tvTempsAmplitudeJ6);
    }

    private void deleteSemaine() {
        DeleteDateDebutJ1();
        DeleteDateDebutJ2();
        DeleteDateDebutJ3();
        DeleteDateDebutJ4();
        DeleteDateDebutJ5();
        DeleteDateDebutJ6();
        DeleteDateFinJ1();
        DeleteDateFinJ2();
        DeleteDateFinJ3();
        DeleteDateFinJ4();
        DeleteDateFinJ5();
        DeleteDateFinJ6();
        tvHeureConduiteJ1.setText("00:00");
        tvHeureConduiteJ2.setText("00:00");
        tvHeureConduiteJ3.setText("00:00");
        tvHeureConduiteJ4.setText("00:00");
        tvHeureConduiteJ5.setText("00:00");
        tvHeureConduiteJ6.setText("00:00");
        tvHeureTravailJ1.setText("00:00");
        tvHeureTravailJ2.setText("00:00");
        tvHeureTravailJ3.setText("00:00");
        tvHeureTravailJ4.setText("00:00");
        tvHeureTravailJ5.setText("00:00");
        tvHeureTravailJ6.setText("00:00");
        tvHeureDispoJ1.setText("00:00");
        tvHeureDispoJ2.setText("00:00");
        tvHeureDispoJ3.setText("00:00");
        tvHeureDispoJ4.setText("00:00");
        tvHeureDispoJ5.setText("00:00");
        tvHeureDispoJ6.setText("00:00");
        tvTempsAmplitudeJ1.setText("00:00");
        tvTempsAmplitudeJ2.setText("00:00");
        tvTempsAmplitudeJ3.setText("00:00");
        tvTempsAmplitudeJ4.setText("00:00");
        tvTempsAmplitudeJ5.setText("00:00");
        tvTempsAmplitudeJ6.setText("00:00");
        tvNbConduite.setText("0/2");
        tvNbRepos.setText("0/3");
        editor.putInt("key_tempsDeConduite_J1",0);
        editor.putInt("key_tempsDeConduite_J2",0);
        editor.putInt("key_tempsDeConduite_J3",0);
        editor.putInt("key_tempsDeConduite_J4",0);
        editor.putInt("key_tempsDeConduite_J5",0);
        editor.putInt("key_tempsDeConduite_J6",0);
        editor.putInt("key_tempsDeTravail_J1",0);
        editor.putInt("key_tempsDeTravail_J2",0);
        editor.putInt("key_tempsDeTravail_J3",0);
        editor.putInt("key_tempsDeTravail_J4",0);
        editor.putInt("key_tempsDeTravail_J5",0);
        editor.putInt("key_tempsDeTravail_J6",0);
        editor.putInt("key_tempsDeDispo_J1",0);
        editor.putInt("key_tempsDeDispo_J2",0);
        editor.putInt("key_tempsDeDispo_J3",0);
        editor.putInt("key_tempsDeDispo_J4",0);
        editor.putInt("key_tempsDeDispo_J5",0);
        editor.putInt("key_tempsDeDispo_J6",0);
        editor.apply();
        checkBox3HeuresReposJ1.setChecked(false);
        checkBox3HeuresReposJ2.setChecked(false);
        checkBox3HeuresReposJ3.setChecked(false);
        checkBox3HeuresReposJ4.setChecked(false);
        checkBox3HeuresReposJ5.setChecked(false);
        calculTotalparJour(1);
        calculTotalparJour(2);
        calculTotalparJour(3);
        calculTotalparJour(4);
        calculTotalparJour(5);
        calculTotalparJour(6);
        calculTotalparSemaine();
        calculTempsRepos();
    }

    public static void testSiValeursEnregistrees() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        //Jour1
        Calendar calendardebutj1 = Calendar.getInstance();
        int monthDebutJ1 = pref.getInt("key_Debut_MonthJ1", 0);
        int yearDebutJ1 = pref.getInt("key_Debut_YearJ1", 0);
        int dayOfMonthDebutJ1 = pref.getInt("key_Debut_DayJ1", 0);
        int heureDebutJ1 = pref.getInt("key_Debut_HourJ1", 0);
        int minuteDebutJ1 = pref.getInt("key_Debut_MinuteJ1", 0);
        if(monthDebutJ1 == 0 && yearDebutJ1 == 0 && dayOfMonthDebutJ1 == 0 && heureDebutJ1 == 0 && minuteDebutJ1 == 0) {
            tvHeureDebutJ1.setText("");
            tvDateDebutJ1.setText("");
        }else {
            calendardebutj1.set(Calendar.MONTH, monthDebutJ1);
            calendardebutj1.set(Calendar.YEAR, yearDebutJ1);
            calendardebutj1.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ1);
            calendardebutj1.set(Calendar.HOUR_OF_DAY, heureDebutJ1);
            calendardebutj1.set(Calendar.MINUTE, minuteDebutJ1);
            calendardebutj1.set(Calendar.SECOND, 0);
            calendardebutj1.set(Calendar.MILLISECOND, 0);
            int NomduJourdebut = calendardebutj1.get(Calendar.DAY_OF_WEEK);
            tvHeureDebutJ1.setText(String.format("%02d:%02d",heureDebutJ1,minuteDebutJ1));
            tvDateDebutJ1.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebutJ1 ,(monthDebutJ1 + 1), yearDebutJ1));
        }
        Calendar calendarFinj1 = Calendar.getInstance();
        int monthFinJ1 = pref.getInt("key_Fin_MonthJ1", 0);
        int yearFinJ1 = pref.getInt("key_Fin_YearJ1", 0);
        int dayOfMonthFinJ1 = pref.getInt("key_Fin_DayJ1", 0);
        int heureFinJ1 = pref.getInt("key_Fin_HourJ1", 0);
        int minuteFinJ1 = pref.getInt("key_Fin_MinuteJ1", 0);
        if(monthFinJ1 == 0 && yearFinJ1 == 0 && dayOfMonthFinJ1 == 0 && heureFinJ1 == 0 && minuteFinJ1 == 0) {
            tvHeureFinJ1.setText("");
            tvDateFinJ1.setText("");
        }else {
            calendarFinj1.set(Calendar.MONTH, monthFinJ1);
            calendarFinj1.set(Calendar.YEAR, yearFinJ1);
            calendarFinj1.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ1);
            calendarFinj1.set(Calendar.HOUR_OF_DAY, heureFinJ1);
            calendarFinj1.set(Calendar.MINUTE, minuteFinJ1);
            calendarFinj1.set(Calendar.SECOND, 0);
            calendarFinj1.set(Calendar.MILLISECOND, 0);
            int NomduJourFin = calendarFinj1.get(Calendar.DAY_OF_WEEK);
            tvHeureFinJ1.setText(String.format("%02d:%02d",heureFinJ1,minuteFinJ1));
            tvDateFinJ1.setText(getDayName(NomduJourFin-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFinJ1 ,(monthFinJ1 + 1), yearFinJ1));
        }
        int minutesconduiteJ1 = pref.getInt("key_tempsDeConduite_J1", 0);
        int hourconduiteJ1 = (minutesconduiteJ1/60);
        int minutesAAfficherconduiteJ1 = minutesconduiteJ1 - (hourconduiteJ1*60);
        tvHeureConduiteJ1.setText(twoDigitString(hourconduiteJ1) + ":" + twoDigitString(minutesAAfficherconduiteJ1));

        int minutesTravailJ1 = pref.getInt("key_tempsDeTravail_J1", 0);
        int hourTravailJ1 = (minutesTravailJ1/60);
        int minutesAAfficherTravailJ1 = minutesTravailJ1 - (hourTravailJ1*60);
        tvHeureTravailJ1.setText(twoDigitString(hourTravailJ1) + ":" + twoDigitString(minutesAAfficherTravailJ1));

        int minutesDispoJ1 = pref.getInt("key_tempsDeDispo_J1", 0);
        int hourDispoJ1 = (minutesDispoJ1/60);
        int minutesAAfficherDispoJ1 = minutesDispoJ1 - (hourDispoJ1*60);
        tvHeureDispoJ1.setText(twoDigitString(hourDispoJ1) + ":" + twoDigitString(minutesAAfficherDispoJ1));

        //Jour2
        Calendar calendardebutj2 = Calendar.getInstance();
        int monthDebutJ2 = pref.getInt("key_Debut_MonthJ2", 0);
        int yearDebutJ2 = pref.getInt("key_Debut_YearJ2", 0);
        int dayOfMonthDebutJ2 = pref.getInt("key_Debut_DayJ2", 0);
        int heureDebutJ2 = pref.getInt("key_Debut_HourJ2", 0);
        int minuteDebutJ2 = pref.getInt("key_Debut_MinuteJ2", 0);
        if(monthDebutJ2 == 0 && yearDebutJ2 == 0 && dayOfMonthDebutJ2 == 0 && heureDebutJ2 == 0 && minuteDebutJ2 == 0) {
            tvHeureDebutJ2.setText("");
            tvDateDebutJ2.setText("");
        }else {
            calendardebutj2.set(Calendar.MONTH, monthDebutJ2);
            calendardebutj2.set(Calendar.YEAR, yearDebutJ2);
            calendardebutj2.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ2);
            calendardebutj2.set(Calendar.HOUR_OF_DAY, heureDebutJ2);
            calendardebutj2.set(Calendar.MINUTE, minuteDebutJ2);
            calendardebutj2.set(Calendar.SECOND, 0);
            calendardebutj2.set(Calendar.MILLISECOND, 0);
            int NomduJourdebut = calendardebutj2.get(Calendar.DAY_OF_WEEK);
            tvHeureDebutJ2.setText(String.format("%02d:%02d",heureDebutJ2,minuteDebutJ2));
            tvDateDebutJ2.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebutJ2 ,(monthDebutJ2 + 1), yearDebutJ2));
        }
        Calendar calendarFinj2 = Calendar.getInstance();
        int monthFinJ2 = pref.getInt("key_Fin_MonthJ2", 0);
        int yearFinJ2 = pref.getInt("key_Fin_YearJ2", 0);
        int dayOfMonthFinJ2 = pref.getInt("key_Fin_DayJ2", 0);
        int heureFinJ2 = pref.getInt("key_Fin_HourJ2", 0);
        int minuteFinJ2 = pref.getInt("key_Fin_MinuteJ2", 0);
        if(monthFinJ2 == 0 && yearFinJ2 == 0 && dayOfMonthFinJ2 == 0 && heureFinJ2 == 0 && minuteFinJ2 == 0) {
            tvHeureFinJ2.setText("");
            tvDateFinJ2.setText("");
        }else {
            calendarFinj2.set(Calendar.MONTH, monthFinJ2);
            calendarFinj2.set(Calendar.YEAR, yearFinJ2);
            calendarFinj2.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ2);
            calendarFinj2.set(Calendar.HOUR_OF_DAY, heureFinJ2);
            calendarFinj2.set(Calendar.MINUTE, minuteFinJ2);
            calendarFinj2.set(Calendar.SECOND, 0);
            calendarFinj2.set(Calendar.MILLISECOND, 0);
            int NomduJourFin = calendarFinj2.get(Calendar.DAY_OF_WEEK);
            tvHeureFinJ2.setText(String.format("%02d:%02d",heureFinJ2,minuteFinJ2));
            tvDateFinJ2.setText(getDayName(NomduJourFin-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFinJ2 ,(monthFinJ2 + 1), yearFinJ2));
        }
        int minutesconduiteJ2 = pref.getInt("key_tempsDeConduite_J2", 0);
        int hourconduiteJ2 = (minutesconduiteJ2/60);
        int minutesAAfficherconduiteJ2 = minutesconduiteJ2 - (hourconduiteJ2*60);
        tvHeureConduiteJ2.setText(twoDigitString(hourconduiteJ2) + ":" + twoDigitString(minutesAAfficherconduiteJ2));

        int minutesTravailJ2 = pref.getInt("key_tempsDeTravail_J2", 0);
        int hourTravailJ2 = (minutesTravailJ2/60);
        int minutesAAfficherTravailJ2 = minutesTravailJ2 - (hourTravailJ2*60);
        tvHeureTravailJ2.setText(twoDigitString(hourTravailJ2) + ":" + twoDigitString(minutesAAfficherTravailJ2));

        int minutesDispoJ2 = pref.getInt("key_tempsDeDispo_J2", 0);
        int hourDispoJ2 = (minutesDispoJ2/60);
        int minutesAAfficherDispoJ2 = minutesDispoJ2 - (hourDispoJ2*60);
        tvHeureDispoJ2.setText(twoDigitString(hourDispoJ2) + ":" + twoDigitString(minutesAAfficherDispoJ2));

        //Jour3
        Calendar calendardebutj3 = Calendar.getInstance();
        int monthDebutJ3 = pref.getInt("key_Debut_MonthJ3", 0);
        int yearDebutJ3 = pref.getInt("key_Debut_YearJ3", 0);
        int dayOfMonthDebutJ3 = pref.getInt("key_Debut_DayJ3", 0);
        int heureDebutJ3 = pref.getInt("key_Debut_HourJ3", 0);
        int minuteDebutJ3 = pref.getInt("key_Debut_MinuteJ3", 0);
        if(monthDebutJ3 == 0 && yearDebutJ3 == 0 && dayOfMonthDebutJ3 == 0 && heureDebutJ3 == 0 && minuteDebutJ3 == 0) {
            tvHeureDebutJ3.setText("");
            tvDateDebutJ3.setText("");
        }else {
            calendardebutj3.set(Calendar.MONTH, monthDebutJ3);
            calendardebutj3.set(Calendar.YEAR, yearDebutJ3);
            calendardebutj3.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ3);
            calendardebutj3.set(Calendar.HOUR_OF_DAY, heureDebutJ3);
            calendardebutj3.set(Calendar.MINUTE, minuteDebutJ3);
            calendardebutj3.set(Calendar.SECOND, 0);
            calendardebutj3.set(Calendar.MILLISECOND, 0);
            int NomduJourdebut = calendardebutj3.get(Calendar.DAY_OF_WEEK);
            tvHeureDebutJ3.setText(String.format("%02d:%02d",heureDebutJ3,minuteDebutJ3));
            tvDateDebutJ3.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebutJ3 ,(monthDebutJ3 + 1), yearDebutJ3));
        }
        Calendar calendarFinj3 = Calendar.getInstance();
        int monthFinJ3 = pref.getInt("key_Fin_MonthJ3", 0);
        int yearFinJ3 = pref.getInt("key_Fin_YearJ3", 0);
        int dayOfMonthFinJ3 = pref.getInt("key_Fin_DayJ3", 0);
        int heureFinJ3 = pref.getInt("key_Fin_HourJ3", 0);
        int minuteFinJ3 = pref.getInt("key_Fin_MinuteJ3", 0);
        if(monthFinJ3 == 0 && yearFinJ3 == 0 && dayOfMonthFinJ3 == 0 && heureFinJ3 == 0 && minuteFinJ3 == 0) {
            tvHeureFinJ3.setText("");
            tvDateFinJ3.setText("");
        }else {
            calendarFinj3.set(Calendar.MONTH, monthFinJ3);
            calendarFinj3.set(Calendar.YEAR, yearFinJ3);
            calendarFinj3.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ3);
            calendarFinj3.set(Calendar.HOUR_OF_DAY, heureFinJ3);
            calendarFinj3.set(Calendar.MINUTE, minuteFinJ3);
            calendarFinj3.set(Calendar.SECOND, 0);
            calendarFinj3.set(Calendar.MILLISECOND, 0);
            int NomduJourFin = calendarFinj3.get(Calendar.DAY_OF_WEEK);
            tvHeureFinJ3.setText(String.format("%02d:%02d",heureFinJ3,minuteFinJ3));
            tvDateFinJ3.setText(getDayName(NomduJourFin-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFinJ3 ,(monthFinJ3 + 1), yearFinJ3));
        }
        int minutesconduiteJ3 = pref.getInt("key_tempsDeConduite_J3", 0);
        int hourconduiteJ3 = (minutesconduiteJ3/60);
        int minutesAAfficherconduiteJ3 = minutesconduiteJ3 - (hourconduiteJ3*60);
        tvHeureConduiteJ3.setText(twoDigitString(hourconduiteJ3) + ":" + twoDigitString(minutesAAfficherconduiteJ3));

        int minutesTravailJ3 = pref.getInt("key_tempsDeTravail_J3", 0);
        int hourTravailJ3 = (minutesTravailJ3/60);
        int minutesAAfficherTravailJ3 = minutesTravailJ3 - (hourTravailJ3*60);
        tvHeureTravailJ3.setText(twoDigitString(hourTravailJ3) + ":" + twoDigitString(minutesAAfficherTravailJ3));

        int minutesDispoJ3 = pref.getInt("key_tempsDeDispo_J3", 0);
        int hourDispoJ3 = (minutesDispoJ3/60);
        int minutesAAfficherDispoJ3 = minutesDispoJ3 - (hourDispoJ3*60);
        tvHeureDispoJ3.setText(twoDigitString(hourDispoJ3) + ":" + twoDigitString(minutesAAfficherDispoJ3));

        //Jour4
        Calendar calendardebutj4 = Calendar.getInstance();
        int monthDebutJ4 = pref.getInt("key_Debut_MonthJ4", 0);
        int yearDebutJ4 = pref.getInt("key_Debut_YearJ4", 0);
        int dayOfMonthDebutJ4 = pref.getInt("key_Debut_DayJ4", 0);
        int heureDebutJ4 = pref.getInt("key_Debut_HourJ4", 0);
        int minuteDebutJ4 = pref.getInt("key_Debut_MinuteJ4", 0);
        if(monthDebutJ4 == 0 && yearDebutJ4 == 0 && dayOfMonthDebutJ4 == 0 && heureDebutJ4 == 0 && minuteDebutJ4 == 0) {
            tvHeureDebutJ4.setText("");
            tvDateDebutJ4.setText("");
        }else {
            calendardebutj4.set(Calendar.MONTH, monthDebutJ4);
            calendardebutj4.set(Calendar.YEAR, yearDebutJ4);
            calendardebutj4.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ4);
            calendardebutj4.set(Calendar.HOUR_OF_DAY, heureDebutJ4);
            calendardebutj4.set(Calendar.MINUTE, minuteDebutJ4);
            calendardebutj4.set(Calendar.SECOND, 0);
            calendardebutj4.set(Calendar.MILLISECOND, 0);
            int NomduJourdebut = calendardebutj4.get(Calendar.DAY_OF_WEEK);
            tvHeureDebutJ4.setText(String.format("%02d:%02d",heureDebutJ4,minuteDebutJ4));
            tvDateDebutJ4.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebutJ4 ,(monthDebutJ4 + 1), yearDebutJ4));
        }
        Calendar calendarFinj4 = Calendar.getInstance();
        int monthFinJ4 = pref.getInt("key_Fin_MonthJ4", 0);
        int yearFinJ4 = pref.getInt("key_Fin_YearJ4", 0);
        int dayOfMonthFinJ4 = pref.getInt("key_Fin_DayJ4", 0);
        int heureFinJ4 = pref.getInt("key_Fin_HourJ4", 0);
        int minuteFinJ4 = pref.getInt("key_Fin_MinuteJ4", 0);
        if(monthFinJ4 == 0 && yearFinJ4 == 0 && dayOfMonthFinJ4 == 0 && heureFinJ4 == 0 && minuteFinJ4 == 0) {
            tvHeureFinJ4.setText("");
            tvDateFinJ4.setText("");
        }else {
            calendarFinj4.set(Calendar.MONTH, monthFinJ4);
            calendarFinj4.set(Calendar.YEAR, yearFinJ4);
            calendarFinj4.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ4);
            calendarFinj4.set(Calendar.HOUR_OF_DAY, heureFinJ4);
            calendarFinj4.set(Calendar.MINUTE, minuteFinJ4);
            calendarFinj4.set(Calendar.SECOND, 0);
            calendarFinj4.set(Calendar.MILLISECOND, 0);
            int NomduJourFin = calendarFinj4.get(Calendar.DAY_OF_WEEK);
            tvHeureFinJ4.setText(String.format("%02d:%02d",heureFinJ4,minuteFinJ4));
            tvDateFinJ4.setText(getDayName(NomduJourFin-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFinJ4 ,(monthFinJ4 + 1), yearFinJ4));
        }
        int minutesconduiteJ4 = pref.getInt("key_tempsDeConduite_J4", 0);
        int hourconduiteJ4 = (minutesconduiteJ4/60);
        int minutesAAfficherconduiteJ4 = minutesconduiteJ4 - (hourconduiteJ4*60);
        tvHeureConduiteJ4.setText(twoDigitString(hourconduiteJ4) + ":" + twoDigitString(minutesAAfficherconduiteJ4));

        int minutesTravailJ4 = pref.getInt("key_tempsDeTravail_J4", 0);
        int hourTravailJ4 = (minutesTravailJ4/60);
        int minutesAAfficherTravailJ4 = minutesTravailJ4 - (hourTravailJ4*60);
        tvHeureTravailJ4.setText(twoDigitString(hourTravailJ4) + ":" + twoDigitString(minutesAAfficherTravailJ4));

        int minutesDispoJ4 = pref.getInt("key_tempsDeDispo_J4", 0);
        int hourDispoJ4 = (minutesDispoJ4/60);
        int minutesAAfficherDispoJ4 = minutesDispoJ4 - (hourDispoJ4*60);
        tvHeureDispoJ4.setText(twoDigitString(hourDispoJ4) + ":" + twoDigitString(minutesAAfficherDispoJ4));

        //Jour5
        Calendar calendardebutj5 = Calendar.getInstance();
        int monthDebutJ5 = pref.getInt("key_Debut_MonthJ5", 0);
        int yearDebutJ5 = pref.getInt("key_Debut_YearJ5", 0);
        int dayOfMonthDebutJ5 = pref.getInt("key_Debut_DayJ5", 0);
        int heureDebutJ5 = pref.getInt("key_Debut_HourJ5", 0);
        int minuteDebutJ5 = pref.getInt("key_Debut_MinuteJ5", 0);
        if(monthDebutJ5 == 0 && yearDebutJ5 == 0 && dayOfMonthDebutJ5 == 0 && heureDebutJ5 == 0 && minuteDebutJ5 == 0) {
            tvHeureDebutJ5.setText("");
            tvDateDebutJ5.setText("");
        }else {
            calendardebutj5.set(Calendar.MONTH, monthDebutJ5);
            calendardebutj5.set(Calendar.YEAR, yearDebutJ5);
            calendardebutj5.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ5);
            calendardebutj5.set(Calendar.HOUR_OF_DAY, heureDebutJ5);
            calendardebutj5.set(Calendar.MINUTE, minuteDebutJ5);
            calendardebutj5.set(Calendar.SECOND, 0);
            calendardebutj5.set(Calendar.MILLISECOND, 0);
            int NomduJourdebut = calendardebutj5.get(Calendar.DAY_OF_WEEK);
            tvHeureDebutJ5.setText(String.format("%02d:%02d",heureDebutJ5,minuteDebutJ5));
            tvDateDebutJ5.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebutJ5 ,(monthDebutJ5 + 1), yearDebutJ5));
        }
        Calendar calendarFinj5 = Calendar.getInstance();
        int monthFinJ5 = pref.getInt("key_Fin_MonthJ5", 0);
        int yearFinJ5 = pref.getInt("key_Fin_YearJ5", 0);
        int dayOfMonthFinJ5 = pref.getInt("key_Fin_DayJ5", 0);
        int heureFinJ5 = pref.getInt("key_Fin_HourJ5", 0);
        int minuteFinJ5 = pref.getInt("key_Fin_MinuteJ5", 0);
        if(monthFinJ5 == 0 && yearFinJ5 == 0 && dayOfMonthFinJ5 == 0 && heureFinJ5 == 0 && minuteFinJ5 == 0) {
            tvHeureFinJ5.setText("");
            tvDateFinJ5.setText("");
        }else {
            calendarFinj5.set(Calendar.MONTH, monthFinJ5);
            calendarFinj5.set(Calendar.YEAR, yearFinJ5);
            calendarFinj5.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ5);
            calendarFinj5.set(Calendar.HOUR_OF_DAY, heureFinJ5);
            calendarFinj5.set(Calendar.MINUTE, minuteFinJ5);
            calendarFinj5.set(Calendar.SECOND, 0);
            calendarFinj5.set(Calendar.MILLISECOND, 0);
            int NomduJourFin = calendarFinj5.get(Calendar.DAY_OF_WEEK);
            tvHeureFinJ5.setText(String.format("%02d:%02d",heureFinJ5,minuteFinJ5));
            tvDateFinJ5.setText(getDayName(NomduJourFin-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFinJ5 ,(monthFinJ5 + 1), yearFinJ5));
        }
        int minutesconduiteJ5 = pref.getInt("key_tempsDeConduite_J5", 0);
        int hourconduiteJ5 = (minutesconduiteJ5/60);
        int minutesAAfficherconduiteJ5 = minutesconduiteJ5 - (hourconduiteJ5*60);
        tvHeureConduiteJ5.setText(twoDigitString(hourconduiteJ5) + ":" + twoDigitString(minutesAAfficherconduiteJ5));

        int minutesTravailJ5 = pref.getInt("key_tempsDeTravail_J5", 0);
        int hourTravailJ5 = (minutesTravailJ5/60);
        int minutesAAfficherTravailJ5 = minutesTravailJ5 - (hourTravailJ5*60);
        tvHeureTravailJ5.setText(twoDigitString(hourTravailJ5) + ":" + twoDigitString(minutesAAfficherTravailJ5));

        int minutesDispoJ5 = pref.getInt("key_tempsDeDispo_J5", 0);
        int hourDispoJ5 = (minutesDispoJ5/60);
        int minutesAAfficherDispoJ5 = minutesDispoJ5 - (hourDispoJ5*60);
        tvHeureDispoJ5.setText(twoDigitString(hourDispoJ5) + ":" + twoDigitString(minutesAAfficherDispoJ5));

        //Jour6
        Calendar calendardebutj6 = Calendar.getInstance();
        int monthDebutJ6 = pref.getInt("key_Debut_MonthJ6", 0);
        int yearDebutJ6 = pref.getInt("key_Debut_YearJ6", 0);
        int dayOfMonthDebutJ6 = pref.getInt("key_Debut_DayJ6", 0);
        int heureDebutJ6 = pref.getInt("key_Debut_HourJ6", 0);
        int minuteDebutJ6 = pref.getInt("key_Debut_MinuteJ6", 0);
        if(monthDebutJ6 == 0 && yearDebutJ6 == 0 && dayOfMonthDebutJ6 == 0 && heureDebutJ6 == 0 && minuteDebutJ6 == 0) {
            tvHeureDebutJ6.setText("");
            tvDateDebutJ6.setText("");
        }else {
            calendardebutj6.set(Calendar.MONTH, monthDebutJ6);
            calendardebutj6.set(Calendar.YEAR, yearDebutJ6);
            calendardebutj6.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ6);
            calendardebutj6.set(Calendar.HOUR_OF_DAY, heureDebutJ6);
            calendardebutj6.set(Calendar.MINUTE, minuteDebutJ6);
            calendardebutj6.set(Calendar.SECOND, 0);
            calendardebutj6.set(Calendar.MILLISECOND, 0);
            int NomduJourdebut = calendardebutj1.get(Calendar.DAY_OF_WEEK);
            tvHeureDebutJ6.setText(String.format("%02d:%02d",heureDebutJ6,minuteDebutJ6));
            tvDateDebutJ6.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebutJ6 ,(monthDebutJ6 + 1), yearDebutJ6));
        }
        Calendar calendarFinj6 = Calendar.getInstance();
        int monthFinJ6 = pref.getInt("key_Fin_MonthJ6", 0);
        int yearFinJ6 = pref.getInt("key_Fin_YearJ6", 0);
        int dayOfMonthFinJ6 = pref.getInt("key_Fin_DayJ6", 0);
        int heureFinJ6 = pref.getInt("key_Fin_HourJ6", 0);
        int minuteFinJ6 = pref.getInt("key_Fin_MinuteJ6", 0);
        if(monthFinJ6 == 0 && yearFinJ6 == 0 && dayOfMonthFinJ6 == 0 && heureFinJ6 == 0 && minuteFinJ6 == 0) {
            tvHeureFinJ6.setText("");
            tvDateFinJ6.setText("");
        }else {
            calendarFinj6.set(Calendar.MONTH, monthFinJ6);
            calendarFinj6.set(Calendar.YEAR, yearFinJ6);
            calendarFinj6.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ6);
            calendarFinj6.set(Calendar.HOUR_OF_DAY, heureFinJ6);
            calendarFinj6.set(Calendar.MINUTE, minuteFinJ6);
            calendarFinj6.set(Calendar.SECOND, 0);
            calendarFinj6.set(Calendar.MILLISECOND, 0);
            int NomduJourFin = calendarFinj6.get(Calendar.DAY_OF_WEEK);
            tvHeureFinJ6.setText(String.format("%02d:%02d",heureFinJ6,minuteFinJ6));
            tvDateFinJ6.setText(getDayName(NomduJourFin-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthFinJ6 ,(monthFinJ6 + 1), yearFinJ6));
        }
        int minutesconduiteJ6 = pref.getInt("key_tempsDeConduite_J6", 0);
        int hourconduiteJ6 = (minutesconduiteJ6/60);
        int minutesAAfficherconduiteJ6 = minutesconduiteJ6 - (hourconduiteJ6*60);
        tvHeureConduiteJ6.setText(twoDigitString(hourconduiteJ6) + ":" + twoDigitString(minutesAAfficherconduiteJ6));

        int minutesTravailJ6 = pref.getInt("key_tempsDeTravail_J6", 0);
        int hourTravailJ6 = (minutesTravailJ6/60);
        int minutesAAfficherTravailJ6 = minutesTravailJ6 - (hourTravailJ6*60);
        tvHeureTravailJ6.setText(twoDigitString(hourTravailJ6) + ":" + twoDigitString(minutesAAfficherTravailJ6));

        int minutesDispoJ6 = pref.getInt("key_tempsDeDispo_J6", 0);
        int hourDispoJ6 = (minutesDispoJ6/60);
        int minutesAAfficherDispoJ6 = minutesDispoJ6 - (hourDispoJ6*60);
        tvHeureDispoJ6.setText(twoDigitString(hourDispoJ6) + ":" + twoDigitString(minutesAAfficherDispoJ6));

        boolean CheckboxJ1 = pref.getBoolean("key_CheckBoxRepos_J1",false);
        boolean CheckboxJ2 = pref.getBoolean("key_CheckBoxRepos_J2",false);
        boolean CheckboxJ3 = pref.getBoolean("key_CheckBoxRepos_J3",false);
        boolean CheckboxJ4 = pref.getBoolean("key_CheckBoxRepos_J4",false);
        boolean CheckboxJ5 = pref.getBoolean("key_CheckBoxRepos_J5",false);
        checkBox3HeuresReposJ1.setChecked(CheckboxJ1);
        checkBox3HeuresReposJ2.setChecked(CheckboxJ2);
        checkBox3HeuresReposJ3.setChecked(CheckboxJ3);
        checkBox3HeuresReposJ4.setChecked(CheckboxJ4);
        checkBox3HeuresReposJ5.setChecked(CheckboxJ5);

        calculTotalparJour(1);
        calculTotalparJour(2);
        calculTotalparJour(3);
        calculTotalparJour(4);
        calculTotalparJour(5);
        calculTotalparJour(6);
        calculTotalparSemaine();
        calculAmplitudeJ1();
        calculAmplitudeJ2();
        calculAmplitudeJ3();
        calculAmplitudeJ4();
        calculAmplitudeJ5();
        calculAmplitudeJ6();
        calculTempsRepos();
    }

    private static void calculTotalparJour(int numJour) {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutesconduite = pref.getInt("key_tempsDeConduite_J" + numJour, 0);
        int minutesTravailJ = pref.getInt("key_tempsDeTravail_J" + numJour, 0);
        int minutesDispoJ = pref.getInt("key_tempsDeDispo_J"+ numJour, 0);
        int totalMinute = minutesDispoJ + minutesTravailJ + minutesconduite;
        int totalhour = (totalMinute/60);
        int minutesAAfficher = totalMinute - (totalhour*60);
        switch(numJour) {
            case 1:
                tvTempsServiceJ1.setText(twoDigitString(totalhour) + ":" + twoDigitString(minutesAAfficher));
                break;
            case 2:
                tvTempsServiceJ2.setText(twoDigitString(totalhour) + ":" + twoDigitString(minutesAAfficher));
                break;
            case 3:
                tvTempsServiceJ3.setText(twoDigitString(totalhour) + ":" + twoDigitString(minutesAAfficher));
                break;
            case 4:
                tvTempsServiceJ4.setText(twoDigitString(totalhour) + ":" + twoDigitString(minutesAAfficher));
                break;
            case 5:
                tvTempsServiceJ5.setText(twoDigitString(totalhour) + ":" + twoDigitString(minutesAAfficher));
                break;
            case 6:
                tvTempsServiceJ6.setText(twoDigitString(totalhour) + ":" + twoDigitString(minutesAAfficher));
                break;
            default:
                tvTempsServiceJ1.setText("00:00");
                tvTempsServiceJ2.setText("00:00");
                tvTempsServiceJ3.setText("00:00");
                tvTempsServiceJ4.setText("00:00");
                tvTempsServiceJ5.setText("00:00");
                tvTempsServiceJ6.setText("00:00");
                break;
        }
    }

    private static void calculTotalparSemaine() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int nbConduite = 0;
        int minutesConduiteJ1 = pref.getInt("key_tempsDeConduite_J1", 0);
        if(minutesConduiteJ1>540){
            nbConduite++;
        }
        int minutesConduiteJ2 = pref.getInt("key_tempsDeConduite_J2", 0);
        if(minutesConduiteJ2>540){
            nbConduite++;
        }
        int minutesConduiteJ3 = pref.getInt("key_tempsDeConduite_J3", 0);
        if(minutesConduiteJ3>540){
            nbConduite++;
        }
        int minutesConduiteJ4 = pref.getInt("key_tempsDeConduite_J4", 0);
        if(minutesConduiteJ4>540){
            nbConduite++;
        }
        int minutesConduiteJ5 = pref.getInt("key_tempsDeConduite_J5", 0);
        if(minutesConduiteJ5>540){
            nbConduite++;
        }
        int minutesConduiteJ6 = pref.getInt("key_tempsDeConduite_J6", 0);
        if(minutesConduiteJ6>540){
            nbConduite++;
        }
        int totalConduiteSemaine = minutesConduiteJ1 + minutesConduiteJ2 + minutesConduiteJ3 + minutesConduiteJ4 + minutesConduiteJ5 + minutesConduiteJ6;
        tvNbConduite.setText(nbConduite+"/2");
        int minutesTravailJ1 = pref.getInt("key_tempsDeTravail_J1", 0);
        int minutesTravailJ2 = pref.getInt("key_tempsDeTravail_J2", 0);
        int minutesTravailJ3 = pref.getInt("key_tempsDeTravail_J3", 0);
        int minutesTravailJ4 = pref.getInt("key_tempsDeTravail_J4", 0);
        int minutesTravailJ5 = pref.getInt("key_tempsDeTravail_J5", 0);
        int minutesTravailJ6 = pref.getInt("key_tempsDeTravail_J6", 0);
        int totalTravailSemaine = minutesTravailJ1 + minutesTravailJ2 + minutesTravailJ3 + minutesTravailJ4 + minutesTravailJ5 + minutesTravailJ6;

        int minutesDispoJ1 = pref.getInt("key_tempsDeDispo_J1", 0);
        int minutesDispoJ2 = pref.getInt("key_tempsDeDispo_J2", 0);
        int minutesDispoJ3 = pref.getInt("key_tempsDeDispo_J3", 0);
        int minutesDispoJ4 = pref.getInt("key_tempsDeDispo_J4", 0);
        int minutesDispoJ5 = pref.getInt("key_tempsDeDispo_J5", 0);
        int minutesDispoJ6 = pref.getInt("key_tempsDeDispo_J6", 0);
        int totalDispoSemaine = minutesDispoJ1 + minutesDispoJ2 + minutesDispoJ3 + minutesDispoJ4 + minutesDispoJ5 + minutesDispoJ6;

        int totalServiceSemaine = totalConduiteSemaine + totalTravailSemaine + totalDispoSemaine;

        tvTotalConduiteSemaine.setText(transformMinutes(totalConduiteSemaine));
        tvTotalTravailSemaine.setText(transformMinutes(totalTravailSemaine));
        tvTotalDispoSemaine.setText(transformMinutes(totalDispoSemaine));
        tvTotalServiceSemaine.setText(transformMinutes(totalServiceSemaine));
    }

    private static void calculTempsRepos() {
        nbRepos9h = 0;
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        Calendar CalendrierDebutJ1 = Calendar.getInstance();
        int monthDebutJ1 = pref.getInt("key_Debut_MonthJ1", 0);
        int yearDebutJ1 = pref.getInt("key_Debut_YearJ1", 0);
        int dayOfMonthDebutJ1 = pref.getInt("key_Debut_DayJ1", 0);
        int HourDebutJ1 = pref.getInt("key_Debut_HourJ1", 0);
        int MinuteDebutJ1 = pref.getInt("key_Debut_MinuteJ1", 0);
        if (monthDebutJ1!= 0  && yearDebutJ1 != 0 && dayOfMonthDebutJ1 != 0 ){
            CalendrierDebutJ1.set(Calendar.MONTH, monthDebutJ1);
            CalendrierDebutJ1.set(Calendar.YEAR, yearDebutJ1);
            CalendrierDebutJ1.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ1);
            CalendrierDebutJ1.set(Calendar.HOUR_OF_DAY, HourDebutJ1);
            CalendrierDebutJ1.set(Calendar.MINUTE, MinuteDebutJ1);
            CalendrierDebutJ1.set(Calendar.SECOND, 0);
            CalendrierDebutJ1.set(Calendar.MILLISECOND, 0);
        } else{
            tvTempsReposJ1.setText("00:00");
        }

        Calendar CalendrierDebutJ2 = Calendar.getInstance();
        int monthDebutJ2 = pref.getInt("key_Debut_MonthJ2", 0);
        int yearDebutJ2 = pref.getInt("key_Debut_YearJ2", 0);
        int dayOfMonthDebutJ2 = pref.getInt("key_Debut_DayJ2", 0);
        int HourDebutJ2 = pref.getInt("key_Debut_HourJ2", 0);
        int MinuteDebutJ2 = pref.getInt("key_Debut_MinuteJ2", 0);
        if (monthDebutJ2!= 0  && yearDebutJ2 != 0 && dayOfMonthDebutJ2 != 0 ){
            CalendrierDebutJ2.set(Calendar.MONTH, monthDebutJ2);
            CalendrierDebutJ2.set(Calendar.YEAR, yearDebutJ2);
            CalendrierDebutJ2.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ2);
            CalendrierDebutJ2.set(Calendar.HOUR_OF_DAY, HourDebutJ2);
            CalendrierDebutJ2.set(Calendar.MINUTE, MinuteDebutJ2);
            CalendrierDebutJ2.set(Calendar.SECOND, 0);
            CalendrierDebutJ2.set(Calendar.MILLISECOND, 0);
        }else{
            tvTempsReposJ2.setText("00:00");
        }

        Calendar CalendrierDebutJ3 = Calendar.getInstance();
        int monthDebutJ3 = pref.getInt("key_Debut_MonthJ3", 0);
        int yearDebutJ3 = pref.getInt("key_Debut_YearJ3", 0);
        int dayOfMonthDebutJ3 = pref.getInt("key_Debut_DayJ3", 0);
        int HourDebutJ3 = pref.getInt("key_Debut_HourJ3", 0);
        int MinuteDebutJ3 = pref.getInt("key_Debut_MinuteJ3", 0);
        if (monthDebutJ3!= 0  && yearDebutJ3 != 0 && dayOfMonthDebutJ3 != 0 ){
            CalendrierDebutJ3.set(Calendar.MONTH, monthDebutJ3);
            CalendrierDebutJ3.set(Calendar.YEAR, yearDebutJ3);
            CalendrierDebutJ3.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ3);
            CalendrierDebutJ3.set(Calendar.HOUR_OF_DAY, HourDebutJ3);
            CalendrierDebutJ3.set(Calendar.MINUTE, MinuteDebutJ3);
            CalendrierDebutJ3.set(Calendar.SECOND, 0);
            CalendrierDebutJ3.set(Calendar.MILLISECOND, 0);
        }else{
            tvTempsReposJ3.setText("00:00");
        }

        Calendar CalendrierDebutJ4 = Calendar.getInstance();
        int monthDebutJ4 = pref.getInt("key_Debut_MonthJ4", 0);
        int yearDebutJ4 = pref.getInt("key_Debut_YearJ4", 0);
        int dayOfMonthDebutJ4 = pref.getInt("key_Debut_DayJ4", 0);
        int HourDebutJ4 = pref.getInt("key_Debut_HourJ4", 0);
        int MinuteDebutJ4 = pref.getInt("key_Debut_MinuteJ4", 0);
        if (monthDebutJ4!= 0  && yearDebutJ4 != 0 && dayOfMonthDebutJ4 != 0 ){
            CalendrierDebutJ4.set(Calendar.MONTH, monthDebutJ4);
            CalendrierDebutJ4.set(Calendar.YEAR, yearDebutJ4);
            CalendrierDebutJ4.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ4);
            CalendrierDebutJ4.set(Calendar.HOUR_OF_DAY, HourDebutJ4);
            CalendrierDebutJ4.set(Calendar.MINUTE, MinuteDebutJ4);
            CalendrierDebutJ4.set(Calendar.SECOND, 0);
            CalendrierDebutJ4.set(Calendar.MILLISECOND, 0);
        }else{
            tvTempsReposJ4.setText("00:00");
        }

        Calendar CalendrierDebutJ5 = Calendar.getInstance();
        int monthDebutJ5 = pref.getInt("key_Debut_MonthJ5", 0);
        int yearDebutJ5 = pref.getInt("key_Debut_YearJ5", 0);
        int dayOfMonthDebutJ5 = pref.getInt("key_Debut_DayJ5", 0);
        int HourDebutJ5 = pref.getInt("key_Debut_HourJ5", 0);
        int MinuteDebutJ5 = pref.getInt("key_Debut_MinuteJ5", 0);
        if (monthDebutJ5!= 0  && yearDebutJ5 != 0 && dayOfMonthDebutJ5 != 0 ){
            CalendrierDebutJ5.set(Calendar.MONTH, monthDebutJ5);
            CalendrierDebutJ5.set(Calendar.YEAR, yearDebutJ5);
            CalendrierDebutJ5.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ5);
            CalendrierDebutJ5.set(Calendar.HOUR_OF_DAY, HourDebutJ5);
            CalendrierDebutJ5.set(Calendar.MINUTE, MinuteDebutJ5);
            CalendrierDebutJ5.set(Calendar.SECOND, 0);
            CalendrierDebutJ5.set(Calendar.MILLISECOND, 0);
        }else{
            tvTempsReposJ5.setText("00:00");
        }

        Calendar CalendrierDebutJ6 = Calendar.getInstance();
        int monthDebutJ6 = pref.getInt("key_Debut_MonthJ6", 0);
        int yearDebutJ6 = pref.getInt("key_Debut_YearJ6", 0);
        int dayOfMonthDebutJ6 = pref.getInt("key_Debut_DayJ6", 0);
        int HourDebutJ6 = pref.getInt("key_Debut_HourJ6", 0);
        int MinuteDebutJ6 = pref.getInt("key_Debut_MinuteJ6", 0);
        if (monthDebutJ6!= 0  && yearDebutJ6 != 0 && dayOfMonthDebutJ6 != 0 ){
            CalendrierDebutJ6.set(Calendar.MONTH, monthDebutJ6);
            CalendrierDebutJ6.set(Calendar.YEAR, yearDebutJ6);
            CalendrierDebutJ6.set(Calendar.DAY_OF_MONTH, dayOfMonthDebutJ6);
            CalendrierDebutJ6.set(Calendar.HOUR_OF_DAY, HourDebutJ6);
            CalendrierDebutJ6.set(Calendar.MINUTE, MinuteDebutJ6);
            CalendrierDebutJ6.set(Calendar.SECOND, 0);
            CalendrierDebutJ6.set(Calendar.MILLISECOND, 0);
        }

        Calendar CalendrierFinJ1 = Calendar.getInstance();
        int monthFinJ1 = pref.getInt("key_Fin_MonthJ1", 0);
        int yearFinJ1 = pref.getInt("key_Fin_YearJ1", 0);
        int dayOfMonthFinJ1 = pref.getInt("key_Fin_DayJ1", 0);
        int HourFinJ1 = pref.getInt("key_Fin_HourJ1", 0);
        int MinuteFinJ1 = pref.getInt("key_Fin_MinuteJ1", 0);
        if (monthFinJ1!= 0  && yearFinJ1 != 0 && dayOfMonthFinJ1 != 0 ){
            CalendrierFinJ1.set(Calendar.MONTH, monthFinJ1);
            CalendrierFinJ1.set(Calendar.YEAR, yearFinJ1);
            CalendrierFinJ1.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ1);
            CalendrierFinJ1.set(Calendar.HOUR_OF_DAY, HourFinJ1);
            CalendrierFinJ1.set(Calendar.MINUTE, MinuteFinJ1);
            CalendrierFinJ1.set(Calendar.SECOND, 0);
            CalendrierFinJ1.set(Calendar.MILLISECOND, 0);
        }

        Calendar CalendrierFinJ2 = Calendar.getInstance();
        int monthFinJ2 = pref.getInt("key_Fin_MonthJ2", 0);
        int yearFinJ2 = pref.getInt("key_Fin_YearJ2", 0);
        int dayOfMonthFinJ2 = pref.getInt("key_Fin_DayJ2", 0);
        int HourFinJ2 = pref.getInt("key_Fin_HourJ2", 0);
        int MinuteFinJ2 = pref.getInt("key_Fin_MinuteJ2", 0);
        if (monthFinJ2!= 0  && yearFinJ2 != 0 && dayOfMonthFinJ2 != 0 ){
            CalendrierFinJ2.set(Calendar.MONTH, monthFinJ2);
            CalendrierFinJ2.set(Calendar.YEAR, yearFinJ2);
            CalendrierFinJ2.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ2);
            CalendrierFinJ2.set(Calendar.HOUR_OF_DAY, HourFinJ2);
            CalendrierFinJ2.set(Calendar.MINUTE, MinuteFinJ2);
            CalendrierFinJ2.set(Calendar.SECOND, 0);
            CalendrierFinJ2.set(Calendar.MILLISECOND, 0);
        }

        Calendar CalendrierFinJ3 = Calendar.getInstance();
        int monthFinJ3 = pref.getInt("key_Fin_MonthJ3", 0);
        int yearFinJ3 = pref.getInt("key_Fin_YearJ3", 0);
        int dayOfMonthFinJ3 = pref.getInt("key_Fin_DayJ3", 0);
        int HourFinJ3 = pref.getInt("key_Fin_HourJ3", 0);
        int MinuteFinJ3 = pref.getInt("key_Fin_MinuteJ3", 0);
        if (monthFinJ3!= 0  && yearFinJ3 != 0 && dayOfMonthFinJ3 != 0 ){
            CalendrierFinJ3.set(Calendar.MONTH, monthFinJ3);
            CalendrierFinJ3.set(Calendar.YEAR, yearFinJ3);
            CalendrierFinJ3.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ3);
            CalendrierFinJ3.set(Calendar.HOUR_OF_DAY, HourFinJ3);
            CalendrierFinJ3.set(Calendar.MINUTE, MinuteFinJ3);
            CalendrierFinJ3.set(Calendar.SECOND, 0);
            CalendrierFinJ3.set(Calendar.MILLISECOND, 0);
        }

        Calendar CalendrierFinJ4 = Calendar.getInstance();
        int monthFinJ4 = pref.getInt("key_Fin_MonthJ4", 0);
        int yearFinJ4 = pref.getInt("key_Fin_YearJ4", 0);
        int dayOfMonthFinJ4 = pref.getInt("key_Fin_DayJ4", 0);
        int HourFinJ4 = pref.getInt("key_Fin_HourJ4", 0);
        int MinuteFinJ4 = pref.getInt("key_Fin_MinuteJ4", 0);
        if (monthFinJ4!= 0  && yearFinJ4 != 0 && dayOfMonthFinJ4 != 0 ){
            CalendrierFinJ4.set(Calendar.MONTH, monthFinJ4);
            CalendrierFinJ4.set(Calendar.YEAR, yearFinJ4);
            CalendrierFinJ4.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ4);
            CalendrierFinJ4.set(Calendar.HOUR_OF_DAY, HourFinJ4);
            CalendrierFinJ4.set(Calendar.MINUTE, MinuteFinJ4);
            CalendrierFinJ4.set(Calendar.SECOND, 0);
            CalendrierFinJ4.set(Calendar.MILLISECOND, 0);
        }

        Calendar CalendrierFinJ5 = Calendar.getInstance();
        int monthFinJ5 = pref.getInt("key_Fin_MonthJ5", 0);
        int yearFinJ5 = pref.getInt("key_Fin_YearJ5", 0);
        int dayOfMonthFinJ5 = pref.getInt("key_Fin_DayJ5", 0);
        int HourFinJ5 = pref.getInt("key_Fin_HourJ5", 0);
        int MinuteFinJ5 = pref.getInt("key_Fin_MinuteJ5", 0);
        if (monthFinJ5!= 0  && yearFinJ5 != 0 && dayOfMonthFinJ5 != 0 ){
            CalendrierFinJ5.set(Calendar.MONTH, monthFinJ5);
            CalendrierFinJ5.set(Calendar.YEAR, yearFinJ5);
            CalendrierFinJ5.set(Calendar.DAY_OF_MONTH, dayOfMonthFinJ5);
            CalendrierFinJ5.set(Calendar.HOUR_OF_DAY, HourFinJ5);
            CalendrierFinJ5.set(Calendar.MINUTE, MinuteFinJ5);
            CalendrierFinJ5.set(Calendar.SECOND, 0);
            CalendrierFinJ5.set(Calendar.MILLISECOND, 0);
        }



        Calendar calendarJ124h = (Calendar) CalendrierDebutJ1.clone();
        calendarJ124h.add(Calendar.HOUR_OF_DAY, 24);
        boolean CheckboxJ1 = pref.getBoolean("key_CheckBoxRepos_J1",false);
        if (tvDateDebutJ2.getText() !=""){
            if(calendarJ124h.compareTo(CalendrierDebutJ2) < 0){
                long diff = calendarJ124h.getTimeInMillis() - CalendrierFinJ1.getTimeInMillis();
                int minutesReposAmplitudeJ1 = (int) (diff / 60000);
                if(minutesReposAmplitudeJ1 < 660 && !CheckboxJ1){
                    nbRepos9h ++;
                }
                tvTempsReposJ1.setText(transformMinutes(minutesReposAmplitudeJ1));
            } else {
                long diff = CalendrierDebutJ2.getTimeInMillis() - CalendrierFinJ1.getTimeInMillis();
                int minutesReposAmplitudeJ1 = (int) (diff / 60000);
                if(minutesReposAmplitudeJ1 < 660 && !CheckboxJ1){
                    nbRepos9h++;
                }
                tvTempsReposJ1.setText(transformMinutes(minutesReposAmplitudeJ1));
            }
        } else {
            tvTempsReposJ1.setText("Saisir le début du jour suivant");
        }

        Calendar calendarJ224h = (Calendar) CalendrierDebutJ2.clone();
        calendarJ224h.add(Calendar.HOUR_OF_DAY, 24);
        boolean CheckboxJ2 = pref.getBoolean("key_CheckBoxRepos_J2",false);
        if (tvDateDebutJ3.getText() !=""){
            if(calendarJ224h.compareTo(CalendrierDebutJ3) < 0){
                long diff = calendarJ224h.getTimeInMillis() - CalendrierFinJ2.getTimeInMillis();
                int minutesReposAmplitudeJ2 = (int) (diff / 60000);
                if(minutesReposAmplitudeJ2 < 660 && !CheckboxJ2){
                    nbRepos9h ++;
                }
                tvTempsReposJ2.setText(transformMinutes(minutesReposAmplitudeJ2));
            } else {
                long diff = CalendrierDebutJ3.getTimeInMillis() - CalendrierFinJ2.getTimeInMillis();
                int minutesReposAmplitudeJ2 = (int) (diff / 60000);
                if(minutesReposAmplitudeJ2 < 660 && !CheckboxJ2){
                    nbRepos9h ++;
                }
                tvTempsReposJ2.setText(transformMinutes(minutesReposAmplitudeJ2));
            }
        } else {
            tvTempsReposJ2.setText("Saisir le début du jour suivant");
        }

        Calendar calendarJ324h = (Calendar) CalendrierDebutJ3.clone();
        calendarJ324h.add(Calendar.HOUR_OF_DAY, 24);
        boolean CheckboxJ3 = pref.getBoolean("key_CheckBoxRepos_J3",false);
        if (tvDateDebutJ4.getText() !=""){
            if(calendarJ324h.compareTo(CalendrierDebutJ4) < 0){
                long diff = calendarJ324h.getTimeInMillis() - CalendrierFinJ3.getTimeInMillis();
                int minutesReposAmplitudeJ3 = (int) (diff / 60000);
                if(minutesReposAmplitudeJ3 < 660 && !CheckboxJ3){
                    nbRepos9h ++;
                }
                tvTempsReposJ3.setText(transformMinutes(minutesReposAmplitudeJ3));
            } else {
                long diff = CalendrierDebutJ4.getTimeInMillis() - CalendrierFinJ3.getTimeInMillis();
                int minutesReposAmplitudeJ3 = (int) (diff / 60000);
                if(minutesReposAmplitudeJ3 < 660 && !CheckboxJ3){
                    nbRepos9h ++;
                }
                tvTempsReposJ3.setText(transformMinutes(minutesReposAmplitudeJ3));
            }
        } else {
            tvTempsReposJ3.setText("Saisir le début du jour suivant");
        }

        Calendar calendarJ424h = (Calendar) CalendrierDebutJ4.clone();
        calendarJ424h.add(Calendar.HOUR_OF_DAY, 24);
        boolean CheckboxJ4 = pref.getBoolean("key_CheckBoxRepos_J4",false);
        if (tvDateDebutJ5.getText() !=""){
            if(calendarJ424h.compareTo(CalendrierDebutJ5) < 0){
                long diff = calendarJ424h.getTimeInMillis() - CalendrierFinJ4.getTimeInMillis();
                int minutesReposAmplitudeJ4 = (int) (diff / 60000);
                if(minutesReposAmplitudeJ4 < 660 && !CheckboxJ4){
                    nbRepos9h ++;
                }
                tvTempsReposJ4.setText(transformMinutes(minutesReposAmplitudeJ4));
            } else {
                long diff = CalendrierDebutJ5.getTimeInMillis() - CalendrierFinJ4.getTimeInMillis();
                int minutesReposAmplitudeJ4 = (int) (diff / 60000);
                if(minutesReposAmplitudeJ4 < 660 && !CheckboxJ4){
                    nbRepos9h ++;
                }
                tvTempsReposJ4.setText(transformMinutes(minutesReposAmplitudeJ4));
            }
        } else {
            tvTempsReposJ4.setText("Saisir le début du jour suivant");
        }

        Calendar calendarJ524h = (Calendar) CalendrierDebutJ5.clone();
        calendarJ524h.add(Calendar.HOUR_OF_DAY, 24);
        boolean CheckboxJ5 = pref.getBoolean("key_CheckBoxRepos_J5",false);
        if (tvDateDebutJ6.getText() !=""){
            if(calendarJ524h.compareTo(CalendrierDebutJ6) < 0){
                long diff = calendarJ524h.getTimeInMillis() - CalendrierFinJ5.getTimeInMillis();
                int minutesReposAmplitudeJ5 = (int) (diff / 60000);
                if(minutesReposAmplitudeJ5 < 660 && !CheckboxJ5){
                    nbRepos9h ++;
                }
                tvTempsReposJ5.setText(transformMinutes(minutesReposAmplitudeJ5));
            } else {
                long diff = CalendrierDebutJ6.getTimeInMillis() - CalendrierFinJ5.getTimeInMillis();
                int minutesReposAmplitudeJ5 = (int) (diff / 60000);
                if(minutesReposAmplitudeJ5 < 660 && !CheckboxJ5){
                    nbRepos9h ++;
                }
                tvTempsReposJ5.setText(transformMinutes(minutesReposAmplitudeJ5));
            }
        } else {
            tvTempsReposJ5.setText("Saisir le début du jour suivant");
        }


        tvNbRepos.setText(nbRepos9h + "/3");
    }

    private static void calculAmplitudeJ1() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ1.getText().toString().equals("") || tvDateFinJ1.getText().toString().equals("")){
            tvTempsAmplitudeJ1.setText("00:00");
        }else{
            int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
            Calendar calendarDebutJ1 = Calendar.getInstance();
            monthDebut = pref.getInt("key_Debut_MonthJ1", 0);
            yearDebut = pref.getInt("key_Debut_YearJ1", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ1", 0);
            HourDebut = pref.getInt("key_Debut_HourJ1", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ1", 0);
            calendarDebutJ1.set(Calendar.MONTH, monthDebut);
            calendarDebutJ1.set(Calendar.YEAR, yearDebut);
            calendarDebutJ1.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ1.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ1.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ1.set(Calendar.SECOND, 0);
            calendarDebutJ1.set(Calendar.MILLISECOND, 0);

            int yearFin,monthFin,dayOfMonthFin,HourFin,MinuteFin;
            Calendar calendarFinJ1 = Calendar.getInstance();
            monthFin = pref.getInt("key_Fin_MonthJ1", 0);
            yearFin = pref.getInt("key_Fin_YearJ1", 0);
            dayOfMonthFin = pref.getInt("key_Fin_DayJ1", 0);
            HourFin = pref.getInt("key_Fin_HourJ1", 0);
            MinuteFin = pref.getInt("key_Fin_MinuteJ1", 0);
            calendarFinJ1.set(Calendar.MONTH, monthFin);
            calendarFinJ1.set(Calendar.YEAR, yearFin);
            calendarFinJ1.set(Calendar.DAY_OF_MONTH, dayOfMonthFin);
            calendarFinJ1.set(Calendar.HOUR_OF_DAY, HourFin);
            calendarFinJ1.set(Calendar.MINUTE, MinuteFin);
            calendarFinJ1.set(Calendar.SECOND, 0);
            calendarFinJ1.set(Calendar.MILLISECOND, 0);

            if(calendarFinJ1.compareTo(calendarDebutJ1) > 0) {
                long diff = calendarFinJ1.getTimeInMillis() - calendarDebutJ1.getTimeInMillis();
                int minutesAmplitudeJ1 = (int) (diff / 60000);
                tvTempsAmplitudeJ1.setText(transformMinutes(minutesAmplitudeJ1));
            } else{
                tvTempsAmplitudeJ1.setText("Erreur de dates");
            }
        }
    }
    private static void calculAmplitudeJ2() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ2.getText().toString().equals("") || tvDateFinJ2.getText().toString().equals("")){
            tvTempsAmplitudeJ2.setText("00:00");
        }else{
            int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
            Calendar calendarDebutJ2 = Calendar.getInstance();
            monthDebut = pref.getInt("key_Debut_MonthJ2", 0);
            yearDebut = pref.getInt("key_Debut_YearJ2", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ2", 0);
            HourDebut = pref.getInt("key_Debut_HourJ2", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ2", 0);
            calendarDebutJ2.set(Calendar.MONTH, monthDebut);
            calendarDebutJ2.set(Calendar.YEAR, yearDebut);
            calendarDebutJ2.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ2.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ2.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ2.set(Calendar.SECOND, 0);
            calendarDebutJ2.set(Calendar.MILLISECOND, 0);

            int yearFin,monthFin,dayOfMonthFin,HourFin,MinuteFin;
            Calendar calendarFinJ2 = Calendar.getInstance();
            monthFin = pref.getInt("key_Fin_MonthJ2", 0);
            yearFin = pref.getInt("key_Fin_YearJ2", 0);
            dayOfMonthFin = pref.getInt("key_Fin_DayJ2", 0);
            HourFin = pref.getInt("key_Fin_HourJ2", 0);
            MinuteFin = pref.getInt("key_Fin_MinuteJ2", 0);
            calendarFinJ2.set(Calendar.MONTH, monthFin);
            calendarFinJ2.set(Calendar.YEAR, yearFin);
            calendarFinJ2.set(Calendar.DAY_OF_MONTH, dayOfMonthFin);
            calendarFinJ2.set(Calendar.HOUR_OF_DAY, HourFin);
            calendarFinJ2.set(Calendar.MINUTE, MinuteFin);
            calendarFinJ2.set(Calendar.SECOND, 0);
            calendarFinJ2.set(Calendar.MILLISECOND, 0);

            if(calendarFinJ2.compareTo(calendarDebutJ2) > 0) {
                long diff = calendarFinJ2.getTimeInMillis() - calendarDebutJ2.getTimeInMillis();
                int minutesAmplitudeJ2 = (int) (diff / 60000);
                tvTempsAmplitudeJ2.setText(transformMinutes(minutesAmplitudeJ2));
            } else{
                tvTempsAmplitudeJ2.setText("Erreur de dates");
            }
        }
    }
    private static void calculAmplitudeJ3() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ3.getText().toString().equals("") || tvDateFinJ3.getText().toString().equals("")){
            tvTempsAmplitudeJ3.setText("00:00");
        }else{
            int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
            Calendar calendarDebutJ3 = Calendar.getInstance();
            monthDebut = pref.getInt("key_Debut_MonthJ3", 0);
            yearDebut = pref.getInt("key_Debut_YearJ3", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ3", 0);
            HourDebut = pref.getInt("key_Debut_HourJ3", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ3", 0);
            calendarDebutJ3.set(Calendar.MONTH, monthDebut);
            calendarDebutJ3.set(Calendar.YEAR, yearDebut);
            calendarDebutJ3.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ3.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ3.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ3.set(Calendar.SECOND, 0);
            calendarDebutJ3.set(Calendar.MILLISECOND, 0);

            int yearFin,monthFin,dayOfMonthFin,HourFin,MinuteFin;
            Calendar calendarFinJ3 = Calendar.getInstance();
            monthFin = pref.getInt("key_Fin_MonthJ3", 0);
            yearFin = pref.getInt("key_Fin_YearJ3", 0);
            dayOfMonthFin = pref.getInt("key_Fin_DayJ3", 0);
            HourFin = pref.getInt("key_Fin_HourJ3", 0);
            MinuteFin = pref.getInt("key_Fin_MinuteJ3", 0);
            calendarFinJ3.set(Calendar.MONTH, monthFin);
            calendarFinJ3.set(Calendar.YEAR, yearFin);
            calendarFinJ3.set(Calendar.DAY_OF_MONTH, dayOfMonthFin);
            calendarFinJ3.set(Calendar.HOUR_OF_DAY, HourFin);
            calendarFinJ3.set(Calendar.MINUTE, MinuteFin);
            calendarFinJ3.set(Calendar.SECOND, 0);
            calendarFinJ3.set(Calendar.MILLISECOND, 0);

            if(calendarFinJ3.compareTo(calendarDebutJ3) > 0) {
                long diff = calendarFinJ3.getTimeInMillis() - calendarDebutJ3.getTimeInMillis();
                int minutesAmplitudeJ3 = (int) (diff / 60000);
                tvTempsAmplitudeJ3.setText(transformMinutes(minutesAmplitudeJ3));
            } else{
                tvTempsAmplitudeJ3.setText("Erreur de dates");
            }
        }
    }
    private static void calculAmplitudeJ4() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ4.getText().toString().equals("") || tvDateFinJ4.getText().toString().equals("")){
            tvTempsAmplitudeJ4.setText("00:00");
        }else{
            int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
            Calendar calendarDebutJ4 = Calendar.getInstance();
            monthDebut = pref.getInt("key_Debut_MonthJ4", 0);
            yearDebut = pref.getInt("key_Debut_YearJ4", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ4", 0);
            HourDebut = pref.getInt("key_Debut_HourJ4", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ4", 0);
            calendarDebutJ4.set(Calendar.MONTH, monthDebut);
            calendarDebutJ4.set(Calendar.YEAR, yearDebut);
            calendarDebutJ4.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ4.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ4.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ4.set(Calendar.SECOND, 0);
            calendarDebutJ4.set(Calendar.MILLISECOND, 0);

            int yearFin,monthFin,dayOfMonthFin,HourFin,MinuteFin;
            Calendar calendarFinJ4 = Calendar.getInstance();
            monthFin = pref.getInt("key_Fin_MonthJ4", 0);
            yearFin = pref.getInt("key_Fin_YearJ4", 0);
            dayOfMonthFin = pref.getInt("key_Fin_DayJ4", 0);
            HourFin = pref.getInt("key_Fin_HourJ4", 0);
            MinuteFin = pref.getInt("key_Fin_MinuteJ4", 0);
            calendarFinJ4.set(Calendar.MONTH, monthFin);
            calendarFinJ4.set(Calendar.YEAR, yearFin);
            calendarFinJ4.set(Calendar.DAY_OF_MONTH, dayOfMonthFin);
            calendarFinJ4.set(Calendar.HOUR_OF_DAY, HourFin);
            calendarFinJ4.set(Calendar.MINUTE, MinuteFin);
            calendarFinJ4.set(Calendar.SECOND, 0);
            calendarFinJ4.set(Calendar.MILLISECOND, 0);

            if(calendarFinJ4.compareTo(calendarDebutJ4) > 0) {
                long diff = calendarFinJ4.getTimeInMillis() - calendarDebutJ4.getTimeInMillis();
                int minutesAmplitudeJ4 = (int) (diff / 60000);
                tvTempsAmplitudeJ4.setText(transformMinutes(minutesAmplitudeJ4));
            } else{
                tvTempsAmplitudeJ4.setText("Erreur de dates");
            }
        }
    }
    private static void calculAmplitudeJ5() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ5.getText().toString().equals("") || tvDateFinJ5.getText().toString().equals("")){
            tvTempsAmplitudeJ5.setText("00:00");
        }else{
            int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
            Calendar calendarDebutJ5 = Calendar.getInstance();
            monthDebut = pref.getInt("key_Debut_MonthJ5", 0);
            yearDebut = pref.getInt("key_Debut_YearJ5", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ5", 0);
            HourDebut = pref.getInt("key_Debut_HourJ5", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ5", 0);
            calendarDebutJ5.set(Calendar.MONTH, monthDebut);
            calendarDebutJ5.set(Calendar.YEAR, yearDebut);
            calendarDebutJ5.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ5.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ5.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ5.set(Calendar.SECOND, 0);
            calendarDebutJ5.set(Calendar.MILLISECOND, 0);

            int yearFin,monthFin,dayOfMonthFin,HourFin,MinuteFin;
            Calendar calendarFinJ5 = Calendar.getInstance();
            monthFin = pref.getInt("key_Fin_MonthJ5", 0);
            yearFin = pref.getInt("key_Fin_YearJ5", 0);
            dayOfMonthFin = pref.getInt("key_Fin_DayJ5", 0);
            HourFin = pref.getInt("key_Fin_HourJ5", 0);
            MinuteFin = pref.getInt("key_Fin_MinuteJ5", 0);
            calendarFinJ5.set(Calendar.MONTH, monthFin);
            calendarFinJ5.set(Calendar.YEAR, yearFin);
            calendarFinJ5.set(Calendar.DAY_OF_MONTH, dayOfMonthFin);
            calendarFinJ5.set(Calendar.HOUR_OF_DAY, HourFin);
            calendarFinJ5.set(Calendar.MINUTE, MinuteFin);
            calendarFinJ5.set(Calendar.SECOND, 0);
            calendarFinJ5.set(Calendar.MILLISECOND, 0);

            if(calendarFinJ5.compareTo(calendarDebutJ5) > 0) {
                long diff = calendarFinJ5.getTimeInMillis() - calendarDebutJ5.getTimeInMillis();
                int minutesAmplitudeJ5 = (int) (diff / 60000);
                tvTempsAmplitudeJ5.setText(transformMinutes(minutesAmplitudeJ5));
            } else{
                tvTempsAmplitudeJ5.setText("Erreur de dates");
            }
        }
    }
    private static void calculAmplitudeJ6() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ6.getText().toString().equals("") || tvDateFinJ6.getText().toString().equals("")){
            tvTempsAmplitudeJ6.setText("00:00");
        }else{
            int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
            Calendar calendarDebutJ6 = Calendar.getInstance();
            monthDebut = pref.getInt("key_Debut_MonthJ6", 0);
            yearDebut = pref.getInt("key_Debut_YearJ6", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ6", 0);
            HourDebut = pref.getInt("key_Debut_HourJ6", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ6", 0);
            calendarDebutJ6.set(Calendar.MONTH, monthDebut);
            calendarDebutJ6.set(Calendar.YEAR, yearDebut);
            calendarDebutJ6.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ6.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ6.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ6.set(Calendar.SECOND, 0);
            calendarDebutJ6.set(Calendar.MILLISECOND, 0);

            int yearFin,monthFin,dayOfMonthFin,HourFin,MinuteFin;
            Calendar calendarFinJ6 = Calendar.getInstance();
            monthFin = pref.getInt("key_Fin_MonthJ6", 0);
            yearFin = pref.getInt("key_Fin_YearJ6", 0);
            dayOfMonthFin = pref.getInt("key_Fin_DayJ6", 0);
            HourFin = pref.getInt("key_Fin_HourJ6", 0);
            MinuteFin = pref.getInt("key_Fin_MinuteJ6", 0);
            calendarFinJ6.set(Calendar.MONTH, monthFin);
            calendarFinJ6.set(Calendar.YEAR, yearFin);
            calendarFinJ6.set(Calendar.DAY_OF_MONTH, dayOfMonthFin);
            calendarFinJ6.set(Calendar.HOUR_OF_DAY, HourFin);
            calendarFinJ6.set(Calendar.MINUTE, MinuteFin);
            calendarFinJ6.set(Calendar.SECOND, 0);
            calendarFinJ6.set(Calendar.MILLISECOND, 0);

            if(calendarFinJ6.compareTo(calendarDebutJ6) > 0) {
                long diff = calendarFinJ6.getTimeInMillis() - calendarDebutJ6.getTimeInMillis();
                int minutesAmplitudeJ6 = (int) (diff / 60000);
                tvTempsAmplitudeJ6.setText(transformMinutes(minutesAmplitudeJ6));
            } else{
                tvTempsAmplitudeJ6.setText("Erreur de dates");
            }
        }
    }

    private void selectDateDebutJ1() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ1 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ1.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ1", 0);
            yearDebut = pref.getInt("key_Debut_YearJ1", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ1", 0);
            HourDebut = pref.getInt("key_Debut_HourJ1", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ1", 0);
            calendarDebutJ1.set(Calendar.MONTH, monthDebut);
            calendarDebutJ1.set(Calendar.YEAR, yearDebut);
            calendarDebutJ1.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ1.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ1.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ1.set(Calendar.SECOND, 0);
            calendarDebutJ1.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarDebutJ1.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ1.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ1.set(Calendar.MONTH, month);
            calendarDebutJ1.set(Calendar.YEAR, year);
            calendarDebutJ1.set(Calendar.DAY_OF_MONTH, day);
            calendarDebutJ1.set(Calendar.SECOND, 0);
            calendarDebutJ1.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarDebutJ1.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Debut_HourJ1",HourDebut);
            editor.putInt("key_Debut_MinuteJ1",MinuteDebut);
            editor.putInt("key_Debut_YearJ1",year);
            editor.putInt("key_Debut_MonthJ1",month);
            editor.putInt("key_Debut_DayJ1",day);
            editor.apply(); // commit changes
            tvHeureDebutJ1.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateDebutJ1.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ1();
                }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }
    private void selectDateDebutJ2() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ2 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ2.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ2", 0);
            yearDebut = pref.getInt("key_Debut_YearJ2", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ2", 0);
            HourDebut = pref.getInt("key_Debut_HourJ2", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ2", 0);
            calendarDebutJ2.set(Calendar.MONTH, monthDebut);
            calendarDebutJ2.set(Calendar.YEAR, yearDebut);
            calendarDebutJ2.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ2.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ2.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ2.set(Calendar.SECOND, 0);
            calendarDebutJ2.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarDebutJ2.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ2.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ2.set(Calendar.MONTH, month);
            calendarDebutJ2.set(Calendar.YEAR, year);
            calendarDebutJ2.set(Calendar.DAY_OF_MONTH, day);
            calendarDebutJ2.set(Calendar.SECOND, 0);
            calendarDebutJ2.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarDebutJ2.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Debut_HourJ2",HourDebut);
            editor.putInt("key_Debut_MinuteJ2",MinuteDebut);
            editor.putInt("key_Debut_YearJ2",year);
            editor.putInt("key_Debut_MonthJ2",month);
            editor.putInt("key_Debut_DayJ2",day);
            editor.apply(); // commit changes
            tvHeureDebutJ2.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateDebutJ2.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ2();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }
    private void selectDateDebutJ3() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ3 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ3.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ3", 0);
            yearDebut = pref.getInt("key_Debut_YearJ3", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ3", 0);
            HourDebut = pref.getInt("key_Debut_HourJ3", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ3", 0);
            calendarDebutJ3.set(Calendar.MONTH, monthDebut);
            calendarDebutJ3.set(Calendar.YEAR, yearDebut);
            calendarDebutJ3.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ3.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ3.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ3.set(Calendar.SECOND, 0);
            calendarDebutJ3.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarDebutJ3.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ3.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ3.set(Calendar.MONTH, month);
            calendarDebutJ3.set(Calendar.YEAR, year);
            calendarDebutJ3.set(Calendar.DAY_OF_MONTH, day);
            calendarDebutJ3.set(Calendar.SECOND, 0);
            calendarDebutJ3.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarDebutJ3.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Debut_HourJ3",HourDebut);
            editor.putInt("key_Debut_MinuteJ3",MinuteDebut);
            editor.putInt("key_Debut_YearJ3",year);
            editor.putInt("key_Debut_MonthJ3",month);
            editor.putInt("key_Debut_DayJ3",day);
            editor.apply(); // commit changes
            tvHeureDebutJ3.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateDebutJ3.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ3();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }
    private void selectDateDebutJ4() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ4 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ4.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ4", 0);
            yearDebut = pref.getInt("key_Debut_YearJ4", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ4", 0);
            HourDebut = pref.getInt("key_Debut_HourJ4", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ4", 0);
            calendarDebutJ4.set(Calendar.MONTH, monthDebut);
            calendarDebutJ4.set(Calendar.YEAR, yearDebut);
            calendarDebutJ4.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ4.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ4.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ4.set(Calendar.SECOND, 0);
            calendarDebutJ4.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarDebutJ4.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ4.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ4.set(Calendar.MONTH, month);
            calendarDebutJ4.set(Calendar.YEAR, year);
            calendarDebutJ4.set(Calendar.DAY_OF_MONTH, day);
            calendarDebutJ4.set(Calendar.SECOND, 0);
            calendarDebutJ4.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarDebutJ4.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Debut_HourJ4",HourDebut);
            editor.putInt("key_Debut_MinuteJ4",MinuteDebut);
            editor.putInt("key_Debut_YearJ4",year);
            editor.putInt("key_Debut_MonthJ4",month);
            editor.putInt("key_Debut_DayJ4",day);
            editor.apply(); // commit changes
            tvHeureDebutJ4.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateDebutJ4.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ4();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }
    private void selectDateDebutJ5() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ5 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ5.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ5", 0);
            yearDebut = pref.getInt("key_Debut_YearJ5", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ5", 0);
            HourDebut = pref.getInt("key_Debut_HourJ5", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ5", 0);
            calendarDebutJ5.set(Calendar.MONTH, monthDebut);
            calendarDebutJ5.set(Calendar.YEAR, yearDebut);
            calendarDebutJ5.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ5.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ5.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ5.set(Calendar.SECOND, 0);
            calendarDebutJ5.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarDebutJ5.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ5.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ5.set(Calendar.MONTH, month);
            calendarDebutJ5.set(Calendar.YEAR, year);
            calendarDebutJ5.set(Calendar.DAY_OF_MONTH, day);
            calendarDebutJ5.set(Calendar.SECOND, 0);
            calendarDebutJ5.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarDebutJ5.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Debut_HourJ5",HourDebut);
            editor.putInt("key_Debut_MinuteJ5",MinuteDebut);
            editor.putInt("key_Debut_YearJ5",year);
            editor.putInt("key_Debut_MonthJ5",month);
            editor.putInt("key_Debut_DayJ5",day);
            editor.apply(); // commit changes
            tvHeureDebutJ5.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateDebutJ5.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ5();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }
    private void selectDateDebutJ6() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ6 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ6.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ6", 0);
            yearDebut = pref.getInt("key_Debut_YearJ6", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ6", 0);
            HourDebut = pref.getInt("key_Debut_HourJ6", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ6", 0);
            calendarDebutJ6.set(Calendar.MONTH, monthDebut);
            calendarDebutJ6.set(Calendar.YEAR, yearDebut);
            calendarDebutJ6.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ6.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ6.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ6.set(Calendar.SECOND, 0);
            calendarDebutJ6.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarDebutJ6.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ6.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ6.set(Calendar.MONTH, month);
            calendarDebutJ6.set(Calendar.YEAR, year);
            calendarDebutJ6.set(Calendar.DAY_OF_MONTH, day);
            calendarDebutJ6.set(Calendar.SECOND, 0);
            calendarDebutJ6.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarDebutJ6.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Debut_HourJ6",HourDebut);
            editor.putInt("key_Debut_MinuteJ6",MinuteDebut);
            editor.putInt("key_Debut_YearJ6",year);
            editor.putInt("key_Debut_MonthJ6",month);
            editor.putInt("key_Debut_DayJ6",day);
            editor.apply(); // commit changes
            tvHeureDebutJ6.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateDebutJ6.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ6();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }

    private void selectDateFinJ1() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ1 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ1.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ1", 0);
            yearDebut = pref.getInt("key_Fin_YearJ1", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ1", 0);
            HourDebut = pref.getInt("key_Fin_HourJ1", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ1", 0);
            calendarFinJ1.set(Calendar.MONTH, monthDebut);
            calendarFinJ1.set(Calendar.YEAR, yearDebut);
            calendarFinJ1.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ1.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ1.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ1.set(Calendar.SECOND, 0);
            calendarFinJ1.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarFinJ1.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ1.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ1.set(Calendar.MONTH, month);
            calendarFinJ1.set(Calendar.YEAR, year);
            calendarFinJ1.set(Calendar.DAY_OF_MONTH, day);
            calendarFinJ1.set(Calendar.SECOND, 0);
            calendarFinJ1.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarFinJ1.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Fin_HourJ1",HourDebut);
            editor.putInt("key_Fin_MinuteJ1",MinuteDebut);
            editor.putInt("key_Fin_YearJ1",year);
            editor.putInt("key_Fin_MonthJ1",month);
            editor.putInt("key_Fin_DayJ1",day);
            editor.apply(); // commit changes
            tvHeureFinJ1.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateFinJ1.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ1();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }
    private void selectDateFinJ2() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ2 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ2.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ2", 0);
            yearDebut = pref.getInt("key_Fin_YearJ2", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ2", 0);
            HourDebut = pref.getInt("key_Fin_HourJ2", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ2", 0);
            calendarFinJ2.set(Calendar.MONTH, monthDebut);
            calendarFinJ2.set(Calendar.YEAR, yearDebut);
            calendarFinJ2.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ2.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ2.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ2.set(Calendar.SECOND, 0);
            calendarFinJ2.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarFinJ2.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ2.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ2.set(Calendar.MONTH, month);
            calendarFinJ2.set(Calendar.YEAR, year);
            calendarFinJ2.set(Calendar.DAY_OF_MONTH, day);
            calendarFinJ2.set(Calendar.SECOND, 0);
            calendarFinJ2.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarFinJ2.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Fin_HourJ2",HourDebut);
            editor.putInt("key_Fin_MinuteJ2",MinuteDebut);
            editor.putInt("key_Fin_YearJ2",year);
            editor.putInt("key_Fin_MonthJ2",month);
            editor.putInt("key_Fin_DayJ2",day);
            editor.apply(); // commit changes
            tvHeureFinJ2.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateFinJ2.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ2();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }
    private void selectDateFinJ3() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ3 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ3.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ3", 0);
            yearDebut = pref.getInt("key_Fin_YearJ3", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ3", 0);
            HourDebut = pref.getInt("key_Fin_HourJ3", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ3", 0);
            calendarFinJ3.set(Calendar.MONTH, monthDebut);
            calendarFinJ3.set(Calendar.YEAR, yearDebut);
            calendarFinJ3.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ3.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ3.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ3.set(Calendar.SECOND, 0);
            calendarFinJ3.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarFinJ3.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ3.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ3.set(Calendar.MONTH, month);
            calendarFinJ3.set(Calendar.YEAR, year);
            calendarFinJ3.set(Calendar.DAY_OF_MONTH, day);
            calendarFinJ3.set(Calendar.SECOND, 0);
            calendarFinJ3.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarFinJ3.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Fin_HourJ3",HourDebut);
            editor.putInt("key_Fin_MinuteJ3",MinuteDebut);
            editor.putInt("key_Fin_YearJ3",year);
            editor.putInt("key_Fin_MonthJ3",month);
            editor.putInt("key_Fin_DayJ3",day);
            editor.apply(); // commit changes
            tvHeureFinJ3.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateFinJ3.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ3();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }
    private void selectDateFinJ4() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ4 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ4.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ4", 0);
            yearDebut = pref.getInt("key_Fin_YearJ4", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ4", 0);
            HourDebut = pref.getInt("key_Fin_HourJ4", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ4", 0);
            calendarFinJ4.set(Calendar.MONTH, monthDebut);
            calendarFinJ4.set(Calendar.YEAR, yearDebut);
            calendarFinJ4.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ4.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ4.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ4.set(Calendar.SECOND, 0);
            calendarFinJ4.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarFinJ4.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ4.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ4.set(Calendar.MONTH, month);
            calendarFinJ4.set(Calendar.YEAR, year);
            calendarFinJ4.set(Calendar.DAY_OF_MONTH, day);
            calendarFinJ4.set(Calendar.SECOND, 0);
            calendarFinJ4.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarFinJ4.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Fin_HourJ4",HourDebut);
            editor.putInt("key_Fin_MinuteJ4",MinuteDebut);
            editor.putInt("key_Fin_YearJ4",year);
            editor.putInt("key_Fin_MonthJ4",month);
            editor.putInt("key_Fin_DayJ4",day);
            editor.apply(); // commit changes
            tvHeureFinJ4.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateFinJ4.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ4();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }
    private void selectDateFinJ5() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ5 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ5.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ5", 0);
            yearDebut = pref.getInt("key_Fin_YearJ5", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ5", 0);
            HourDebut = pref.getInt("key_Fin_HourJ5", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ5", 0);
            calendarFinJ5.set(Calendar.MONTH, monthDebut);
            calendarFinJ5.set(Calendar.YEAR, yearDebut);
            calendarFinJ5.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ5.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ5.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ5.set(Calendar.SECOND, 0);
            calendarFinJ5.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarFinJ5.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ5.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ5.set(Calendar.MONTH, month);
            calendarFinJ5.set(Calendar.YEAR, year);
            calendarFinJ5.set(Calendar.DAY_OF_MONTH, day);
            calendarFinJ5.set(Calendar.SECOND, 0);
            calendarFinJ5.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarFinJ5.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Fin_HourJ5",HourDebut);
            editor.putInt("key_Fin_MinuteJ5",MinuteDebut);
            editor.putInt("key_Fin_YearJ5",year);
            editor.putInt("key_Fin_MonthJ5",month);
            editor.putInt("key_Fin_DayJ5",day);
            editor.apply(); // commit changes
            tvHeureFinJ5.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateFinJ5.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ5();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }
    private void selectDateFinJ6() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ6 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ6.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ6", 0);
            yearDebut = pref.getInt("key_Fin_YearJ6", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ6", 0);
            HourDebut = pref.getInt("key_Fin_HourJ6", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ6", 0);
            calendarFinJ6.set(Calendar.MONTH, monthDebut);
            calendarFinJ6.set(Calendar.YEAR, yearDebut);
            calendarFinJ6.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ6.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ6.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ6.set(Calendar.SECOND, 0);
            calendarFinJ6.set(Calendar.MILLISECOND, 0);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,(datePicker, year, month, day) -> {
            calendarFinJ6.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ6.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ6.set(Calendar.MONTH, month);
            calendarFinJ6.set(Calendar.YEAR, year);
            calendarFinJ6.set(Calendar.DAY_OF_MONTH, day);
            calendarFinJ6.set(Calendar.SECOND, 0);
            calendarFinJ6.set(Calendar.MILLISECOND, 0);
            int NomDuJourDebut = calendarFinJ6.get(Calendar.DAY_OF_WEEK);
            editor.putInt("key_Fin_HourJ6",HourDebut);
            editor.putInt("key_Fin_MinuteJ6",MinuteDebut);
            editor.putInt("key_Fin_YearJ6",year);
            editor.putInt("key_Fin_MonthJ6",month);
            editor.putInt("key_Fin_DayJ6",day);
            editor.apply(); // commit changes
            tvHeureFinJ6.setText(String.format("%02d:%02d", HourDebut, MinuteDebut));
            tvDateFinJ6.setText(getDayName(NomDuJourDebut-1) + "\n" + String.format("%02d/%02d/%02d",day ,(month +1) , year));
            calculTempsRepos();
            calculAmplitudeJ6();
        }, yearDebut, monthDebut, dayOfMonthDebut);
        datePickerDialog.show();
    }

    private void selectHeureDebutJ1() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ1 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ1.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ1", 0);
            yearDebut = pref.getInt("key_Debut_YearJ1", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ1", 0);
            HourDebut = pref.getInt("key_Debut_HourJ1", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ1", 0);
            calendarDebutJ1.set(Calendar.MONTH, monthDebut);
            calendarDebutJ1.set(Calendar.YEAR, yearDebut);
            calendarDebutJ1.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ1.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ1.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ1.set(Calendar.SECOND, 0);
            calendarDebutJ1.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarDebutJ1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarDebutJ1.set(Calendar.MINUTE, minutes);
                calendarDebutJ1.set(Calendar.MONTH, monthDebut);
                calendarDebutJ1.set(Calendar.YEAR, yearDebut);
                calendarDebutJ1.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarDebutJ1.set(Calendar.SECOND, 0);
                calendarDebutJ1.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarDebutJ1.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Debut_HourJ1",hourOfDay );
                editor.putInt("key_Debut_MinuteJ1",minutes );
                editor.putInt("key_Debut_YearJ1",yearDebut );
                editor.putInt("key_Debut_MonthJ1",monthDebut );
                editor.putInt("key_Debut_DayJ1",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureDebutJ1.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateDebutJ1.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ1();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }
    private void selectHeureDebutJ2() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ2 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ2.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ2", 0);
            yearDebut = pref.getInt("key_Debut_YearJ2", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ2", 0);
            HourDebut = pref.getInt("key_Debut_HourJ2", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ2", 0);
            calendarDebutJ2.set(Calendar.MONTH, monthDebut);
            calendarDebutJ2.set(Calendar.YEAR, yearDebut);
            calendarDebutJ2.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ2.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ2.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ2.set(Calendar.SECOND, 0);
            calendarDebutJ2.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarDebutJ2.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarDebutJ2.set(Calendar.MINUTE, minutes);
                calendarDebutJ2.set(Calendar.MONTH, monthDebut);
                calendarDebutJ2.set(Calendar.YEAR, yearDebut);
                calendarDebutJ2.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarDebutJ2.set(Calendar.SECOND, 0);
                calendarDebutJ2.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarDebutJ2.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Debut_HourJ2",hourOfDay );
                editor.putInt("key_Debut_MinuteJ2",minutes );
                editor.putInt("key_Debut_YearJ2",yearDebut );
                editor.putInt("key_Debut_MonthJ2",monthDebut );
                editor.putInt("key_Debut_DayJ2",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureDebutJ2.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateDebutJ2.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ2();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }
    private void selectHeureDebutJ3() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ3 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ3.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ3", 0);
            yearDebut = pref.getInt("key_Debut_YearJ3", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ3", 0);
            HourDebut = pref.getInt("key_Debut_HourJ3", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ3", 0);
            calendarDebutJ3.set(Calendar.MONTH, monthDebut);
            calendarDebutJ3.set(Calendar.YEAR, yearDebut);
            calendarDebutJ3.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ3.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ3.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ3.set(Calendar.SECOND, 0);
            calendarDebutJ3.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarDebutJ3.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarDebutJ3.set(Calendar.MINUTE, minutes);
                calendarDebutJ3.set(Calendar.MONTH, monthDebut);
                calendarDebutJ3.set(Calendar.YEAR, yearDebut);
                calendarDebutJ3.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarDebutJ3.set(Calendar.SECOND, 0);
                calendarDebutJ3.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarDebutJ3.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Debut_HourJ3",hourOfDay );
                editor.putInt("key_Debut_MinuteJ3",minutes );
                editor.putInt("key_Debut_YearJ3",yearDebut );
                editor.putInt("key_Debut_MonthJ3",monthDebut );
                editor.putInt("key_Debut_DayJ3",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureDebutJ3.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateDebutJ3.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ3();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }
    private void selectHeureDebutJ4() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ4 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ4.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ4", 0);
            yearDebut = pref.getInt("key_Debut_YearJ4", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ4", 0);
            HourDebut = pref.getInt("key_Debut_HourJ4", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ4", 0);
            calendarDebutJ4.set(Calendar.MONTH, monthDebut);
            calendarDebutJ4.set(Calendar.YEAR, yearDebut);
            calendarDebutJ4.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ4.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ4.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ4.set(Calendar.SECOND, 0);
            calendarDebutJ4.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarDebutJ4.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarDebutJ4.set(Calendar.MINUTE, minutes);
                calendarDebutJ4.set(Calendar.MONTH, monthDebut);
                calendarDebutJ4.set(Calendar.YEAR, yearDebut);
                calendarDebutJ4.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarDebutJ4.set(Calendar.SECOND, 0);
                calendarDebutJ4.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarDebutJ4.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Debut_HourJ4",hourOfDay );
                editor.putInt("key_Debut_MinuteJ4",minutes );
                editor.putInt("key_Debut_YearJ4",yearDebut );
                editor.putInt("key_Debut_MonthJ4",monthDebut );
                editor.putInt("key_Debut_DayJ4",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureDebutJ4.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateDebutJ4.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ4();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }
    private void selectHeureDebutJ5() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ5 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ5.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ5", 0);
            yearDebut = pref.getInt("key_Debut_YearJ5", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ5", 0);
            HourDebut = pref.getInt("key_Debut_HourJ5", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ5", 0);
            calendarDebutJ5.set(Calendar.MONTH, monthDebut);
            calendarDebutJ5.set(Calendar.YEAR, yearDebut);
            calendarDebutJ5.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ5.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ5.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ5.set(Calendar.SECOND, 0);
            calendarDebutJ5.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarDebutJ5.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarDebutJ5.set(Calendar.MINUTE, minutes);
                calendarDebutJ5.set(Calendar.MONTH, monthDebut);
                calendarDebutJ5.set(Calendar.YEAR, yearDebut);
                calendarDebutJ5.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarDebutJ5.set(Calendar.SECOND, 0);
                calendarDebutJ5.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarDebutJ5.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Debut_HourJ5",hourOfDay );
                editor.putInt("key_Debut_MinuteJ5",minutes );
                editor.putInt("key_Debut_YearJ5",yearDebut );
                editor.putInt("key_Debut_MonthJ5",monthDebut );
                editor.putInt("key_Debut_DayJ5",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureDebutJ5.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateDebutJ5.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ5();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }
    private void selectHeureDebutJ6() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarDebutJ6 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateDebutJ6.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Debut_MonthJ6", 0);
            yearDebut = pref.getInt("key_Debut_YearJ6", 0);
            dayOfMonthDebut = pref.getInt("key_Debut_DayJ6", 0);
            HourDebut = pref.getInt("key_Debut_HourJ6", 0);
            MinuteDebut = pref.getInt("key_Debut_MinuteJ6", 0);
            calendarDebutJ6.set(Calendar.MONTH, monthDebut);
            calendarDebutJ6.set(Calendar.YEAR, yearDebut);
            calendarDebutJ6.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarDebutJ6.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarDebutJ6.set(Calendar.MINUTE, MinuteDebut);
            calendarDebutJ6.set(Calendar.SECOND, 0);
            calendarDebutJ6.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarDebutJ6.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarDebutJ6.set(Calendar.MINUTE, minutes);
                calendarDebutJ6.set(Calendar.MONTH, monthDebut);
                calendarDebutJ6.set(Calendar.YEAR, yearDebut);
                calendarDebutJ6.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarDebutJ6.set(Calendar.SECOND, 0);
                calendarDebutJ6.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarDebutJ6.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Debut_HourJ6",hourOfDay );
                editor.putInt("key_Debut_MinuteJ6",minutes );
                editor.putInt("key_Debut_YearJ6",yearDebut );
                editor.putInt("key_Debut_MonthJ6",monthDebut );
                editor.putInt("key_Debut_DayJ6",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureDebutJ6.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateDebutJ6.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ6();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }

    private void selectHeureFinJ1() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ1 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ1.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ1", 0);
            yearDebut = pref.getInt("key_Fin_YearJ1", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ1", 0);
            HourDebut = pref.getInt("key_Fin_HourJ1", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ1", 0);
            calendarFinJ1.set(Calendar.MONTH, monthDebut);
            calendarFinJ1.set(Calendar.YEAR, yearDebut);
            calendarFinJ1.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ1.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ1.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ1.set(Calendar.SECOND, 0);
            calendarFinJ1.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarFinJ1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarFinJ1.set(Calendar.MINUTE, minutes);
                calendarFinJ1.set(Calendar.MONTH, monthDebut);
                calendarFinJ1.set(Calendar.YEAR, yearDebut);
                calendarFinJ1.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarFinJ1.set(Calendar.SECOND, 0);
                calendarFinJ1.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarFinJ1.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Fin_HourJ1",hourOfDay );
                editor.putInt("key_Fin_MinuteJ1",minutes );
                editor.putInt("key_Fin_YearJ1",yearDebut );
                editor.putInt("key_Fin_MonthJ1",monthDebut );
                editor.putInt("key_Fin_DayJ1",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureFinJ1.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateFinJ1.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ1();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }
    private void selectHeureFinJ2() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ2 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ2.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ2", 0);
            yearDebut = pref.getInt("key_Fin_YearJ2", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ2", 0);
            HourDebut = pref.getInt("key_Fin_HourJ2", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ2", 0);
            calendarFinJ2.set(Calendar.MONTH, monthDebut);
            calendarFinJ2.set(Calendar.YEAR, yearDebut);
            calendarFinJ2.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ2.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ2.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ2.set(Calendar.SECOND, 0);
            calendarFinJ2.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarFinJ2.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarFinJ2.set(Calendar.MINUTE, minutes);
                calendarFinJ2.set(Calendar.MONTH, monthDebut);
                calendarFinJ2.set(Calendar.YEAR, yearDebut);
                calendarFinJ2.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarFinJ2.set(Calendar.SECOND, 0);
                calendarFinJ2.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarFinJ2.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Fin_HourJ2",hourOfDay );
                editor.putInt("key_Fin_MinuteJ2",minutes );
                editor.putInt("key_Fin_YearJ2",yearDebut );
                editor.putInt("key_Fin_MonthJ2",monthDebut );
                editor.putInt("key_Fin_DayJ2",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureFinJ2.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateFinJ2.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ2();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }
    private void selectHeureFinJ3() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ3 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ3.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ3", 0);
            yearDebut = pref.getInt("key_Fin_YearJ3", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ3", 0);
            HourDebut = pref.getInt("key_Fin_HourJ3", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ3", 0);
            calendarFinJ3.set(Calendar.MONTH, monthDebut);
            calendarFinJ3.set(Calendar.YEAR, yearDebut);
            calendarFinJ3.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ3.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ3.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ3.set(Calendar.SECOND, 0);
            calendarFinJ3.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarFinJ3.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarFinJ3.set(Calendar.MINUTE, minutes);
                calendarFinJ3.set(Calendar.MONTH, monthDebut);
                calendarFinJ3.set(Calendar.YEAR, yearDebut);
                calendarFinJ3.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarFinJ3.set(Calendar.SECOND, 0);
                calendarFinJ3.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarFinJ3.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Fin_HourJ3",hourOfDay );
                editor.putInt("key_Fin_MinuteJ3",minutes );
                editor.putInt("key_Fin_YearJ3",yearDebut );
                editor.putInt("key_Fin_MonthJ3",monthDebut );
                editor.putInt("key_Fin_DayJ3",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureFinJ3.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateFinJ3.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ3();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }
    private void selectHeureFinJ4() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ4 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ4.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ4", 0);
            yearDebut = pref.getInt("key_Fin_YearJ4", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ4", 0);
            HourDebut = pref.getInt("key_Fin_HourJ4", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ4", 0);
            calendarFinJ4.set(Calendar.MONTH, monthDebut);
            calendarFinJ4.set(Calendar.YEAR, yearDebut);
            calendarFinJ4.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ4.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ4.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ4.set(Calendar.SECOND, 0);
            calendarFinJ4.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarFinJ4.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarFinJ4.set(Calendar.MINUTE, minutes);
                calendarFinJ4.set(Calendar.MONTH, monthDebut);
                calendarFinJ4.set(Calendar.YEAR, yearDebut);
                calendarFinJ4.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarFinJ4.set(Calendar.SECOND, 0);
                calendarFinJ4.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarFinJ4.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Fin_HourJ4",hourOfDay );
                editor.putInt("key_Fin_MinuteJ4",minutes );
                editor.putInt("key_Fin_YearJ4",yearDebut );
                editor.putInt("key_Fin_MonthJ4",monthDebut );
                editor.putInt("key_Fin_DayJ4",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureFinJ4.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateFinJ4.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ4();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }
    private void selectHeureFinJ5() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ5 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ5.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ5", 0);
            yearDebut = pref.getInt("key_Fin_YearJ5", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ5", 0);
            HourDebut = pref.getInt("key_Fin_HourJ5", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ5", 0);
            calendarFinJ5.set(Calendar.MONTH, monthDebut);
            calendarFinJ5.set(Calendar.YEAR, yearDebut);
            calendarFinJ5.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ5.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ5.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ5.set(Calendar.SECOND, 0);
            calendarFinJ5.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarFinJ5.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarFinJ5.set(Calendar.MINUTE, minutes);
                calendarFinJ5.set(Calendar.MONTH, monthDebut);
                calendarFinJ5.set(Calendar.YEAR, yearDebut);
                calendarFinJ5.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarFinJ5.set(Calendar.SECOND, 0);
                calendarFinJ5.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarFinJ5.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Fin_HourJ5",hourOfDay );
                editor.putInt("key_Fin_MinuteJ5",minutes );
                editor.putInt("key_Fin_YearJ5",yearDebut );
                editor.putInt("key_Fin_MonthJ5",monthDebut );
                editor.putInt("key_Fin_DayJ5",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureFinJ5.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateFinJ5.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ5();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }
    private void selectHeureFinJ6() {
        int yearDebut,monthDebut,dayOfMonthDebut,HourDebut,MinuteDebut;
        Calendar calendarFinJ6 = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        if(tvDateFinJ6.getText().toString().equals("")){
            yearDebut = now.get(Calendar.YEAR);
            monthDebut = now.get(Calendar.MONTH);
            dayOfMonthDebut = now.get(Calendar.DAY_OF_MONTH);
            HourDebut = now.get(Calendar.HOUR_OF_DAY);
            MinuteDebut = now.get(Calendar.MINUTE);
        }else{
            monthDebut = pref.getInt("key_Fin_MonthJ6", 0);
            yearDebut = pref.getInt("key_Fin_YearJ6", 0);
            dayOfMonthDebut = pref.getInt("key_Fin_DayJ6", 0);
            HourDebut = pref.getInt("key_Fin_HourJ6", 0);
            MinuteDebut = pref.getInt("key_Fin_MinuteJ6", 0);
            calendarFinJ6.set(Calendar.MONTH, monthDebut);
            calendarFinJ6.set(Calendar.YEAR, yearDebut);
            calendarFinJ6.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
            calendarFinJ6.set(Calendar.HOUR_OF_DAY, HourDebut);
            calendarFinJ6.set(Calendar.MINUTE, MinuteDebut);
            calendarFinJ6.set(Calendar.SECOND, 0);
            calendarFinJ6.set(Calendar.MILLISECOND, 0);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                calendarFinJ6.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarFinJ6.set(Calendar.MINUTE, minutes);
                calendarFinJ6.set(Calendar.MONTH, monthDebut);
                calendarFinJ6.set(Calendar.YEAR, yearDebut);
                calendarFinJ6.set(Calendar.DAY_OF_MONTH, dayOfMonthDebut);
                calendarFinJ6.set(Calendar.SECOND, 0);
                calendarFinJ6.set(Calendar.MILLISECOND, 0);
                int NomduJourdebut = calendarFinJ6.get(Calendar.DAY_OF_WEEK);
                editor.putInt("key_Fin_HourJ6",hourOfDay );
                editor.putInt("key_Fin_MinuteJ6",minutes );
                editor.putInt("key_Fin_YearJ6",yearDebut );
                editor.putInt("key_Fin_MonthJ6",monthDebut );
                editor.putInt("key_Fin_DayJ6",dayOfMonthDebut );
                editor.apply();// commit changes
                tvHeureFinJ6.setText(String.format("%02d:%02d", hourOfDay, minutes));
                tvDateFinJ6.setText(getDayName(NomduJourdebut-1) + "\n" + String.format("%02d/%02d/%02d",dayOfMonthDebut,(monthDebut +1),yearDebut));
                calculTempsRepos();
                calculAmplitudeJ6();
            }
        }, HourDebut, MinuteDebut, true);
        timePickerDialog.show();
    }

    private void DeleteDateDebutJ1() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateDebutJ1.setText("");
        tvHeureDebutJ1.setText("");
        editor.remove("key_Debut_HourJ1");
        editor.remove("key_Debut_MinuteJ1");
        editor.remove("key_Debut_YearJ1");
        editor.remove("key_Debut_MonthJ1");
        editor.remove("key_Debut_DayJ1");
        editor.apply();
        tvTempsReposJ1.setText("00:00");
        tvTempsAmplitudeJ1.setText("00:00");
    }
    private void DeleteDateDebutJ2() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateDebutJ2.setText("");
        tvHeureDebutJ2.setText("");
        editor.remove("key_Debut_HourJ2");
        editor.remove("key_Debut_MinuteJ2");
        editor.remove("key_Debut_YearJ2");
        editor.remove("key_Debut_MonthJ2");
        editor.remove("key_Debut_DayJ2");
        editor.apply();
        tvTempsReposJ2.setText("00:00");
        tvTempsAmplitudeJ2.setText("00:00");
    }
    private void DeleteDateDebutJ3() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateDebutJ3.setText("");
        tvHeureDebutJ3.setText("");
        editor.remove("key_Debut_HourJ3");
        editor.remove("key_Debut_MinuteJ3");
        editor.remove("key_Debut_YearJ3");
        editor.remove("key_Debut_MonthJ3");
        editor.remove("key_Debut_DayJ3");
        editor.apply();
        tvTempsReposJ3.setText("00:00");
        tvTempsAmplitudeJ3.setText("00:00");
    }
    private void DeleteDateDebutJ4() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateDebutJ4.setText("");
        tvHeureDebutJ4.setText("");
        editor.remove("key_Debut_HourJ4");
        editor.remove("key_Debut_MinuteJ4");
        editor.remove("key_Debut_YearJ4");
        editor.remove("key_Debut_MonthJ4");
        editor.remove("key_Debut_DayJ4");
        editor.apply();
        tvTempsReposJ4.setText("00:00");
        tvTempsAmplitudeJ4.setText("00:00");
    }
    private void DeleteDateDebutJ5() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateDebutJ5.setText("");
        tvHeureDebutJ5.setText("");
        editor.remove("key_Debut_HourJ5");
        editor.remove("key_Debut_MinuteJ5");
        editor.remove("key_Debut_YearJ5");
        editor.remove("key_Debut_MonthJ5");
        editor.remove("key_Debut_DayJ5");
        editor.apply();
        tvTempsReposJ5.setText("00:00");
        tvTempsAmplitudeJ5.setText("00:00");
    }
    private void DeleteDateDebutJ6() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateDebutJ6.setText("");
        tvHeureDebutJ6.setText("");
        editor.remove("key_Debut_HourJ6");
        editor.remove("key_Debut_MinuteJ6");
        editor.remove("key_Debut_YearJ6");
        editor.remove("key_Debut_MonthJ6");
        editor.remove("key_Debut_DayJ6");
        editor.apply();
        tvTempsAmplitudeJ6.setText("00:00");
    }

    private void DeleteDateFinJ1() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateFinJ1.setText("");
        tvHeureFinJ1.setText("");
        editor.remove("key_Fin_HourJ1");
        editor.remove("key_Fin_MinuteJ1");
        editor.remove("key_Fin_YearJ1");
        editor.remove("key_Fin_MonthJ1");
        editor.remove("key_Fin_DayJ1");
        editor.apply();
        tvTempsReposJ1.setText("00:00");
        tvTempsAmplitudeJ1.setText("00:00");
    }
    private void DeleteDateFinJ2() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateFinJ2.setText("");
        tvHeureFinJ2.setText("");
        editor.remove("key_Fin_HourJ2");
        editor.remove("key_Fin_MinuteJ2");
        editor.remove("key_Fin_YearJ2");
        editor.remove("key_Fin_MonthJ2");
        editor.remove("key_Fin_DayJ2");
        editor.apply();
        tvTempsReposJ2.setText("00:00");
        tvTempsAmplitudeJ2.setText("00:00");
    }
    private void DeleteDateFinJ3() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateFinJ3.setText("");
        tvHeureFinJ3.setText("");
        editor.remove("key_Fin_HourJ3");
        editor.remove("key_Fin_MinuteJ3");
        editor.remove("key_Fin_YearJ3");
        editor.remove("key_Fin_MonthJ3");
        editor.remove("key_Fin_DayJ3");
        editor.apply();
        tvTempsReposJ3.setText("00:00");
        tvTempsAmplitudeJ3.setText("00:00");
    }
    private void DeleteDateFinJ4() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateFinJ4.setText("");
        tvHeureFinJ4.setText("");
        editor.remove("key_Fin_HourJ4");
        editor.remove("key_Fin_MinuteJ4");
        editor.remove("key_Fin_YearJ4");
        editor.remove("key_Fin_MonthJ4");
        editor.remove("key_Fin_DayJ4");
        editor.apply();
        tvTempsReposJ4.setText("00:00");
        tvTempsAmplitudeJ4.setText("00:00");
    }
    private void DeleteDateFinJ5() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateFinJ5.setText("");
        tvHeureFinJ5.setText("");
        editor.remove("key_Fin_HourJ5");
        editor.remove("key_Fin_MinuteJ5");
        editor.remove("key_Fin_YearJ5");
        editor.remove("key_Fin_MonthJ5");
        editor.remove("key_Fin_DayJ5");
        editor.apply();
        tvTempsReposJ5.setText("00:00");
        tvTempsAmplitudeJ5.setText("00:00");
    }
    private void DeleteDateFinJ6() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        tvDateFinJ6.setText("");
        tvHeureFinJ6.setText("");
        editor.remove("key_Fin_HourJ6");
        editor.remove("key_Fin_MinuteJ6");
        editor.remove("key_Fin_YearJ6");
        editor.remove("key_Fin_MonthJ6");
        editor.remove("key_Fin_DayJ6");
        editor.apply();
        tvTempsAmplitudeJ6.setText("00:00");
    }

    private void TempsConduiteJ1() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeConduite_J1", 0);
        int hour = (minutes/60) ;
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de conduite");
        builder.setMessage("Saisir votre temps de conduite de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureConduiteJ1.setText("00:00");
                editor.putInt("key_tempsDeConduite_J1",totalminute);
                editor.apply();
                calculTotalparJour(1);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureConduiteJ1.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeConduite_J1",totalminute);
                editor.apply();
                calculTotalparJour(1);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsConduiteJ2() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeConduite_J2", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//
        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de conduite");
        builder.setMessage("Saisir votre temps de conduite de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureConduiteJ2.setText("00:00");
                editor.putInt("key_tempsDeConduite_J2",totalminute);
                editor.apply();
                calculTotalparJour(2);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureConduiteJ2.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeConduite_J2",totalminute);
                editor.apply();
                calculTotalparJour(2);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsConduiteJ3() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeConduite_J3", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de conduite");
        builder.setMessage("Saisir votre temps de conduite de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureConduiteJ3.setText("00:00");
                editor.putInt("key_tempsDeConduite_J3",totalminute);
                editor.apply();
                calculTotalparJour(3);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureConduiteJ3.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeConduite_J3",totalminute);
                editor.apply();
                calculTotalparJour(3);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsConduiteJ4() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeConduite_J4", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de conduite");
        builder.setMessage("Saisir votre temps de conduite de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureConduiteJ4.setText("00:00");
                editor.putInt("key_tempsDeConduite_J4",totalminute);
                editor.apply();
                calculTotalparJour(4);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureConduiteJ4.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeConduite_J4",totalminute);
                editor.apply();
                calculTotalparJour(4);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsConduiteJ5() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeConduite_J5", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de conduite");
        builder.setMessage("Saisir votre temps de conduite de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureConduiteJ5.setText("00:00");
                editor.putInt("key_tempsDeConduite_J5",totalminute);
                editor.apply();
                calculTotalparJour(5);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureConduiteJ5.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeConduite_J5",totalminute);
                editor.apply();
                calculTotalparJour(5);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsConduiteJ6() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeConduite_J6", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de conduite");
        builder.setMessage("Saisir votre temps de conduite de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureConduiteJ6.setText("00:00");
                editor.putInt("key_tempsDeConduite_J6",totalminute);
                editor.apply();
                calculTotalparJour(6);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureConduiteJ6.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeConduite_J6",totalminute);
                editor.apply();
                calculTotalparJour(6);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }

    private void TempsTravailJ1() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeTravail_J1", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de travail");
        builder.setMessage("Saisir votre temps de travail de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureTravailJ1.setText("00:00");
                editor.putInt("key_tempsDeTravail_J1",totalminute);
                editor.apply();
                calculTotalparJour(1);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureTravailJ1.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeTravail_J1",totalminute);
                editor.apply();
                calculTotalparJour(1);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsTravailJ2() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeTravail_J2", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de travail");
        builder.setMessage("Saisir votre temps de travail de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureTravailJ2.setText("00:00");
                editor.putInt("key_tempsDeTravail_J2",totalminute);
                editor.apply();
                calculTotalparJour(2);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureTravailJ2.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeTravail_J2",totalminute);
                editor.apply();
                calculTotalparJour(2);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsTravailJ3() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeTravail_J3", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de travail");
        builder.setMessage("Saisir votre temps de travail de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureTravailJ3.setText("00:00");
                editor.putInt("key_tempsDeTravail_J3",totalminute);
                editor.apply();
                calculTotalparJour(3);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureTravailJ3.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeTravail_J3",totalminute);
                editor.apply();
                calculTotalparJour(3);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsTravailJ4() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeTravail_J4", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de travail");
        builder.setMessage("Saisir votre temps de travail de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureTravailJ4.setText("00:00");
                editor.putInt("key_tempsDeTravail_J4",totalminute);
                editor.apply();
                calculTotalparJour(4);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureTravailJ4.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeTravail_J4",totalminute);
                editor.apply();
                calculTotalparJour(4);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsTravailJ5() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeTravail_J5", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de travail");
        builder.setMessage("Saisir votre temps de travail de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureTravailJ5.setText("00:00");
                editor.putInt("key_tempsDeTravail_J5",totalminute);
                editor.apply();
                calculTotalparJour(5);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureTravailJ5.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeTravail_J5",totalminute);
                editor.apply();
                calculTotalparJour(5);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsTravailJ6() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeTravail_J6", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de travail");
        builder.setMessage("Saisir votre temps de travail de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureTravailJ6.setText("00:00");
                editor.putInt("key_tempsDeTravail_J6",totalminute);
                editor.apply();
                calculTotalparJour(6);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureTravailJ6.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeTravail_J6",totalminute);
                editor.apply();
                calculTotalparJour(6);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }

    private void TempsDispoJ1() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeDispo_J1", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de disponibilité");
        builder.setMessage("Saisir votre temps de diponibilité de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureDispoJ1.setText("00:00");
                editor.putInt("key_tempsDeDispo_J1",totalminute);
                editor.apply();
                calculTotalparJour(1);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureDispoJ1.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeDispo_J1",totalminute);
                editor.apply();
                calculTotalparJour(1);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsDispoJ2() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeDispo_J2", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de disponibilité");
        builder.setMessage("Saisir votre temps de diponibilité de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureDispoJ2.setText("00:00");
                editor.putInt("key_tempsDeDispo_J2",totalminute);
                editor.apply();
                calculTotalparJour(2);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureDispoJ2.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeDispo_J2",totalminute);
                editor.apply();
                calculTotalparJour(2);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsDispoJ3() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeDispo_J3", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de disponibilité");
        builder.setMessage("Saisir votre temps de diponibilité de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureDispoJ3.setText("00:00");
                editor.putInt("key_tempsDeDispo_J3",totalminute);
                editor.apply();
                calculTotalparJour(3);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureDispoJ3.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeDispo_J3",totalminute);
                editor.apply();
                calculTotalparJour(3);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsDispoJ4() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeDispo_J4", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de disponibilité");
        builder.setMessage("Saisir votre temps de diponibilité de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureDispoJ4.setText("00:00");
                editor.putInt("key_tempsDeDispo_J4",totalminute);
                editor.apply();
                calculTotalparJour(4);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureDispoJ4.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeDispo_J4",totalminute);
                editor.apply();
                calculTotalparJour(4);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsDispoJ5() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeDispo_J5", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de disponibilité");
        builder.setMessage("Saisir votre temps de diponibilité de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureDispoJ5.setText("00:00");
                editor.putInt("key_tempsDeDispo_J5",totalminute);
                editor.apply();
                calculTotalparJour(5);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureDispoJ5.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeDispo_J5",totalminute);
                editor.apply();
                calculTotalparJour(5);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }
    private void TempsDispoJ6() {
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        int minutes = pref.getInt("key_tempsDeDispo_J6", 0);
        int hour = (minutes/60);
        int minutesAAfficher = minutes - (hour*60);

        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout LL1 = new LinearLayout(context);
        LL1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LL2 = new LinearLayout(context);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
//

        final NumberPicker aNumberPickerHour2 = new NumberPicker(context);
        aNumberPickerHour2.setMaxValue(10);
        aNumberPickerHour2.setMinValue(0);
        aNumberPickerHour2.setValue(hour);
//
        final NumberPicker aNumberPickerMinute2 = new NumberPicker(context);
        aNumberPickerMinute2.setMaxValue(59);
        aNumberPickerMinute2.setMinValue(0);
        aNumberPickerMinute2.setValue(minutesAAfficher);
        //aNumberPickerA.setDisplayedValues(new String[] { "Tea Cup", "Glass","Plate","Small Plate","Cutlets","Medium","Piece","Katori","Balls","Serving","egg"});
//
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;

        TextView tv1 = new TextView(context);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setText("Heures");
        TextView tv2 = new TextView(context);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(0,50,0,0);
        tv2.setText("Minutes");
//
        LinearLayout.LayoutParams numPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams1.weight = 1;
        numPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams1.weight = 1;
        qPicerParams1.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams numPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams2.weight = 1;
        numPicerParams2.setMargins(50,0,50,0);
//
        LinearLayout.LayoutParams qPicerParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams2.weight = 1;
        qPicerParams2.setMargins(50,0,50,0);

        LL1.setLayoutParams(params1);
        LL1.addView(tv1,qPicerParams1);
        LL1.addView(tv2,numPicerParams1);
        LL2.setLayoutParams(params2);
        LL2.addView(aNumberPickerHour2,qPicerParams2);
        LL2.addView(aNumberPickerMinute2,numPicerParams2);
        LL.addView(LL1);
        LL.addView(LL2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LL);
        builder.setTitle("Temps de disponibilité");
        builder.setMessage("Saisir votre temps de diponibilité de la journée.");
        builder.setNeutralButton("Effacer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = 0;
                tvHeureDispoJ6.setText("00:00");
                editor.putInt("key_tempsDeDispo_J6",totalminute);
                editor.apply();
                calculTotalparJour(6);
                calculTotalparSemaine();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int totalminute = aNumberPickerMinute2.getValue() + aNumberPickerHour2.getValue() * 60;
                tvHeureDispoJ6.setText(twoDigitString(aNumberPickerHour2.getValue()) +
                        ":" +
                        twoDigitString(aNumberPickerMinute2.getValue()));
                editor.putInt("key_tempsDeDispo_J6",totalminute);
                editor.apply();
                calculTotalparJour(6);
                calculTotalparSemaine();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        builder.show();
    }


    @Override
    public void onResume() {
        NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_semaines).setChecked(true);
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        testSiValeursEnregistrees();
        //OpenExpandListener();
        super.onResume();
    }

    private void OpenExpandListener() {
        reposHbefore.removeOnExpandedListener();
        amplitude1.removeOnExpandedListener();
        amplitude2.removeOnExpandedListener();
        amplitude3.removeOnExpandedListener();
        amplitude4.removeOnExpandedListener();
        amplitude5.removeOnExpandedListener();
        amplitude6.removeOnExpandedListener();
        reposHend.removeOnExpandedListener();
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        boolean reposHbeforeOpen = pref.getBoolean("key_reposHbefore_open", false);
        if(reposHbeforeOpen){
            reposHbefore.expand();
        }
        boolean amplitude1Open = pref.getBoolean("key_amplitude1_open", false);
        if(amplitude1Open){
            amplitude1.expand();
        }
        boolean amplitude2Open = pref.getBoolean("key_amplitude2_open", false);
        if(amplitude2Open){
            amplitude2.expand();
        }
        boolean amplitude3Open = pref.getBoolean("key_amplitude3_open", false);
        if(amplitude3Open){
            amplitude3.expand();
        }
        boolean amplitude4Open = pref.getBoolean("key_amplitude4_open", false);
        if(amplitude4Open){
            amplitude4.expand();
        }
        boolean amplitude5Open = pref.getBoolean("key_amplitude5_open", false);
        if(amplitude5Open){
            amplitude5.expand();
        }
        boolean amplitude6Open = pref.getBoolean("key_amplitude6_open", false);
        if(amplitude6Open){
            amplitude6.expand();
        }
        boolean reposHendOpen = pref.getBoolean("key_reposHend_open", false);
        if(reposHendOpen){
            reposHend.expand();
        }
        reposHbefore.setOnExpandedListener((v12, isExpanded) -> {
            //Toast.makeText(context, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            if(reposHbefore.isExpanded()){
                editor.putBoolean("key_reposHbefore_open", true);
                editor.apply();
                if(amplitude1.isExpanded()){
                    amplitude1.collapse();
                }
                if(amplitude2.isExpanded()){
                    amplitude2.collapse();
                }
                if(amplitude3.isExpanded()){
                    amplitude3.collapse();
                }
                if(amplitude4.isExpanded()){
                    amplitude4.collapse();
                }
                if(amplitude5.isExpanded()){
                    amplitude5.collapse();
                }
                if(amplitude6.isExpanded()){
                    amplitude6.collapse();
                }
                if(reposHend.isExpanded()){
                    reposHend.collapse();
                }
            } else{
                editor.putBoolean("key_reposHbefore_open", false);
                editor.apply();
            }
        });
        amplitude1.setOnExpandedListener((v13, isExpanded) -> {
            //Toast.makeText(context, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            if(amplitude1.isExpanded()){
                editor.putBoolean("key_amplitude1_open", true);
                editor.apply();
                if(reposHbefore.isExpanded()){
                    reposHbefore.collapse();
                }
                if(amplitude2.isExpanded()){
                    amplitude2.collapse();
                }
                if(amplitude3.isExpanded()){
                    amplitude3.collapse();
                }
                if(amplitude4.isExpanded()){
                    amplitude4.collapse();
                }
                if(amplitude5.isExpanded()){
                    amplitude5.collapse();
                }
                if(amplitude6.isExpanded()){
                    amplitude6.collapse();
                }
                if(reposHend.isExpanded()){
                    reposHend.collapse();
                }
            } else{
                editor.putBoolean("key_amplitude1_open", false);
                editor.apply();
            }
        });
        amplitude2.setOnExpandedListener((v14, isExpanded) -> {
            //Toast.makeText(context, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            if(amplitude2.isExpanded()){
                editor.putBoolean("key_amplitude2_open", true);
                editor.apply();
                if(reposHbefore.isExpanded()){
                    reposHbefore.collapse();
                }
                if(amplitude1.isExpanded()){
                    amplitude1.collapse();
                }
                if(amplitude3.isExpanded()){
                    amplitude3.collapse();
                }
                if(amplitude4.isExpanded()){
                    amplitude4.collapse();
                }
                if(amplitude5.isExpanded()){
                    amplitude5.collapse();
                }
                if(amplitude6.isExpanded()){
                    amplitude6.collapse();
                }
                if(reposHend.isExpanded()){
                    reposHend.collapse();
                }
            } else{
                editor.putBoolean("key_amplitude2_open", false);
                editor.apply();
            }
        });
        amplitude3.setOnExpandedListener((v15, isExpanded) -> {
            //Toast.makeText(context, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            if(amplitude3.isExpanded()){
                editor.putBoolean("key_amplitude3_open", true);
                editor.apply();
                if(reposHbefore.isExpanded()){
                    reposHbefore.collapse();
                }
                if(amplitude1.isExpanded()){
                    amplitude1.collapse();
                }
                if(amplitude2.isExpanded()){
                    amplitude2.collapse();
                }
                if(amplitude4.isExpanded()){
                    amplitude4.collapse();
                }
                if(amplitude5.isExpanded()){
                    amplitude5.collapse();
                }
                if(amplitude6.isExpanded()){
                    amplitude6.collapse();
                }
                if(reposHend.isExpanded()){
                    reposHend.collapse();
                }
            } else{
                editor.putBoolean("key_amplitude3_open", false);
                editor.apply();
            }
        });
        amplitude4.setOnExpandedListener((v16, isExpanded) -> {
            //Toast.makeText(context, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            if(amplitude4.isExpanded()){
                editor.putBoolean("key_amplitude4_open", true);
                editor.apply();
                if(reposHbefore.isExpanded()){
                    reposHbefore.collapse();
                }
                if(amplitude1.isExpanded()){
                    amplitude1.collapse();
                }
                if(amplitude2.isExpanded()){
                    amplitude2.collapse();
                }
                if(amplitude3.isExpanded()){
                    amplitude3.collapse();
                }
                if(amplitude5.isExpanded()){
                    amplitude5.collapse();
                }
                if(amplitude6.isExpanded()){
                    amplitude6.collapse();
                }
                if(reposHend.isExpanded()){
                    reposHend.collapse();
                }
            } else{
                editor.putBoolean("key_amplitude4_open", false);
                editor.apply();
            }
        });
        amplitude5.setOnExpandedListener((v18, isExpanded) -> {
            //Toast.makeText(context, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            if(amplitude5.isExpanded()){
                editor.putBoolean("key_amplitude5_open", true);
                editor.apply();
                if(reposHbefore.isExpanded()){
                    reposHbefore.collapse();
                }
                if(amplitude1.isExpanded()){
                    amplitude1.collapse();
                }
                if(amplitude2.isExpanded()){
                    amplitude2.collapse();
                }
                if(amplitude3.isExpanded()){
                    amplitude3.collapse();
                }
                if(amplitude4.isExpanded()){
                    amplitude4.collapse();
                }
                if(amplitude6.isExpanded()){
                    amplitude6.collapse();
                }
                if(reposHend.isExpanded()){
                    reposHend.collapse();
                }
            } else{
                editor.putBoolean("key_amplitude5_open", false);
                editor.apply();
            }
        });
        amplitude6.setOnExpandedListener((v19, isExpanded) -> {
            //Toast.makeText(context, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            if(amplitude6.isExpanded()){
                editor.putBoolean("key_amplitude6_open", true);
                editor.apply();
                if(reposHbefore.isExpanded()){
                    reposHbefore.collapse();
                }
                if(amplitude1.isExpanded()){
                    amplitude1.collapse();
                }
                if(amplitude2.isExpanded()){
                    amplitude2.collapse();
                }
                if(amplitude3.isExpanded()){
                    amplitude3.collapse();
                }
                if(amplitude4.isExpanded()){
                    amplitude4.collapse();
                }
                if(amplitude5.isExpanded()){
                    amplitude5.collapse();
                }
                if(reposHend.isExpanded()){
                    reposHend.collapse();
                }
            } else{
                editor.putBoolean("key_amplitude6_open", false);
                editor.apply();
            }
        });
        reposHend.setOnExpandedListener((v110, isExpanded) -> {
            //Toast.makeText(context, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            if(reposHend.isExpanded()){
                editor.putBoolean("key_reposHend_open", true);
                editor.apply();
                if(reposHbefore.isExpanded()){
                    reposHbefore.collapse();
                }
                if(amplitude1.isExpanded()){
                    amplitude1.collapse();
                }
                if(amplitude2.isExpanded()){
                    amplitude2.collapse();
                }
                if(amplitude3.isExpanded()){
                    amplitude3.collapse();
                }
                if(amplitude4.isExpanded()){
                    amplitude4.collapse();
                }
                if(amplitude5.isExpanded()){
                    amplitude5.collapse();
                }
                if(amplitude6.isExpanded()){
                    amplitude6.collapse();
                }
            } else{
                editor.putBoolean("key_reposHend_open", false);
                editor.apply();
            }
        });
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

    private static String transformMinutes(int totalMinutes) {
        int totalhour = (totalMinutes/60) ;
        int minutesAAfficher = totalMinutes - (totalhour*60);
        return twoDigitString(totalhour) + ":" + twoDigitString(minutesAAfficher);
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
}
