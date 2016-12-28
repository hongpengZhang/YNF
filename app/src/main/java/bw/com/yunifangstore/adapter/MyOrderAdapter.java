package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.DetailsData;
import bw.com.yunifangstore.interfaceclass.OnMoneyChangeListenter;

import static bw.com.yunifangstore.R.id.cart_middle;
import static bw.com.yunifangstore.R.id.shoppingcar_alertNum;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/18.
 */
public class MyOrderAdapter extends CommonAdapter<DetailsData.DataBean.GoodsBean> {

    private final OnMoneyChangeListenter onMoneyChangeListenter;

    public MyOrderAdapter(Context context, List<DetailsData.DataBean.GoodsBean> mDatas, int itemLayoutId, OnMoneyChangeListenter onMoneyChangeListenter) {
        super(context, mDatas, itemLayoutId);
        this.onMoneyChangeListenter = onMoneyChangeListenter;
    }

    @Override
    public void convert(ViewHolder helper, final DetailsData.DataBean.GoodsBean item) {
        View convertView = helper.getConvertView();
        convertView.findViewById(cart_middle).setVisibility(View.VISIBLE);
        convertView.findViewById(R.id.goods_nummber).setVisibility(View.INVISIBLE);
        convertView.findViewById(R.id.cart_select).setVisibility(View.GONE);
        helper.setImageByUrl(R.id.cart_image, item.getGoods_img());
        ImageView iv_dijuan = helper.getView(R.id.iv_dijuan);
        ImageView juan = helper.getView(R.id.juan);
        int goodsCounts = item.getGoodsCounts();
        helper.setText(shoppingcar_alertNum, goodsCounts + "");
        helper.setText(R.id.goods_price, "￥" + item.getShop_price());
        helper.setText(R.id.goods_name, item.getGoods_name());
        if (item.isIs_coupon_allowed()) {
            juan.setVisibility(View.VISIBLE);
        } else {
            juan.setVisibility(View.INVISIBLE);
        }
        if (item.isIs_coupon_allowed()) {
            iv_dijuan.setVisibility(View.VISIBLE);
        } else {
            iv_dijuan.setVisibility(View.INVISIBLE);
        }

        ImageButton shoppingcar_alertSubtra = helper.getView(R.id.shoppingcar_alertSubtra);

        ImageButton shoppingcar_alertAdd = helper.getView(R.id.shoppingcar_alertAdd);
        //购买数量
        final TextView shoppingcar_alertNum = helper.getView(R.id.shoppingcar_alertNum);
        //减
        shoppingcar_alertSubtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = item.getGoodsCounts() - 1;
                if (count > 0) {
                    item.setGoodsCounts(count);
                    shoppingcar_alertNum.setText(count + "");
                    getMoney();
                }
            }
        });
        shoppingcar_alertAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = item.getGoodsCounts() + 1;
                item.setGoodsCounts(count);
                shoppingcar_alertNum.setText(count + "");
                getMoney();
            }
        });
    }

    public void getMoney() {
        float goodsSum = 0;
        int goodsCounts = 0;
        DecimalFormat df = new DecimalFormat("###.00");
        for (int j = 0; j < mDatas.size(); j++) {
            goodsSum = (float) (goodsSum + (mDatas.get(j).getShop_price() * mDatas.get(j).getGoodsCounts()));
            goodsCounts = goodsCounts + mDatas.get(j).getGoodsCounts();
            if (onMoneyChangeListenter != null) {
                onMoneyChangeListenter.setMoneyChange(Float.parseFloat(df.format(goodsSum)), goodsCounts);
            }
        }
    }
}
