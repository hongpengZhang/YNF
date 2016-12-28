package bw.com.yunifangstore.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.activity.MyMapActivity;
import bw.com.yunifangstore.activity.MyOrderActivity;
import bw.com.yunifangstore.activity.SettingActivity;
import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.intent.IntentLoginActivity;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.ImageLoaderUtils;
import bw.com.yunifangstore.view.ShowingPage;


/**
 * Created by zhiyuan on 16/9/28.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView user_icon;
    public static TextView username;
    private TextView login_btn;
    public static String profile_image_url;
    public TextView dizhi;
    private TextView tv_adresss;
    private RadioGroup mine_radioGroup;

    @Override
    public void onLoad() {
        MineFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }


    @Override
    public View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.minefragment_layout, null);
        initView(view);
        return view;
    }

    /**
     * 初始化控件
     */
    private void initView(View view) {
        login_btn = (TextView) view.findViewById(R.id.login_btn);
        ImageButton settings = (ImageButton) view.findViewById(R.id.settings);
        user_icon = (ImageView) view.findViewById(R.id.user_icon);
        username = (TextView) view.findViewById(R.id.username);
        login_btn.setOnClickListener(this);
        settings.setOnClickListener(this);
        dizhi = (TextView) view.findViewById(R.id.dizhi);
        tv_adresss = (TextView) view.findViewById(R.id.tv_adresss);
        tv_adresss.setOnClickListener(this);
        mine_radioGroup = (RadioGroup) view.findViewById(R.id.mine_radioGroup);
        for (int i = 0; i < mine_radioGroup.getChildCount(); i++) {
            mine_radioGroup.getChildAt(i).setOnClickListener(this);
        }
        view.findViewById(R.id.myorder).setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        MineFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        //写入文件中
        profile_image_url = CommonUtils.getSp("profile_image_url");
        String screen_name = CommonUtils.getSp("screen_name");

        if (!TextUtils.isEmpty(this.profile_image_url) && !TextUtils.isEmpty(screen_name)) {
            //整个应用
            isLogin(screen_name, this.profile_image_url);
        } else {
            username.setVisibility(View.GONE);
            login_btn.setText("登录");
            user_icon.setImageResource(R.mipmap.user_icon_no_set);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录
            case R.id.login_btn:
                if (login_btn.getText().toString().equals("会员")) {
                    Toast.makeText(getActivity(), "暂时没有开通", Toast.LENGTH_SHORT).show();
                } else {
                    IntentLoginActivity.intentDetailActivity(getActivity());
                }
                break;
            //设置
            case R.id.settings:
                Intent intent2 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent2);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;
            case R.id.tv_adresss:
                Intent intent3 = new Intent(getActivity(), MyMapActivity.class);
                getActivity().startActivityForResult(intent3, 1);
                break;
            //条目一展示全部商品
            case R.id.myorder:
                intentMyOrderActivity(0);
                break;

        }
        //**我的订单
        for (int i = 0; i < mine_radioGroup.getChildCount(); i++) {
            TextView childAt = (TextView) mine_radioGroup.getChildAt(i);
            if (childAt.getId() == v.getId()) {
                intentMyOrderActivity(i + 1);
                return;
            }
        }


    }

    private void intentMyOrderActivity(int position) {
        if (position == mine_radioGroup.getChildCount()) {
            Toast.makeText(getActivity(), "退款", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent2 = new Intent(getActivity(), MyOrderActivity.class);
            intent2.putExtra("position", position);

            getActivity().startActivity(intent2);
        }
        getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);

    }

    /**
     * 登录后回调界面
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String city = data.getStringExtra("city");
            String addrStr = data.getStringExtra("addrStr");
            String street = data.getStringExtra("street");
            String streetNumber = data.getStringExtra("streetNumber");
            dizhi.setText("城市:" + city + " " + addrStr + " " + street + " " + streetNumber);
        }
    }

    /**
     * 是否登录
     */
    private void isLogin(String screen_name, String profile_image_url) {
        username.setText(screen_name);
        login_btn.setText("会员");
        ImageLoader.getInstance().displayImage(profile_image_url, user_icon, ImageLoaderUtils.initOptions());
    }
}
