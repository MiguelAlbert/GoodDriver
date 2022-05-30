package albert.miguel.gooddriver;


import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;


public class FraisFragment extends Fragment {

    //https://androidexample365.com/a-simple-expandable-cardview-for-android/

    Context context;
    Button button;
    ExpandableCardView card ;
    private AdView mPublisherAdView;
    private Activity v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = Objects.requireNonNull(container).getContext();
        View v = inflater.inflate(R.layout.fragment_fraisdeplacement,container,false);


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
    @Override
    public void onResume() {
        NavigationView navigationView = (NavigationView) requireActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_frais).setChecked(true);
        super.onResume();
    }

}
