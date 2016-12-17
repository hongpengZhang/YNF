package bw.com.yunifangstore.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.activity.AllGoodsActivity;
import bw.com.yunifangstore.activity.DetailsActivity;
import bw.com.yunifangstore.adapter.ActivityInfoAdapter;
import bw.com.yunifangstore.adapter.CommonAdapter;
import bw.com.yunifangstore.adapter.MyCommonAdapter;
import bw.com.yunifangstore.adapter.RecyclerViewAdapter;
import bw.com.yunifangstore.adapter.RotateDownPageTransformer;
import bw.com.yunifangstore.adapter.ViewHolder;
import bw.com.yunifangstore.base.BaseData;
import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.bean.RoolData;
import bw.com.yunifangstore.intent.IntentWebActivity;
import bw.com.yunifangstore.interfaceclass.OnItemClickListener;
import bw.com.yunifangstore.interfaceclass.OnPageClickListener;
import bw.com.yunifangstore.utils.URLUtils;
import bw.com.yunifangstore.view.MyGridView;
import bw.com.yunifangstore.view.MyListView;
import bw.com.yunifangstore.view.RoolViewPager;
import bw.com.yunifangstore.view.ShowingPage;


/**
 * Created by zhiyuan on 16/9/28.
 */
public class HomeFragment extends BaseFragment implements SpringView.OnFreshListener, View.OnClickListener {
    private String data;
    private RoolViewPager roolViewPager;
    int[] dotArray = {R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    private ArrayList<String> imageUrlList = new ArrayList<>();
    private ArrayList<ImageView> dotList = new ArrayList<>();
    private RoolData roolData;
    private LinearLayout ll_layoutdots;
    private SpringView springView;
    private GridView ad5_gridView;
    private RecyclerView recyclerView;
    private ViewPager activityInfo;
    private MyListView myListView;
    private MyGridView lastGridView;
    private View queryGoods;
    private RelativeLayout benWork_rlayout;
    private View view;
    private List<RoolData.DataBean.Ad1Bean> ad1List;
    private MyHomeData myHomeData;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    public void onLoad() {
        myHomeData = new MyHomeData();
        myHomeData.getData(URLUtils.homeUrl, URLUtils.homeArgs, 0, BaseData.NORMALTIME);
    }

    @Override
    public View createSuccessView() {
        view = initView();
        //1.无限轮播
        setRoolViewPager();
        //2.四项专区
        setTaskData();
        //3.本周热销
        setRecyclerViewData();
        //4.优惠活动
        setActivityInfo();
        //5热门专题
        setMyListViewData();
        //6最后镇点之宝
        setMyGridViewData();
        return view;
    }

    private void setMyGridViewData() {
        final List<RoolData.DataBean.DefaultGoodsListBean> defaultGoodsList = roolData.getData().getDefaultGoodsList();
        CommonAdapter<RoolData.DataBean.DefaultGoodsListBean> commonAdapter = new CommonAdapter<RoolData.DataBean.DefaultGoodsListBean>(getActivity(), defaultGoodsList, R.layout.last_mygridview_item) {
            @Override
            public void convert(ViewHolder helper, RoolData.DataBean.DefaultGoodsListBean item) {
                helper.setImageByUrl(R.id.lastgv_img, item.getGoods_img());
                helper.setText(R.id.last_gv_name, item.getEfficacy());
                TextView last_gv_oldprice = helper.getView(R.id.last_gv_oldprice);
                last_gv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                helper.setText(R.id.last_gv_oldprice, "￥" + item.getMarket_price());
                helper.setText(R.id.last_gv_newprice, "￥" + item.getShop_price());
                helper.setText(R.id.last_tvdes, item.getGoods_name());
            }
        };
        lastGridView.setAdapter(commonAdapter);
        lastGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("id", defaultGoodsList.get(position).getId());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);

            }
        });
    }

    private void setMyListViewData() {
        final List<RoolData.DataBean.SubjectsBean> subjectsList = roolData.getData().getSubjects();

        MyCommonAdapter myCommonAdapter = new MyCommonAdapter(getActivity(), subjectsList, R.layout.hot_mylistview_item);
        myListView.setAdapter(myCommonAdapter);
    }

    private void setActivityInfo() {
        activityInfo.setAdapter(new ActivityInfoAdapter(getActivity(), roolData.getData().getActivityInfo().getActivityInfoList()));
        //设置缓存的页面数量
        activityInfo.setOffscreenPageLimit(3);
        activityInfo.setCurrentItem(5000);
        activityInfo.setPageTransformer(false, new RotateDownPageTransformer());

    }


    private void setTaskData() {
        final List<RoolData.DataBean.Ad5Bean> ad5List = roolData.getData().getAd5();
        CommonAdapter<RoolData.DataBean.Ad5Bean> commonAdapter = new CommonAdapter<RoolData.DataBean.Ad5Bean>(getActivity(), ad5List, R.layout.ad5_grid_item) {
            @Override
            public void convert(ViewHolder helper, RoolData.DataBean.Ad5Bean item) {
                helper.setImageByUrl(R.id.ad5_grid_iv, item.getImage());
                helper.setText(R.id.ad5_grid_tv, item.getTitle());
            }
        };
        ad5_gridView.setAdapter(commonAdapter);
        ad5_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentWebActivity.intentWebActivity(getActivity(), ad5List.get(position).getAd_type_dynamic_data());
            }
        });

    }

    /**
     * 还没做完跳转的界面
     */
    private void setRecyclerViewData() {
        if (roolData.getData().getBestSellers() == null) {
            AutoLinearLayout bzrx = (AutoLinearLayout) view.findViewById(R.id.bzrx);
            bzrx.setVisibility(View.GONE);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerViewAdapter = new RecyclerViewAdapter(roolData.getData().getBestSellers().get(0), getActivity());
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void setOnItemClickListener(int potision) {
                    String id = roolData.getData().getBestSellers().get(0).getId();
                   /* Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);*/
                }
            });
        }

    }

    /**
     * 无限轮播的图片
     */
    private void setRoolViewPager() {

        ad1List = roolData.getData().getAd1();
        for (int i = 0; i < ad1List.size(); i++) {
            imageUrlList.add(ad1List.get(i).getImage());
        }
        initDots(ad1List);
    }

    /**
     * 初始化小点
     */
    private void initDots(final List<RoolData.DataBean.Ad1Bean> ad1List) {
        dotList.clear();
        ll_layoutdots.removeAllViews();
        for (int i = 0; i < ad1List.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            if (i == 0) {
                imageView.setImageResource(dotArray[0]);
            } else {
                imageView.setImageResource(dotArray[1]);
            }
            dotList.add(imageView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(15, 10, 15, 10);
            ll_layoutdots.addView(imageView, layoutParams);
        }
        /**
         * 传送数据并相应跳转到webView
         */
        roolViewPager.initData(imageUrlList, dotList, dotArray, new OnPageClickListener() {
            @Override
            public void setOnPage(int position) {
                IntentWebActivity.intentWebActivity(getActivity(), ad1List.get(position).getAd_type_dynamic_data());

            }
        });
        roolViewPager.setRoolAdapter();
    }

    /**
     * 初始化控件
     */
    @NonNull
    private View initView() {

        View view = View.inflate(getActivity(), R.layout.homefragment_layout, null);
        roolViewPager = (RoolViewPager) view.findViewById(R.id.roolViewPager);
        ll_layoutdots = (LinearLayout) view.findViewById(R.id.ll_layoutdots);
        activityInfo = (ViewPager) view.findViewById(R.id.activityInfo);
        ad5_gridView = (GridView) view.findViewById(R.id.ad5_gridView);

        //SpringView
        benWork_rlayout = (RelativeLayout) view.findViewById(R.id.benWork_rlayout);
        springView = (SpringView) view.findViewById(R.id.springView);
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setListener(this);
        //设置隐藏
        springView.setType(SpringView.Type.FOLLOW);

        //RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //MyListView
        myListView = (MyListView) view.findViewById(R.id.hot_myListView);
        //MyGridView
        lastGridView = (MyGridView) view.findViewById(R.id.last_gridView);
        queryGoods = view.findViewById(R.id.query_goods);
        queryGoods.setOnClickListener(this);
        return view;
    }

    /**
     * 单击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //所有商品
            case R.id.query_goods:
                Intent intent = new Intent(getActivity(), AllGoodsActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        myHomeData.getData(URLUtils.homeUrl, URLUtils.homeArgs, 0, BaseData.NORMALTIME);
        springView.onFinishFreshAndLoad();
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadmore() {

    }


    /**
     * 请求数据的类进行分装
     */
    public class MyHomeData extends BaseData {

        @Override
        public void setResultData(String data) {
            //先设置数据
            HomeFragment.this.data = data;
            Gson gson = new Gson();
            roolData = gson.fromJson(data, RoolData.class);
            //再展示
            HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        }

        /**
         * 数据出错data有可能为空
         */
        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }
    }
}
