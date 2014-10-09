package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.managers.DefinitionsManager;
import com.mileem.mileem.models.IdName;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.models.User;
import com.mileem.mileem.utils.DefinitionsUtils;

import java.util.ArrayList;
import java.util.List;


public class ContactFragment extends BaseFragment {

    public static final String TAG = ContactFragment.class.getSimpleName();

    //private Spinner horarioDeContacto;


    public ContactFragment() {
        super(TAG);
    }

    public static ContactFragment newInstance(PublicationDetails publicationDetails) {
        ContactFragment myFragment = new ContactFragment();

        Bundle args = new Bundle();
        args.putInt("publicationId", publicationDetails.getId());
        args.putString("publicationAddress", publicationDetails.getAddress());
        args.putString("publicationNeigthboorhood", publicationDetails.getNeighborhood().getName());
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.email_fragment, container, false);
       // addItemsToHorarioSpinner(rootView);
        setTextViewText(rootView,R.id.numeroPublicacion,"Publicación n° " + getArguments().getInt("publicationId"));
        setTextViewText(rootView,R.id.direccion,getArguments().getString("publicationAddress"));
        setTextViewText(rootView,R.id.barrio,getArguments().getString("publicationNeigthboorhood"));
        return rootView;
    }

    private void setTextViewText(View rootView, int widgetId, String text) {
        TextView view = (TextView) rootView.findViewById(widgetId);
        view.setText(text);
    }

  /*  private void addItemsToHorarioSpinner(View rootView) {
        horarioDeContacto = (Spinner) rootView.findViewById(R.id.tipo_propiedad);
        List list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getPropertyTypesCollection(),"Todos");
        ArrayAdapter dataAdapter = new ArrayAdapter(rootView.getContext(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        horarioDeContacto.setAdapter(dataAdapter);
    }
*/

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button button = (Button) view.findViewById(R.id.button_enviar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Bundle arguments = new Bundle();
               boolean isValid = true;

               if (isValid)
                   Toast.makeText(getActivity(),"Envio de mail!",Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public String getTittle() {
        return "E-Mail";
    }
}
