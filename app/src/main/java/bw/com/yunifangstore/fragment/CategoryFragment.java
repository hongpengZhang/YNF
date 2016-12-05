package bw.com.yunifangstore.fragment;

import android.view.View;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.view.ShowingPage;

/**
 * Created by zhiyuan on 16/9/28.
 */
public class CategoryFragment extends BaseFragment {
    @Override
    public void onLoad() {
        CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    public View createSuccessView() {
        View view = CommonUtils.inflate(R.layout.categoryfragment_layout);
        return view;
    }
}
