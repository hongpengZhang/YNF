package bw.com.yunifangstore.fragment;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.CommonAdapter;
import bw.com.yunifangstore.adapter.ViewHolder;
import bw.com.yunifangstore.base.BaseData;
import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.bean.CategoryData;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.URLUtils;
import bw.com.yunifangstore.view.MyGridView;
import bw.com.yunifangstore.view.ShowingPage;

/**
 * Created by zhiyuan on 16/9/28.
 */
public class CategoryFragment extends BaseFragment {
    private String data;
    private GridView skin_myGridView;
    private CategoryData categoryData;
    private TextView tv_skinName;
    private MyGridView category_last_gridView;

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

    private void setStarData() {
        List<CategoryData.DataBean.GoodsBriefBean> goodsBriefList = categoryData.getData().getGoodsBrief();
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
    }

    private void setSkinGridViewData() {
        final int[] colorList = new int[]{0xFFDEDEB9, 0xFFEED4C2, 0xFFD4D7F7, 0xFFD1E1C3, 0xFFF1D2CB, 0xFFBFF1EC};
        List<CategoryData.DataBean.CategoryBean.ChildrenBean> childrenList = categoryData.getData().getCategory().get(2).getChildren();
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

    }

    @NonNull
    private View initView() {
        View view = CommonUtils.inflate(R.layout.categoryfragment_layout);
        skin_myGridView = (GridView) view.findViewById(R.id.skin_MyGridView);
        tv_skinName = (TextView) view.findViewById(R.id.tv_skin);
        category_last_gridView = (MyGridView) view.findViewById(R.id.category_last_gridView);
        return view;
    }

    class MyCategoryData extends BaseData {
        @Override
        public void setResultData(String data) {
            CategoryFragment.this.data = data;
            Gson gson = new Gson();
            categoryData = gson.fromJson(data, CategoryData.class);
            CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }
    }
}
