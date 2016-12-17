package bw.com.yunifangstore.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.activity.DetailsActivity;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/6.
 */

public class IntentDetailActivity {
    public static void intentDetailActivity(Context context, String id) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.login_in, R.anim.login_in0);
    }
}
