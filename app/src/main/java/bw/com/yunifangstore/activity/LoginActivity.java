package bw.com.yunifangstore.activity;

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

import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoLinearLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
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
                break;
            //其他登录的方式
            case R.id.tv_login_other:
                View view = CommonUtils.inflate(R.layout.login_pop);
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

                break;
            //手机快速登录
            case R.id.rbut_login_phone:
                setVisibleLayout();
                break;
            //御泥坊账号登录
            case R.id.rbut_login_ynf:
                setHideVisibleLayout();
                break;
        }
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
