package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.activity.LaunchActivity;
import bw.com.yunifangstore.activity.MainActivity;
import bw.com.yunifangstore.utils.CommonUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/11/29.
 */
public class LaunchAdapter extends PagerAdapter {

    private final Context context;
    private int[] pic;
    private ImageView viewPager__iv_hide;

    public LaunchAdapter(int[] pic, Context context) {
        this.pic = pic;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pic.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = CommonUtils.inflate(R.layout.activity_launch2);
        ImageView viewPager_iv = (ImageView) view.findViewById(R.id.viewPager_iv);
        viewPager__iv_hide = (ImageView) view.findViewById(R.id.viewPager__iv_hide);
        viewPager_iv.setBackgroundResource(pic[position]);
        container.addView(view);
        if (position == pic.length - 1) {
            viewPager__iv_hide.setVisibility(View.VISIBLE);
            viewPager__iv_hide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, -1, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
                    translateAnimation.setFillAfter(true);
                    translateAnimation.setDuration(2000);
                    viewPager__iv_hide.startAnimation(translateAnimation);
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            LaunchActivity activity = (LaunchActivity) context;
                            activity.finish();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
        }

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
