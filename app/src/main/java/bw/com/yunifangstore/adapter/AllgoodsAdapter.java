package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.QueryGoods;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.ImageLoaderUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/8.
 */
public class AllgoodsAdapter extends RecyclerView.Adapter<AllgoodsAdapter.MyViewHolder> {
    private final DisplayImageOptions options;
    private  ArrayList<QueryGoods.DataBean> list;
    private Context context;

    public AllgoodsAdapter(ArrayList<QueryGoods.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
      options = ImageLoaderUtils.initOptions();
    }

    @Override
    public AllgoodsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = CommonUtils.inflate(R.layout.last_mygridview_item);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(AllgoodsAdapter.MyViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(list.get(position).getGoods_img(),holder.lastgv_img,options );
        holder.last_gv_oldprice.setText("￥" + list.get(position).getMarket_price());
        holder.last_gv_newprice.setText("￥" + list.get(position).getShop_price());
        holder.last_tvdes.setText(list.get(position).getGoods_name());
        holder.last_gv_name.setText(list.get(position).getEfficacy());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView lastgv_img;
        private final TextView last_gv_name;
        private final TextView last_gv_oldprice;
        private final TextView last_gv_newprice;
        private final TextView last_tvdes;

        public MyViewHolder(View itemView) {
            super(itemView);

            lastgv_img = (ImageView) itemView.findViewById(R.id.lastgv_img);
            last_gv_name = (TextView) itemView.findViewById(R.id.last_gv_name);
            last_gv_oldprice = (TextView) itemView.findViewById(R.id.last_gv_oldprice);
            last_gv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            last_gv_newprice = (TextView) itemView.findViewById(R.id.last_gv_newprice);
            last_tvdes = (TextView) itemView.findViewById(R.id.last_tvdes);
        }
    }
}
