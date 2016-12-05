package bw.com.yunifangstore.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.zhy.autolayout.AutoLayoutActivity;

import bw.com.yunifangstore.R;

public class SettingActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView but_title_left_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        but_title_left_image = (ImageView) findViewById(R.id.but_title_left_image);
        but_title_left_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_title_left_image:
                finish();
                break;
        }
    }
}
