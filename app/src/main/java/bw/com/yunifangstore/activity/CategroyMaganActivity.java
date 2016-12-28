package bw.com.yunifangstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.CategoryData;
import bw.com.yunifangstore.factory.MoreTitleFragmentFactory;
import bw.com.yunifangstore.utils.URLUtils;

public class CategroyMaganActivity extends AutoLayoutActivity implements View.OnClickListener {

    private RadioGroup maganradioGroup;
    private ViewPager viewPager;
    private ImageView but_title_left_image;
    private TextView tv_title;
    private List<CategoryData.DataBean.CategoryBean.ChildrenBean> list;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_categroy_magan);
        initView();
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        tv_title.setText(title);
        list = (List<CategoryData.DataBean.CategoryBean.ChildrenBean>) intent.getSerializableExtra("list");
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                RadioButton radioButton = (RadioButton) View.inflate(this, R.layout.radio_item, null);
                radioButton.setText(list.get(i).getCat_name());
                maganradioGroup.addView(radioButton, layoutParams);
            }
            //添加适配器
            setViewPagerAdapter();
            //滑动监听
            setScrollPage();
            maganradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    for (int i = 0; i < maganradioGroup.getChildCount(); i++) {
                        RadioButton radioButton = (RadioButton) maganradioGroup.getChildAt(i);
                        if (radioButton.isChecked()) {
                            viewPager.setCurrentItem(i);
                        }
                    }
                }
            });

            setRadioPosition(getIntent().getIntExtra("position", 0));
            viewPager.setCurrentItem(getIntent().getIntExtra("position", 0));

        }
    }

    private void setScrollPage() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setRadioPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setViewPagerAdapter() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragmentInstance = MoreTitleFragmentFactory.getFragmentInstance(URLUtils.categoryTypeUrl + list.get(position).getId(), URLUtils.categoryTypeUrl + list.get(position).getId());
                return fragmentInstance;
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }

    private void setRadioPosition(int position) {

        ((RadioButton) maganradioGroup.getChildAt(position)).setChecked(true);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        but_title_left_image = (ImageView) findViewById(R.id.but_title_left_image);
        but_title_left_image.setOnClickListener(this);
        //标题
        tv_title = (TextView) findViewById(R.id.tv_title);
        maganradioGroup = (RadioGroup) findViewById(R.id.magan_radioGroup);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

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
