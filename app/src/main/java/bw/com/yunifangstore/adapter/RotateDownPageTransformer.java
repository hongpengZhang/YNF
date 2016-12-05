package bw.com.yunifangstore.adapter;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/3.
 */

public class RotateDownPageTransformer implements ViewPager.PageTransformer {
    private static final float DEFAULT_MIN_ALPHA = 0.6f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;
    private static final float DEFAULT_MIN_SCALE = 0.9f;
    private float mMinScale = DEFAULT_MIN_SCALE;

    @Override
    public void transformPage(View view, float position) {
        view.setPivotX((float) (view.getWidth() * 0.5));
        view.setPivotY((float) (view.getWidth() * 0.5));

        if (position < -1) {
            view.setAlpha(mMinAlpha);
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);

        } else if (position <= 1) { // [-1,1]

            if (position < 0) {         //[0，-1]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                float mScale = mMinScale + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
                view.setScaleX(mScale);
                view.setScaleY(mScale);

            } else {        //[1，0]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                float mScale = mMinScale + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
                view.setScaleX(mScale);
                view.setScaleY(mScale);
            }
        } else { // (1,+Infinity]
            view.setAlpha(mMinAlpha);
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
        }

    }
}
