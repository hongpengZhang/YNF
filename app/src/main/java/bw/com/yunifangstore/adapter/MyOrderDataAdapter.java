package bw.com.yunifangstore.adapter;

import android.content.Context;

import java.text.DecimalFormat;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.OrderGoods;
import bw.com.yunifangstore.interfaceclass.OnMoneyChangeListenter;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/27.
 */
public class MyOrderDataAdapter  extends CommonAdapter<OrderGoods>{
    private final OnMoneyChangeListenter onMoneyChangeListenter;

    public MyOrderDataAdapter(Context context, List<OrderGoods> mDatas, int itemLayoutId,OnMoneyChangeListenter onMoneyChangeListenter) {
        super(context, mDatas, itemLayoutId);
        this.onMoneyChangeListenter = onMoneyChangeListenter;
    }

    @Override
    public void convert(ViewHolder helper, OrderGoods item) {
        helper.setText(R.id.goods_name, item.getGoodsName());
        helper.setText(R.id.goods_nummber, "数量:" + item.getGoodsNumber());
        helper.setText(R.id.goods_price,  "￥"+item.getGoodsPrice());
        helper.setImageByUrl(R.id.cart_image, item.getGoodsPicture());
        getMoney();
    }
    public void getMoney() {
        float goodsSum = 0;
        int goodsCounts = 0;
        DecimalFormat df = new DecimalFormat("###.00");
        for (int j = 0; j < mDatas.size(); j++) {
            goodsSum = (float) (goodsSum + (mDatas.get(j).getGoodsPrice() * mDatas.get(j).getGoodsNumber()));
            goodsCounts = goodsCounts + mDatas.get(j).getGoodsNumber();
            if (onMoneyChangeListenter != null) {
                onMoneyChangeListenter.setMoneyChange(Float.parseFloat(df.format(goodsSum)), goodsCounts);
            }
        }
    }
}
