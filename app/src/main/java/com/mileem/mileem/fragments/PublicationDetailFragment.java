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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mileem.mileem.AppController;
import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.adapters.MultimediaSlidePagerAdapter;
import com.mileem.mileem.models.Multimedia;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.networking.AsyncRestHttpClient;
import com.mileem.mileem.networking.PublicationDetailsDataManager;
import com.mileem.mileem.networking.PublicityDataManager;
import com.mileem.mileem.utils.ShareUtils;
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
    private MultimediaSlidePagerAdapter mPagerAdapter;
    private PublicationDetails currentPublication;
    private NetworkImageView publicityView;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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
        buildPublicityView(rootView);
        buildFixedTextViews();
        requestPublicationData();
        return rootView;
    }

    private void buildPublicityView(View rootView) {
        publicityView = (NetworkImageView) rootView.findViewById(R.id.publicity);
        publicityView.setDefaultImageResId(R.drawable.image_placeholder);
        publicityView.setErrorImageResId(R.drawable.image_placeholder);
        publicityView.setVisibility(View.GONE);
    }

    private void buildGallery(final PublicationDetails publication) {
        mPager = (SmartViewPager) rootView.findViewById(R.id.pager);
        RelativeLayout pagerContainer = (RelativeLayout) rootView.findViewById(R.id.pager_container);
        Boolean hasPictures = publication.getPictures().size() > 0;
        Boolean hasVideo = publication.getVideo().hasVideo();
        Boolean showGallery = hasPictures || hasVideo;
        int visibility = showGallery || hasVideo ? View.VISIBLE : View.GONE;
        pagerContainer.setVisibility(visibility);

        if (showGallery) {
            mPagerAdapter = new MultimediaSlidePagerAdapter(this.getActivity().getFragmentManager(), publication.getMultimediaData(), ImageView.ScaleType.CENTER_CROP);
            mPagerAdapter.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int pageNumber = mPager.getCurrentItem();
                    MultimediaPageFragment fragment = mPagerAdapter.getItem(pageNumber);
                    Multimedia multimedia = fragment.getMultimedia();
                    if (multimedia.getType() == Multimedia.Type.VIDEO) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(multimedia.getUrl()));
                        startActivity(intent);
                    } else if (multimedia.getType() == Multimedia.Type.IMAGE) {
                        MultimediaGalleryFragment gallery = MultimediaGalleryFragment.newInstance(publication, pageNumber);
                        ((MainActivity) getActivity()).displayView(gallery, false);
                    }
                }
            });
            mPager.setAdapter(mPagerAdapter);
            CirclePageIndicator titleIndicator = (CirclePageIndicator)rootView.findViewById(R.id.pager_indicator);
            titleIndicator.setViewPager(mPager);
        }
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
        String sizeWithEnv = String.valueOf(publication.getSize())+ " m2" + " | " +  publication.getEnvironment().getName();

        buildTextViewWidget(R.id.prop_m2_amb, sizeWithEnv , arial);

        buildTextViewWidget(R.id.prop_tipo,publication.getPropertyType().getName(), arial);
        buildTextViewWidget(R.id.prop_op,publication.getOperationType().getName(), arial);
        buildTextViewWidget(R.id.prop_scubierta,publication.getCoveredSize() + " m2", arial);
        buildTextViewWidget(R.id.prop_sdescubierta,String.valueOf((publication.getSize()-publication.getCoveredSize()) + " m2"), arial);
        String antiguedad = publication.getAgeString();
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
                    buildGallery(publication);
                    fillPublicationView(publication);
                    currentPublication = publication;
                    if(publication.isFree()){
                        new PublicityDataManager().getPublicity(new PublicityDataManager.PublicityCallbackHandler() {
                            @Override
                            public void onComplete(String bannerUrl, final String link) {
                                if(publicityView != null){
                                    publicityView.setImageUrl(bannerUrl,imageLoader);
                                    publicityView.setVisibility(View.VISIBLE);
                                    publicityView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                            startActivity(browserIntent);
                                        }
                                    });
                                }
                                hidePDialog();
                            }

                            @Override
                            public void onFailure(Error error) {
                                publicityView.setVisibility(View.VISIBLE);
                                publicityView.setOnClickListener(null);
                                hidePDialog();
                            }
                        });
                    } else {
                        if(publicityView != null) publicityView.setVisibility(View.GONE);
                        hidePDialog();
                    }
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

        MenuItem amenitiesItem = menu.findItem(R.id.action_amenities);
        amenitiesItem.setVisible(true);
        amenitiesItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(currentPublication.getAmenitieType().isEmpty()){
                    Toast.makeText(getActivity(),"La publicación no posee amenities",Toast.LENGTH_LONG).show();
                    return false;
                }
                ((MainActivity)getActivity()).displayView(AmenitiesFragment.newInstance(currentPublication),false);
                return true;
            }
        });
        
        MenuItem mapItem = menu.findItem(R.id.action_location);
        mapItem.setVisible(true);
        mapItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(currentPublication.getLatitude().isEmpty() && currentPublication.getLongitude().isEmpty()){
                    Toast.makeText(getActivity(),"No se han especificado datos sobre la ubicación para la publicación",Toast.LENGTH_LONG).show();
                    return false;
                }
                ((MainActivity)getActivity()).displayView(LocationFragment.newInstance(currentPublication),false);
                return true;
            }
        });

        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareItem.setVisible(true);
        shareItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                PublicationDetailFragment fr = PublicationDetailFragment.this;
                ShareUtils.share(fr.getActivity(), fr.getView(), fr.currentPublication);
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
                return true;
            case R.id.action_location:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_contact).setVisible(true);
        menu.findItem(R.id.action_amenities).setVisible(true);
        menu.findItem(R.id.action_location).setVisible(true);
        menu.findItem(R.id.action_share).setVisible(true);
    }
}
