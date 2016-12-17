package bw.com.yunifangstore.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.LaunchAdapter;

public class LaunchActivity extends AutoLayoutActivity implements View.OnClickListener {
    RelativeLayout timelayout;
    ImageView activitylaunch;
    private Boolean isFirst;
    private ViewPager launch_viewPager;
    private int[] pic = {R.mipmap.guidance_1,
            R.mipmap.guidance_2,
            R.mipmap.guidance_3,
            R.mipmap.guidance_4,
    };
    private SharedPreferences preferences;
    private int t1 = 4;
    private int t2 = 5;

    private TextView tv_time;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {
                t1--;
                if (t1 == 0) {
                    removeCallbacksAndMessages(null);
                    timelayoutAnim();
                    handler.sendEmptyMessage(1);
                }
                sendEmptyMessageDelayed(0, 1000);
            }
            if (msg.what == 1) {
                t2--;
                tv_time.setText("跳过" +t2+ "s");
                if (t2 == 0) {
                    handler.removeCallbacksAndMessages(null);
                    IsFirst();
                }
                sendEmptyMessageDelayed(1, 1000);
            }
            if (msg.what == 2) {
                IsFirst();
            }
        }
    };
    private ImageView imageView3;

    /**
     * 判断是否第一次
     */
    private void IsFirst() {
        if (isFirst) {
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("IsFirst", false);
            edit.commit();
            setContentView(R.layout.launch_layout);
            launch_viewPager = (ViewPager) LaunchActivity.this.findViewById(R.id.launch_viewPager);
            launch_viewPager.setAdapter(new LaunchAdapter(pic, LaunchActivity.this));
        } else {
            jump();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_launch);
        initView();
        preferences = getSharedPreferences("IsFirstYNF", MODE_PRIVATE);
        isFirst = preferences.getBoolean("IsFirst", true);
        handler.sendEmptyMessage(0);
    }


    /**
     * 跳转
     */
    private void jump() {
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.login_in0, R.anim.login_out);

    }

    /**
     * 跳过监听事件
     */
    @Override
    public void onClick(View v) {
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessage(2);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        timelayout = (RelativeLayout) findViewById(R.id.time_layout);
        activitylaunch = (ImageView) findViewById(R.id.activity_launch);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
    }

    /**
     * 动画
     */
    public void timelayoutAnim() {
        activitylaunch.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        timelayout.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(500);
        timelayout.startAnimation(alphaAnimation);
    }
}
