package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.managers.DefinitionsManager;
import com.mileem.mileem.utils.DefinitionsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchFragment extends BaseFragment {

    public static final String TAG = SearchFragment.class.getSimpleName();

    private AutoCompleteTextView barrioACTV;
    private Spinner tipoPropiedadSpinner;
    private Spinner operacionSpinner;
    private Spinner ambientesSpinner;

    public SearchFragment() {
        super(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        addItemsToAutoCompleteTextView(rootView);
        addItemsToTipoPropiedadSpinner(rootView);
        addItemsToOperacionSpinner(rootView);
        addItemsToAmbientesSpinner(rootView);
        return rootView;
    }

    private void addItemsToAmbientesSpinner(View rootView) {
        tipoPropiedadSpinner = (Spinner) rootView.findViewById(R.id.tipo_propiedad);
        /*List list = new ArrayList();
        Collections.addAll(list, getResources().getStringArray(R.array.tipos_propiedad));*/
        List list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getEnvironmentsTypesCollection());
        ArrayAdapter dataAdapter = new ArrayAdapter(rootView.getContext(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoPropiedadSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToOperacionSpinner(View rootView) {
        ambientesSpinner = (Spinner) rootView.findViewById(R.id.operacion);
        /*List list = new ArrayList();
        Collections.addAll(list, getResources().getStringArray(R.array.operaciones));*/
        List list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getOperationTypesCollection());
        ArrayAdapter dataAdapter = new ArrayAdapter(rootView.getContext(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ambientesSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToTipoPropiedadSpinner(View rootView) {
        operacionSpinner = (Spinner) rootView.findViewById(R.id.ambientes);
        /*List list = new ArrayList();
        Collections.addAll(list, getResources().getStringArray(R.array.ambientes));*/
        List list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getPropertyTypesCollection());
        ArrayAdapter dataAdapter = new ArrayAdapter(rootView.getContext(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operacionSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToAutoCompleteTextView(View rootView) {
       /* String[] countries = getResources().getStringArray(R.array.barrios);*/
        List list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getNeightboardsCollection());
        ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(),android.R.layout.select_dialog_item,list);
        barrioACTV = (AutoCompleteTextView) rootView.findViewById(R.id.barrio);
        barrioACTV.setThreshold(1);//will start working from first character
        barrioACTV.setAdapter(adapter);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button button_generar_repo = (Button) view.findViewById(R.id.button_buscar);

        button_generar_repo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).displayViewForMenu(1);
            }
        });

    }


    @Override
    public String getTittle() {
        return "Busqueda";
    }
}
