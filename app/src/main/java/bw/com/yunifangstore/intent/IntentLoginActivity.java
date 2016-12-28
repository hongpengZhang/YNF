package bw.com.yunifangstore.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.activity.LoginActivity;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/6.
 */

public class IntentLoginActivity {
    public static void intentDetailActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.login_in, R.anim.login_in0);
    }
}
