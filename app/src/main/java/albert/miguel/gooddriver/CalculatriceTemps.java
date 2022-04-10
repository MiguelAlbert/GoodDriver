package albert.miguel.gooddriver;


import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class CalculatriceTemps extends Fragment {

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();

        View v = inflater.inflate(R.layout.fragment_calculatrice_temps,container,false);
        EditText etHeure1 = (EditText) v.findViewById(R.id.etHeure1);

        // prevent system keyboard from appearing when EditText is tapped
        etHeure1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        etHeure1.setTextIsSelectable(true);
        //editText.setShowSoftInputOnFocus(false);
        etHeure1.setInputType(InputType.TYPE_CLASS_NUMBER);

        return v;
    }

}
