package com.mileem.mileem.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by ramirodiaz on 11/10/14.
 */
public class CustomAutoCompleteTextView extends AutoCompleteTextView {
    private int customThreshold = 0;

    public CustomAutoCompleteTextView(Context context) {
        super(context);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    public void setThreshold(int threshold) {
        if (threshold < 0) {
            threshold = 0;
        }
        customThreshold = threshold;
    }

    @Override
    public int getThreshold() {
        return customThreshold;
    }

    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
}