package bw.com.yunifangstore.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.activity.AllGoodsActivity;
import bw.com.yunifangstore.activity.CategroyMaganActivity;
import bw.com.yunifangstore.activity.CategroyTypeActivity;
import bw.com.yunifangstore.adapter.CommonAdapter;
import bw.com.yunifangstore.adapter.ViewHolder;
import bw.com.yunifangstore.base.BaseData;
import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.bean.CategoryData;
import bw.com.yunifangstore.intent.IntentDetailActivity;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.URLUtils;
import bw.com.yunifangstore.view.MyGridView;
import bw.com.yunifangstore.view.ShowingPage;

/**
 * Created by zhiyuan on 16/9/28.
 */
public class CategoryFragment extends BaseFragment implements View.OnClickListener {
    private String data;
    private GridView skin_myGridView;
    private CategoryData categoryData;
    private TextView tv_skinName;
    private MyGridView category_last_gridView;
    private View query_goods;
    private ImageView category_two_img1;
    private ImageView category_two_img2;
    private ImageView category_two_img3;
    private ImageView category_two_img4;
    private ImageView category_two_img5;
    private ImageView category_two_img6;
    private List<CategoryData.DataBean.CategoryBean.ChildrenBean> childrenList;
    private List<CategoryData.DataBean.CategoryBean.ChildrenBean> categoryList;
    private ArrayList<CategoryData.DataBean.CategoryBean.ChildrenBean> list = new ArrayList<>();
    private int b = 0;
    private LinearLayout effect_llt_layout;

    @Override
    public void onLoad() {
        MyCategoryData myCategoryData = new MyCategoryData();
        myCategoryData.getData(URLUtils.categoryUrl, URLUtils.categoryArgs, 0, BaseData.NORMALTIME);

    }

    @Override
    public View createSuccessView() {
        View view = initView();
        //按功效
        setSkinGridViewData();
        //明星产品
        setStarData();
        return view;
    }

    /**
     * 明星产品
     */
    private void setStarData() {
        final List<CategoryData.DataBean.GoodsBriefBean> goodsBriefList = categoryData.getData().getGoodsBrief();
        CommonAdapter<CategoryData.DataBean.GoodsBriefBean> commonAdapter = new CommonAdapter<CategoryData.DataBean.GoodsBriefBean>(getActivity(), goodsBriefList, R.layout.last_mygridview_item) {
            @Override
            public void convert(ViewHolder helper, CategoryData.DataBean.GoodsBriefBean item) {
                helper.setImageByUrl(R.id.lastgv_img, item.getGoods_img());
                helper.setText(R.id.last_gv_name, item.getEfficacy());
                TextView last_gv_oldprice = helper.getView(R.id.last_gv_oldprice);
                last_gv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                helper.setText(R.id.last_gv_oldprice, "￥" + item.getMarket_price());
                helper.setText(R.id.last_gv_newprice, "￥" + item.getShop_price());
                helper.setText(R.id.last_tvdes, item.getGoods_name());

            }
        };
        category_last_gridView.setAdapter(commonAdapter);
        /**
         * 跳转到详情
         */
        category_last_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentDetailActivity.intentDetailActivity(getActivity(), goodsBriefList.get(position).getId());
            }
        });

    }

    /**
     * 按肤质
     */
    private void setSkinGridViewData() {
        final int[] colorList = new int[]{0xFFDEDEB9, 0xFFEED4C2, 0xFFD4D7F7, 0xFFD1E1C3, 0xFFF1D2CB, 0xFFBFF1EC};
        childrenList = categoryData.getData().getCategory().get(2).getChildren();
        CommonAdapter<CategoryData.DataBean.CategoryBean.ChildrenBean> commonAdapter = new CommonAdapter<CategoryData.DataBean.CategoryBean.ChildrenBean>(getActivity(), childrenList, R.layout.skin_recycle_griditem) {
            @Override
            public void convert(ViewHolder helper, CategoryData.DataBean.CategoryBean.ChildrenBean item) {
                tv_skinName.setText("—" + categoryData.getData().getCategory().get(2).getCat_name() + "—");
                helper.setText(R.id.fuzhi_item, "#" + item.getCat_name() + "#");
                int position = helper.getPosition();
                TextView fuzhi_item = helper.getView(R.id.fuzhi_item);
                fuzhi_item.setBackgroundColor(colorList[position]);
            }
        };
        skin_myGridView.setAdapter(commonAdapter);
        skin_myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intentMaganActivity(categoryData.getData().getCategory().get(2).getCat_name(), categoryData.getData().getCategory().get(2).getChildren(), position);
            }
        });
    }

    /**
     * 初始化控件
     */
    @NonNull
    private View initView() {
        View view = CommonUtils.inflate(R.layout.categoryfragment_layout);
        skin_myGridView = (GridView) view.findViewById(R.id.skin_MyGridView);
        tv_skinName = (TextView) view.findViewById(R.id.tv_skin);
        category_last_gridView = (MyGridView) view.findViewById(R.id.category_last_gridView);
        query_goods = view.findViewById(R.id.query_goods);
        query_goods.setOnClickListener(this);
        //面膜
        category_two_img1 = (ImageView) view.findViewById(R.id.category_two_img1);
        category_two_img1.setOnClickListener(this);
        //润肤水
        category_two_img2 = (ImageView) view.findViewById(R.id.category_two_img2);
        category_two_img2.setOnClickListener(this);
        //润肤乳
        category_two_img3 = (ImageView) view.findViewById(R.id.category_two_img3);
        category_two_img3.setOnClickListener(this);
        //洁面乳
        category_two_img4 = (ImageView) view.findViewById(R.id.category_two_img4);
        category_two_img4.setOnClickListener(this);
        //其他
        category_two_img5 = (ImageView) view.findViewById(R.id.category_two_img5);
        category_two_img5.setOnClickListener(this);
        //实惠套装
        category_two_img6 = (ImageView) view.findViewById(R.id.category_two_img6);
        category_two_img6.setOnClickListener(this);
        //按功效
        effect_llt_layout = (LinearLayout) view.findViewById(R.id.effect_llt_layout);
        for (int i = 0; i < effect_llt_layout.getChildCount(); i++) {
            effect_llt_layout.getChildAt(i).setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        //按功效
        for (int i = 0; i < effect_llt_layout.getChildCount(); i++) {
            ImageView childAt = (ImageView) effect_llt_layout.getChildAt(i);
            if (childAt.getId() == v.getId()) {
                b++;
                if (b == 1) {
                    intentMaganActivity(categoryData.getData().getCategory().get(0).getCat_name(), categoryData.getData().getCategory().get(0).getChildren(), i);
                    b = 0;
                } else {
                    return;
                }
            }
        }
        switch (v.getId()) {
            //跳转到详情
            case R.id.query_goods:
                Intent intent = new Intent(getActivity(), AllGoodsActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;
            //面膜
            case R.id.category_two_img1:

                list.add(categoryData.getData().getCategory().get(1).getChildren().get(6));
                list.add(categoryData.getData().getCategory().get(1).getChildren().get(7));
                list.add(categoryData.getData().getCategory().get(1).getChildren().get(8));
                intentMaganActivity(categoryData.getData().getCategory().get(1).getChildren().get(0).getCat_name(), list, 0);
                break;
            //润肤水
            case R.id.category_two_img2:
                intentCateTypeActivity(1);
                break;
            //润肤乳
            case R.id.category_two_img3:
                intentCateTypeActivity(2);
                break;
            //洁面乳
            case R.id.category_two_img4:
                intentCateTypeActivity(3);
                break;
            //其他
            case R.id.category_two_img5:
                intentCateTypeActivity(4);
                break;
            //实惠套装
            case R.id.category_two_img6:
                intentCateTypeActivity(5);
                break;
        }
    }

    /**
     * 跳转
     */
    private void intentMaganActivity(String title, List<CategoryData.DataBean.CategoryBean.ChildrenBean> list, int position) {
        Intent intent2 = new Intent(getActivity(), CategroyMaganActivity.class);
        intent2.putExtra("list", (Serializable) list);
        intent2.putExtra("title", title);
        intent2.putExtra("position", position);
        getActivity().startActivity(intent2);
        getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);
    }

    /**
     * 跳转到分类的类型
     */

    private void intentCateTypeActivity(int i) {
        Intent in = new Intent(getActivity(), CategroyTypeActivity.class);
        in.putExtra("typename", categoryList.get(i).getCat_name());
        in.putExtra("id", categoryList.get(i).getId() + "");
        startActivity(in);
        getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);

    }

    class MyCategoryData extends BaseData {
        @Override
        public void setResultData(String data) {
            CategoryFragment.this.data = data;
            Gson gson = new Gson();
            categoryData = gson.fromJson(data, CategoryData.class);
            categoryList = categoryData.getData().getCategory().get(1).getChildren();
            CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }
    }
}
