package bw.com.yunifangstore.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/27.
 */
@Table(name = "ordergoods")
public class OrderGoods {
    @Column(name = "id", isId = true)
    private String id;
    @Column(name = "parentId", property = "NOT NULL")
    private String parentId;

    @Column(name = "goodsName", property = "NOT NULL")
    private String goodsName;

    @Column(name = "goodsPrice", property = "NOT NULL")
    private double goodsPrice;

    @Column(name = "goodsNumber", property = "NOT NULL")
    private int goodsNumber;

    @Column(name = "goodsPicture", property = "NOT NULL")
    private String goodsPicture;


    public OrderGoods(String goodsName, double goodsPrice, int goodsNumber, String goodsPicture) {
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsNumber = goodsNumber;
        this.goodsPicture = goodsPicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    @Override
    public String toString() {
        return "OrderGoods{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsNumber=" + goodsNumber +
                ", goodsPicture='" + goodsPicture + '\'' +
                '}';
    }

    public OrderGoods() {
        super();
    }
}
