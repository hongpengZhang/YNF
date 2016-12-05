package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.RoolData;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.ImageLoaderUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/3.
 */

public class ActivityInfoAdapter extends PagerAdapter {
    Context context;
    List<RoolData.DataBean.ActivityInfoBean.ActivityInfoListBean> list;
    private final DisplayImageOptions imageOptions;

    public ActivityInfoAdapter(Context context, List<RoolData.DataBean.ActivityInfoBean.ActivityInfoListBean> list) {
        this.context = context;
        this.list = list;
        imageOptions = ImageLoaderUtils.initOptions();
    }

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
        View view = CommonUtils.inflate(R.layout.activity_item);
        ImageView iv_roolviewpager = (ImageView) view.findViewById(R.id.iv_roolviewpager);
        ImageLoader.getInstance().displayImage(list.get(position % list.size()).getActivityImg(), iv_roolviewpager, imageOptions);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
