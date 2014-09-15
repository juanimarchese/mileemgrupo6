package com.mileem.mileem.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.mileem.mileem.R;
import com.mileem.mileem.fragments.BaseFragment;

public abstract class BaseActivity extends Activity {

    private BaseFragment currentFragment;
    private BaseFragment previousFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void showFragment(BaseFragment fragment) {

        if (fragment != null) {
            FragmentManager fm = getFragmentManager();
            final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            final Fragment fragmentByTag = fm.findFragmentByTag(fragment.getCustomTag());

            if (currentFragment != null && currentFragment.getCustomTag().equals(fragment.getCustomTag()))
                return;

            if (currentFragment != null) {

                Fragment f = fm.findFragmentByTag(currentFragment.getCustomTag());
                final InputMethodManager imm = (InputMethodManager) f.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(f.getView().getWindowToken(), 0);

                fragmentTransaction.hide(fm.findFragmentByTag(currentFragment.getCustomTag()));
            }
            if (fragmentByTag == null) {
               fragmentTransaction.add(R.id.frame_container, fragment, fragment.getCustomTag());
            } else {
                if (fragmentByTag.isHidden()) {
                    fragmentTransaction.show(fragmentByTag);
                }
            }

            fragmentTransaction.commit();
            previousFragment = currentFragment;
            currentFragment = fragment;
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    public BaseFragment getPreviousFragment() {
        return previousFragment;
    }
}
