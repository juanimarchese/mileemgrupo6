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
    private static final String BASE_URL = "http://107.20.253.197";
    private static final int VIEW_TYPE_ROW_1 = 1;
    private static final int VIEW_TYPE_ROW_2 = 2;
    private static final int VIEW_TYPE_ROW_3 = 3;
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
    public int getItemViewType(int position) {
        PublicationDetails msg = getItem(position);

        if (msg.isPremium()) {
            return VIEW_TYPE_ROW_1;
        } else if (msg.isBasic()) {
            return VIEW_TYPE_ROW_2;
        } else {
            return VIEW_TYPE_ROW_3;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class Holder{

        public abstract class ListViewHolder {
            public NetworkImageView networkImageView3;
            public NetworkImageView networkImageView2;
            public NetworkImageView networkImageView1;
            public TextView precioTextView;
            public TextView direccionTextView;
            public TextView m2TextView;
            public TextView ambientesTextView;
            public ImageView arrowImageView;
        }
        public class PremiumViewHolder extends ListViewHolder{

        }

        public class BasicViewHolder extends ListViewHolder{
        }

        public class FreeViewHolder extends ListViewHolder{
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        Holder.PremiumViewHolder premiumViewHolder = null;

        Holder.BasicViewHolder basicViewHolder = null;

        Holder.FreeViewHolder freeViewHolder = null;

        int type = getItemViewType(position);

        if (type == VIEW_TYPE_ROW_1) {

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.publication_premium_list_item, null);

                premiumViewHolder = new Holder().new PremiumViewHolder();

                premiumViewHolder.networkImageView3 = (NetworkImageView) convertView.findViewById(R.id.prop_imagen_2);
                premiumViewHolder.networkImageView2 = (NetworkImageView) convertView.findViewById(R.id.prop_imagen_1);
                createBasicHolderBasic(convertView, premiumViewHolder);

                convertView.setTag(premiumViewHolder);

            } else {

                premiumViewHolder = (Holder.PremiumViewHolder) convertView.getTag();

            }


            // Set holder elements values

            buildCommonWidgets(premiumViewHolder, getItem(position));


        } else if (type == VIEW_TYPE_ROW_2) {

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.publication_basic_list_item, null);

                basicViewHolder = new Holder().new BasicViewHolder();

                basicViewHolder.networkImageView2 = (NetworkImageView) convertView.findViewById(R.id.prop_imagen_1);
                createBasicHolderBasic(convertView, basicViewHolder);

                convertView.setTag(basicViewHolder);

            } else {

                basicViewHolder = (Holder.BasicViewHolder) convertView.getTag();

            }


            buildCommonWidgets(basicViewHolder, getItem(position));

        } else  {

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.publication_free_list_item, null);

                freeViewHolder = new Holder().new FreeViewHolder();

                createBasicHolderBasic(convertView, freeViewHolder);

                convertView.setTag(freeViewHolder);

            } else {

                freeViewHolder = (Holder.FreeViewHolder) convertView.getTag();

            }

            buildCommonWidgets(freeViewHolder, getItem(position));

        }

        return convertView;
    }


    private void createBasicHolderBasic(View convertView, Holder.ListViewHolder listViewHolder) {
        listViewHolder.networkImageView1 = (NetworkImageView) convertView.findViewById(R.id.prop_imagen);
        listViewHolder.precioTextView = (TextView)convertView.findViewById(R.id.prop_precio);
        listViewHolder.direccionTextView = (TextView)convertView.findViewById(R.id.prop_direccion);
        listViewHolder.m2TextView = (TextView)convertView.findViewById(R.id.prop_m2);
        listViewHolder.ambientesTextView = (TextView)convertView.findViewById(R.id.prop_ambientes);
        listViewHolder.arrowImageView = (ImageView) convertView.findViewById(R.id.arrow);
    }

    private void buildCommonWidgets(Holder.ListViewHolder holder, PublicationDetails msg) {
        ArrayList<String> pictures = msg.getPictures();
        if(pictures.isEmpty()) pictures.add("/assets/img/nophoto.jpg");
        if (pictures.size() > 0) {
            buildFullScreenImageWidget(holder.networkImageView1, createUrlForPicture(pictures.get(0)));
            if((msg.isPremium() || msg.isBasic()) && pictures.size() > 1){
                buildFullScreenImageWidget(holder.networkImageView2, createUrlForPicture(pictures.get(1)));
                if(msg.isPremium() && pictures.size() > 2){
                   buildFullScreenImageWidget(holder.networkImageView3, createUrlForPicture(pictures.get(2)));
                }
            }
        }


        buildTextViewWidget(holder.precioTextView, String.valueOf(msg.getCurrency() + " " + msg.getPrice()));
        buildTextViewWidget(holder.direccionTextView, msg.getAddress() );
        buildTextViewWidget(holder.m2TextView, String.valueOf(msg.getCoveredSize() + msg.getSize())+ " m2" );
        buildTextViewWidget(holder.ambientesTextView, msg.getEnvironment().getName());

        buildLocalImageWidget(holder.arrowImageView,R.drawable.arrow);
    }

    private String createUrlForPicture(String pathToImg) {
        return BASE_URL + pathToImg;
    }

    private void buildTextViewWidget(View convertView, String txt) {
        TextView textView = (TextView)convertView;
        if(textView != null) textView.setText(txt);
    }

    private NetworkImageView buildImageWidget(View convertView, String url) {
        NetworkImageView networkImageView = (NetworkImageView) convertView;
        if(networkImageView != null) networkImageView.setImageUrl(url,imageLoader);
        return networkImageView;
    }

    private void buildLocalImageWidget(View convertView, int imageId) {
        ImageView imageView = (ImageView) convertView;
        if(imageView != null) imageView.setImageResource(imageId);
    }

    private void buildFullScreenImageWidget(View convertView, String url) {
        NetworkImageView networkImageView = buildImageWidget(convertView,url);
        if(networkImageView != null){
            networkImageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Imagen Maximixada", Toast.LENGTH_LONG).show();
                }
            });
            }
    }
}
