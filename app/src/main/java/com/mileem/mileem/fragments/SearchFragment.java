package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.managers.DefinitionsManager;
import com.mileem.mileem.models.Currency;
import com.mileem.mileem.models.IdName;
import com.mileem.mileem.utils.DefinitionsUtils;
import com.mileem.mileem.widgets.CustomAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends BaseFragment {

    public static final String TAG = SearchFragment.class.getSimpleName();

    private CustomAutoCompleteTextView barrioACTV;
    private Spinner tipoPropiedadSpinner;
    private Spinner operacionSpinner;
    private Spinner ambientesSpinner;
    private Spinner m2Spinner;
    private Spinner monedaSpinner;
    private Spinner fechaSpinner;
    private boolean isAdvanceSearch = false;

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
        addItemsToM2Spinner(rootView);
        addItemsToFechaSpinner(rootView);
        addItemsToMonedaSpinner(rootView);
        return rootView;
    }

    private void addItemsToM2Spinner(View rootView) {
        m2Spinner = (Spinner) rootView.findViewById(R.id.m2);
        List<String> list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getSizes(), "Todos");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        m2Spinner.setAdapter(dataAdapter);
    }

    private void addItemsToMonedaSpinner(View rootView) {
        monedaSpinner = (Spinner) rootView.findViewById(R.id.moneda);
        ArrayList<Currency> currencies = DefinitionsManager.getInstance().getCurrencies();
        List<String> list = new ArrayList<String>();
        list.add("Todas");
        for(Currency currency : currencies){
            list.add(currency.getId());
        }
        ArrayAdapter dataAdapter = new ArrayAdapter(rootView.getContext(),R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        monedaSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToFechaSpinner(View rootView) {
        fechaSpinner = (Spinner) rootView.findViewById(R.id.fecha);
        List<String> list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getDateRanges(), "Todas");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        fechaSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToTipoPropiedadSpinner(View rootView) {
        tipoPropiedadSpinner = (Spinner) rootView.findViewById(R.id.tipo_propiedad);
        List<String> list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getPropertyTypes(), "Todos");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        tipoPropiedadSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToOperacionSpinner(View rootView) {
        operacionSpinner = (Spinner) rootView.findViewById(R.id.operacion);
        List<String> list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getOperationTypes(), "Todas");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        operacionSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToAmbientesSpinner(View rootView) {
        ambientesSpinner = (Spinner) rootView.findViewById(R.id.ambientes);
        List<String> list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getEnvironments(), "Todos");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ambientesSpinner.setAdapter(dataAdapter);
    }

    private void addItemsToAutoCompleteTextView(View rootView) {
        List<String> list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getNeighborhoods());
        String defaultElement = "Todos";
        list.add(defaultElement);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.autocomplete_item,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        barrioACTV = (CustomAutoCompleteTextView) rootView.findViewById(R.id.barrio);
        barrioACTV.setThreshold(0);
        barrioACTV.setAdapter(adapter);
        barrioACTV.setText(defaultElement);
        barrioACTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    barrioACTV.setText("");
                }
                return false;
            }
        });
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buildAdvancedSearchButton(view);
        buildSearchButton(view);
    }

    private void buildAdvancedSearchButton(View view) {
        Button button = (Button) view.findViewById(R.id.button_advance);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonAvanzada = (Button) getView().findViewById(R.id.button_advance);
                CharSequence buttonLabel = buttonAvanzada.getText();
                if (getResources().getString(R.string.button_avanzada).equals(buttonLabel)){
                    showAdvanceSeccion(buttonAvanzada);
                } else {
                    hideAdvanceSeccion(buttonAvanzada);
                }
            }

        });
    }

    private void setViewVisibility(int id,int visibility){
        View view = getView().findViewById(id);
        if(view != null)
            view.setVisibility(visibility);
    }

    private void hideAdvanceSeccion(Button buttonAvanzada) {
        buttonAvanzada.setText(getResources().getString(R.string.button_avanzada));
        setViewVisibility(R.id.m2, View.GONE);
        setViewVisibility(R.id.m2_txt, View.GONE);
        setViewVisibility(R.id.fecha_txt, View.GONE);
        setViewVisibility(R.id.fecha, View.GONE);
        setViewVisibility(R.id.precio_txt, View.GONE);
        setViewVisibility(R.id.moneda, View.GONE);
        setViewVisibility(R.id.moneda_txt, View.GONE);
        setViewVisibility(R.id.precio_txt, View.GONE);
        setViewVisibility(R.id.precio_max, View.GONE);
        setViewVisibility(R.id.precio_min, View.GONE);
        isAdvanceSearch = false;
    }

    private void showAdvanceSeccion(Button buttonAvanzada) {
        buttonAvanzada.setText(getResources().getString(R.string.button_simple));
        setViewVisibility(R.id.m2, View.VISIBLE);
        setViewVisibility(R.id.m2_txt, View.VISIBLE);
        setViewVisibility(R.id.fecha_txt,View.VISIBLE);
        setViewVisibility(R.id.fecha,View.VISIBLE);
        setViewVisibility(R.id.precio_txt, View.VISIBLE);
        setViewVisibility(R.id.moneda, View.VISIBLE);
        setViewVisibility(R.id.moneda_txt, View.VISIBLE);
        setViewVisibility(R.id.precio_txt, View.VISIBLE);
        setViewVisibility(R.id.precio_max,View.VISIBLE);
        setViewVisibility(R.id.precio_min,View.VISIBLE);
        isAdvanceSearch = true;
    }

    private void buildSearchButton(View view) {
        Button button_generar_repo = (Button) view.findViewById(R.id.button_buscar);
        button_generar_repo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                boolean isValid = true;

                putParamsFromSpinner(arguments, R.id.ambientes, DefinitionsManager.getInstance().getEnvironments(), "environments");
                putParamsFromSpinner(arguments, R.id.tipo_propiedad, DefinitionsManager.getInstance().getPropertyTypes(), "propertyTypes");
                putParamsFromSpinner(arguments, R.id.operacion, DefinitionsManager.getInstance().getOperationTypes(), "operationTypes");

                isValid = putParamsFromAutoComplete(arguments, isValid);

                if (isAdvanceSearch) {
                    putParamsFromSpinner(arguments, R.id.m2, DefinitionsManager.getInstance().getSizes(), "m2Sizes");
                    putParamsFromSpinner(arguments, R.id.fecha, DefinitionsManager.getInstance().getDateRanges(), "dates");
                    putParamsFromSpinnerCurrency(arguments, R.id.moneda, "currencys");
                    isValid = putParamsFromEditTexts(arguments,isValid);
                }

                if (isValid)
                    ((MainActivity) getActivity()).displayView(new ResultsFragment(), arguments, false);
            }
        });
    }

    private boolean putParamsFromEditTexts(Bundle arguments, boolean isValid) {
        EditText pMaxView = (EditText) getView().findViewById(R.id.precio_max);
        EditText pMinView = (EditText) getView().findViewById(R.id.precio_min);
        String precioMax = pMaxView.getText().toString();
        String precioMin = pMinView.getText().toString();
        Long precioMinInt;
        Long precioMaxInt;

        if(precioMax != null && !precioMax.isEmpty()){
            precioMaxInt = Long.valueOf(precioMax);
        } else {
            precioMaxInt = (long) 99999999;
        }

        if(precioMin != null && !precioMin.isEmpty()){
            precioMinInt = Long.valueOf(precioMin);
        } else {
            precioMinInt = (long) 0;
        }


        if(precioMaxInt < precioMinInt){
            isValid = false;
            pMaxView.setError("Precio máximo debe ser mayor al mínimo");
        }


        if(precioMaxInt > 99999999){
            isValid = false;
            pMaxView.setError("Valor máximo 99999999");
        }

        if(precioMinInt > 99999999){
            isValid = false;
            pMinView.setError("Valor máximo 99999999");
        }

        arguments.putLong("precioMin", precioMinInt);
        arguments.putLong("precioMax", precioMaxInt);
        return isValid;
    }

    private boolean putParamsFromAutoComplete(Bundle arguments, boolean isValid) {
        CustomAutoCompleteTextView barrioView = (CustomAutoCompleteTextView) getView().findViewById(R.id.barrio);
        String barrio = barrioView.getText().toString();
        if(barrio == null || barrio.isEmpty()){
            isValid = false;
            barrioView.setError("Debe indicar un barrio");
        }
        int[] neighborhoods = getIntsFromAutoComplete(DefinitionsManager.getInstance().getNeighborhoods(), barrio);
        if(neighborhoods.length > 0 && neighborhoods[0] == -1){
            isValid = false;
            barrioView.setError("Debe indicar un barrio válido");
        }
        arguments.putIntArray("neighborhoods",neighborhoods);
        return isValid;
    }

    private void putParamsFromSpinner(Bundle arguments,int spinnerId,ArrayList<IdName> spinnerList,String paramId){
        String selectedItem = (String) ((Spinner)getView().findViewById(spinnerId)).getSelectedItem();
        int[] selectedValues = getIntsFromSpinner(spinnerList, selectedItem);
        arguments.putIntArray(paramId,selectedValues);
    }

    private void putParamsFromSpinnerCurrency(Bundle arguments,int spinnerId,String paramId){
        String selectedItem = (String) ((Spinner)getView().findViewById(spinnerId)).getSelectedItem();
        arguments.putString(paramId,selectedItem);
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
            if(getArguments().getBoolean("viewAdvanceSearch")){
                Button button = (Button) getView().findViewById(R.id.button_advance);
                showAdvanceSeccion(button);
                getArguments().remove("viewAdvanceSearch");
            }
        }
    }
}
