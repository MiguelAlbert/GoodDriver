package albert.miguel.gooddriver;

import static android.content.ContentValues.TAG;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Objects;


public class MainFragment extends Fragment {

        Button button1, button2, button3, button4, button5, button6, button7;

        Activity thisActivity;
        Context context;

        private InterstitialAd mInterstitialAd;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            context = Objects.requireNonNull(container).getContext();
            View v = inflater.inflate(R.layout.accueil_fragment,container,false);
            thisActivity = getActivity();
            loadAdd();
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
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(thisActivity);
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }
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
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(thisActivity);
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
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
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(thisActivity);
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
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
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(thisActivity);
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
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
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(thisActivity);
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
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
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(thisActivity);
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
                }
            });
            button7 = (Button) v.findViewById(R.id.button7);
            button7.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    CalculPalettes CalculPalettes = new CalculPalettes();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, CalculPalettes, "Palettes_fragment"); // give your fragment container id in first parameter
                    transaction.disallowAddToBackStack();
                    //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
                    transaction.commit();
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(thisActivity);
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
                }
            });

        return v;
    }

    private void loadAdd() {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                AdRequest adRequest = new AdRequest.Builder().build();

                InterstitialAd.load(context,"ca-app-pub-6506972643290681/2038757206", adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                // The mInterstitialAd reference will be null until
                                // an ad is loaded.
                                mInterstitialAd = interstitialAd;
                                Log.i(TAG, "onAdLoaded");

                                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error
                                Log.i(TAG, loadAdError.getMessage());
                                mInterstitialAd = null;
                            }
                        });
            }
        });
    }

    @Override
    public void onResume() {
            Log.e("DEBUG", "onResume of HomeFragment");
            super.onResume();
            loadAdd();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

    }
}