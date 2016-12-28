package bw.com.yunifangstore.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.Map;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.utils.CommonUtils;

public class LoginActivity extends AutoLayoutActivity implements View.OnClickListener {

    private boolean b = false;
    private ImageView back;
    private TextView tv_login_other;
    private AutoLinearLayout ll_ynf_login;
    private AutoLinearLayout ll_phone_login;
    private RadioButton rbut_login_phone;
    private RadioButton rbut_login_ynf;
    private ImageView iv_qq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.but_title_left_image);
        tv_login_other = (TextView) findViewById(R.id.tv_login_other);
        tv_login_other.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        rbut_login_phone = (RadioButton) findViewById(R.id.rbut_login_phone);
        rbut_login_ynf = (RadioButton) findViewById(R.id.rbut_login_ynf);
        rbut_login_ynf.setOnClickListener(this);
        rbut_login_phone.setOnClickListener(this);
        back.setOnClickListener(this);
        tv_login_other.setOnClickListener(this);
        //线性布局的切换
        ll_ynf_login = (AutoLinearLayout) findViewById(R.id.ll_ynf_login);
        ll_phone_login = (AutoLinearLayout) findViewById(R.id.ll_phone_login);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.but_title_left_image:
                finish();
                //跳转到第四个Fragment
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("login",true);
                startActivity(intent);
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //其他登录的方式
            case R.id.tv_login_other:
                View view = initPopView();
                setPopWindow(view);
                break;
            //手机快速登录
            case R.id.rbut_login_phone:
                setVisibleLayout();
                break;
            //御泥坊账号登录
            case R.id.rbut_login_ynf:
                setHideVisibleLayout();
                break;
            //QQ登录
            case R.id.iv_qq:
                UMShareAPI mShareAPI = UMShareAPI.get(LoginActivity.this);
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
        }
    }

    /**
     * QQ登录监听
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String screen_name = data.get("screen_name");
            String profile_image_url = data.get("profile_image_url");
            Toast.makeText(getApplicationContext(),"screen_name" + screen_name + " profile_image_url" + profile_image_url, Toast.LENGTH_SHORT).show();
            CommonUtils.saveSp("profile_image_url", profile_image_url);
            CommonUtils.saveSp("screen_name", screen_name);
            finish();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 初始化popWindow的控件
     */
    private View initPopView() {
        View view = CommonUtils.inflate(R.layout.login_pop);
        iv_qq = (ImageView) view.findViewById(R.id.iv_qq);
        iv_qq.setOnClickListener(this);
        return view;
    }

    /**
     * 第三方登录的popwindow
     */
    private void setPopWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        popupWindow.showAtLocation(tv_login_other, Gravity.BOTTOM, 0, 0);

        backgroundAlpha(0.4f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
    }

    /**
     * 显示
     */
    private void setVisibleLayout() {
        rbut_login_phone.setTextColor(0xffE61A5F);
        rbut_login_ynf.setTextColor(0xff2B2B2B);
        ll_ynf_login.setVisibility(View.GONE);
        ll_phone_login.setVisibility(View.VISIBLE);

    }

    /**
     * 隐藏
     */
    private void setHideVisibleLayout() {
        rbut_login_phone.setTextColor(0xff2B2B2B);
        rbut_login_ynf.setTextColor(0xffE61A5F);
        ll_ynf_login.setVisibility(View.VISIBLE);
        ll_phone_login.setVisibility(View.GONE);
    }

    /**
     * 背景变暗
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
