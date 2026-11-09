package com.example.cebulionerzy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Kontener na jeden element o sztywnym rozmiarze (w dp, bez marginesów).
 * Jeżeli element nie mieści się w dostępnym miejscu, jest proporcjonalnie zmniejszany
 * i wyśrodkowywany, dzięki czemu ręcznie ułożona zawartość nie rozjeżdża się na małych ekranach.
 */
public class ScaleToFitLayout extends FrameLayout {

    public ScaleToFitLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        ViewGroup.LayoutParams lp = child.getLayoutParams();
        int unspecified = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        child.measure(getChildMeasureSpec(unspecified, 0, lp.width),
                getChildMeasureSpec(unspecified, 0, lp.height));

        setMeasuredDimension(resolveSize(child.getMeasuredWidth(), widthMeasureSpec),
                resolveSize(child.getMeasuredHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View child = getChildAt(0);
        int childWidth = child.getMeasuredWidth();
        int childHeight = child.getMeasuredHeight();
        if(childWidth == 0 || childHeight == 0) {
            return;
        }

        int width = right - left;
        int height = bottom - top;
        float scale = Math.min(1f, Math.min((float) width / childWidth, (float) height / childHeight));

        child.setPivotX(0f);
        child.setPivotY(0f);
        child.setScaleX(scale);
        child.setScaleY(scale);

        int childLeft = Math.round((width - childWidth * scale) / 2);
        int childTop = Math.round((height - childHeight * scale) / 2);
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
    }
}
