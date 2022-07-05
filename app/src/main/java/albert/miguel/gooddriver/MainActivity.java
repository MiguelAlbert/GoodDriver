package albert.miguel.gooddriver;

import static android.content.ContentValues.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.Collections;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    private InterstitialAd mInterstitialAd;
    private ConsentInformation consentInformation;
    private ConsentForm consentForm;

    private static final String TAG_MY_FRAGMENT = "myFragment";
    MainFragment mFragment;

    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 100;

    private ReviewManager reviewManager;

    ReviewInfo reviewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        if (savedInstanceState == null) {
            MainFragment MainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame,MainFragment)
                    .disallowAddToBackStack()
                    .commit();
        } else{
            mFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(TAG_MY_FRAGMENT);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            addDynamicShortcut();
        }

        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo,
                                AppUpdateType.FLEXIBLE,
                                MainActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mAppUpdateManager.registerListener(installStateUpdatedListener);

        loadAdd();
        consentement();
        createchannel();
        showRateApp();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final PeriodicWorkRequest periodicWorkRequest1 = new PeriodicWorkRequest.Builder(WorkerWidget.class,15, TimeUnit.MINUTES)
                .setInitialDelay(1000,TimeUnit.MILLISECONDS)
                .build();

        WorkManager workManager =  WorkManager.getInstance(this);

        workManager.enqueue(periodicWorkRequest1);

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest1.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if (workInfo != null) {
                            Log.w("periodicWorkRequest", "Status changed to : " + workInfo.getState());

                        }
                    }
                });
    }



    private void addDynamicShortcut() {
        //Intent googleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, MainActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activityIntent.putExtra("fragment", "carte");
        Intent activityAmplitudeIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, MainActivity.class);
        activityAmplitudeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activityAmplitudeIntent.putExtra("fragment", "amplitude");
        Intent activityReposHebdoIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, MainActivity.class);
        activityReposHebdoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activityReposHebdoIntent.putExtra("fragment", "hebdo");

        ShortcutInfo shortcut = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcut = new ShortcutInfo.Builder(this, "dynamic_shortcut")
                    .setShortLabel("Amplitude")
                    .setLongLabel("Réglage Amplitude")
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setIntent(activityAmplitudeIntent)
                    .build();
        }
        ShortcutInfo shortcut2 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcut2 = new ShortcutInfo.Builder(this, "dynamic_shortcut2")
                    .setShortLabel("Carte")
                    .setLongLabel("Carte tachygraphe")
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setIntent(activityIntent)
                    .build();
        }
        ShortcutInfo shortcut3 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcut3 = new ShortcutInfo.Builder(this, "dynamic_shortcut3")
                    .setShortLabel("Repos hebdo")
                    .setLongLabel("Réglage repos hebdo")
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setIntent(activityReposHebdoIntent)
                    .build();
        }

        ShortcutManager shortcutManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                shortcutManager = getSystemService(ShortcutManager.class);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutManager.addDynamicShortcuts(Collections.singletonList(shortcut));
            shortcutManager.addDynamicShortcuts(Collections.singletonList(shortcut2));
            shortcutManager.addDynamicShortcuts(Collections.singletonList(shortcut3));
        }
    }

    private final InstallStateUpdatedListener installStateUpdatedListener = installState -> {
        if(installState.installStatus() == InstallStatus.DOWNLOADED){
            showCompletedUpdate();
        }
    };

    @Override
    protected void onStop() {
        if(mAppUpdateManager!=null) mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        getIntent().removeExtra("fragment");
        super.onStop();
    }

    private void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "New app is ready!",
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_APP_UPDATE){
            //Toast.makeText(this, "Cancel",Toast.LENGTH_SHORT).show();
        }
    }

    private void createchannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(AmplitudeFragment.id,
                    getString(R.string.channel_name4),  //name of the channel
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

    private void consentement() {
        // Set tag for underage of consent. false means users are not underage.
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation = (ConsentInformation) UserMessagingPlatform.getConsentInformation(context);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
                    @Override
                    public void onConsentInfoUpdateSuccess() {
                        // The consent information state was updated.
                        // You are now ready to check if a form is available.
                        if (consentInformation.isConsentFormAvailable()) {
                            loadForm();
                        }
                    }
                },
                new ConsentInformation.OnConsentInfoUpdateFailureListener() {
                    @Override
                    public void onConsentInfoUpdateFailure(FormError formError) {
                        // Handle the error.
                    }
                });
    }

    public void showRateApp() {
        reviewManager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
                // show native rate app dialog on error
                //showRateAppFallbackDialog();
            }
        });
    }

    /**
     * Showing native dialog with three buttons to review the app
     * Redirect user to playstore to review the app
     */
    private void showRateAppFallbackDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Notez l'application")
                .setMessage("Si vous aimez mettez une note")
                .setPositiveButton("Je note maintenant", (dialog, which) -> {
                    showRateApp();
                })
                .setNegativeButton("Non merci",
                        (dialog, which) -> {
                        })
                .setNeutralButton("Plus tard",
                        (dialog, which) -> {
                        })
                .setOnDismissListener(dialog -> {
                })
                .show();
    }


    private void loadForm() {
        UserMessagingPlatform.loadConsentForm(
                context,
                new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
                    @Override
                    public void onConsentFormLoadSuccess(ConsentForm consentForm) {
                        MainActivity.this.consentForm = consentForm;
                        if(consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                            consentForm.show(
                                    MainActivity.this,
                                    formError -> {
                                        // Handle dismissal by reloading form.
                                        loadForm();
                                    });

                        }
                    }
                },
                new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
                    @Override
                    public void onConsentFormLoadFailure(FormError formError) {
                        // Handle the error
                    }
                }
        );
    }

    private void loadAdd() {
        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                AdRequest adRequest = new AdRequest.Builder().build();
                InterstitialAd.load(MainActivity.this,"ca-app-pub-6506972643290681/2038757206", adRequest,
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        super.onResume();
        try {
            Bundle extras = getIntent().getExtras();
            String message = extras.getString("fragment");
            if (message.equals("amplitude")) {
                ViewPagerAmplitude ViewPagerAmplitude = new ViewPagerAmplitude();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, ViewPagerAmplitude, "Amplitude_fragment"); // give your fragment container id in first parameter
                transaction.disallowAddToBackStack();
                //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Bundle extras = getIntent().getExtras();
            String message = extras.getString("fragment");
            if (message.equals("carte")) {
                CarteFragment CarteFragment = new CarteFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, CarteFragment, "Carte_fragment"); // give your fragment container id in first parameter
                transaction.disallowAddToBackStack();
                //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Bundle extras = getIntent().getExtras();
            String message = extras.getString("fragment");
            if (message.equals("hebdo")) {
                ViewPagerReposHebdo ViewPagerReposHebdo = new ViewPagerReposHebdo();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, ViewPagerReposHebdo, "Repos_hebdo_fragment"); // give your fragment container id in first parameter
                transaction.disallowAddToBackStack();
                //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getIntent().removeExtra("fragment");
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if(appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo,
                                AppUpdateType.FLEXIBLE,
                                MainActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        //getIntent().removeExtra("fragment");
    }

    @Override
    public void onBackPressed() {
        ActuWebView ActuWebView = (ActuWebView) getSupportFragmentManager().findFragmentByTag("Actu_fragment");
        ViewPagerAmplitude ViewPagerAmplitude = (ViewPagerAmplitude) getSupportFragmentManager().findFragmentByTag("Amplitude_fragment");
        ViewPagerReposHebdo ViewPagerReposHebdo = (ViewPagerReposHebdo) getSupportFragmentManager().findFragmentByTag("Repos_hebdo_fragment");
        ReglementationFragment ReglementationFragment = (ReglementationFragment) getSupportFragmentManager().findFragmentByTag("Reglementation_fragment");
        SemaineFragment SemaineFragment = (SemaineFragment) getSupportFragmentManager().findFragmentByTag("Semaine_fragment");
        ViewPagerCalcTemps ViewPagerCalcTemps = (ViewPagerCalcTemps) getSupportFragmentManager().findFragmentByTag("Calc_temps_fragment");
        ViewPagerVitesseMoyenne ViewPagerVitesseMoyenne = (ViewPagerVitesseMoyenne) getSupportFragmentManager().findFragmentByTag("Vitesse_fragment");
        CalculPalettes CalculPalettes = (CalculPalettes) getSupportFragmentManager().findFragmentByTag("Palettes_fragment");
        CarteFragment CarteFragment = (CarteFragment) getSupportFragmentManager().findFragmentByTag("Carte_fragment");
        FraisFragment FraisFragment = (FraisFragment) getSupportFragmentManager().findFragmentByTag("Frais_fragment");

        if (ViewPagerAmplitude != null && ViewPagerAmplitude.isVisible()) {
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            /*
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }*/
            transaction.commit();
        } else if(ViewPagerReposHebdo != null && ViewPagerReposHebdo.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment,"TAG_Repos_Hebdo"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            /*
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }*/
            transaction.commit();
        } else if(ReglementationFragment != null && ReglementationFragment.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            /*
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }*/
            transaction.commit();
        } else if(ViewPagerCalcTemps != null && ViewPagerCalcTemps.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            /*
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }*/
            transaction.commit();
        }else if(SemaineFragment != null && SemaineFragment.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            /*
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }*/
            transaction.commit();
        }else if(ViewPagerVitesseMoyenne != null && ViewPagerVitesseMoyenne.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            /*
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }*/
            transaction.commit();
        }else if(CalculPalettes != null && CalculPalettes.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment,"TAG_Palettes"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            /*
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }*/
            transaction.commit();
        }else if(CarteFragment != null && CarteFragment.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment,"TAG_Carte"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            /*
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }*/
            transaction.commit();
        }else if(FraisFragment != null && FraisFragment.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment,"TAG_Frais"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            /*
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }*/
            transaction.commit();
        }else if(ActuWebView != null && ActuWebView.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment,"TAG_Actu"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            /*
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }*/
            transaction.commit();
        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.app_name);
            //builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Êtes-vous sur(e) de vouloir quitter l'application ?")
                    .setCancelable(false)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            closeContextMenu();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            /*
            Intent intent = new Intent(this, SimpleWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
            // since it seems the onUpdate() is only fired on that:
            int[] ids = AppWidgetManager.getInstance(getApplication())
                    .getAppWidgetIds(new ComponentName(getApplication(), SimpleWidgetProvider.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            sendBroadcast(intent);
 */

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle("Informations");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Application développée par Miguel ALBERT\nL'auteur vérifie la véracité des contenus fournis. Il décline toute responsabilité quant à l'inexatitude des informations ou des calculs.")
                    .setNegativeButton("Fermer",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();

            return true;
        }

        if (id == R.id.action_rgpd) {
            consentInformation.reset();
            consentement();
            return true;
        }

        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "GoodDriver");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "GoodDriver\n\nJe souhaite partager avec vous cette application.\n\nVous pouvez la télécharger depuis le Store en cliquant sur ce lien\n\nhttps://play.google.com/store/apps/details?id=albert.miguel.gooddriver&gl=FR");
            startActivity(Intent.createChooser(shareIntent, "Share link"));
            return true;
        }

        if (id == R.id.action_settings) {
            /*
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle("Réglages");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Bientôt les réglages")
                    .setNegativeButton("Fermer",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
*/
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("app_package", context.getPackageName());
                intent.putExtra("app_uid", context.getApplicationInfo().uid);
            } else {
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
            }
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_accueil) {
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_amplitude) {
            ViewPagerAmplitude ViewPagerAmplitude = new ViewPagerAmplitude();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, ViewPagerAmplitude, "Amplitude_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_repos_hebdo) {
            ViewPagerReposHebdo ViewPagerReposHebdo = new ViewPagerReposHebdo();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, ViewPagerReposHebdo, "Repos_hebdo_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_reglementation) {
            ReglementationFragment ReglementationFragment = new ReglementationFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, ReglementationFragment, "Reglementation_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_semaines) {
            SemaineFragment SemaineFragment = new SemaineFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, SemaineFragment, "Semaine_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_calculatrice) {
            ViewPagerCalcTemps ViewPagerCalcTemps = new ViewPagerCalcTemps();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, ViewPagerCalcTemps, "Calc_temps_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_vitesse) {
            ViewPagerVitesseMoyenne ViewPagerVitesseMoyenne = new ViewPagerVitesseMoyenne();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, ViewPagerVitesseMoyenne, "Vitesse_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_palette) {
            CalculPalettes CalculPalettes = new CalculPalettes();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, CalculPalettes, "Palettes_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_carte) {
            CarteFragment CarteFragment = new CarteFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, CarteFragment, "Carte_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_frais) {
            FraisFragment FraisFragment = new FraisFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, FraisFragment, "Frais_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_actu) {
            ActuWebView ActuWebView = new ActuWebView();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, ActuWebView, "Actu_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        if (id == R.id.nav_quit) {
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}