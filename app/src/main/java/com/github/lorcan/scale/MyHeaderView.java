package com.github.lorcan.scale;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.LayoutParams.*;

/**
 * Created by luocan on 15/1/22.
 */
public class MyHeaderView extends RelativeLayout {
    private static int MAX_IMG_WIDTH_DP = 150;
    private static int MIN_IMG_WIDTH_DP = 45;
    private static int MAX_IMG_TOP_MARGIN = 140;
    private static int MIN_IMG_TOP_MARGIN = 10;

    private int maxImgWidth;
    private int minImgWidth;
    private int maxTopMargin;
    private int minTopMargin;
    private View parentView;
    private ImageView hederImg;
    private int screenWidth;
    private int screenHeight;
    private float screenDensity;
    private int width;
    private int marginTop;
    private int height;
    private View headerDivider;
    private ScrollTopListener listener;

    public MyHeaderView(Context context) {
        super(context);
        initView(context);

    }

    public MyHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }


    private void initView(Context context) {
        parentView = LayoutInflater.from(context).inflate(R.layout.ui_my_header, null);
        hederImg = (ImageView) parentView.findViewById(R.id.header_icon);
        headerDivider = parentView.findViewById(R.id.header_divider);

        LayoutParams lp = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        addView(parentView, lp);

        // init header height
        ViewTreeObserver observer = parentView.getViewTreeObserver();
        if (null != observer) {
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    height = getHeight();

                    ViewTreeObserver observer = getViewTreeObserver();
                    if (null != observer) {
                        observer.removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }

        getDisplayMetrics(context);
        maxImgWidth = (int) (screenDensity * MAX_IMG_WIDTH_DP);
        minImgWidth = (int) (screenDensity * MIN_IMG_WIDTH_DP);
        maxTopMargin = (int) (screenDensity * MAX_IMG_TOP_MARGIN);
        minTopMargin = (int) (screenDensity * MIN_IMG_TOP_MARGIN);
    }


    public void getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        screenDensity = dm.density;
    }

    public void updateHeaderView(float deltaY) {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) hederImg.getLayoutParams();

        height = getHeight() / 2;
        width = (int) (((height - deltaY) / height) * maxImgWidth);

        if (width < minImgWidth) {
            float expectY = getHeight() - (MIN_IMG_TOP_MARGIN * screenDensity + minImgWidth * 2);

            if (deltaY >= expectY && listener != null) {
                hederImg.setVisibility(View.INVISIBLE);
                listener.onScrollToTop();
            } else if (deltaY < expectY) {
                hederImg.setVisibility(View.VISIBLE);
                listener.onScrollNotTop();
            }

            if (deltaY >= expectY + minImgWidth) {
                headerDivider.setVisibility(INVISIBLE);
            } else if (deltaY < expectY + minImgWidth) {
                headerDivider.setVisibility(VISIBLE);

            }

            width = minImgWidth;
        } else {
            hederImg.setVisibility(View.VISIBLE);
        }

        if (width > maxImgWidth) {
            width = maxImgWidth;
        }

        layoutParams.width = width;
        layoutParams.height = width;
        marginTop = (int) ((height - deltaY) / height * maxTopMargin) + maxTopMargin;


        if (marginTop < minTopMargin) {
            marginTop = minTopMargin;
        }

        if (marginTop > maxTopMargin) {
            marginTop = maxTopMargin;
        }

        hederImg.setLayoutParams(layoutParams);

//
        RelativeLayout.LayoutParams dividerParams = (LayoutParams) headerDivider.getLayoutParams();
        dividerParams.width = 3;
        dividerParams.height = width;
        headerDivider.setLayoutParams(dividerParams);
    }


    public boolean isMinHeight() {
        if (width == minImgWidth) {
            return true;
        }

        return false;
    }

    public boolean isMaxHeight() {
        if (width == maxImgWidth && marginTop == maxTopMargin) {
            return true;
        }

        return false;
    }

    public void setOnTopListener(ScrollTopListener onTopListener) {
        this.listener = onTopListener;
    }

    public interface ScrollTopListener {
        void onScrollToTop();

        void onScrollNotTop();
    }
}
