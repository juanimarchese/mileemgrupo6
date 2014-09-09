package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.mileem.mileem.R;


public class SearchFragment extends BaseFragment {

    public static final String TAG = "SearchFragment";

    private AutoCompleteTextView actv;

    public SearchFragment() {
        super(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        String[] countries = getResources().
                getStringArray(R.array.barrios);
        ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(),android.R.layout.select_dialog_item,countries);
        actv = (AutoCompleteTextView) rootView.findViewById(R.id.barrio);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);
        return rootView;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /*Button button_generar_repo = (Button) view.findViewById(R.id.button_generar);

        button_generar_repo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((BaseActivity)getActivity()).showFragment(new ContactsFragment());
            }
        });*/

    }


    @Override
    public String getTittle() {
        return "Busqueda";
    }
}
