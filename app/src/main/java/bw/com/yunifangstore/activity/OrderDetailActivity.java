package bw.com.yunifangstore.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import bw.com.yunifangstore.R;

public class OrderDetailActivity extends AutoLayoutActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initView();

    }

    private void initView() {
        findViewById(R.id.but_title_left_image).setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.detail_order);
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
