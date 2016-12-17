package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.CategroyTypeBean;
import bw.com.yunifangstore.interfaceclass.OnItemClickListener;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.ImageLoaderUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/13.
 */
public class CategroyTypeAdapter extends RecyclerView.Adapter<CategroyTypeAdapter.MyViewHolder> {
    private Context context;
    private List<CategroyTypeBean.DataBean> dataBeanList;
    private OnItemClickListener onItemClickListener;

    public CategroyTypeAdapter(Context context, List<CategroyTypeBean.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = CommonUtils.inflate(R.layout.last_mygridview_item);
        CategroyTypeAdapter.MyViewHolder myViewHolder = new CategroyTypeAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = holder.getLayoutPosition();
                onItemClickListener.setOnItemClickListener(layoutPosition);
            }
        });
        ImageLoader.getInstance().displayImage(dataBeanList.get(position).getGoods_img(), holder.lastgv_img, ImageLoaderUtils.initOptions());
        holder.last_gv_oldprice.setText("￥" + dataBeanList.get(position).getMarket_price());
        holder.last_gv_newprice.setText("￥" + dataBeanList.get(position).getShop_price());
        holder.last_tvdes.setText(dataBeanList.get(position).getGoods_name());
        holder.last_gv_name.setText(dataBeanList.get(position).getEfficacy());
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView lastgv_img;
        private final TextView last_gv_name;
        private final TextView last_gv_oldprice;
        private final TextView last_gv_newprice;
        private final TextView last_tvdes;
        private View itemView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            lastgv_img = (ImageView) itemView.findViewById(R.id.lastgv_img);
            last_gv_name = (TextView) itemView.findViewById(R.id.last_gv_name);
            last_gv_oldprice = (TextView) itemView.findViewById(R.id.last_gv_oldprice);
            last_gv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            last_gv_newprice = (TextView) itemView.findViewById(R.id.last_gv_newprice);
            last_tvdes = (TextView) itemView.findViewById(R.id.last_tvdes);
        }
    }
}
