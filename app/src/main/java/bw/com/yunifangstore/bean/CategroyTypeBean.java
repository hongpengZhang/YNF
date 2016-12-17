package bw.com.yunifangstore.bean;

import java.util.List;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/13.
 */

public class CategroyTypeBean {

    /**
     * code : 200
     * msg : success
     * data : [{"id":"336","goods_name":"玫瑰滋养润肤乳液","shop_price":69,"market_price":89,"is_coupon_allowed":true,"is_allow_credit":false,"goods_img":"http://image.hmeili.com/yunifang/images/goods/336/goods_img/160819092836113507603804875.jpg","sales_volume":15242,"efficacy":"长效保湿 持久滋养","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png","sort":0},{"id":"344","goods_name":"美白嫩肤润肤乳液120ml","shop_price":59.9,"market_price":99,"is_coupon_allowed":true,"is_allow_credit":false,"goods_img":"http://image.hmeili.com/yunifang/images/goods/344/goods_img/161109195851812653741618273.jpg","sales_volume":5724,"efficacy":"美白滋养 补水保湿","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png","sort":0},{"id":"341","goods_name":"清爽平衡矿物爽肤乳液120ml","shop_price":79,"market_price":89,"is_coupon_allowed":true,"is_allow_credit":false,"goods_img":"http://image.hmeili.com/yunifang/images/goods/341/goods_img/16081909295829444526554550.jpg","sales_volume":4417,"efficacy":"补水保湿 清爽控油","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png","sort":0},{"id":"346","goods_name":"红石榴矿物养肤乳液120ml","shop_price":49.5,"market_price":99,"is_coupon_allowed":true,"is_allow_credit":false,"goods_img":"http://image.hmeili.com/yunifang/images/goods/346/goods_img/160819093117519502611448962.jpg","sales_volume":3003,"efficacy":"提亮肤色 补水保湿","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png","sort":0},{"id":"513","goods_name":"蜂蜜矿物润肤乳液120ml","shop_price":99,"market_price":109,"is_coupon_allowed":true,"is_allow_credit":false,"goods_img":"http://image.hmeili.com/yunifang/images/goods/513/goods_img/16081909418262937204935916.jpg","sales_volume":1797,"efficacy":"补水保湿 持久滋养","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png","sort":0},{"id":"338","goods_name":"薰衣草矿物柔肤乳液120ml","shop_price":89,"market_price":99,"is_coupon_allowed":true,"is_allow_credit":false,"goods_img":"http://image.hmeili.com/yunifang/images/goods/338/goods_img/160819092964320132162271201.jpg","sales_volume":1042,"efficacy":"水润滋养 舒缓修护","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png","sort":0},{"id":"340","goods_name":"黑玫瑰矿物柔肤乳液120ml","shop_price":159,"market_price":169,"is_coupon_allowed":true,"is_allow_credit":false,"goods_img":"http://image.hmeili.com/yunifang/images/goods/340/goods_img/160819092931413070202481427.jpg","sales_volume":442,"efficacy":"提亮肤色 补水润肤","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png","sort":0},{"id":"356","goods_name":"光感皙妍矿物柔肤乳液120ml","shop_price":109,"market_price":119,"is_coupon_allowed":true,"is_allow_credit":false,"goods_img":"http://image.hmeili.com/yunifang/images/goods/356/goods_img/16081909338747124811788553.jpg","sales_volume":349,"efficacy":"改善黯哑 提亮肤色","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png","sort":0},{"id":"1220","goods_name":"男士炫活亮肤凝乳","shop_price":39.5,"market_price":89,"is_coupon_allowed":false,"is_allow_credit":false,"goods_img":"http://image.hmeili.com/yunifang/images/goods/1220/goods_img/161120132537912312357149912.jpg","sales_volume":124,"efficacy":"改善黯哑 保湿锁水","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png","sort":0}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 336
         * goods_name : 玫瑰滋养润肤乳液
         * shop_price : 69.0
         * market_price : 89.0
         * is_coupon_allowed : true
         * is_allow_credit : false
         * goods_img : http://image.hmeili.com/yunifang/images/goods/336/goods_img/160819092836113507603804875.jpg
         * sales_volume : 15242
         * efficacy : 长效保湿 持久滋养
         * watermarkUrl : http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png
         * sort : 0
         */

        private String id;
        private String goods_name;
        private double shop_price;
        private double market_price;
        private boolean is_coupon_allowed;
        private boolean is_allow_credit;
        private String goods_img;
        private int sales_volume;
        private String efficacy;
        private String watermarkUrl;
        private int sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public double getShop_price() {
            return shop_price;
        }

        public void setShop_price(double shop_price) {
            this.shop_price = shop_price;
        }

        public double getMarket_price() {
            return market_price;
        }

        public void setMarket_price(double market_price) {
            this.market_price = market_price;
        }

        public boolean isIs_coupon_allowed() {
            return is_coupon_allowed;
        }

        public void setIs_coupon_allowed(boolean is_coupon_allowed) {
            this.is_coupon_allowed = is_coupon_allowed;
        }

        public boolean isIs_allow_credit() {
            return is_allow_credit;
        }

        public void setIs_allow_credit(boolean is_allow_credit) {
            this.is_allow_credit = is_allow_credit;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public int getSales_volume() {
            return sales_volume;
        }

        public void setSales_volume(int sales_volume) {
            this.sales_volume = sales_volume;
        }

        public String getEfficacy() {
            return efficacy;
        }

        public void setEfficacy(String efficacy) {
            this.efficacy = efficacy;
        }

        public String getWatermarkUrl() {
            return watermarkUrl;
        }

        public void setWatermarkUrl(String watermarkUrl) {
            this.watermarkUrl = watermarkUrl;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
