package bw.com.yunifangstore.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.AllgoodsAdapter;
import bw.com.yunifangstore.base.BaseData;
import bw.com.yunifangstore.bean.QueryGoods;
import bw.com.yunifangstore.intent.IntentDetailActivity;
import bw.com.yunifangstore.interfaceclass.OnItemClickListener;
import bw.com.yunifangstore.utils.URLUtils;
import bw.com.yunifangstore.view.ShowingPage;

public class AllGoodsActivity extends AutoLayoutActivity implements View.OnClickListener, SpringView.OnFreshListener {
    private ImageView but_title_left_image;
    private RadioGroup radioGroup;
    private ArrayList<QueryGoods.DataBean> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private AllgoodsAdapter allgoodsAdapter;
    private SpringView goods_springView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_goods);
        initView();
        requestData();
        setAdapter();
    }

    /**
     * 添加适配器
     */
    private void setAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        allgoodsAdapter = new AllgoodsAdapter(list, this);
        recyclerView.setAdapter(allgoodsAdapter);
        allgoodsAdapter.setOnItemClickListener(new OnItemClickListener() {
            //单击跳转到详情
            @Override
            public void setOnItemClickListener(int potision) {
                String id = list.get(potision).getId();
                IntentDetailActivity.intentDetailActivity(AllGoodsActivity.this, id);
            }

        });
    }

    /**
     * 找控件
     */
    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("全部商品");
        but_title_left_image = (ImageView) findViewById(R.id.but_title_left_image);
        but_title_left_image.setOnClickListener(this);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setOnClickListener(this);
        }
        recyclerView = (RecyclerView) findViewById(R.id.allgoods_recyclerView);
        goods_springView = (SpringView) findViewById(R.id.goods_SpringView);
        goods_springView.setHeader(new DefaultHeader(AllGoodsActivity.this,R.mipmap.takeout_img_list_loading_pic1,R.mipmap.takeout_img_list_loading_pic2));
        goods_springView.setType(SpringView.Type.FOLLOW);
        goods_springView.setListener(this);
    }

    /**
     * 请求数据
     */
    private void requestData() {
        BaseData baseData = new BaseData() {
            @Override
            public void setResultData(String data) {
                Gson gson = new Gson();
                QueryGoods queryGoods = gson.fromJson(data, QueryGoods.class);
                List<QueryGoods.DataBean> goodsList = queryGoods.getData();
                list.clear();
                list.addAll(goodsList);
            }

            @Override
            protected void setResulttError(ShowingPage.StateType stateLoadError) {
                Toast.makeText(AllGoodsActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        };
        baseData.getData(URLUtils.AllGoodsUrl, URLUtils.AllGoodsArgs, 0, BaseData.NORMALTIME);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_title_left_image:
                finish();
               overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //正常排序
            case R.id.normal_order:
                requestData();
                setAdapternotifyDataSetChanged();
                break;
            //升序排序
            case R.id.asc_order:
                Collections.sort(list);
                setAdapternotifyDataSetChanged();
                break;
            //降序排序
            case R.id.desc_order:
                Collections.sort(list);
                Collections.reverse(list);
                setAdapternotifyDataSetChanged();
                break;
        }
    }

    /**
     * 刷洗适配器
     */
    private void setAdapternotifyDataSetChanged() {
        allgoodsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setAdapternotifyDataSetChanged();
                goods_springView.onFinishFreshAndLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadmore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setAdapternotifyDataSetChanged();
                goods_springView.onFinishFreshAndLoad();
            }
        }, 2000);
    }
}
