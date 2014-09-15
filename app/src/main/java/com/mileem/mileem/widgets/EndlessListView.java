package com.mileem.mileem.widgets;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import com.mileem.mileem.adapters.PublicationListAdapter;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.networking.PublicationsDataManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EndlessListView extends ListView implements OnScrollListener {

    private Context context;
    private View footer;
    private boolean isLoading;
    private EndLessListener listener;
    private PublicationListAdapter adapter;
    private int currentFirstVisibleItem;
    private int currentVisibleItemCount;
    private int currentTotalItemCount;
    private int currentScrollState;

    public EndlessListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(this);

        this.context = context;
    }

    public EndlessListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);

        this.context = context;
    }

    public EndlessListView(Context context) {
        super(context);
        this.setOnScrollListener(this);

        this.context = context;
    }



    //	4
    public void addNewData(ArrayList<PublicationDetails> data) {
        this.removeFooterView(footer);
        if(!data.isEmpty()){
            adapter.addAll(data);
            adapter.notifyDataSetChanged();
        }
        isLoading = false;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        this.currentFirstVisibleItem = firstVisibleItem;
        this.currentVisibleItemCount = visibleItemCount;
        this.currentTotalItemCount = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
            this.currentScrollState = scrollState;
            this.isScrollCompleted();

    }

    private void isScrollCompleted() {
        if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
            if(getAdapter() == null)
                return;

            if(getAdapter().getCount() == 0)
                return;

            boolean lastItem = currentFirstVisibleItem + currentVisibleItemCount == currentTotalItemCount && this.getChildAt(currentVisibleItemCount -1) != null && this.getChildAt(currentVisibleItemCount-1).getBottom() <= this.getHeight();
            if (lastItem  && !isLoading ){

                //	add footer layout
                this.addFooterView(footer);
                //	set progress boolean
                isLoading = true;

                //	call interface method to load new data
                listener.loadData();
            }
        }
    }

    //	Calling order from MainActivity
    //	1
    public void setLoadingView(int resId) {
        //footer = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resId, null);		//		footer = (View)inflater.inflate(resId, null);
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footer = (View) inflater.inflate(resId, null);
        this.addFooterView(footer);
    }

    //	2
    public void setListener(EndLessListener listener) {
        this.listener = listener;
    }

    //	3
    public void setAdapter(PublicationListAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
        this.removeFooterView(footer);
    }

    //	interface
    public interface EndLessListener{
        public void loadData();
    }
}
