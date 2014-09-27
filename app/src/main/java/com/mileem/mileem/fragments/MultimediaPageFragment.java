package com.mileem.mileem.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mileem.mileem.AppController;
import com.mileem.mileem.R;
import com.mileem.mileem.models.Multimedia;

/**
 * Created by ramirodiaz on 25/09/14.
 */
public class MultimediaPageFragment extends Fragment {
    private final Multimedia multimedia;
    private NetworkImageView networkImageView;
    private ImageLoader imageLoader;
    private ImageView icon;

    public MultimediaPageFragment(Multimedia multimedia) {
        this.multimedia = multimedia;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.multimedia_item, container, false);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        icon = (ImageView) rootView.findViewById(R.id.icon);
        icon.setBackgroundColor(Color.rgb(255, 255, 255));

        int drawable = this.multimedia.getType() == Multimedia.Type.IMAGE ? R.drawable.image_icon : R.drawable.video_icon;
        icon.setImageResource(drawable);

        networkImageView = (NetworkImageView) rootView.findViewById(R.id.multimedia);
        networkImageView.setDefaultImageResId(R.drawable.image_placeholder);
        networkImageView.setErrorImageResId(R.drawable.image_placeholder);
        networkImageView.setImageUrl(multimedia.getPreviewUrl(), imageLoader);

        networkImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String type = multimedia.getType().toString();

                if (multimedia.getType() == Multimedia.Type.VIDEO) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://player.vimeo.com/video/" + "106827149")));


                    VideoFragment f = VideoFragment.newInstance("XeR0qo0aoXA");
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction tx = fm.beginTransaction();
                    tx.add(R.id.frame_container, f).addToBackStack( "addToBackStack" ).commit();

                }
                Toast.makeText(v.getContext(), "Multimedia. Tipo: " + type, Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }

}