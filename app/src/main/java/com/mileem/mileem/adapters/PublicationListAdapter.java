package com.mileem.mileem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mileem.mileem.AppController;
import com.mileem.mileem.R;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.models.TipoPublicacion;

import java.util.ArrayList;

/**
 * Created by Juan-Asus on 09/09/2014.
 */
public class PublicationListAdapter extends ArrayAdapter<PublicationDetails> {
    private ArrayList<PublicationDetails> _data;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Context _c;


    public PublicationListAdapter(ArrayList<PublicationDetails> _data, Context _c) {
        super(_c,R.layout.publication_free_list_item, _data);
        this._data = _data;
        this._c = _c;
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public PublicationDetails getItem(int position) {
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
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        PublicationDetails msg = _data.get(position);

        if (convertView == null){
            if (TipoPublicacion.PREMIUM.equals(msg.getTipo())){
                convertView = inflater.inflate(R.layout.publication_premium_list_item, null);
            } else if (TipoPublicacion.BASIC.equals(msg.getTipo())){
                convertView = inflater.inflate(R.layout.publication_basic_list_item, null);
            } else {
                convertView = inflater.inflate(R.layout.publication_free_list_item, null);
            }
        }


        buildCommonWidgets(convertView, msg);

        return convertView;
    }

    private void buildCommonWidgets(View convertView, PublicationDetails msg) {
        buildFullScreenImageWidget(convertView, R.id.prop_imagen, msg.getThumbnailUrl());
        buildTextViewWidget(convertView,R.id.prop_precio, msg.getPrecio());
        buildTextViewWidget(convertView,R.id.prop_direccion, msg.getDireccion());
        buildTextViewWidget(convertView,R.id.prop_m2, msg.getM2());
        buildTextViewWidget(convertView,R.id.prop_ambientes, msg.getCantAmbientes());
        buildLocalImageWidget(convertView,R.id.arrow,R.drawable.arrow);
    }

    private void buildTextViewWidget(View convertView, int widgetId, String txt) {
        TextView textView = (TextView)convertView.findViewById(widgetId);
        if(textView != null) textView.setText(txt);
    }

    private NetworkImageView buildImageWidget(View convertView, int widgetId, String url) {
        NetworkImageView networkImageView = (NetworkImageView) convertView.findViewById(widgetId);
        if(networkImageView != null) networkImageView.setImageUrl(url,imageLoader);
        return networkImageView;
    }

    private void buildLocalImageWidget(View convertView, int widgetId, int imageId) {
        ImageView imageView = (ImageView) convertView.findViewById(widgetId);
        if(imageView != null) imageView.setImageResource(imageId);
    }

    private void buildFullScreenImageWidget(View convertView, int widgetId, String url) {
        NetworkImageView networkImageView = buildImageWidget(convertView,widgetId,url);
        if(networkImageView != null){
            networkImageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Imagen Maximixada", Toast.LENGTH_LONG).show();
                }
            });
            }
    }
}
