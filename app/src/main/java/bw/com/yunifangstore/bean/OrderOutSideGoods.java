package bw.com.yunifangstore.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import bw.com.yunifangstore.utils.DBUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/27.
 */
@Table(name = "ORDER_GOODS_BEAN")
public class OrderOutSideGoods {

    @Column(name = "INDENT_TYPE")
    private String orderType;
    @Column(name = "goodsOutTradeNo", isId = true)
    private String goodsOutTradeNo;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getGoodsOutTradeNo() {
        return goodsOutTradeNo;
    }

    public void setGoodsOutTradeNo(String goodsOutTradeNo) {
        this.goodsOutTradeNo = goodsOutTradeNo;
    }


    private List<OrderGoods> goodsList = new ArrayList<>();

    public OrderOutSideGoods( String orderType, String goodsOutTradeNo) {
        this.orderType = orderType;
        this.goodsOutTradeNo = goodsOutTradeNo;
    }
    public OrderOutSideGoods() {
        super();
    }
    public void saveGoodsList(List<OrderGoods> goodsList) {
        //保存所有的子条目
        try {
            for (int i = 0; i < goodsList.size(); i++) {
                goodsList.get(i).setParentId(this.goodsOutTradeNo);
                DBUtils.getDb().saveOrUpdate(goodsList.get(i));
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取所有对应的子条目
     *
     * @return
     * @throws DbException
     */
    public List<OrderGoods> getGoodsList() {
        try {
            goodsList.clear();
            goodsList.addAll(DBUtils.getDb().selector(OrderGoods.class).where("parentId", "=", this.goodsOutTradeNo).findAll());
        } catch (DbException e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    /**
     * 删除所有对应的子条目
     */
    public void deleteIndentList() {
        try {
            DBUtils.getDb().delete(goodsList);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }




}
