package com.mileem.mileem.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import com.mileem.mileem.R;
import com.mileem.mileem.activities.BaseActivity;

public abstract class BaseFragment extends Fragment {

    private final String tag;

    public void BaseFragment() {
    }

    protected BaseFragment(String aTag) {
        tag = aTag;
    }

    public String getFragmentTag() {
        return tag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        }

    public String getCustomTag() {
        return tag;
    }

    public void showFragment(BaseFragment fragment){
        ((BaseActivity) getActivity()).showFragment(fragment);
    }


    public abstract String getTittle();
}
