package bw.com.yunifangstore.factory;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import bw.com.yunifangstore.fragment.CateTypeFragment;

/**
 * @author :   郗琛
 * @date :   2016/12/12
 */

public class MoreTitleFragmentFactory {
    private static Map<String, Fragment> fragmentMap = new HashMap<>();

    public static Fragment getFragmentInstance(String index, String url) {
        Fragment fragment = fragmentMap.get(index);
        if (fragment != null) {
            return fragment;
        }
        fragment = new CateTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        fragmentMap.put(index, fragment);
        return fragment;
    }
}
