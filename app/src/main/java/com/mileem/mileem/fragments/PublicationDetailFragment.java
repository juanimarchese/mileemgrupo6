package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.adapters.MultimediaSlidePagerAdapter;
import com.mileem.mileem.models.Multimedia;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.networking.AsyncRestHttpClient;
import com.mileem.mileem.networking.PublicationDetailsDataManager;
import com.mileem.mileem.widgets.SmartViewPager;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;


import java.util.ArrayList;

/**
 * Created by JuanMarchese on 18/09/2014.
 */
public class PublicationDetailFragment extends BaseFragment {

    public static final String TAG = PublicationDetailFragment.class.getSimpleName();
    private View rootView;

    private SmartViewPager mPager;
    private PagerAdapter mPagerAdapter;

    public PublicationDetailFragment() {
        super(TAG);
    }

    public static PublicationDetailFragment newInstance(int publicationDetailId) {
        PublicationDetailFragment myFragment = new PublicationDetailFragment();

        Bundle args = new Bundle();
        args.putInt("publicationDetailId", publicationDetailId);
        myFragment.setArguments(args);

        return myFragment;
    }

    public int getPublicationId(){
        return getArguments().getInt("publicationDetailId", -1);
    }

    @Override
    public String getTittle() {
        return "Detalle";
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            requestPublicationData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publication_detail_fragment, container, false);
        this.rootView = rootView;
        buildPublicationView();
        requestPublicationData();
        return rootView;
    }

    private void buildGallery(PublicationDetails publication) {
        mPager = (SmartViewPager) rootView.findViewById(R.id.pager);
        RelativeLayout pagerContainer = (RelativeLayout) rootView.findViewById(R.id.pager_container);
        Boolean hasPictures = publication.getPictures().size() > 0;
        Boolean hasVideo = publication.getVideo().hasVideo();
        Boolean showGallery = hasPictures || hasVideo;
        int visibility = showGallery || hasVideo ? View.VISIBLE : View.GONE;
        pagerContainer.setVisibility(visibility);

        if (showGallery) {
         ArrayList<Multimedia> data = new ArrayList<Multimedia>();

            for (String picture : publication.getPictures()) {
                data.add(new Multimedia(Multimedia.Type.IMAGE, AsyncRestHttpClient.getAbsoluteUrlRelativeToHost(picture), null));
            }

            if (hasVideo) {
                data.add(new Multimedia(Multimedia.Type.VIDEO, publication.getVideo().getThumbnail(), publication.getVideo().getUrl()));
            }
            mPagerAdapter = new MultimediaSlidePagerAdapter(this.getActivity().getFragmentManager(), data);
            mPager.setAdapter(mPagerAdapter);

            CirclePageIndicator titleIndicator = (CirclePageIndicator)rootView.findViewById(R.id.pager_indicator);
            titleIndicator.setViewPager(mPager);
        }
    }

    private void buildPublicationView() {
        //TODO - Inicializar el Layout de Vista

    }
    private void fillPublicationView(PublicationDetails publication) {
        buildTextViewWidget(R.id.prop_direccion,publication.getAddress());
        buildTextViewWidget(R.id.prop_barrio,publication.getNeighborhood().getName());
        buildTextViewWidget(R.id.prop_precio,publication.getCurrency() + " " + publication.getPrice());
        buildTextViewWidget(R.id.prop_m2, String.valueOf(publication.getCoveredSize() + publication.getSize())+ " m2");
        buildTextViewWidget(R.id.prop_ambientes,publication.getEnvironment().getName());
        buildTextViewWidget(R.id.prop_tipo,publication.getPropertyType().getName());
        buildTextViewWidget(R.id.prop_op,publication.getOperationType().getName());
        buildTextViewWidget(R.id.prop_scubierta,publication.getCoveredSize() + " m2");
        buildTextViewWidget(R.id.prop_sdescubierta,publication.getSize() + " m2");
        String antiguedad = publication.getAge() == 0 ? "A estrenar" : publication.getAge() > 1 ? publication.getAge() + " años" : "1 año";
        buildTextViewWidget(R.id.prop_antiguedad, antiguedad);
        buildTextViewWidget(R.id.prop_expensas, publication.getCurrency() + " " + publication.getExpenses());
        buildTextViewWidget(R.id.prop_descripcion, publication.getDescription());
    }

    private void buildTextViewWidget(int convertViewId, String txt) {
        TextView textView = (TextView)rootView.findViewById(convertViewId);
        if(textView != null) textView.setText(txt);
    }


    private void requestPublicationData() {
        showPDialog(rootView.getContext());
        try {
            new PublicationDetailsDataManager().getDetails(getPublicationId(), new PublicationDetailsDataManager.PublicationsDetailsCallbackHandler() {
                @Override
                public void onComplete(PublicationDetails publication) {
                    //TODO - Bindear datos con la vista
                    buildGallery(publication);
                    fillPublicationView(publication);
                    hidePDialog();
                }

                @Override
                public void onFailure(Error error) {
                    showError();
                    hidePDialog();
                }
            });
        } catch (Throwable e) {
            showError();
            hidePDialog();
        }
    }

    private void showError() {
        Toast.makeText(getActivity(), "Error al tratar de obtener los datos de la publicación", Toast.LENGTH_LONG).show();
        ((MainActivity) rootView.getContext()).displayViewForMenu(1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_contact).setVisible(true);
        menu.findItem(R.id.action_amenities).setVisible(true);
        menu.findItem(R.id.action_location).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_contact:
                Toast.makeText(rootView.getContext(), "Vista de Contacto", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_amenities:
                Toast.makeText(rootView.getContext(), "Vista de Amenities", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_location:
                Toast.makeText(rootView.getContext(), "Vista de Mapa", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
