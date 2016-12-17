package bw.com.yunifangstore.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.CommonAdapter;
import bw.com.yunifangstore.adapter.ViewHolder;
import bw.com.yunifangstore.bean.RoolData;
import bw.com.yunifangstore.view.MyGridView;

public class SubJectActivity extends AutoLayoutActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageView but_title_left_image;
    private TextView sub_tv_title;
    private TextView tv_datail;

    private MyGridView sub_gridView;
    private List<RoolData.DataBean.SubjectsBean.GoodsListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sub_ject);
        initView();
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String detail = intent.getStringExtra("detail");
        sub_tv_title.setText("#" + title + "#");
        tv_datail.setText(detail);
        list = (List<RoolData.DataBean.SubjectsBean.GoodsListBean>) intent.getSerializableExtra("list");
        initData();

    }

    private void initData() {
        CommonAdapter<RoolData.DataBean.SubjectsBean.GoodsListBean> commonAdapter = new CommonAdapter<RoolData.DataBean.SubjectsBean.GoodsListBean>(SubJectActivity.this, list, R.layout.last_mygridview_item) {
            @Override
            public void convert(ViewHolder helper, RoolData.DataBean.SubjectsBean.GoodsListBean item) {

                helper.setImageByUrl(R.id.lastgv_img, item.getGoods_img());
                helper.setText(R.id.last_gv_name,item.getEfficacy());
                TextView last_gv_oldprice = helper.getView(R.id.last_gv_oldprice);
                last_gv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                helper.setText(R.id.last_gv_oldprice, "￥" + item.getMarket_price());
                helper.setText(R.id.last_gv_newprice, "￥" + item.getShop_price());
                helper.setText(R.id.last_tvdes,item.getGoods_name());
            }
        };
        sub_gridView.setAdapter(commonAdapter);
        sub_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(SubJectActivity.this,DetailsActivity.class);
                intent.putExtra("id",list.get(position).getId());
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.login_in0);

            }
        });
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("专题");
        but_title_left_image = (ImageView) findViewById(R.id.but_title_left_image);
        but_title_left_image.setOnClickListener(this);
        sub_tv_title = (TextView) findViewById(R.id.sub_tv_title);
        tv_datail = (TextView) findViewById(R.id.tv_datail);
        sub_gridView = (MyGridView) findViewById(R.id.sub_gridView);

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
}
