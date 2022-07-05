package albert.miguel.gooddriver;


import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;


public class SemaineFragment extends Fragment {


    Context context;
    Button button;
    ImageButton imageButtonDateDebutJ1;
    ExpandableCardView reposHbefore, reposHend, amplitude1, amplitude2, amplitude3, amplitude4, amplitude5, amplitude6;
    static SharedPreferences.Editor editor;
    private AdView mPublisherAdView;
    private Activity v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = Objects.requireNonNull(container).getContext();
        View v = inflater.inflate(R.layout.fragment_semaines,container,false);

        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();

        reposHbefore = v.findViewById(R.id.reposHbefore);
        amplitude1 = v.findViewById(R.id.amplitude1);
        amplitude2 = v.findViewById(R.id.amplitude2);
        amplitude3 = v.findViewById(R.id.amplitude3);
        amplitude4 = v.findViewById(R.id.amplitude4);
        amplitude5 = v.findViewById(R.id.amplitude5);
        amplitude6 = v.findViewById(R.id.amplitude6);
        reposHend = v.findViewById(R.id.reposHend);

        imageButtonDateDebutJ1 = v.findViewById(R.id.imageButtonDateDebutJ1);
        imageButtonDateDebutJ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Date", Toast.LENGTH_SHORT).show();
            }
        });

        reposHbefore.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
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
            }
        });
        amplitude1.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
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
            }
        });
        amplitude2.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
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
            }
        });
        amplitude3.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
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
            }
        });
        amplitude4.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
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
            }
        });
        amplitude5.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
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
            }
        });
        amplitude6.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
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
            }
        });
        reposHend.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
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
            }
        });
        return v;
    }
    @Override
    public void onResume() {
        NavigationView navigationView = (NavigationView) requireActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_semaines).setChecked(true);
        SharedPreferences pref = context.getSharedPreferences("PrefSemaine", MODE_PRIVATE);
        editor = pref.edit();
        OpenExpandListener();
        super.onResume();
    }

    private void OpenExpandListener() {
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
    }

}
