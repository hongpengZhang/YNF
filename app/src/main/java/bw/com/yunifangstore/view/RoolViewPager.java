package bw.com.yunifangstore.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.interfaceclass.OnPageClickListener;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.ImageLoaderUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/1.
 */

public class RoolViewPager extends ViewPager {

    private DisplayImageOptions imageOptions;
    private ArrayList<String> imageUrlList;
    private ArrayList<ImageView> dotList;
    private int[] pic;
    private RoolViewPagerAdapter adapter;
    private static final int RoolZERO = 0;
    private OnPageClickListener onPageClickListener;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentItem = RoolViewPager.this.getCurrentItem();
            currentItem++;
            RoolViewPager.this.setCurrentItem(currentItem);
            this.sendEmptyMessageDelayed(RoolZERO, 2000);
        }
    };

    public RoolViewPager(Context context) {
        super(context);
        init();
    }


    public RoolViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        imageOptions = ImageLoaderUtils.initOptions();
    }

    public void initData(final ArrayList<String> imageUrlList, final ArrayList<ImageView> dotList, final int[] pic, OnPageClickListener onPageClickListener) {
        this.imageUrlList = imageUrlList;
        this.dotList = dotList;
        this.pic = pic;
        this.onPageClickListener = onPageClickListener;
        /**
         * 小点随之滑动
         */
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotList.size(); i++) {
                    if (position % imageUrlList.size() == i) {
                        dotList.get(i).setImageResource(pic[0]);
                    } else {
                        dotList.get(i).setImageResource(pic[1]);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置适配器
     */
    public void setRoolAdapter() {
        if (adapter == null) {
            adapter = new RoolViewPagerAdapter();
        }
        this.setAdapter(adapter);
        handler.sendEmptyMessageDelayed(RoolZERO, 2000);
    }

    /**
     * 内部类适配器
     */
    public class RoolViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = CommonUtils.inflate(R.layout.roolviewpager_item);
            ImageView iv_roolviewpager = (ImageView) view.findViewById(R.id.iv_roolviewpager);
            ImageLoader.getInstance().displayImage(imageUrlList.get(position % imageUrlList.size()), iv_roolviewpager, imageOptions);
            container.addView(view);
            //冲突事件
            view.setOnTouchListener(new OnTouchListener() {

                private long downTime;
                private float downY;
                private float downX;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            downX = event.getX();
                            downY = event.getY();
                            //按下时间
                            downTime = System.currentTimeMillis();
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            float upX = event.getX();
                            float upY = event.getY();
                            //抬起时间
                            long upTime = System.currentTimeMillis();
                            if (downX == upX && downY == upY && upTime - downTime < 1000) {
                                if (onPageClickListener != null) {
                                    onPageClickListener.setOnPage(position % imageUrlList.size());
                                }
                            }
                            handler.sendEmptyMessageDelayed(RoolZERO, 2000);
                            break;
                    }
                    return true;
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    /**
     * 当不在当前屏幕时停止轮播
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }
}
