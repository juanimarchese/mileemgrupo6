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
import com.mileem.mileem.models.IdName;
import com.mileem.mileem.utils.DefinitionsUtils;

import java.util.ArrayList;
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
        getActivity().getActionBar().hide();
        addItemsToAutoCompleteTextView(rootView);
        addItemsToTipoPropiedadSpinner(rootView);
        addItemsToOperacionSpinner(rootView);
        addItemsToAmbientesSpinner(rootView);
        return rootView;
    }

    private void addItemsToTipoPropiedadSpinner(View rootView) {
        tipoPropiedadSpinner = (Spinner) rootView.findViewById(R.id.tipo_propiedad);
        List list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getPropertyTypesCollection(),"Todos");
        ArrayAdapter dataAdapter = new ArrayAdapter(rootView.getContext(),R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        tipoPropiedadSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToOperacionSpinner(View rootView) {
        operacionSpinner = (Spinner) rootView.findViewById(R.id.operacion);
        List list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getOperationTypesCollection(),"Todas");
        ArrayAdapter dataAdapter = new ArrayAdapter(rootView.getContext(),R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        operacionSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToAmbientesSpinner(View rootView) {
        ambientesSpinner = (Spinner) rootView.findViewById(R.id.ambientes);
        List list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getEnvironmentsTypesCollection(),"Todos");
        ArrayAdapter dataAdapter = new ArrayAdapter(rootView.getContext(),R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ambientesSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToAutoCompleteTextView(View rootView) {
        List list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getNeightboardsCollection());
        list.add("Todos");
        ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(),R.layout.autocomplete_item,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
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
                Bundle arguments = new Bundle();
                boolean isValid = true;
                String ambiente = (String) ((Spinner)getView().findViewById(R.id.ambientes)).getSelectedItem();
                int[] environments = getIntsFromSpinner(DefinitionsManager.getInstance().getEnvironmentsTypesCollection(), ambiente);
                arguments.putIntArray("environments",environments);

                String propType = (String) ((Spinner)getView().findViewById(R.id.tipo_propiedad)).getSelectedItem();
                int[] propertyTypes = getIntsFromSpinner(DefinitionsManager.getInstance().getPropertyTypesCollection(), propType);
                arguments.putIntArray("propertyTypes",propertyTypes);

                String opType = (String) ((Spinner)getView().findViewById(R.id.operacion)).getSelectedItem();
                int[] operationTypes = getIntsFromSpinner(DefinitionsManager.getInstance().getOperationTypesCollection(), opType);
                arguments.putIntArray("operationTypes",operationTypes);

                AutoCompleteTextView barrioView = (AutoCompleteTextView) getView().findViewById(R.id.barrio);
                String barrio = barrioView.getText().toString();
                if(barrio == null || barrio.isEmpty()){
                    isValid = false;
                    barrioView.setError("Debe indicar un barrio");
                }
                int[] neighborhoods = getIntsFromAutoComplete(DefinitionsManager.getInstance().getNeightboardsCollection(), barrio);
                if(neighborhoods.length > 0 && neighborhoods[0] == -1){
                    isValid = false;
                    barrioView.setError("Debe indicar un barrio válido");
                }
                arguments.putIntArray("neighborhoods",neighborhoods);

                if(isValid)
                    ((MainActivity)getActivity()).displayView(new ResultsFragment(), arguments,false);
            }
        });

    }

    private int[] getIntsFromAutoComplete(ArrayList<IdName> collection, String key) {
        int[] array;
        if(key == null || key.isEmpty() || key.equals("Todos")) {
            array = new int[0];
        } else {
            int id = DefinitionsUtils.findIdByName(collection,key);
            array = new int[1];
            array[0] = id;
        }
        return array;
    }

    private int[] getIntsFromSpinner(ArrayList<IdName> collection, String key) {
        int[] array;
        int id = DefinitionsUtils.findIdByName(collection,key);
        if(id != -1){
            array = new int[1];
            array[0] = id;
        } else {
            array = new int[0];
        }
        return array;
    }


    @Override
    public String getTittle() {
        return "Busqueda";
    }

    public boolean hasMenuOption(){
        return true;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            getActivity().getActionBar().hide();
        }
    }
}
