package com.mileem.mileem.adapters;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.fragments.MultimediaGalleryFragment;
import com.mileem.mileem.fragments.ResultsFragment;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.networking.AsyncRestHttpClient;

import java.util.ArrayList;

/**
 * Created by Juan-Asus on 09/09/2014.
 */
public class PublicationListAdapter extends ArrayAdapter<PublicationDetails> {
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
            public NetworkImageView networkImageView4;
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

                premiumViewHolder.networkImageView4 = (NetworkImageView) convertView.findViewById(R.id.prop_imagen_3);
                premiumViewHolder.networkImageView3 = (NetworkImageView) convertView.findViewById(R.id.prop_imagen_2);
                premiumViewHolder.networkImageView2 = (NetworkImageView) convertView.findViewById(R.id.prop_imagen_1);
                createBasicHolderBasic(convertView, premiumViewHolder);

                convertView.setTag(premiumViewHolder);

            } else {

                premiumViewHolder = (Holder.PremiumViewHolder) convertView.getTag();

                premiumViewHolder.networkImageView1.setImageUrl(null, imageLoader);
                premiumViewHolder.networkImageView1.setOnClickListener(null);
                premiumViewHolder.networkImageView1.setVisibility(View.INVISIBLE);
                premiumViewHolder.networkImageView2.setImageUrl(null, imageLoader);
                premiumViewHolder.networkImageView2.setOnClickListener(null);
                premiumViewHolder.networkImageView2.setVisibility(View.INVISIBLE);
                premiumViewHolder.networkImageView3.setImageUrl(null, imageLoader);
                premiumViewHolder.networkImageView3.setOnClickListener(null);
                premiumViewHolder.networkImageView3.setVisibility(View.INVISIBLE);
                premiumViewHolder.networkImageView4.setImageUrl(null, imageLoader);
                premiumViewHolder.networkImageView4.setOnClickListener(null);
                premiumViewHolder.networkImageView4.setVisibility(View.INVISIBLE);

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
                basicViewHolder.networkImageView1.setImageUrl(null, imageLoader);
                basicViewHolder.networkImageView1.setOnClickListener(null);
                basicViewHolder.networkImageView1.setVisibility(View.INVISIBLE);
                basicViewHolder.networkImageView2.setImageUrl(null, imageLoader);
                basicViewHolder.networkImageView2.setOnClickListener(null);
                basicViewHolder.networkImageView2.setVisibility(View.INVISIBLE);

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
                freeViewHolder.networkImageView1.setImageUrl(null, imageLoader);
                freeViewHolder.networkImageView1.setOnClickListener(null);
                freeViewHolder.networkImageView1.setVisibility(View.INVISIBLE);

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
        buildImageArea(holder, msg);

        buildTextViewWidget(holder.precioTextView, String.valueOf(msg.getCurrency() + " " + msg.getPrice()));
        buildTextViewWidget(holder.direccionTextView, msg.getAddress() );
        buildTextViewWidget(holder.m2TextView, String.valueOf(msg.getSize())+ " m2" );
        buildTextViewWidget(holder.ambientesTextView, msg.getEnvironment().getName());

        buildLocalImageWidget(holder.arrowImageView,R.drawable.arrow);
    }

    private void buildImageArea(Holder.ListViewHolder holder, PublicationDetails msg) {
        ArrayList<String> pictures = new ArrayList<String>();
        boolean videoAdded = false;
        String videoUrl = null;
        if (msg.isPremium() && msg.getVideo() != null && msg.getVideo().hasVideo()){
            pictures.add(msg.getVideo().getThumbnail());
            videoUrl = msg.getVideo().getUrl();
            videoAdded = true;
        }
        pictures.addAll(msg.getPictures());
        if(pictures.isEmpty()){
            buildFullScreenImageWidget(holder.networkImageView1, R.drawable.image_placeholder);
        } else {
            if(videoAdded){
                buildVideoImageWidget(holder.networkImageView1, pictures.get(0),videoUrl);
            } else {
                buildFullScreenImageWidget(holder.networkImageView1, createUrlForPicture(pictures.get(0)), msg, 0);
            }
            if((msg.isPremium() || msg.isBasic()) && pictures.size() > 1){
                buildFullScreenImageWidget(holder.networkImageView2, createUrlForPicture(pictures.get(1)), msg, 1);
                if(msg.isPremium() && pictures.size() > 2){
                   buildFullScreenImageWidget(holder.networkImageView3, createUrlForPicture(pictures.get(2)), msg, 2);
                    if(pictures.size() > 3){
                        buildFullScreenImageWidget(holder.networkImageView4, createUrlForPicture(pictures.get(3)), msg, 3);
                    }
                }
            }
        }
    }



    private String createUrlForPicture(String pathToImg) {
        return AsyncRestHttpClient.getAbsoluteUrlRelativeToHost(pathToImg);
    }

    private void buildTextViewWidget(View convertView, String txt) {
        TextView textView = (TextView)convertView;
        if(textView != null) textView.setText(txt);
    }

    private NetworkImageView buildImageWidget(View convertView, String url) {
        NetworkImageView networkImageView = (NetworkImageView) convertView;
        if(networkImageView != null){
            networkImageView.setDefaultImageResId(R.drawable.image_placeholder);
            networkImageView.setErrorImageResId(R.drawable.image_placeholder);
            networkImageView.setImageUrl(url,imageLoader);
            networkImageView.setVisibility(View.VISIBLE);
        }
        return networkImageView;
    }

    private NetworkImageView buildImageWidget(View convertView, int image_placeholder) {
        NetworkImageView networkImageView = (NetworkImageView) convertView;
        if(networkImageView != null){
            networkImageView.setDefaultImageResId(R.drawable.image_placeholder);
            networkImageView.setErrorImageResId(R.drawable.image_placeholder);
            networkImageView.setImageResource(image_placeholder);
            networkImageView.setVisibility(View.VISIBLE);
        }
        return networkImageView;
    }

    private void buildLocalImageWidget(View convertView, int imageId) {
        ImageView imageView = (ImageView) convertView;
        if(imageView != null) imageView.setImageResource(imageId);
    }

    private void buildFullScreenImageWidget(View convertView, String url, final PublicationDetails publication, final int position) {
        NetworkImageView networkImageView = buildImageWidget(convertView,url);
        if(networkImageView != null){
            networkImageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MultimediaGalleryFragment gallery = MultimediaGalleryFragment.newInstance(publication, position);
                    ((MainActivity) _c).displayView(gallery, false);
                }
            });
        }
    }

    private void buildFullScreenImageWidget(View convertView, int image_placeholder ) {
        NetworkImageView networkImageView = buildImageWidget(convertView,image_placeholder);
    }



    private void buildVideoImageWidget(final View convertView, String url, final String videoUrl) {
        NetworkImageView networkImageView = buildImageWidget(convertView,url);
        if(networkImageView != null){
            networkImageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(videoUrl != null ){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                        getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}
