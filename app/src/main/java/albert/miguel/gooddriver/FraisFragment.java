package albert.miguel.gooddriver;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Objects;


public class FraisFragment extends Fragment {

    //https://androidexample365.com/a-simple-expandable-cardview-for-android/

    Context context;
    Button button;
    ExpandableCardView card ;
    private AdView mPublisherAdView;
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

}
