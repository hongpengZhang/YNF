package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.activity.DetailsActivity;
import bw.com.yunifangstore.activity.SubJectActivity;
import bw.com.yunifangstore.bean.RoolData;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.ImageLoaderUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/4.
 */
public class MyCommonAdapter extends CommonAdapter<RoolData.DataBean.SubjectsBean>  {
    private final List<RoolData.DataBean.SubjectsBean> subjectsList;
    private final Context context;
    private int position;
    private View inflate;
    private int i;

    public MyCommonAdapter(Context context, List<RoolData.DataBean.SubjectsBean> subjectsList, int itemLayoutId) {
        super(context, subjectsList, itemLayoutId);
        this.subjectsList = subjectsList;
        this.context = context;
    }

    @Override
    public void convert(ViewHolder helper, RoolData.DataBean.SubjectsBean item) {
        position = helper.getPosition();
        ImageView hot_iv = helper.getView(R.id.hot_iv);
        LinearLayout hot_llt_layout = helper.getView(R.id.hot_llt_layout);
        helper.setImageByUrl(R.id.hot_iv, subjectsList.get(position).getImage());
        addLlt_Layout(position, hot_llt_layout);
        /**
         * 跳转到专题Activity
         */
        hot_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpSubJect(position);
            }
        });
    }

    private void jumpSubJect(int position) {
        Intent intent = new Intent(context, SubJectActivity.class);
        intent.putExtra("title",subjectsList.get(position).getTitle());
        intent.putExtra("detail",subjectsList.get(position).getDetail());
        intent.putExtra("list", (Serializable) subjectsList.get(position).getGoodsList());
        context.startActivity(intent);
    }

    private void addLlt_Layout(final int position, final LinearLayout hot_llt_layout) {
        hot_llt_layout.removeAllViews();
        for (i = 0; i < 6; i++) {
            inflate = CommonUtils.inflate(R.layout.recycle_item);
            inflate.setId(i);
            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, DetailsActivity.class);
                    intent.putExtra("id",subjectsList.get(position).getGoodsList().get(v.getId()).getId());
                    context.startActivity(intent);
                }
            });
            ImageView selling_rv_item_img = (ImageView) inflate.findViewById(R.id.selling_rv_item_img);
            selling_rv_item_img.setScaleType(ImageView.ScaleType.FIT_XY);
            TextView selling_rv_item_des = (TextView) inflate.findViewById(R.id.selling_rv_item_des);
            TextView selling_rv_item_oldprice = (TextView) inflate.findViewById(R.id.selling_rv_item_oldprice);
            TextView selling_rv_item_newprice = (TextView) inflate.findViewById(R.id.selling_rv_item_newprice);

            ImageLoader.getInstance().displayImage(subjectsList.get(position).getGoodsList().get(i).getGoods_img(), selling_rv_item_img, ImageLoaderUtils.initOptions());
            String goods_name = subjectsList.get(position).getGoodsList().get(i).getGoods_name();
            if (goods_name.length() > 14) {
                selling_rv_item_des.setText(goods_name.substring(0, 14) + "...");
            } else {
                selling_rv_item_des.setText(goods_name);
            }
            selling_rv_item_oldprice.setText("￥" + subjectsList.get(position).getGoodsList().get(i).getMarket_price());
            selling_rv_item_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            selling_rv_item_newprice.setText("￥" + subjectsList.get(position).getGoodsList().get(i).getShop_price());
            hot_llt_layout.addView(inflate);

        }
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setImageResource(R.mipmap.weibu);
        hot_llt_layout.addView(imageView, params);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpSubJect(position);
            }
        });
    }


}

