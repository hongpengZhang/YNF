package bw.com.yunifangstore.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.CommentsCommonAdapter;
import bw.com.yunifangstore.adapter.CommonAdapter;
import bw.com.yunifangstore.adapter.DetailsAdapter;
import bw.com.yunifangstore.adapter.MyLargeAdapter;
import bw.com.yunifangstore.adapter.ViewHolder;
import bw.com.yunifangstore.base.BaseData;
import bw.com.yunifangstore.bean.DetailsData;
import bw.com.yunifangstore.bean.LargePictureData;
import bw.com.yunifangstore.intent.IntentWebActivity;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.DBUtils;
import bw.com.yunifangstore.utils.ImageLoaderUtils;
import bw.com.yunifangstore.utils.URLUtils;
import bw.com.yunifangstore.view.MyListView;
import bw.com.yunifangstore.view.ShowingPage;

public class DetailsActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView iv_title_back;
    private ImageView share;
    private ImageView but_title_shopping;
    private LinearLayout goods_dot_llt;
    private ViewPager large_viewPager;
    private TextView goods_name;
    private TextView goods_shoucang_tv;
    private ImageView goods_juan;
    private ImageView goods_di_img;
    TextView goods_shop_price;
    private TextView goods_first_price;
    private TextView goodsInfo_sales_volume;
    private TextView goodsInfo_collect_count;
    private String id;
    private DetailsData.DataBean.GoodsBean goods;
    private DetailsData detailsData;
    private RadioButton goods_comment_tv;
    private LargePictureData[] largePictureDatas;
    int[] dotArray = {R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    private ArrayList<String> imageUrlList = new ArrayList<>();
    private ArrayList<ImageView> dotList = new ArrayList<>();
    private TextView goods_add_shopping_tv;
    private Button sure_goods;
    private PopupWindow popupWindow;
    private ImageView colse_detailpop;
    private TextView goods_kefu_tv;
    private TextView goods_buy_tv;
    private boolean isWho = false;
    private boolean isSure = false;
    private MyListView myListView;
    private MyListView item_myListView;
    private TextView shoppingcar_alertNum;
    private ImageView shoppingcar_alertSubtra;
    private ImageView shoppingcar_alertAdd;
    private int goodsCounts = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //初始化控件
        initView();
        //请求数据
        requestData();
    }


    /**
     * 请求数据
     */
    private void requestData() {
        new BaseData() {
            @Override
            public void setResultData(String data) {
                Gson gson = new Gson();
                detailsData = gson.fromJson(data, DetailsData.class);
                goods = detailsData.getData().getGoods();
                //赋值
                setData();
                addLargePicture();
                //产品详情
                largePictureDatas = gson.fromJson(goods.getGoods_desc(), LargePictureData[].class);
                setDetailData();
            }

            @Override
            protected void setResulttError(ShowingPage.StateType stateLoadError) {
            }
        }.getData(URLUtils.DetailGoodsUrl + id, "", 0, BaseData.NOTIME);
    }

    /**
     * 添加大图
     */
    private void addLargePicture() {
        List<DetailsData.DataBean.GoodsBean.GalleryBean> gallery = goods.getGallery();
        imageUrlList.clear();
        for (int i = 0; i < gallery.size(); i++) {
            imageUrlList.add(gallery.get(i).getNormal_url());
        }
        dotList.clear();
        goods_dot_llt.removeAllViews();
        for (int i = 0; i < gallery.size(); i++) {
            ImageView imageView = new ImageView(DetailsActivity.this);
            if (i == 0) {
                imageView.setImageResource(dotArray[0]);
            } else {
                imageView.setImageResource(dotArray[1]);
            }
            dotList.add(imageView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(15, 10, 15, 10);
            goods_dot_llt.addView(imageView, layoutParams);
        }
        MyLargeAdapter myLargeAdapter = new MyLargeAdapter(imageUrlList, DetailsActivity.this);
        large_viewPager.setAdapter(myLargeAdapter);
        large_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotList.size(); i++) {
                    if (position % imageUrlList.size() == i) {
                        dotList.get(i).setImageResource(dotArray[0]);
                    } else {
                        dotList.get(i).setImageResource(dotArray[1]);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 添加数据
     */
    private void setData() {
        goods_shop_price.setText("￥" + goods.getShop_price());
        goods_name.setText(goods.getGoods_name());
        goods_first_price.setText("￥" + goods.getMarket_price());
        goods_first_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        goodsInfo_sales_volume.setText(goods.getSales_volume() + "");
        goodsInfo_collect_count.setText(goods.getCollect_count() + "");

        goods_comment_tv.setText("评论(" + detailsData.getData().getCommentNumber() + ")");
//        b-is_coupon_allowed : true优惠劵   n-commentNumber : 16评论数  //b-collected : false是否收藏
        final List<DetailsData.DataBean.ActivityBean> activitylist = detailsData.getData().getActivity();
        item_myListView.setAdapter(new CommonAdapter<DetailsData.DataBean.ActivityBean>(DetailsActivity.this, activitylist, R.layout.detail_itemgoods) {
            @Override
            public void convert(ViewHolder helper, DetailsData.DataBean.ActivityBean item) {
                helper.setText(R.id.goods_tv1, item.getTitle());
            }
        });
        item_myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = activitylist.get(position).getDescription();
                IntentWebActivity.intentWebActivity(DetailsActivity.this, description);
                overridePendingTransition(R.anim.login_in, R.anim.login_in0);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        share = (ImageView) findViewById(R.id.share);
        share.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("商品详情");
        but_title_shopping = (ImageView) findViewById(R.id.but_title_shopping);
        but_title_shopping.setVisibility(View.VISIBLE);
        but_title_shopping.setOnClickListener(this);
        //大图
        goods_dot_llt = (LinearLayout) findViewById(R.id.goods_dot);
        large_viewPager = (ViewPager) findViewById(R.id.large_ViewPager);
        //名字
        goods_name = (TextView) findViewById(R.id.goods_name);
        //新价格
        goods_shop_price = (TextView) findViewById(R.id.goods_shop_price);
        //旧价格
        goods_first_price = (TextView) findViewById(R.id.goods_first_price);
        //销量
        goodsInfo_sales_volume = (TextView) findViewById(R.id.goodsInfo_sales_volume);
        //收藏人数
        goodsInfo_collect_count = (TextView) findViewById(R.id.goodsInfo_collect_count);
        //收藏
        goods_shoucang_tv = (TextView) findViewById(R.id.goods_shoucang_tv);
        goods_shoucang_tv.setOnClickListener(this);
        //劵
        goods_juan = (ImageView) findViewById(R.id.goods_juan);
        //抵
        goods_di_img = (ImageView) findViewById(R.id.goods_di_img);

        //评论数
        goods_comment_tv = (RadioButton) findViewById(R.id.goods_comment_tv);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setOnClickListener(this);
        }
        //那两行
        myListView = (MyListView) findViewById(R.id.myListView);
        item_myListView = (MyListView) findViewById(R.id.item_myListView);

        //加入购物车
        goods_add_shopping_tv = (TextView) findViewById(R.id.goods_add_shopping_tv);
        goods_add_shopping_tv.setOnClickListener(this);
        //客服
        goods_kefu_tv = (TextView) findViewById(R.id.goods_kefu_tv);
        goods_kefu_tv.setOnClickListener(this);
        //立即购买
        goods_buy_tv = (TextView) findViewById(R.id.goods_buy_tv);
        goods_buy_tv.setOnClickListener(this);

    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //收藏
            case R.id.goods_shoucang_tv:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                View shareView = initGoodsShareView();
                addGoodsCart(shareView);
                break;

            //产品详情
            case R.id.goods_goods_tv:
                setDetailData();
                break;
            //评论
            case R.id.goods_comment_tv:
                setCommentsData();
                break;
            //产品参数
            case R.id.goods_parameter_tv:
                setAttributesData();
                break;
            //购物车
            case R.id.goods_add_shopping_tv:
                isWho = true;
                View view = initPopWindowView();
                addGoodsCart(view);
                break;
            //添加购物车
            case R.id.sure_goods:
                if (goods.getRestrict_purchase_num() == 0) {
                    Toast.makeText(this, "此商品限货", Toast.LENGTH_SHORT).show();
                } else {
                    if (isWho) {
                        isSure = true;
                        Toast.makeText(DetailsActivity.this, "恭喜,添加购物车成功。", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailsActivity.this, "立即购买", Toast.LENGTH_SHORT).show();
                    }
                }

                popupWindow.dismiss();
                break;
            //关闭
            case R.id.colse_detailpop:
                popupWindow.dismiss();
                break;
            //立即购买
            case R.id.goods_buy_tv:
                isWho = false;
                View viewbuy = initPopWindowView();
                addGoodsCart(viewbuy);
                break;
            //客服
            case R.id.goods_kefu_tv:
                break;
            //减少
            case R.id.shoppingcar_alertSubtra:
                goodsCounts--;
                if (goodsCounts < 1) {
                    goodsCounts = 1;
                    shoppingcar_alertNum.setText(goodsCounts + "");
                } else {
                    shoppingcar_alertNum.setText(goodsCounts + "");
                }

                break;
            //增加
            case R.id.shoppingcar_alertAdd:
                goodsCounts++;
                if (goodsCounts > goods.getRestrict_purchase_num()) {
                    goodsCounts = goods.getRestrict_purchase_num();
                    shoppingcar_alertNum.setText(goodsCounts + "");
                } else {
                    shoppingcar_alertNum.setText(goodsCounts + "");
                }
                break;
            //跳转到购物车
            case R.id.but_title_shopping:
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                intent.putExtra("cart", true);
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isSure) {
            try {
                goods.setGoodsCounts(goodsCounts);
                DBUtils.getDb().saveOrUpdate(goods);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 分享
     */
    private View initGoodsShareView() {
        View view = CommonUtils.inflate(R.layout.pop_share);

        return view;
    }

    private void addGoodsCart(View view) {
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        popupWindow.showAtLocation(goods_add_shopping_tv, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.4f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
    }

    /**
     * 购买和加入购物车的控件
     *
     * @return
     */
    private View initPopWindowView() {
        View view = CommonUtils.inflate(R.layout.detail_popwindow);

        ImageView shoppingcar_alertImage = (ImageView) view.findViewById(R.id.shoppingcar_alertImage);
//add_delete_llt 库存 价格n-shop_price   restrict_purchase_num限购
        ImageLoader.getInstance().displayImage(goods.getGoods_img(), shoppingcar_alertImage, ImageLoaderUtils.initOptions());
        // 关闭
        colse_detailpop = (ImageView) view.findViewById(R.id.colse_detailpop);
        colse_detailpop.setOnClickListener(this);

        //价格
        TextView shoppingcar_alertPrice = (TextView) view.findViewById(R.id.shoppingcar_alertPrice);
        shoppingcar_alertPrice.setText("￥" + goods.getShop_price());
        //库存数
        TextView tv_kucun_sum = (TextView) view.findViewById(R.id.tv_kucun_sum);
        tv_kucun_sum.setText("库存" + goods.getStock_number() + "件");
        //限购
        TextView xiangou = (TextView) view.findViewById(R.id.xiangou);
        xiangou.setText("限购" + goods.getRestrict_purchase_num() + "件");
        //确定购物
        sure_goods = (Button) view.findViewById(R.id.sure_goods);
        sure_goods.setOnClickListener(this);

        //真实购买数
        shoppingcar_alertNum = (TextView) view.findViewById(R.id.shoppingcar_alertNum);
        shoppingcar_alertNum.setText(goodsCounts + "");
        //减少
        shoppingcar_alertSubtra = (ImageView) view.findViewById(R.id.shoppingcar_alertSubtra);
        shoppingcar_alertSubtra.setOnClickListener(this);
        //增加
        shoppingcar_alertAdd = (ImageView) view.findViewById(R.id.shoppingcar_alertAdd);
        shoppingcar_alertAdd.setOnClickListener(this);
        if (goods.isIs_coupon_allowed()) {
            goods_juan.setVisibility(View.VISIBLE);
        } else {
            goods_juan.setVisibility(View.GONE);
        }
        if (goods.isReservable()) {
            goods_di_img.setVisibility(View.VISIBLE);
        } else {
            goods_di_img.setVisibility(View.GONE);
        }

        return view;
    }

    /**
     * 背景变暗
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);

    }

    private void setCommentsData() {
        List<DetailsData.DataBean.CommentsBean> commentsList = detailsData.getData().getComments();
        CommentsCommonAdapter commentsCommonAdapter = new CommentsCommonAdapter(DetailsActivity.this, commentsList, R.layout.comments_item);
        myListView.setAdapter(commentsCommonAdapter);
    }

    private void setAttributesData() {
        List<DetailsData.DataBean.GoodsBean.AttributesBean> attributesList = goods.getAttributes();
        myListView.setAdapter(new CommonAdapter<DetailsData.DataBean.GoodsBean.AttributesBean>(DetailsActivity.this, attributesList, R.layout.attributes_item) {
            @Override
            public void convert(ViewHolder helper, DetailsData.DataBean.GoodsBean.AttributesBean item) {
                helper.setText(R.id.attr_name1, item.getAttr_name());
                helper.setText(R.id.attr_value1, item.getAttr_value());
            }
        });
    }

    private void setDetailData() {
        myListView.setAdapter(new DetailsAdapter(largePictureDatas, DetailsActivity.this));
    }
}
