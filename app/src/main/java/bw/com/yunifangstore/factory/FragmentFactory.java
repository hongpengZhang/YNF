package bw.com.yunifangstore.factory;

import android.support.v4.app.Fragment;

import java.util.HashMap;

import bw.com.yunifangstore.fragment.CartFragment;
import bw.com.yunifangstore.fragment.CategoryFragment;
import bw.com.yunifangstore.fragment.HomeFragment;
import bw.com.yunifangstore.fragment.MineFragment;

/**
 * @author : 张鸿鹏
 * @date : 2016/11/28.
 */

public class FragmentFactory {
    public static HashMap<Integer, Fragment> map = new HashMap<>();

    public static Fragment getFragment(int position) {
        Fragment fragment = map.get(position);
        if (fragment != null) {
            return fragment;
        }
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new CategoryFragment();
                break;
            case 2:
                fragment = new CartFragment();
                break;
            case 3:
                fragment = new MineFragment();
                break;
        }
        //存储到集合中
        map.put(position, fragment);
        return fragment;
    }
}
