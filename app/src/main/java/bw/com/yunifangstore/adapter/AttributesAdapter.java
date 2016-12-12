package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.DetailsData;
import bw.com.yunifangstore.holder.AttributesHolder;
import bw.com.yunifangstore.utils.CommonUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/11.
 */
public class AttributesAdapter extends RecyclerView.Adapter<AttributesHolder> {
     private  List<DetailsData.DataBean.GoodsBean.AttributesBean> attributes;
    private Context context;

    public AttributesAdapter(List<DetailsData.DataBean.GoodsBean.AttributesBean> attributes, Context context) {
        this.attributes = attributes;
        this.context = context;
    }

    @Override
    public AttributesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = CommonUtils.inflate(R.layout.attributes_item);
        AttributesHolder attributesHolder=new AttributesHolder(inflate);
        return attributesHolder;
    }

    @Override
    public void onBindViewHolder(AttributesHolder holder, int position) {
        holder.attr_name1.setText(attributes.get(position).getAttr_name());
        holder.attr_value1.setText(attributes.get(position).getAttr_value());
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }

}
