package bw.com.yunifangstore.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Window;
import android.widget.RadioGroup;

import com.zhy.autolayout.AutoLayoutActivity;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.factory.FragmentFactory;
import bw.com.yunifangstore.view.NoScrollViewPager;

public class MainActivity extends AutoLayoutActivity {

    NoScrollViewPager viewPager;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        setAdapterAndSetPage();
    }

    /**
     * 添加适配器和点击切换监听
     */
    private void setAdapterAndSetPage() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentFactory.getFragment(position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rb_home_page:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_category:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_cart:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_mine:
                        viewPager.setCurrentItem(3);
                        break;

                }
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager_Main);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_Main);
    }
}
