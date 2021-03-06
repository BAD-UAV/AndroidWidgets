package com.o3dr.android.lib.andwidgets.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.VideoView;

import com.o3dr.android.lib.andwidgets.R;

/**
 * Generates a VideoView that maintains the aspect ratio of the video.
 *
 * Use heightRatio and widthRatio to calculate the aspect ratio of the video
 */
public class AspectRatioVideoView extends VideoView {
    private int widthRatio;
    private int heightRatio;

    public AspectRatioVideoView(Context context) {
        super(context);
    }

    public AspectRatioVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.AspectRatioVideoView,
            0, 0);
        setAttr(array);
    }

    public AspectRatioVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray array = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.AspectRatioVideoView,
            defStyle, 0);
        setAttr(array);
    }

    private void setAttr(TypedArray array) {
        try {
            widthRatio = array.getInteger(R.styleable.AspectRatioVideoView_widthRatio, 0);
            heightRatio = array.getInteger(R.styleable.AspectRatioVideoView_heightRatio, 0);
        } finally {
            array.recycle();
        }
    }

    public int getHeightRatio() {
        return heightRatio;
    }

    public void setHeightRatio(int heightRatio) {
        this.heightRatio = heightRatio;
        invalidate();
    }

    public int getWidthRatio() {
        return widthRatio;
    }

    public void setWidthRatio(int widthRatio) {
        this.widthRatio = widthRatio;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = widthMeasureSpec;
        int height = heightMeasureSpec;

        if (widthRatio != 0 && heightRatio != 0) {
            width = getDefaultSize(widthRatio, widthMeasureSpec);
            height = getDefaultSize(heightRatio, heightMeasureSpec);
            if (widthRatio * height > width * heightRatio) {
                // Image is too tall
                height = width * heightRatio / widthRatio;
            } else if (widthRatio * height < width * heightRatio) {
                // Image is too wide
                width = height * widthRatio / heightRatio;
            }
        }

        setMeasuredDimension(width, height);
    }
}
