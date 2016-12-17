package bw.com.yunifangstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.CategroyTypeAdapter;
import bw.com.yunifangstore.base.BaseData;
import bw.com.yunifangstore.bean.CategroyTypeBean;
import bw.com.yunifangstore.intent.IntentDetailActivity;
import bw.com.yunifangstore.interfaceclass.OnItemClickListener;
import bw.com.yunifangstore.utils.URLUtils;
import bw.com.yunifangstore.view.ShowingPage;


public class CategroyTypeActivity extends AutoLayoutActivity implements View.OnClickListener, SpringView.OnFreshListener {

    private RecyclerView categroy_recyclerView;
    private SpringView categroy_springView;
    private String id;
    private List<CategroyTypeBean.DataBean> dataBeanList;
    private ArrayList<CategroyTypeBean.DataBean> list = new ArrayList<>();

    private CategroyTypeAdapter categroyTypeAdapter;
    private String typename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_categroy_type);
        Intent intent = getIntent();
        typename = intent.getStringExtra("typename");
        id = intent.getStringExtra("id");
        initView();
        requestData();
        setAdapter();
    }

    /**
     * 请求网络数据
     */
    private void requestData() {
        new BaseData() {

            @Override
            public void setResultData(String data) {
                Gson gson = new Gson();
                CategroyTypeBean typeBean = gson.fromJson(data, CategroyTypeBean.class);
                dataBeanList = typeBean.getData();
                list.addAll(dataBeanList);
            }

            @Override
            protected void setResulttError(ShowingPage.StateType stateLoadError) {

            }
        }.getData(URLUtils.categoryTypeUrl + id, URLUtils.categoryTypeArg, 0, BaseData.NOTIME);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        ImageView but_title_left_image = (ImageView) findViewById(R.id.but_title_left_image);
        but_title_left_image.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(typename);
        categroy_recyclerView = (RecyclerView) findViewById(R.id.categroy_recyclerView);
        categroy_springView = (SpringView) findViewById(R.id.categroy_springView);
        categroy_springView.setHeader(new DefaultHeader(this, R.mipmap.takeout_img_list_loading_pic1, R.mipmap.takeout_img_list_loading_pic2));
        categroy_springView.setType(SpringView.Type.FOLLOW);
        categroy_springView.setListener(this);
    }

    private void setAdapter() {
        categroy_recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categroyTypeAdapter = new CategroyTypeAdapter(CategroyTypeActivity.this, list);
        categroy_recyclerView.setAdapter(categroyTypeAdapter);
        categroyTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            //单击跳转到详情
            @Override
            public void setOnItemClickListener(int potision) {
                String id = list.get(potision).getId();
                IntentDetailActivity.intentDetailActivity(CategroyTypeActivity.this, id);


            }

        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_title_left_image:
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;

        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                categroy_springView.onFinishFreshAndLoad();
            }
        }, 2000);
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadmore() {
    }
}
