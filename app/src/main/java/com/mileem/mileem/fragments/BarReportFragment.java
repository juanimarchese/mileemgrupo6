package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mileem.mileem.AppController;
import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.managers.DefinitionsManager;
import com.mileem.mileem.models.IdName;
import com.mileem.mileem.models.PublicationOrder;
import com.mileem.mileem.networking.ReportDataManager;
import com.mileem.mileem.utils.DefinitionsUtils;

import java.util.ArrayList;

/**
 * Created by JuanMarchese on 18/09/2014.
 */
public class BarReportFragment extends BaseFragment {

    public static final String TAG = BarReportFragment.class.getSimpleName();
    private View rootView;
    NetworkImageView networkImageView;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    TextView reportLabel;
    String moneda;
    String operation;


    public BarReportFragment() {
        super(TAG);
    }

    public static BarReportFragment newInstance(IdName neighborhood) {
        BarReportFragment myFragment = new BarReportFragment();

        Bundle args = new Bundle();
        args.putInt("neighborhoodId", neighborhood.getId());
        args.putString("neighborhoodName", neighborhood.getName());
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public String getTittle() {
        return "Zonas Aleñadas";
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
        View rootView = inflater.inflate(R.layout.bar_report_fragment, container, false);
        this.rootView = rootView;
        buildReportLabel(rootView);
        buildImage(rootView);
        requestBarReportData();
        return rootView;
    }

    private void setDefaultOptions() {
        moneda = "$";
        int ventaId = DefinitionsUtils.findIdByName(DefinitionsManager.getInstance().getOperationTypes(), "Venta");
        if (ventaId > 0) {
            operation = "" + ventaId;
        } else {
            operation = "";
        }

    }

    private void buildImage(View rootView) {
        networkImageView = (NetworkImageView) rootView.findViewById(R.id.report_imagen);
        if(networkImageView != null){
            networkImageView.setDefaultImageResId(R.drawable.image_placeholder);
            networkImageView.setErrorImageResId(R.drawable.image_placeholder);
            networkImageView.setVisibility(View.VISIBLE);
        }
    }

    private void buildReportLabel(View rootView) {
        reportLabel = (TextView) rootView.findViewById(R.id.reportLabel);
    }

    private void requestBarReportData(String currency,String operation) {
        showPDialog(rootView.getContext());
        try {
            IdName operationIdName = DefinitionsUtils.findIdNameById(DefinitionsManager.getInstance().getOperationTypes(), operation);
            reportLabel.setText("El siguiente reporte muestra el precio del metro cuadrado correspondiente a los barrios aleñados a " + getNeighborhoodName() + " en "+currency+" para la operación: "+operationIdName.getName()+". Los datos utilizados son obtenidos de las publicaciones realizadas en esta aplicación.");
            DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels - reportLabel.getHeight() - 30;
            new ReportDataManager().getReportAveragePricePerSquareMeterOfSurroundingNeighborhood(getNeighborhoodId(), currency,operation, width, height, new ReportDataManager.ReportDataManagerCallbackHandler() {
                @Override
                public void onComplete(String neighborhoodName, String graphUrl) {
                    networkImageView.setImageUrl(graphUrl,imageLoader);
                    hidePDialog();
                }

                @Override
                public void onComplete(String neighborhoodName, String currency, String price) {
                    showError();
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
        setDefaultOptions();
        requestBarReportData(moneda,operation);
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
        buildMonedaSubItem(currencyItem, R.id.dollar,"U$S");
        buildMonedaSubItem(currencyItem, R.id.pesos, "$");
    }

    private void buildMonedaSubItem(MenuItem sortItem, int subItemId, final String valor) {
        MenuItem subItem = sortItem.getSubMenu().findItem(subItemId);
        subItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                moneda = valor;
                requestBarReportData(moneda,operation);
                return true;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        buildOperationItemMenu(menu);
    }

    private void buildOperationItemMenu(Menu menu) {

        MenuItem operationItem = menu.findItem(R.id.action_operation);
        operationItem.setVisible(true);
        operationItem.getSubMenu().clear();
        ArrayList<IdName> operationTypes = DefinitionsManager.getInstance().getOperationTypes();
        int i = 1;
        for (IdName op : operationTypes){
            buildOperationSubItem(operationItem,i, op);
            i++;
        }
    }

    private void buildOperationSubItem(MenuItem sortItem, int subItemId, final IdName valor) {
        SubMenu subMenu = sortItem.getSubMenu();
        MenuItem menuItem = subMenu.add(0, subItemId, Menu.NONE, valor.getName());
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                operation = "" + valor.getId();
                requestBarReportData(moneda,operation);
                return true;
            }
        });
    }

}
