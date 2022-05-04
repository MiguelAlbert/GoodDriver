package albert.miguel.gooddriver;


import static android.content.Context.MODE_PRIVATE;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class ReglementationFragment extends Fragment {

    //https://androidexample365.com/a-simple-expandable-cardview-for-android/

    Context context;
    Button button;
    ExpandableCardView card ;
    private AdView mPublisherAdView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = Objects.requireNonNull(container).getContext();
        View v = inflater.inflate(R.layout.fragment_reglementation,container,false);

        mPublisherAdView = v.findViewById(R.id.publisherAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);
        /*
        card = v.findViewById(R.id.conduite);
        card.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {

                Toast.makeText(context, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            }
        });
        button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "test button", Toast.LENGTH_SHORT).show();
                card.collapse();
                card.setTitle("Title");
                card.setIcon(R.drawable.ic_baseline_delete_24);
            }
        });

         */
        return v;

    }

}
