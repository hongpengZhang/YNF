package bw.com.yunifangstore.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.base.BaseHolder;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/10.
 */
public class DeailViewHolder extends BaseHolder<String> {


    private final ImageView detailpic_iv;

    public DeailViewHolder(View itemView) {
        super(itemView);
        detailpic_iv = (ImageView) itemView.findViewById(R.id.detailpic_iv);
    }

    @Override
    public void setData(Context context,String picUri) {
        ImageLoader.getInstance().displayImage(picUri,detailpic_iv);

    }
}
