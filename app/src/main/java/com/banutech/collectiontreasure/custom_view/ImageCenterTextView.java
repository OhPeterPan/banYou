package com.banutech.collectiontreasure.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/5/29 0029.
 */

public class ImageCenterTextView extends TextView {
    public ImageCenterTextView(Context context) {
        super(context);
    }

    public ImageCenterTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageCenterTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (null != drawables) {
            Drawable drawableLeft = drawables[0];
            Drawable drawableRight = drawables[2];
            float textWidth = getPaint().measureText(getText().toString());
            if (null != drawableLeft) {
                setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                float contentWidth = textWidth + getCompoundDrawablePadding() + drawableLeft.getIntrinsicWidth();
                if (getWidth() - contentWidth > 0) {
                    canvas.translate((getWidth() - contentWidth - getPaddingRight() - getPaddingLeft()) / 2, 0);
                }
            }
            if (null != drawableRight) {
                setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                float contentWidth = textWidth + getCompoundDrawablePadding() + drawableRight.getIntrinsicWidth();
                if (getWidth() - contentWidth > 0) {
                    canvas.translate(-(getWidth() - contentWidth - getPaddingRight() - getPaddingLeft()) / 2, 0);
                }
            }
            if (null == drawableRight && null == drawableLeft) {
                setGravity(Gravity.CENTER);
            }
        }
        super.onDraw(canvas);
    }
}
