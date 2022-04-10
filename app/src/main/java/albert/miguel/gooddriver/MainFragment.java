package albert.miguel.gooddriver;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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

        Button button1, button2, button3, button4, button5, button6;

        Activity thisActivity;
        Context context;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            context = container.getContext();
            View v = inflater.inflate(R.layout.accueil_fragment,container,false);
            thisActivity = getActivity();

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
                    ViewPagerReposHebdo ViewPagerReposHebdo = new ViewPagerReposHebdo();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, ViewPagerReposHebdo, "Repos_hebdo_fragment"); // give your fragment container id in first parameter
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
            button6 = (Button) v.findViewById(R.id.button6);
            button6.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ViewPagerCalcTemps ViewPagerCalcTemps = new ViewPagerCalcTemps();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, ViewPagerCalcTemps, "Calc_temps_fragment"); // give your fragment container id in first parameter
                    transaction.disallowAddToBackStack();
                    //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
                    transaction.commit();

                }
            });
            button5 = (Button) v.findViewById(R.id.button5);
            button5.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ViewPagerVitesseMoyenne ViewPagerVitesseMoyenne = new ViewPagerVitesseMoyenne();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, ViewPagerVitesseMoyenne, "Vitesse_fragment"); // give your fragment container id in first parameter
                    transaction.disallowAddToBackStack();
                    //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
                    transaction.commit();

                }
            });

        return v;
    }

    @Override
    public void onResume() {
            Log.e("DEBUG", "onResume of HomeFragment");
            super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

    }
}