package bw.com.yunifangstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.factory.FragmentFactory;
import bw.com.yunifangstore.intent.IntentLoginActivity;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.view.LazyViewPager;

public class MainActivity extends AutoLayoutActivity {

    LazyViewPager viewPager;
    public static RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        setAdapterAndSetPage();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra("cart", false)) {
            ((RadioButton) radioGroup.getChildAt(2)).setChecked(true);
        }

        if (intent.getBooleanExtra("login", false)) {
            if (viewPager.getCurrentItem() == 0) {
                ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
            }
            if (viewPager.getCurrentItem() == 1) {
                ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
            }
            if (viewPager.getCurrentItem() == 3) {
                ((RadioButton) radioGroup.getChildAt(3)).setChecked(true);
            }
        }
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
//        viewPager.setOffscreenPageLimit(3);
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
                        String profile_image_url = CommonUtils.getSp("profile_image_url");
                        if (!TextUtils.isEmpty(profile_image_url)) {
                            viewPager.setCurrentItem(2);
                        } else {
                            IntentLoginActivity.intentDetailActivity(MainActivity.this);
                            ((RadioButton) radioGroup.getChildAt(3)).setChecked(true);
                        }
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
        viewPager = (LazyViewPager) findViewById(R.id.viewPager_Main);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_Main);
    }


    private long mPressedTime = 0;

    @Override
    public void onBackPressed() {
        //获取第一次按键时间
        long mNowTime = System.currentTimeMillis();
        if ((mNowTime - mPressedTime) > 2000) {
            Toast.makeText(this, "再按一次退出应用程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {
            finish();
        }
    }
}
