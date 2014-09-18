package com.mileem.mileem.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import com.mileem.mileem.R;
import com.mileem.mileem.activities.BaseActivity;

public abstract class BaseFragment extends Fragment {

    protected final String tag;

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
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_sort).setVisible(false);
        menu.findItem(R.id.action_refresh).setVisible(false);
    }

    public abstract String getTittle();
}
