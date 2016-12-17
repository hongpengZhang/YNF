package bw.com.yunifangstore.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.activity.LoginActivity;
import bw.com.yunifangstore.activity.SettingActivity;
import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.view.ShowingPage;


/**
 * Created by zhiyuan on 16/9/28.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
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
        TextView login_btn = (TextView) view.findViewById(R.id.login_btn);
        ImageButton settings = (ImageButton) view.findViewById(R.id.settings);
        login_btn.setOnClickListener(this);
        settings.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录
            case R.id.login_btn:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;
            //设置
            case R.id.settings:
                Intent intent2 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent2);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);

                break;
        }
    }
}
