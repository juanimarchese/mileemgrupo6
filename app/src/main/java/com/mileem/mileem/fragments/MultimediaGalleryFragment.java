package com.mileem.mileem.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.adapters.MultimediaSlidePagerAdapter;
import com.mileem.mileem.models.Multimedia;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.widgets.SmartViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by ramirodiaz on 9/11/14.
 */
public class MultimediaGalleryFragment extends BaseFragment{
    private SmartViewPager mPager;
    private View rootView;
    public static final String TAG = MultimediaGalleryFragment.class.getSimpleName();

    public MultimediaGalleryFragment() {
        super(TAG);
    }

    public static MultimediaGalleryFragment newInstance(PublicationDetails publication, int position) {
        MultimediaGalleryFragment myFragment = new MultimediaGalleryFragment();
        Bundle args = new Bundle();
        args.putInt("initialPosition", position);
        args.putParcelable("publication", publication);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.multimedia_gallery_fragment, container, false);
        this.rootView = rootView;
        this.buildGallery();
        return rootView;
    }

    private void buildGallery() {
        PublicationDetails publication = getArguments().getParcelable("publication");
        int position = getArguments().getInt("initialPosition");
        mPager = (SmartViewPager) rootView.findViewById(R.id.full_pager);
        final MultimediaSlidePagerAdapter pagerAdapter = new MultimediaSlidePagerAdapter(this.getActivity().getFragmentManager(), publication.getMultimediaData() , ImageView.ScaleType.FIT_CENTER);
        mPager.setAdapter(pagerAdapter);
        mPager.setCurrentItem(position);
        pagerAdapter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int pageNumber = mPager.getCurrentItem();
                MultimediaPageFragment fragment = pagerAdapter.getItem(pageNumber);
                Multimedia multimedia = fragment.getMultimedia();
                if (multimedia.getType() == Multimedia.Type.VIDEO) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(multimedia.getUrl()));
                    startActivity(intent);
                }
            }
        });
        CirclePageIndicator titleIndicator = (CirclePageIndicator)rootView.findViewById(R.id.pager_indicator);
        titleIndicator.setViewPager(mPager);
    }

    @Override
    public String getTittle() {
        return "Galería multimedia";
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            this.buildGallery();
        }
    }
}
