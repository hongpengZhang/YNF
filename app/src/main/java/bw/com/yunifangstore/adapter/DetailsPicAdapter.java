package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.LargePictureData;
import bw.com.yunifangstore.holder.DeailViewHolder;
import bw.com.yunifangstore.utils.CommonUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/10.
 */
public class DetailsPicAdapter extends RecyclerView.Adapter<DeailViewHolder> {
    private LargePictureData[] largePictureDatas;
    private Context context;

    public DetailsPicAdapter(LargePictureData[] largePictureDatas, Context context) {
        this.largePictureDatas = largePictureDatas;
        this.context = context;
    }


    @Override
    public DeailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = CommonUtils.inflate(R.layout.detailpic_item);
        DeailViewHolder deailViewHolder = new DeailViewHolder(inflate);
        return deailViewHolder;
    }

    @Override
    public void onBindViewHolder(DeailViewHolder holder, int position) {
        holder.setData(context, largePictureDatas[position].getUrl());
    }

    @Override
    public int getItemCount() {
        return largePictureDatas.length;
    }
}
