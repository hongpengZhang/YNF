package bw.com.yunifangstore.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.ex.DbException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.MyOrderAdapter;
import bw.com.yunifangstore.alipay.domain.PayResult;
import bw.com.yunifangstore.alipay.util.SignUtils;
import bw.com.yunifangstore.bean.DetailsData;
import bw.com.yunifangstore.bean.OrderGoods;
import bw.com.yunifangstore.bean.OrderOutSideGoods;
import bw.com.yunifangstore.bean.UserBean;
import bw.com.yunifangstore.interfaceclass.OnMoneyChangeListenter;
import bw.com.yunifangstore.utils.DBUtils;


public class OrderActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ListView dd_listView;
    public TextView dd_top_sum;
    private TextView dd_sum_price;
    private float lastSum;
    private CheckBox ali_pay_che;
    private ArrayList<DetailsData.DataBean.GoodsBean> list;
    private RelativeLayout dd_address;
    private TextView display_tv;
    private ArrayList<OrderGoods> orderGoodsList = new ArrayList<>();
    public static final String PARTNER = "2088901305856832";
    // 商户收款账号
    public static final String SELLER = "8@qdbaiu.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM/KCxg/OIj6er2GEig0DOkHqBSzEPHGigMbTXP1k2nrxEHeE59xOOuyovQH/A1LgbuVKyOac3uAN4GXIBEoozRVzDhu5dobeNm48BPcpYSAfvN3K/5GLacvJeENqsiBx8KufM/9inlHaDRQV7WhX1Oe2ckat1EkdHwxxQgc36NhAgMBAAECgYEAwn3sWpq6cUR65LD8h9MIjopTImTlpFjgz72bhsHDZK6A+eJDXcddrwh7DI34t/0IBqu+QEoOc/f0fIEXS9hMwTvFY59XG7M8M6SdeaAbemrGmZ1IdD6YDmpbQFHn2ishaYF0YDZIkBS3WLDFrtk/efaarBCpGAVXeEiVQE4LewECQQD5W1rpkq+dHDRzzdtdi1bJ479wun5CfmVDVb2CJH7Iz0t8zyp/iEVV2QELnxZMphmoSzKaLXutTTj2OImpzCtRAkEA1VMxG6nQq9NkU51H1+I3mlUXRZ0XeFA1GFJ7xWpNRAVhEWlDz2zy9v/gX+RFpNC3f5uznycas70Xp78ew43TEQJAZFFqi9mlqTF1sLk67bFnIyXrGPEOZrXvC13tNfR0xVkQZ4/46wHp0xXQo9pG4GNaoyhNnVV7EkelCPnJ+HPZYQJAQh6T9QgQZoGR8hyovPAf3dUL7oa/VIo/urcuJ8VIB5JHQNdIrk0NjaNHj1E4iNosVgATj3vWWel9IIArb99QkQJAKvfm78lwnImtg5IM604hdn/Wu1XF8tpxsKLWcnfchMr0bM9rCmKmhAY+wdmqSyPZRiNb1QaaaDTqJxLy6AnQ+Q==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    private static final int SDK_PAY_FLAG = 1;
    private String orderInfo;
    private CheckBox wx_pay_che;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            /**
                             * 保存到数据库
                             */
                            try {
                                for (int i = list.size() - 1; i >= 0; i--) {
                                    String goods_name = list.get(i).getGoods_name();
                                    int goodsCounts = list.get(i).getGoodsCounts();
                                    double shop_price = list.get(i).getShop_price();
                                    String goods_img = list.get(i).getGoods_img();
                                    OrderGoods orderGoods = new OrderGoods(goods_name, shop_price, goodsCounts, goods_img);
                                    orderGoodsList.add(orderGoods);
                                    OrderOutSideGoods orderOutSideGoods = new OrderOutSideGoods("待付款", getOutTradeNo());
                                    orderOutSideGoods.saveGoodsList(orderGoodsList);
                                    DBUtils.getDb().saveOrUpdate(orderOutSideGoods);
                                    //删除
                                    DBUtils.getDb().delete(list.get(i));
                                    list.remove(list.get(i));
                                }

                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            /**
                             * 跳转到订单详情
                             */
                            Intent intent = new Intent(OrderActivity.this, OrderDetailActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                            finish();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ding_dan);
        Intent intent = getIntent();
        lastSum = intent.getFloatExtra("sum", 0);
        list = (ArrayList<DetailsData.DataBean.GoodsBean>) intent.getSerializableExtra("list");
        initView();
        setAdapter();
    }

    private void setAdapter() {
        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(this, list, R.layout.cart_item, new OnMoneyChangeListenter() {
            @Override
            public void setMoneyChange(float sum, int count) {

                dd_top_sum.setText("共计" + count + "件商品 总计:￥" + sum);
                dd_sum_price.setText("共计: ￥" + sum);
            }
        });
        dd_listView.setAdapter(myOrderAdapter);
    }


    /**
     * 查询当前地址
     */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            List<UserBean> all = DBUtils.getDb().findAll(UserBean.class);
            if (all != null) {
                for (int i = 0; i < all.size(); i++) {
                    if (all.get(i).isFlag()) {
                        UserBean userBean = all.get(i);
                        display_tv.setText("收货人:" + userBean.getUserName() + "         " + userBean.getPhone() + "\r\n" + "详细地址:" + userBean.getDetailAddress());
                    }
                }
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("确认订单");
        ImageView imageView = (ImageView) findViewById(R.id.but_title_left_image);
        imageView.setOnClickListener(this);
        dd_listView = (ListView) findViewById(R.id.dd_listView);
        dd_top_sum = (TextView) findViewById(R.id.dd_top_sum);
        dd_sum_price = (TextView) findViewById(R.id.dd_sum_price);
        dd_sum_price.setText("共计: ￥" + lastSum);
        dd_top_sum.setText("共计" + list.size() + "件商品 总计:￥" + lastSum);
        //支付宝与 微信
        ali_pay_che = (CheckBox) findViewById(R.id.ali_pay_che);
        ali_pay_che.setOnClickListener(this);
        wx_pay_che = (CheckBox) findViewById(R.id.wx_pay_che);
        wx_pay_che.setOnClickListener(this);

        /*共计0件商品  总.计：￥0.00*/
        //收货地址
        dd_address = (AutoRelativeLayout) findViewById(R.id.dd_address);
        dd_address.setOnClickListener(this);
        display_tv = (TextView) findViewById(R.id.display_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //退出
            case R.id.but_title_left_image:
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //收货地址
            case R.id.dd_address:
                Intent intent = new Intent(this, AddressActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;
        }
    }

    /**
     * 付款
     */
    public void Alipay(View view) {
        if (display_tv.getText().toString().equals("请填写收货地址")) {
            Toast.makeText(this, "请填写收货地址", Toast.LENGTH_SHORT).show();
            return;
        }
        orderInfo = getOrderInfo("商品:", "描述:", "0.1");
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                            overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                        }
                    }).show();
            return;
        }


/**
 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
 */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

/**
 * 完整的符合支付宝参数规范的订单信息
 */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OrderActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

// 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
}
