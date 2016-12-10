package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.utils.CommonUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/10.
 */
public class MyLargeAdapter extends PagerAdapter{
    private  ArrayList<String> imageUrlList;
    private Context context;

    public MyLargeAdapter(ArrayList<String> imageUrlList, Context context) {
        this.imageUrlList = imageUrlList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageUrlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = CommonUtils.inflate(R.layout.roolviewpager_item);
        ImageView iv_roolviewpager = (ImageView) view.findViewById(R.id.iv_roolviewpager);
        ImageLoader.getInstance().displayImage(imageUrlList.get(position),iv_roolviewpager);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
