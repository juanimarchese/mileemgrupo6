package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mileem.mileem.AppController;
import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.models.IdName;
import com.mileem.mileem.networking.ReportDataManager;

/**
 * Created by JuanMarchese on 18/09/2014.
 */
public class AvPriceReportFragment extends BaseFragment {

    public static final String TAG = AvPriceReportFragment.class.getSimpleName();
    private View rootView;
    TextView reportLabel;
    TextView priceLabel;


    public AvPriceReportFragment() {
        super(TAG);
    }

    public static AvPriceReportFragment newInstance(IdName neighborhood) {
        AvPriceReportFragment myFragment = new AvPriceReportFragment();

        Bundle args = new Bundle();
        args.putInt("neighborhoodId", neighborhood.getId());
        args.putString("neighborhoodName", neighborhood.getName());
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public String getTittle() {
        return "Precio Promedio";
    }

    public int getNeighborhoodId(){
        return getArguments().getInt("neighborhoodId");
    }

    public String getNeighborhoodName(){
        return getArguments().getString("neighborhoodName");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            buildReportLabel(rootView);
            requestBarReportData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.av_price_report_fragment, container, false);
        this.rootView = rootView;
        buildReportLabel(rootView);
        buildPriceLabel(rootView);
        requestBarReportData();
        return rootView;
    }


    private void buildReportLabel(View rootView) {
        reportLabel = (TextView) rootView.findViewById(R.id.reportLabel);
    }

    private void buildPriceLabel(View rootView) {
        priceLabel = (TextView) rootView.findViewById(R.id.priceLabel);
    }

    private void requestBarReportData(final String currency) {
        showPDialog(rootView.getContext());
        try {
            reportLabel.setText("El siguiente valor representa el precio promedio del metro cuadrado correspondiente al barrio: " + getNeighborhoodName() + " en "+currency+". Los datos utilizados son obtenidos de las publicaciones realizadas en esta aplicación.");
            DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels - reportLabel.getHeight() - 30;
            new ReportDataManager().getReportAveragePricePerSquareMeterNeighborhood(getNeighborhoodId(), currency, width, height, new ReportDataManager.ReportDataManagerCallbackHandler() {
                @Override
                public void onComplete(String neighborhoodName, String graphUrl) {
                    showError();
                    hidePDialog();
                }

                @Override
                public void onComplete(String neighborhoodName, String currency, String price) {
                    priceLabel.setText(currency + " " + price);
                    hidePDialog();
                }

                @Override
                public void onFailure(Error error) {
                    showError(error.getCause().getMessage());
                    hidePDialog();
                }


            });
        } catch (Throwable e) {
            showError();
            hidePDialog();
        }
    }

    private void requestBarReportData() {
        requestBarReportData("$");
    }

    private void showError() {
        showError("");
    }

    private void showError(String errorMsg) {
        Toast.makeText(getActivity(), "Error al tratar de obtener el reporte del barrio " + getNeighborhoodName() + ". " + errorMsg, Toast.LENGTH_LONG).show();
        ((MainActivity) rootView.getContext()).displayView(new ResultsFragment(),true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem currencyItem = menu.findItem(R.id.action_currency);
        currencyItem.setVisible(true);
        buildSubItem(currencyItem, R.id.dollar,"U$S");
        buildSubItem(currencyItem, R.id.pesos, "$");
    }

    private void buildSubItem(MenuItem sortItem, int subItemId, final String moneda) {
        MenuItem subItem = sortItem.getSubMenu().findItem(subItemId);
        subItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                requestBarReportData(moneda);
                return true;
            }
        });
    }

}
