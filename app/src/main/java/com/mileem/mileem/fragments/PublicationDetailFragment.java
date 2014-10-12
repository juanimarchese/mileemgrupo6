package com.mileem.mileem.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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


import java.util.ArrayList;

/**
 * Created by JuanMarchese on 18/09/2014.
 */
public class PublicationDetailFragment extends BaseFragment {

    public static final String TAG = PublicationDetailFragment.class.getSimpleName();
    private View rootView;

    private SmartViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private PublicationDetails currentPublication;

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
        buildFixedTextViews();
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
                data.add(new Multimedia(Multimedia.Type.VIDEO, publication.getVideo().getThumbnail(), publication.getVideo().getEmbedUrl()));
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

    private void buildFixedTextViews () {
        String arial = "arial.ttf";
        buildTextViewWidget(R.id.prop_tipo_txt, null, arial);
        buildTextViewWidget(R.id.prop_op_txt, null, arial);
        buildTextViewWidget(R.id.prop_scubierta_txt, null, arial);
        buildTextViewWidget(R.id.prop_sdescubierta_txt, null, arial);
        buildTextViewWidget(R.id.prop_antiguedad_txt, null, arial);
        buildTextViewWidget(R.id.prop_expensas_txt, null, arial);
        buildTextViewWidget(R.id.prop_descripcion_txt, null, arial);
    }
    private void fillPublicationView(PublicationDetails publication) {
        String arial = "arial.ttf";
        String arialBold = "arial_bold.ttf";
        buildTextViewWidget(R.id.prop_direccion,publication.getAddress(), arial);
        buildTextViewWidget(R.id.prop_barrio,publication.getNeighborhood().getName(), arial);
        buildTextViewWidget(R.id.prop_precio,publication.getCurrency() + " " + publication.getPrice(), arialBold);
        String sizeWithEnv = String.valueOf(publication.getCoveredSize() + publication.getSize())+ " m2" + " | " +  publication.getEnvironment().getName();

        buildTextViewWidget(R.id.prop_m2_amb, sizeWithEnv , arial);

        buildTextViewWidget(R.id.prop_tipo,publication.getPropertyType().getName(), arial);
        buildTextViewWidget(R.id.prop_op,publication.getOperationType().getName(), arial);
        buildTextViewWidget(R.id.prop_scubierta,publication.getCoveredSize() + " m2", arial);
        buildTextViewWidget(R.id.prop_sdescubierta,publication.getSize() + " m2", arial);
        String antiguedad = publication.getAge() == 0 ? "A estrenar" : publication.getAge() > 1 ? publication.getAge() + " años" : "1 año";
        buildTextViewWidget(R.id.prop_antiguedad, antiguedad, arial);
        buildTextViewWidget(R.id.prop_expensas, publication.getCurrency() + " " + publication.getExpenses(), arial);
        buildTextViewWidget(R.id.prop_descripcion, publication.getDescription(), arial);
    }

    private void buildTextViewWidget(int convertViewId, String txt, String fontName) {
        TextView textView = (TextView)rootView.findViewById(convertViewId);
        if(textView != null) {
            if (fontName != null) {
                Typeface face = Typeface.createFromAsset(this.getActivity().getAssets(),
                        "fonts/" + fontName);
                textView.setTypeface(face);
            }
            if (txt != null)
                textView.setText(txt);
        }
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
                    currentPublication = publication;
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
        ((MainActivity) rootView.getContext()).displayView(new ResultsFragment(),true);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem contactItem = menu.findItem(R.id.action_contact);
        contactItem.setVisible(true);
        MenuItem telItem = contactItem.getSubMenu().findItem(R.id.telefono);
        telItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(currentPublication == null || currentPublication.getUser() == null || currentPublication.getUser().getTelephone() == null || currentPublication.getUser().getTelephone().isEmpty()){
                    Toast.makeText(getActivity(), "No existe un numero de teléfono asociado válido", Toast.LENGTH_LONG).show();
                    return false;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + currentPublication.getUser().getTelephone()));
                try {
                    getActivity().startActivity(callIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(),
                            "Error en la llamada.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        MenuItem mailItem = contactItem.getSubMenu().findItem(R.id.email);
        mailItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((MainActivity)getActivity()).displayView(ContactFragment.newInstance(currentPublication),false);
                return true;
            }
        });

        menu.findItem(R.id.action_amenities).setVisible(true);
        
        MenuItem mapItem = menu.findItem(R.id.action_location);
        mapItem.setVisible(true);
        mapItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((MainActivity)getActivity()).displayView(LocationFragment.newInstance(currentPublication),false);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_contact:
                return true;
            case R.id.action_amenities:
                Toast.makeText(rootView.getContext(), "Vista de Amenities", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_location:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
