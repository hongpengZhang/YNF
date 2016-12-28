package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.RoolData;
import bw.com.yunifangstore.interfaceclass.OnItemClickListener;
import bw.com.yunifangstore.utils.ImageLoaderUtils;

import static bw.com.yunifangstore.R.layout.recycle_item;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/3.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private RoolData.DataBean.BestSellersBean bestSellersBean;
    private Context context;


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public RecyclerViewAdapter(RoolData.DataBean.BestSellersBean bestSellersBean, Context context) {
        this.bestSellersBean = bestSellersBean;
        this.context = context;
    }

    /**
     * 加载布局
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(recycle_item, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 绑定数据
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
       /* if (i == 6) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.mipmap.weibu);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {*/
        String goods_name = bestSellersBean.getGoodsList().get(i).getGoods_name();
        if (goods_name.length() > 14) {
            viewHolder.selling_rv_item_des.setText(goods_name.substring(0, 14) + "...");
        } else {
            viewHolder.selling_rv_item_des.setText(goods_name);
        }
        viewHolder.selling_rv_item_newprice.setText("￥" + bestSellersBean.getGoodsList().get(i).getShop_price());
        viewHolder.selling_rv_item_oldprice.setText("￥" + bestSellersBean.getGoodsList().get(i).getMarket_price());
        viewHolder.selling_rv_item_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ImageLoader.getInstance().displayImage(bestSellersBean.getGoodsList().get(i).getGoods_img(), viewHolder.selling_rv_item_img, ImageLoaderUtils.initOptions());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.setOnItemClickListener(i);

                }
            }
        });
    }

   /* }*/

    /**
     * 条目总数
     */
    @Override
    public int getItemCount() {
        return bestSellersBean.getGoodsList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView selling_rv_item_img;
        private final TextView selling_rv_item_newprice;
        private final TextView selling_rv_item_oldprice;
        private final TextView selling_rv_item_des;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            AutoUtils.autoSize(itemView);
            selling_rv_item_img = (ImageView) itemView.findViewById(R.id.selling_rv_item_img);
            selling_rv_item_newprice = (TextView) itemView.findViewById(R.id.selling_rv_item_newprice);
            selling_rv_item_oldprice = (TextView) itemView.findViewById(R.id.selling_rv_item_oldprice);
            selling_rv_item_des = (TextView) itemView.findViewById(R.id.selling_rv_item_des);
        }
    }
}
