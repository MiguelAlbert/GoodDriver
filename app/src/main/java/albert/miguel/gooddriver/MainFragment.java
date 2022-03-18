package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MainFragment extends Fragment {

        Button button1, button2, button3, button4;
        Button btnLundi10h, btnMardi10h, btnMercredi10h, btnJeudi10h,btnVendredi10h, btnSamedi10h, btnDimanche10h;
        ImageButton imageButtonDeleteConduite, imageButtonDeleteReposJournaliers;
        Button btnJ1_9h, btnJ2_9h, btnJ3_9h, btnJ4_9h,btnJ5_9h, btnJ6_9h;
        Activity thisActivity;
        int nbConduite10h = 0;
        int nbRepos9h = 0;
        Context context;

        SharedPreferences prefConduite;
        SharedPreferences prefRepos;
        SharedPreferences.Editor editorprefConduite;
        SharedPreferences.Editor editorprefRepos;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            context = container.getContext();
            View v = inflater.inflate(R.layout.accueil_fragment,container,false);
            thisActivity = getActivity();
            prefConduite = context.getSharedPreferences("PrefsConduitesRepos", MODE_PRIVATE);
            prefRepos = context.getSharedPreferences("PrefsRepos", MODE_PRIVATE);
            editorprefConduite = prefConduite.edit();
            editorprefRepos  = prefRepos.edit();
            btnLundi10h = (Button) v.findViewById(R.id.btnLundi10h);
            btnMardi10h = (Button) v.findViewById(R.id.btnMardi10h);
            btnMercredi10h = (Button) v.findViewById(R.id.btnMercredi10h);
            btnJeudi10h = (Button) v.findViewById(R.id.btnJeudi10h);
            btnVendredi10h = (Button) v.findViewById(R.id.btnVendredi10h);
            btnSamedi10h = (Button) v.findViewById(R.id.btnSamedi10h);
            btnDimanche10h = (Button) v.findViewById(R.id.btnDimanche10h);
            imageButtonDeleteConduite = (ImageButton) v.findViewById(R.id.imageButtonDeleteConduite);

            btnJ1_9h = (Button) v.findViewById(R.id.btnJ1_9h);
            btnJ2_9h = (Button) v.findViewById(R.id.btnJ2_9h);
            btnJ3_9h = (Button) v.findViewById(R.id.btnJ3_9h);
            btnJ4_9h = (Button) v.findViewById(R.id.btnJ4_9h);
            btnJ5_9h = (Button) v.findViewById(R.id.btnJ5_9h);
            btnJ6_9h = (Button) v.findViewById(R.id.btnJ6_9h);
            imageButtonDeleteReposJournaliers = (ImageButton) v.findViewById(R.id.imageButtonDeleteReposJournaliers);
            GetReposConduiteValue();

            button1 = (Button) v.findViewById(R.id.button1);
            button1.setOnClickListener(new View.OnClickListener()
            {
                    @Override
                    public void onClick(View v)
                    {
                            AmplitudeFragment AmplitudeFragment = new AmplitudeFragment();
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.content_frame, AmplitudeFragment, "Amplitude_fragment"); // give your fragment container id in first parameter
                            transaction.disallowAddToBackStack();
                            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
                            transaction.commit();

                    }
            });
            button2= (Button) v.findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ReposHebdoFragment ReposHebdoFragment = new ReposHebdoFragment();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, ReposHebdoFragment, "Repos_hebdo_fragment"); // give your fragment container id in first parameter
                    transaction.disallowAddToBackStack();
                    //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
                    transaction.commit();

                }
            });
            button3 = (Button) v.findViewById(R.id.button3);
            button3.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ReglementationFragment ReglementationFragment = new ReglementationFragment();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, ReglementationFragment, "Reglementation_fragment"); // give your fragment container id in first parameter
                    transaction.disallowAddToBackStack();
                    //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
                    transaction.commit();

                }
            });
            button4 = (Button) v.findViewById(R.id.button4);
            button4.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SemaineFragment SemaineFragment = new SemaineFragment();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, SemaineFragment, "Semaine_fragment"); // give your fragment container id in first parameter
                    transaction.disallowAddToBackStack();
                    //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
                    transaction.commit();

                }
            });
            btnLundi10h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbConduite10h < 2){
                        nbConduite10h++;
                        btnLundi10h.setTextColor(Color.RED);
                        btnLundi10h.setClickable(false);
                        editorprefConduite.putBoolean("key_btnLundi10h", false);
                        editorprefConduite.apply();
                    } else{
                        Toast.makeText(context, R.string.conuiteSup10h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnMardi10h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbConduite10h < 2){
                        nbConduite10h++;
                        btnMardi10h.setTextColor(Color.RED);
                        btnMardi10h.setClickable(false);
                        editorprefConduite.putBoolean("key_btnMardi10h", false);
                        editorprefConduite.apply();
                    } else{
                        Toast.makeText(context, R.string.conuiteSup10h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnMercredi10h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbConduite10h < 2){
                        nbConduite10h++;
                        btnMercredi10h.setTextColor(Color.RED);
                        btnMercredi10h.setClickable(false);
                        editorprefConduite.putBoolean("key_btnMercredi10h", false);
                        editorprefConduite.apply();
                    } else{
                        Toast.makeText(context, R.string.conuiteSup10h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnJeudi10h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbConduite10h < 2){
                        nbConduite10h++;
                        btnJeudi10h.setTextColor(Color.RED);
                        btnJeudi10h.setClickable(false);
                        editorprefConduite.putBoolean("key_btnJeudi10h", false);
                        editorprefConduite.apply();
                    } else{
                        Toast.makeText(context, R.string.conuiteSup10h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnVendredi10h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbConduite10h < 2){
                        nbConduite10h++;
                        btnVendredi10h.setTextColor(Color.RED);
                        btnVendredi10h.setClickable(false);
                        editorprefConduite.putBoolean("key_btnVendredi10h", false);
                        editorprefConduite.apply();
                    } else{
                        Toast.makeText(context, R.string.conuiteSup10h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnSamedi10h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbConduite10h < 2){
                        nbConduite10h++;
                        btnSamedi10h.setTextColor(Color.RED);
                        btnSamedi10h.setClickable(false);
                        editorprefConduite.putBoolean("key_btnSamedi10h", false);
                        editorprefConduite.apply();
                    } else{
                        Toast.makeText(context, R.string.conuiteSup10h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnDimanche10h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbConduite10h < 2){
                        nbConduite10h++;
                        btnDimanche10h.setTextColor(Color.RED);
                        btnDimanche10h.setClickable(false);
                        editorprefConduite.putBoolean("key_btnDimanche10h", false);
                        editorprefConduite.apply();
                    } else{
                        Toast.makeText(context, R.string.conuiteSup10h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            imageButtonDeleteConduite.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    btnLundi10h.setClickable(true);
                    btnMardi10h.setClickable(true);
                    btnMercredi10h.setClickable(true);
                    btnJeudi10h.setClickable(true);
                    btnVendredi10h.setClickable(true);
                    btnSamedi10h.setClickable(true);
                    btnDimanche10h.setClickable(true);
                    btnLundi10h.setTextColor(Color.BLACK);
                    btnMardi10h.setTextColor(Color.BLACK);
                    btnMercredi10h.setTextColor(Color.BLACK);
                    btnJeudi10h.setTextColor(Color.BLACK);
                    btnVendredi10h.setTextColor(Color.BLACK);
                    btnSamedi10h.setTextColor(Color.BLACK);
                    btnDimanche10h.setTextColor(Color.BLACK);
                    nbConduite10h = 0;
                    // Clear all sharedpreferences pour les conduites
                    editorprefConduite.clear();
                    editorprefConduite.apply();
                    nbConduite10h = 0;
                    editorprefConduite.putInt("key_nbConduite10h",nbConduite10h );
                }
            });

            btnJ1_9h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbRepos9h < 3){
                        nbRepos9h++;
                        btnJ1_9h.setTextColor(Color.RED);
                        btnJ1_9h.setClickable(false);
                        editorprefRepos.putBoolean("key_btnJ1_9h", false);
                        editorprefRepos.apply();
                    } else{
                        Toast.makeText(context, R.string.reposInf11h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnJ2_9h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbRepos9h < 3){
                        nbRepos9h++;
                        btnJ2_9h.setTextColor(Color.RED);
                        btnJ2_9h.setClickable(false);
                        editorprefRepos.putBoolean("key_btnJ2_9h", false);
                        editorprefRepos.apply();
                    } else{
                        Toast.makeText(context, R.string.reposInf11h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnJ3_9h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbRepos9h < 3){
                        nbRepos9h++;
                        btnJ3_9h.setTextColor(Color.RED);
                        btnJ3_9h.setClickable(false);
                        editorprefRepos.putBoolean("key_btnJ3_9h", false);
                        editorprefRepos.apply();
                    } else{
                        Toast.makeText(context, R.string.reposInf11h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnJ4_9h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbRepos9h < 3){
                        nbRepos9h++;
                        btnJ4_9h.setTextColor(Color.RED);
                        btnJ4_9h.setClickable(false);
                        editorprefRepos.putBoolean("key_btnJ4_9h", false);
                        editorprefRepos.apply();
                    } else{
                        Toast.makeText(context, R.string.reposInf11h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnJ5_9h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbRepos9h < 3){
                        nbRepos9h++;
                        btnJ5_9h.setTextColor(Color.RED);
                        btnJ5_9h.setClickable(false);
                        editorprefRepos.putBoolean("key_btnJ5_9h", false);
                        editorprefRepos.apply();
                    } else{
                        Toast.makeText(context, R.string.reposInf11h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btnJ6_9h.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(nbRepos9h < 3){
                        nbRepos9h++;
                        btnJ6_9h.setTextColor(Color.RED);
                        btnJ6_9h.setClickable(false);
                        editorprefRepos.putBoolean("key_btnJ6_9h", false);
                        editorprefRepos.apply();
                    } else{
                        Toast.makeText(context, R.string.reposInf11h, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            imageButtonDeleteReposJournaliers.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    btnJ1_9h.setClickable(true);
                    btnJ2_9h.setClickable(true);
                    btnJ3_9h.setClickable(true);
                    btnJ4_9h.setClickable(true);
                    btnJ5_9h.setClickable(true);
                    btnJ6_9h.setClickable(true);
                    btnJ1_9h.setTextColor(Color.BLACK);
                    btnJ2_9h.setTextColor(Color.BLACK);
                    btnJ3_9h.setTextColor(Color.BLACK);
                    btnJ4_9h.setTextColor(Color.BLACK);
                    btnJ5_9h.setTextColor(Color.BLACK);
                    btnJ6_9h.setTextColor(Color.BLACK);
                    // Clear all sharedpreferences pour les repos
                    editorprefRepos.clear();
                    editorprefRepos.apply();
                    nbRepos9h = 0;
                    editorprefRepos.putInt("key_nbRepos9h",nbRepos9h );
                }
            });


        /* Define Your Functionality Here
           Find any view  => v.findViewById()
          Specifying Application Context in Fragment => getActivity() */

        return v;
    }

    private void GetReposConduiteValue() {
            //REPOS
        nbRepos9h = 0;
        nbRepos9h = prefRepos.getInt("key_nbRepos9h", 0);
        if (prefRepos.getBoolean("key_btnJ1_9h", true)==false) {
            nbRepos9h++;
            btnJ1_9h.setClickable(true);
            btnJ1_9h.setTextColor(Color.RED);
        }else{
            btnJ1_9h.setClickable(false);
            btnJ1_9h.setTextColor(Color.BLACK);
        }
        if (prefRepos.getBoolean("key_btnJ2_9h", true)==false) {
            nbRepos9h++;
            btnJ2_9h.setClickable(true);
            btnJ2_9h.setTextColor(Color.RED);
        }else{
            btnJ2_9h.setClickable(false);
            btnJ2_9h.setTextColor(Color.BLACK);
        }
        if (prefRepos.getBoolean("key_btnJ3_9h", true)==false) {
            nbRepos9h++;
            btnJ3_9h.setClickable(true);
            btnJ3_9h.setTextColor(Color.RED);
        }else{
            btnJ3_9h.setClickable(false);
            btnJ3_9h.setTextColor(Color.BLACK);
        }
        if (prefRepos.getBoolean("key_btnJ4_9h", true)==false) {
            nbRepos9h++;
            btnJ4_9h.setClickable(true);
            btnJ4_9h.setTextColor(Color.RED);
        }else{
            btnJ4_9h.setClickable(false);
            btnJ4_9h.setTextColor(Color.BLACK);
        }
        if (prefRepos.getBoolean("key_btnJ5_9h", true)==false) {
            nbRepos9h++;
            btnJ5_9h.setClickable(true);
            btnJ5_9h.setTextColor(Color.RED);
        }else{
            btnJ5_9h.setClickable(false);
            btnJ5_9h.setTextColor(Color.BLACK);
        }
        if (prefRepos.getBoolean("key_btnJ6_9h", true)==false) {
            nbRepos9h++;
            btnJ6_9h.setClickable(true);
            btnJ6_9h.setTextColor(Color.RED);
        }else{
            btnJ6_9h.setClickable(false);
            btnJ6_9h.setTextColor(Color.BLACK);
        }
        //CONDUITES
        nbConduite10h = 0;
        if (prefConduite.getBoolean("key_btnLundi10h", true)==false) {
            nbConduite10h++;
            btnLundi10h.setClickable(true);
            btnLundi10h.setTextColor(Color.RED);
        }else{
            btnLundi10h.setClickable(false);
            btnLundi10h.setTextColor(Color.BLACK);
        }
        if (prefConduite.getBoolean("key_btnMardi10h", true)==false) {
            nbConduite10h++;
            btnMardi10h.setClickable(true);
            btnMardi10h.setTextColor(Color.RED);
        }else{
            btnMardi10h.setClickable(false);
            btnMardi10h.setTextColor(Color.BLACK);
        }
        if (prefConduite.getBoolean("key_btnMercredi10h", true)==false) {
            nbConduite10h++;
            btnMercredi10h.setClickable(true);
            btnMercredi10h.setTextColor(Color.RED);
        }else{
            btnMercredi10h.setClickable(false);
            btnMercredi10h.setTextColor(Color.BLACK);
        }
        if (prefConduite.getBoolean("key_btnJeudi10h", true)==false) {
            nbConduite10h++;
            btnJeudi10h.setClickable(true);
            btnJeudi10h.setTextColor(Color.RED);
        }else{
            btnJeudi10h.setClickable(false);
            btnJeudi10h.setTextColor(Color.BLACK);
        }
        if (prefConduite.getBoolean("key_btnVendredi10h", true)==false) {
            nbConduite10h++;
            btnVendredi10h.setClickable(true);
            btnVendredi10h.setTextColor(Color.RED);
        }else{
            btnVendredi10h.setClickable(false);
            btnVendredi10h.setTextColor(Color.BLACK);
        }
        if (prefConduite.getBoolean("key_btnSamedi10h", true)==false) {
            nbConduite10h++;
            btnSamedi10h.setClickable(true);
            btnSamedi10h.setTextColor(Color.RED);
        }else{
            btnSamedi10h.setClickable(false);
            btnSamedi10h.setTextColor(Color.BLACK);
        }
        if (prefConduite.getBoolean("key_btnDimanche10h", true)==false) {
            nbConduite10h++;
            btnDimanche10h.setClickable(true);
            btnDimanche10h.setTextColor(Color.RED);
        }else{
            btnDimanche10h.setClickable(false);
            btnDimanche10h.setTextColor(Color.BLACK);
        }
    }


    @Override
    public void onResume() {
            Log.e("DEBUG", "onResume of HomeFragment");
            super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

            //Save the fragment's state here
    }
}