package com.mileem.mileem.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mileem.mileem.R;
import com.mileem.mileem.adapters.NavDrawerListAdapter;
import com.mileem.mileem.fragments.AvPriceReportFragment;
import com.mileem.mileem.fragments.BarReportFragment;
import com.mileem.mileem.fragments.BaseFragment;
import com.mileem.mileem.fragments.NoResultsFragment;
import com.mileem.mileem.fragments.PieReportFragment;
import com.mileem.mileem.fragments.SearchFragment;
import com.mileem.mileem.managers.DefinitionsManager;
import com.mileem.mileem.models.IdName;
import com.mileem.mileem.utils.DefinitionsUtils;
import com.mileem.mileem.widgets.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {


    // True if this activity instance is a search result view (used on pre-HC devices that load
    // search results in a separate instance of the activity rather than loading results in-line
    // as the query is typed.
    private boolean isSearchResultView = false;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    public enum ReportType {
        AROUND_PRICE_M2,AVERAGE_PRICE,PROPERTIES_PERCENT;
    }

    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.main_activity);

        // getActionBar().setDisplayHomeAsUpEnabled(false);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Buscar
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        //Reports
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayViewForMenu(0,false);
        }

    }

    public void displayViewForMenu(int position,boolean isBack) {
        displayViewForMenu(position,new Bundle(),false);
    }

    public void displayFragment(BaseFragment fragment, int position, Bundle arguments, boolean isBack) {
        if (fragment != null) {
           /* FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();*/
            fragment.setArguments(arguments);
            showFragment(fragment,isBack);

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void showNeighborhoodsAndDisplayReport(final ReportType type) {
        List list = DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getNeighborhoods());
        final ArrayAdapter dataAdapter = new ArrayAdapter(getContext(), R.layout.spinner_item_dark, list);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige un barrio");
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String neighboorName = (String) dataAdapter.getItem(which);
                IdName neighboor = DefinitionsUtils.findIdNameByName(DefinitionsManager.getInstance().getNeighborhoods(), neighboorName);
                BaseFragment fragment = null;
                int position = 0;
                if(ReportType.AROUND_PRICE_M2.equals(type)){
                    fragment = BarReportFragment.newInstance(neighboor);
                    position = 1;
                } else if (ReportType.PROPERTIES_PERCENT.equals(type)){
                    fragment = PieReportFragment.newInstance(neighboor);
                    position = 2;
                } else if (ReportType.AVERAGE_PRICE.equals(type)){
                    fragment = AvPriceReportFragment.newInstance(neighboor);
                    position = 3;
                }
                if(fragment != null){
                    MainActivity.this.displayFragment(fragment, position, fragment.getArguments(), false);
                }
            }
        });
        builder.show();
    }


    public void displayViewForMenu(int position,Bundle arguments,boolean isBack) {
        // update the main content by replacing fragments
        switch (position) {
            case 0:
                this.displayFragment(new SearchFragment(), position, arguments, isBack);
                break;
            case 1:
                this.showNeighborhoodsAndDisplayReport(ReportType.AROUND_PRICE_M2);
                break;
            case 2:
                this.showNeighborhoodsAndDisplayReport(ReportType.PROPERTIES_PERCENT);
                break;
            case 3:
                this.showNeighborhoodsAndDisplayReport(ReportType.AVERAGE_PRICE);
                break;
            default:
                break;
        }
    }

    public void displayView(BaseFragment fragment,boolean isBack) {
        Bundle bundle = null;
        if (fragment.getArguments() != null)
            bundle = fragment.getArguments();
        else
            bundle = new Bundle();
        displayView(fragment, bundle, isBack);
    }

    public void displayView(BaseFragment fragment,Bundle arguments,boolean isBack) {
        // update the main content by replacing fragments
        if (fragment != null) {
            /*FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();*/
            fragment.setArguments(arguments);
            showFragment(fragment,isBack);

            // update selected item and title, then close the drawer
            setTitle(fragment.getTittle());
            if(mDrawerLayout.isDrawerOpen(mDrawerList))
                mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onBackPressed() {
        if(this.getCurrentFragment().hasMenuOption()){
            super.onBackPressed();
            return;
        }
        BaseFragment previousFragment = this.getPreviousFragment();
        while (previousFragment.getCustomTag().equals(getCurrentFragment().getCustomTag())){
            previousFragment = this.getPreviousFragment();
        }
        if(this.getCurrentFragment().getCustomTag().equals(NoResultsFragment.TAG)){
            previousFragment = this.getPreviousFragment();
        }
        if(previousFragment != null){
            try {
                displayView(previousFragment.getClass().newInstance(),true);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSearchRequested() {
        // Don't allow another search if this activity instance is already showing
        // search results. Only used pre-HC.
        return !isSearchResultView && super.onSearchRequested();
    }


    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayViewForMenu(position,false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
