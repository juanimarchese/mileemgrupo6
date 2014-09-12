package com.mileem.mileem.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mileem.mileem.AppController;
import com.mileem.mileem.R;
import com.mileem.mileem.models.PublicationDetails;

import java.util.ArrayList;

/**
 * Created by Juan-Asus on 09/09/2014.
 */
public class PublicationListAdapter extends BaseAdapter {
    private ArrayList<PublicationDetails> _data;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Context _c;

    public PublicationListAdapter(ArrayList<PublicationDetails> _data, Context _c) {
        this._data = _data;
        this._c = _c;
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int position) {
        return _data.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.publication_list_item, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView image = (NetworkImageView) convertView.findViewById(R.id.prop_imagen);
        TextView precio = (TextView)convertView.findViewById(R.id.prop_precio);
        TextView direccion = (TextView)convertView.findViewById(R.id.prop_direccion);
        TextView m2 = (TextView)convertView.findViewById(R.id.prop_m2);
        TextView ambientes = (TextView)convertView.findViewById(R.id.prop_ambientes);

        PublicationDetails msg = _data.get(position);
        image.setImageUrl(msg.getThumbnailUrl(),imageLoader);
        precio.setText(msg.getPrecio());
        direccion.setText("Dirección: "+msg.getDireccion());
        m2.setText(msg.getM2());
        ambientes.setText(msg.getCantAmbientes());

        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Imagen Maximixada", Toast.LENGTH_LONG).show();
            }
        });

         return convertView;
    }
}
