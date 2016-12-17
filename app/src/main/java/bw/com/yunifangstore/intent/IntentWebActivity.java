package bw.com.yunifangstore.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.activity.WebActivity;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/6.
 */

public class IntentWebActivity {
    public static void intentWebActivity(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.login_in, R.anim.login_in0);
    }
}
