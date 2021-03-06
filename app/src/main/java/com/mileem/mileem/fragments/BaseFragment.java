package com.mileem.mileem.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.mileem.mileem.R;
import com.mileem.mileem.activities.BaseActivity;
import com.mileem.mileem.activities.MainActivity;

public abstract class BaseFragment extends Fragment {

    protected final String tag;
    protected ProgressDialog pDialog;


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
        ((BaseActivity) getActivity()).showFragment(fragment,false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_contact).setVisible(false);
        menu.findItem(R.id.action_amenities).setVisible(false);
        menu.findItem(R.id.action_location).setVisible(false);
        menu.findItem(R.id.action_refresh).setVisible(false);
        menu.findItem(R.id.action_sort).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
        menu.findItem(R.id.action_currency).setVisible(false);
        menu.findItem(R.id.action_operation).setVisible(false);
    }

    public abstract String getTittle();

    protected void showPDialog(Context context) {
        showPDialog(context,"Buscando...");
    }

    protected void showPDialog(Context context,String msg) {
        pDialog = new ProgressDialog(context);
        // Showing progress dialog before making http request
        pDialog.setMessage(msg);
        pDialog.show();
    }

    protected void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }


    public boolean hasMenuOption(){
       return false;
    }
}
