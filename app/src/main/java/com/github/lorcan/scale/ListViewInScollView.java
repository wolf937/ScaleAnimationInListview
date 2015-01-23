package com.github.lorcan.scale;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewInScollView extends ListView {

    public ListViewInScollView(Context context) {
        super(context);
    }

    public ListViewInScollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewInScollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
