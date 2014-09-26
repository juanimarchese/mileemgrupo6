package com.mileem.mileem.adapters;

import android.app.Fragment;
import android.app.FragmentManager;

import com.mileem.mileem.fragments.MultimediaPageFragment;
import com.mileem.mileem.models.Multimedia;
import com.mileem.mileem.models.PublicationDetails;

import java.util.ArrayList;

public class MultimediaSlidePagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {

    private ArrayList<Multimedia> _data;

    public MultimediaSlidePagerAdapter(FragmentManager fragmentManager, ArrayList<Multimedia> _data) {
        super(fragmentManager);
        this._data = _data;
    }

    @Override
    public Fragment getItem(int position) {
        Multimedia multimedia = _data.get(position);
        return new MultimediaPageFragment(multimedia);
    }

    @Override
    public int getCount() {
        return _data.size();
    }
}