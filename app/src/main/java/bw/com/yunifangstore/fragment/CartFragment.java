package bw.com.yunifangstore.fragment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.view.ShowingPage;

public class CartFragment extends BaseFragment implements View.OnClickListener {


    private TextView bianji;

    @Override
    public void onLoad() {
        CartFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    public View createSuccessView() {
        View inflate = initView();
        return inflate;
    }

    @NonNull
    private View initView() {
        View inflate = CommonUtils.inflate(R.layout.cartfragment_layout);
        ImageView but_title_left_image = (ImageView) inflate.findViewById(R.id.but_title_left_image);
        but_title_left_image.setVisibility(View.GONE);
        //编辑
        bianji = (TextView) but_title_left_image.findViewById(R.id.bianji);
        TextView tv_title= (TextView) inflate.findViewById(R.id.tv_title);
        tv_title.setText("购物车");
        tv_title.setTextColor(Color.BLACK);
        Button bt_guang = (Button) inflate.findViewById(R.id.bt_guang);
        bt_guang.setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onClick(View v) {

    }
}
