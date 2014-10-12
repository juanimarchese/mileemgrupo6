package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.util.Patterns;
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
import com.mileem.mileem.networking.SendMessageDataManager;
import com.mileem.mileem.utils.DefinitionsUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ContactFragment extends BaseFragment {

    public static final String TAG = ContactFragment.class.getSimpleName();

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
        setTextViewText(rootView,R.id.numeroPublicacion,"Publicación n° " + getArguments().getInt("publicationId"));
        setTextViewText(rootView,R.id.direccion,getArguments().getString("publicationAddress"));
        setTextViewText(rootView,R.id.barrio,getArguments().getString("publicationNeigthboorhood"));
        return rootView;
    }

    private void setTextViewText(View rootView, int widgetId, String text) {
        TextView view = (TextView) rootView.findViewById(widgetId);
        view.setText(text);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button button = (Button) view.findViewById(R.id.button_enviar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Bundle arguments = new Bundle();
               boolean isValid = true;

                EditText mailView = (EditText) getView().findViewById(R.id.email);
                String email = mailView.getText().toString();
                if(email == null || email.isEmpty()){
                    isValid = false;
                    mailView.setError("Debe indicar un email");
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    isValid = false;
                    mailView.setError("Debe indicar un email válido");
                }

                EditText telView = (EditText) getView().findViewById(R.id.telefono);
                String tel = telView.getText().toString();
                if(tel != null && !tel.isEmpty() && !Patterns.PHONE.matcher(tel).matches()){
                    isValid = false;
                    telView.setError("Debe indicar un teléfono válido");
                }

                EditText msgView = (EditText) getView().findViewById(R.id.mensaje);
                String msg = msgView.getText().toString();
                if(msg == null || msg.isEmpty()){
                    isValid = false;
                    msgView.setError("Debe indicar un mensaje");
                }
               if (isValid) {
                   Toast.makeText(getActivity(), "El formulario está siendo enviado...", Toast.LENGTH_LONG).show();
                   EditText contactTimeInfo = (EditText) getView().findViewById(R.id.hsRespuesta);

                   int publicationId = getArguments().getInt("publicationId");
                   try {
                       new SendMessageDataManager().sendMessage(publicationId, email, tel, contactTimeInfo.getText().toString(), msg, new SendMessageDataManager.SendMessageDataManagerCallbackHandler() {
                           @Override
                           public void onComplete() {
                               Toast.makeText(getActivity(), "El formulario fue enviado correctamente", Toast.LENGTH_LONG).show();
                           }
                           @Override
                           public void onFailure(Error error) {
                               Toast.makeText(getActivity(), "El formulario no fue enviado correctamente", Toast.LENGTH_LONG).show();
                           }
                       });
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            setTextViewText(getView(),R.id.numeroPublicacion,"Publicación n° " + getArguments().getInt("publicationId"));
            setTextViewText(getView(),R.id.direccion,getArguments().getString("publicationAddress"));
            setTextViewText(getView(),R.id.barrio,getArguments().getString("publicationNeigthboorhood"));
        }
    }

    @Override
    public String getTittle() {
        return "E-Mail";
    }
}
