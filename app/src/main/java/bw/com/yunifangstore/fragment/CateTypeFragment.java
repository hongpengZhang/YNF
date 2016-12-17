package bw.com.yunifangstore.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.CategroyTypeAdapter;
import bw.com.yunifangstore.base.BaseData;
import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.bean.CategroyTypeBean;
import bw.com.yunifangstore.intent.IntentDetailActivity;
import bw.com.yunifangstore.interfaceclass.OnItemClickListener;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.view.ShowingPage;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/14.
 */

public class CateTypeFragment extends BaseFragment {

    private String url;
    private RecyclerView type_recyclerView;
    private List<CategroyTypeBean.DataBean> list = new ArrayList<>();

    @Override
    public void onLoad() {
        CateTypeBaseData cateTypeBaseData = new CateTypeBaseData();
        url = getArguments().getString("url");
        cateTypeBaseData.getData(url, "", 0, BaseData.NOTIME);
    }

    @Override
    protected View createSuccessView() {
        View view = CommonUtils.inflate(R.layout.catetypefragment_layout);
        initView(view);
        setAdapter();
        return view;
    }

    private void setAdapter() {
        CategroyTypeAdapter categroyTypeAdapter = new CategroyTypeAdapter(getActivity(), list);
        type_recyclerView.setAdapter(categroyTypeAdapter);
        categroyTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            //单击跳转到详情
            @Override
            public void setOnItemClickListener(int potision) {
                String id = list.get(potision).getId();
                IntentDetailActivity.intentDetailActivity(getActivity(), id);

            }

        });
    }

    private void initView(View view) {
        type_recyclerView = (RecyclerView) view.findViewById(R.id.type_recyclerView);
        type_recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }


    class CateTypeBaseData extends BaseData {

        @Override
        public void setResultData(String data) {
            Gson gson = new Gson();
            CategroyTypeBean typeBean = gson.fromJson(data, CategroyTypeBean.class);
            List<CategroyTypeBean.DataBean> beanList = typeBean.getData();
            list.addAll(beanList);
            CateTypeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            CateTypeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }
    }

}

