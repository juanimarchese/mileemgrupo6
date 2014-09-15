package com.mileem.mileem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
public class PublicationListAdapter extends ArrayAdapter<PublicationDetails> {
    private ArrayList<PublicationDetails> _data;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Context _c;
    private int numberOfPicturesToShow = 1;


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
            if (msg.getPriority() == 0){
                convertView = inflater.inflate(R.layout.publication_premium_list_item, null);
                numberOfPicturesToShow = 3;
            } else if (msg.getPriority() == 1){
                convertView = inflater.inflate(R.layout.publication_basic_list_item, null);
                numberOfPicturesToShow = 2;
            } else {
                convertView = inflater.inflate(R.layout.publication_free_list_item, null);
                numberOfPicturesToShow = 1;
            }
        }


        buildCommonWidgets(convertView, msg);

        return convertView;
    }

    private void buildCommonWidgets(View convertView, PublicationDetails msg) {
        ArrayList<String> pictures = msg.getPictures();
        pictures.clear();
        pictures.add("http://2.bp.blogspot.com/-C5MH8XTbWiM/VBEDb0Qd8TI/AAAAAAAACRY/H0oy_QkjGyQ/s1600/project-contemporary-house-design-1.jpg");
        if (pictures.size() > 0) {
            buildFullScreenImageWidget(convertView, R.id.prop_imagen, pictures.get(0));
            if(numberOfPicturesToShow > 1 && pictures.size() > 1){
                //buildFullScreenImageWidget(convertView, R.id.prop_imagen_1, pictures.get(1));
                if(numberOfPicturesToShow > 2 && pictures.size() > 2){
                   // buildFullScreenImageWidget(convertView, R.id.prop_imagen_2, pictures.get(2));
                }
            }
        }


        buildTextViewWidget(convertView,R.id.prop_precio, String.valueOf(msg.getPrice() + " " + msg.getCurrency()));
        buildTextViewWidget(convertView,R.id.prop_direccion, msg.getAddress());
        buildTextViewWidget(convertView,R.id.prop_m2, String.valueOf(msg.getCoveredSize() + msg.getSize())+ " m2" );
        buildTextViewWidget(convertView,R.id.prop_ambientes, msg.getEnvironment().getName());

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
