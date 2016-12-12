package bw.com.yunifangstore.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.AttributesAdapter;
import bw.com.yunifangstore.adapter.CommentsAdapter;
import bw.com.yunifangstore.adapter.DetailsPicAdapter;
import bw.com.yunifangstore.adapter.MyLargeAdapter;
import bw.com.yunifangstore.base.BaseData;
import bw.com.yunifangstore.bean.DetailsData;
import bw.com.yunifangstore.bean.LargePictureData;
import bw.com.yunifangstore.utils.URLUtils;
import bw.com.yunifangstore.view.FullyLinearLayoutManager;
import bw.com.yunifangstore.view.ShowingPage;

public class DetailsActivity extends AutoLayoutActivity implements View.OnClickListener {

    private static final String TAG = "TAG";
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
    private TextView goods_tv1;
    private TextView goods_tv2;
    private TextView goods_tv3;
    private DetailsData detailsData;
    private RadioButton goods_comment_tv;
    private LargePictureData[] largePictureDatas;
    int[] dotArray = {R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    private ArrayList<String> imageUrlList = new ArrayList<>();
    private ArrayList<ImageView> dotList = new ArrayList<>();
    private RecyclerView myRecycleView;

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
        goods_tv1.setText(detailsData.getData().getActivity().get(0).getTitle());
        goods_tv2.setText(detailsData.getData().getActivity().get(1).getTitle());
        goods_tv3.setText(detailsData.getData().getActivity().get(2).getTitle());
        goods_comment_tv.setText("评论(" + detailsData.getData().getCommentNumber() + ")");
//        b-is_coupon_allowed : true优惠劵   n-commentNumber : 16评论数  //b-collected : false是否收藏
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
        //下方三行字
        goods_tv1 = (TextView) findViewById(R.id.goods_tv1);
        goods_tv2 = (TextView) findViewById(R.id.goods_tv2);
        goods_tv3 = (TextView) findViewById(R.id.goods_tv3);
        goods_tv1.setOnClickListener(this);
        goods_tv2.setOnClickListener(this);
        goods_tv3.setOnClickListener(this);
        //评论数
        goods_comment_tv = (RadioButton) findViewById(R.id.goods_comment_tv);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setOnClickListener(this);
        }
        myRecycleView = (RecyclerView) findViewById(R.id.myRecycleView);
        myRecycleView.setMinimumHeight(1000);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            //收藏
            case R.id.goods_shoucang_tv:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.but_title_shopping:
                Toast.makeText(this, "购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.goods_tv1:
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.goods_tv2:
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.goods_tv3:
                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
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

        }
    }

    private void setCommentsData() {
        List<DetailsData.DataBean.CommentsBean> commentsList = detailsData.getData().getComments();
        myRecycleView.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        myRecycleView.setLayoutManager(linearLayoutManager);
        myRecycleView.setAdapter(new CommentsAdapter(commentsList, DetailsActivity.this));

    }

    private void setAttributesData() {
        List<DetailsData.DataBean.GoodsBean.AttributesBean> attributes = goods.getAttributes();
        myRecycleView.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        myRecycleView.setLayoutManager(linearLayoutManager);
        myRecycleView.setAdapter(new AttributesAdapter(attributes, DetailsActivity.this));
    }

    private void setDetailData() {
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        myRecycleView.setNestedScrollingEnabled(false);
        //设置布局管理器
        myRecycleView.setLayoutManager(linearLayoutManager);
//        myRecycleView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
        DetailsPicAdapter detailsPicAdapter = new DetailsPicAdapter(largePictureDatas, DetailsActivity.this);
        myRecycleView.setAdapter(detailsPicAdapter);
    }
}
