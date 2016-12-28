package bw.com.yunifangstore.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.fragment.OrderFragment;
import bw.com.yunifangstore.view.OrderLazyViewPager;


public class MyOrderActivity extends AutoLayoutActivity implements View.OnClickListener {
    String[] orderName = {"全部", "待付款", "待发货", "待收货", "待评价"};
    private RadioGroup myOrder_radioGroup;
    private OrderLazyViewPager myOrder_viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_order);
        initView();
        addTitle();
        setAdapter();
        setScrollPage();
    }

    /**
     * 添加标题
     */
    private void addTitle() {
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        for (int i = 0; i < orderName.length; i++) {
            RadioButton radioButton = (RadioButton) View.inflate(this, R.layout.radio_item, null);
            radioButton.setText(orderName[i]);
            radioButton.setTextSize(12);
            myOrder_radioGroup.addView(radioButton, layoutParams);
        }
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        myOrder_viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                OrderFragment orderFragment = OrderFragment.getStatus(orderName[position]);
                return orderFragment;
            }

            @Override
            public int getCount() {
                return orderName.length;
            }
        });
    }

    /**
     * 随之滑动
     */
    private void setScrollPage() {
        myOrder_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                for (int i = 0; i < myOrder_radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) myOrder_radioGroup.getChildAt(i);
                    if (radioButton.isChecked()) {
                        myOrder_viewPager.setCurrentItem(i);
                    }
                }
            }
        });
        setRadioPosition(getIntent().getIntExtra("position", 0));
        myOrder_viewPager.setCurrentItem(getIntent().getIntExtra("position", 0));
        myOrder_viewPager.setOnPageChangeListener(new OrderLazyViewPager.OnPageChangeListener() {
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


    /**
     * 设置对应的position
     */
    private void setRadioPosition(int position) {
        ((RadioButton) myOrder_radioGroup.getChildAt(position)).setChecked(true);
    }

    private void initView() {
        myOrder_radioGroup = (RadioGroup) findViewById(R.id.myOrder_radioGroup);
        myOrder_viewPager = (OrderLazyViewPager) findViewById(R.id.myOrder_viewPager);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.my_order);
        findViewById(R.id.but_title_left_image).setOnClickListener(this);
        TextView bianji = (TextView) findViewById(R.id.bianji);
        bianji.setVisibility(View.VISIBLE);
        bianji.setText(R.string.refund_order);

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

   /* public static String getorderId() {
        int matchId = 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        return matchId + String.format("%015d", hashCodeV);
    }*/
}
