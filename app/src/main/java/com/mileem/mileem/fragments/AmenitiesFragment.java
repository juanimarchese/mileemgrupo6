package com.mileem.mileem.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mileem.mileem.R;
import com.mileem.mileem.managers.DefinitionsManager;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.utils.DefinitionsUtils;

import java.util.ArrayList;


public class AmenitiesFragment extends BaseFragment {

    public static final String TAG = AmenitiesFragment.class.getSimpleName();
    private static final int ELEMENTS_BY_ROW = 2;

    public AmenitiesFragment() {
        super(TAG);
    }

    public static AmenitiesFragment newInstance(PublicationDetails publicationDetails) {
        AmenitiesFragment myFragment = new AmenitiesFragment();

        Bundle args = new Bundle();
        ArrayList<String> publicationAmenities = (ArrayList<String>) DefinitionsUtils.convertToStringList(publicationDetails.getAmenitieType());
        ArrayList<String> otherAmmenities = (ArrayList<String>) DefinitionsUtils.convertToStringList(DefinitionsManager.getInstance().getAmenitieTypes());
        otherAmmenities.removeAll(publicationAmenities);

        args.putStringArrayList("choosenAmmenities", publicationAmenities);
        args.putStringArrayList("nonChoosenAmmenities", otherAmmenities);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.amenities_fragment, container, false);
        TableLayout stk = (TableLayout) rootView.findViewById(R.id.table_main);
        init(rootView,stk);
        return rootView;
    }

    public void init(View rootView, TableLayout stk) {
        ArrayList<String> choosenAmmenities = getArguments().getStringArrayList("choosenAmmenities");
        ArrayList<String> nonChoosenAmmenities = getArguments().getStringArrayList("nonChoosenAmmenities");
        buildAmenities(stk, choosenAmmenities,true,true);
        buildAmenities(stk, nonChoosenAmmenities,false,false);

    }

    private void buildAmenities(TableLayout stk, ArrayList<String> ammenities,boolean isBold, final boolean isChecked) {
        int elementsRenderedByRow = 0;
        TableRow tbrow = new TableRow(getActivity());
        for (int i = 0; i < ammenities.size(); i++) {
            if (elementsRenderedByRow == ELEMENTS_BY_ROW){
                elementsRenderedByRow = 0;
                stk.addView(tbrow);
                tbrow = new TableRow(getActivity());
            }
            final CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setChecked(isChecked);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(isChecked);
                }
            });
            tbrow.addView(checkBox);
            TextView t1v = new TextView(getActivity());
            t1v.setText(ammenities.get(i));
            t1v.setGravity(Gravity.LEFT);
            if(isBold){
                t1v.setTextColor(Color.BLACK);
                t1v.setTypeface(null, Typeface.BOLD);
            } else {
                t1v.setTextColor(Color.DKGRAY);
            }
            tbrow.addView(t1v);
            elementsRenderedByRow++;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            TableLayout stk = (TableLayout) getView().findViewById(R.id.table_main);
            stk.removeAllViewsInLayout();
            init(getView(), stk);
        }
    }

    @Override
    public String getTittle() {
        return "Amenities";
    }
}
