package albert.miguel.gooddriver;

import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    public static String id = "test_channel_01";
    private static final String TAG_MY_FRAGMENT = "myFragment";
    MainFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment MainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame,MainFragment)
                    .disallowAddToBackStack()
                    .commit();
        } else{
            mFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(TAG_MY_FRAGMENT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        AmplitudeFragment AmplitudeFragment = (AmplitudeFragment) getSupportFragmentManager().findFragmentByTag("Amplitude_fragment");
        ReposHebdoFragment ReposHebdoFragment = (ReposHebdoFragment) getSupportFragmentManager().findFragmentByTag("Repos_hebdo_fragment");
        ReglementationFragment ReglementationFragment = (ReglementationFragment) getSupportFragmentManager().findFragmentByTag("Reglementation_fragment");
        SemaineFragment SemaineFragment = (SemaineFragment) getSupportFragmentManager().findFragmentByTag("Semaine_fragment");

        if (AmplitudeFragment != null && AmplitudeFragment.isVisible()) {
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
        } else if(ReposHebdoFragment != null && ReposHebdoFragment.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
        } else if(ReglementationFragment != null && ReglementationFragment.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
        } else if(SemaineFragment != null && SemaineFragment.isVisible()){
            MainFragment MainFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
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

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle("Informations");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Application développée par Miguel ALBERT\nL'auteur vérifie la véracité des contenus fournis. Il décline toute responsabilité quant à l'inexatitude des informations.")
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
            Toast.makeText(getApplicationContext(),"RGPD",Toast.LENGTH_SHORT).show();
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
        if (id == R.id.action_delete) {


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
        }
        if (id == R.id.nav_amplitude) {
            AmplitudeFragment AmplitudeFragment = new AmplitudeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, AmplitudeFragment, "Amplitude_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
        }
        if (id == R.id.nav_repos_hebdo) {
            ReposHebdoFragment ReposHebdoFragment = new ReposHebdoFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, ReposHebdoFragment, "Repos_hebdo_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
        }
        if (id == R.id.nav_reglementation) {
            ReglementationFragment ReglementationFragment = new ReglementationFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, ReglementationFragment, "Reglementation_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
        }
        if (id == R.id.nav_semaines) {
            SemaineFragment SemaineFragment = new SemaineFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, SemaineFragment, "Semaine_fragment"); // give your fragment container id in first parameter
            transaction.disallowAddToBackStack();
            //transaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
            transaction.commit();
        }
        if (id == R.id.nav_quit) {
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}