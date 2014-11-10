package com.mileem.mileem.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import com.mileem.mileem.fragments.MultimediaPageFragment;
import com.mileem.mileem.models.Multimedia;
import com.mileem.mileem.models.PublicationDetails;

import java.util.ArrayList;

public class MultimediaSlidePagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {

    private ArrayList<Multimedia> _data;
    private ImageView.ScaleType scaleType;
    private View.OnClickListener onClickListener;

    public MultimediaSlidePagerAdapter(FragmentManager fragmentManager, ArrayList<Multimedia> _data, ImageView.ScaleType scaleType) {
        
        super(fragmentManager);
        this._data = _data;
        this.scaleType = scaleType;
    }

    @Override
    public MultimediaPageFragment getItem(int position) {
        Multimedia multimedia = _data.get(position);
        MultimediaPageFragment page = new MultimediaPageFragment(_data, multimedia);
        page.setOnClickListener(onClickListener);
        page.setScaleType(scaleType);
        return page;
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setData(ArrayList<Multimedia> data) {
        this._data = data;
    }
}