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
import com.mileem.mileem.fragments.MultimediaGalleryFragment;

import java.util.Stack;

public abstract class BaseActivity extends Activity {

    private BaseFragment currentFragment;
    private Stack<BaseFragment> previousFragment = new Stack<BaseFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void showFragment(BaseFragment fragment,boolean isBackAction) {

        if (fragment != null) {
            FragmentManager fm = getFragmentManager();
            final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            final Fragment fragmentByTag = fm.findFragmentByTag(fragment.getCustomTag());

            if (currentFragment != null && currentFragment.getCustomTag().equals(fragment.getCustomTag()) && currentFragment.getArguments().equals(fragment.getArguments()))
                return;

            if (currentFragment != null) {
                BaseFragment f = (BaseFragment)fm.findFragmentByTag(currentFragment.getCustomTag());
                final InputMethodManager imm = (InputMethodManager) f.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(f.getView().getWindowToken(), 0);
                if (f.getCustomTag().equalsIgnoreCase(MultimediaGalleryFragment.TAG)) {
                    fragmentTransaction.remove(f);
                } else {
                    fragmentTransaction.hide(f);
                }

            }
            if (fragmentByTag == null) {
               fragmentTransaction.add(R.id.frame_container, fragment, fragment.getCustomTag());

            } else {
                if (fragmentByTag.isHidden()) {
                    //Se copian los argumentos de los fragments en caso de que exista, pero hayan cambiado
                    if(fragment.getArguments() != null && !fragment.getArguments().isEmpty())
                        fragmentByTag.getArguments().putAll(fragment.getArguments());
                    fragmentTransaction.show(fragmentByTag);
                } else if(currentFragment.getCustomTag().equals(fragment.getCustomTag()) && !currentFragment.getArguments().equals(fragment.getArguments())) {
                    if(fragment.getArguments() != null && !fragment.getArguments().isEmpty())
                        fragmentByTag.getArguments().putAll(fragment.getArguments());
                    fragmentTransaction.hide(fragmentByTag);
                    fragmentTransaction.show(fragmentByTag);

                }
            }

            fragmentTransaction.commit();
            if(!isBackAction)
                previousFragment.push(currentFragment);

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
        if(!previousFragment.isEmpty())
            return previousFragment.pop();
        return null;
    }
}
