package bw.com.yunifangstore.fragment;

import android.view.View;

import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.view.ShowingPage;

public class CartFragment extends BaseFragment {



    @Override
    public void onLoad() {
        CartFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
    }

    @Override
    public View createSuccessView() {

        return null;
    }
}
