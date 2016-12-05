package bw.com.yunifangstore.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bw.com.yunifangstore.view.ShowingPage;

/**
 * @author : 张鸿鹏
 * @date : 2016/11/28.
 */

public abstract class BaseFragment extends Fragment {
    private ShowingPage showingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //因为我们也不知道如何展示--继续抽象
        showingPage = new ShowingPage(getContext()) {
            @Override
            protected void onload() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        BaseFragment.this.onLoad();
                    }
                }.start();

            }

            @Override
            public View createSuccessView() {
                return BaseFragment.this.createSuccessView();
            }
        };

        return showingPage;
    }

    /**
     * 加载
     */
    public abstract void onLoad();

    /**
     * 展示成功
     */
    protected abstract View createSuccessView();

    /**
     * 对外提供方法，调用展示界面
     *
     * @param stateType
     */
    public void showCurrentPage(ShowingPage.StateType stateType) {
        //调用showingPage的方法
        if (showingPage != null) {
            showingPage.showCurrentPage(stateType);
        }
    }

}
