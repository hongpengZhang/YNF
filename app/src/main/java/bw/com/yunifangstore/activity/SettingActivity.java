package bw.com.yunifangstore.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.VersionInfo;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.DataClearManager;

public class SettingActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView but_title_left_image;
    private LinearLayout clearCache_llt;
    private TextView tv_clearCache;
    private File cacheDir;
    private LinearLayout about_we;
    private LinearLayout call_phone;
    private Button but_unlogin;
    private LinearLayout update_layout;
    private TextView tv_update;
    private VersionInfo versionInfo;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String versionName = versionInfo.getVersionName();

            if (getCurrentVersionName().equals(versionName)) {
                Toast.makeText(SettingActivity.this, "已经是最新版本", Toast.LENGTH_SHORT).show();
            } else {
                tv_update.setText("最新版本为" + versionName);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        //请求更新
        requestUpdateData();

    }

    private void initView() {
        but_title_left_image = (ImageView) findViewById(R.id.but_title_left_image);
        but_title_left_image.setOnClickListener(this);
        clearCache_llt = (LinearLayout) findViewById(R.id.clearCache_llt);
        clearCache_llt.setOnClickListener(this);
        tv_clearCache = (TextView) findViewById(R.id.tv_clearCache);
        cacheDir = getCacheDir();
        about_we = (LinearLayout) findViewById(R.id.about_we);
        about_we.setOnClickListener(this);
        call_phone = (LinearLayout) findViewById(R.id.call_phone);
        call_phone.setOnClickListener(this);
        but_unlogin = (Button) findViewById(R.id.but_unlogin);
        but_unlogin.setOnClickListener(this);
        update_layout = (LinearLayout) findViewById(R.id.update_layout);
        update_layout.setOnClickListener(this);
        tv_update = (TextView) findViewById(R.id.tv_update);
        if (!TextUtils.isEmpty(CommonUtils.getSp("profile_image_url"))) {
            but_unlogin.setVisibility(View.VISIBLE);
        } else {
            but_unlogin.setVisibility(View.GONE);
        }

       /* long cacheSize = getCacheSize(cacheDir);
        tv_clearCache.setText("已缓存" + cacheSize);*/
        try {
            String cacheSize1 = DataClearManager.getCacheSize(cacheDir);
            tv_clearCache.setText("已缓存" + cacheSize1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_title_left_image:
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            // 清除缓存
            case R.id.clearCache_llt:
                deleteCache(cacheDir);
                // 刷新textView
                // 获取当前缓存的大小
                long cacheSize = getCacheSize(cacheDir);
                // 计算182222byte 进行转换
                tv_clearCache.setText("已缓存" + cacheSize + "M");
                Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
                break;
            //关于我们
            case R.id.about_we:
                Intent intent2 = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;
            //打电话
            case R.id.call_phone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                //uri:统一资源标示符（更广）
                intent.setData(Uri.parse("tel:" + "400-688-0900"));
                //开启系统拨号器
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;
            case R.id.but_unlogin:
                if (but_unlogin.getVisibility() == View.VISIBLE) {
                    dialogData();
                }
                break;
            //版本更新
            case R.id.update_layout:
                if (tv_update.getText().toString().trim().startsWith("最新版本为"))
                    updateVersion();
                else
                    requestUpdateData();
                break;

        }
    }

    /**
     * 获取新版本
     */
    private void updateVersion() {
        String downloadUrl = versionInfo.getDownloadUrl();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        RequestParams requestParams = new RequestParams(downloadUrl);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File file) {
                Toast.makeText(SettingActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long l, long l1, boolean b) {
                progressDialog.setMax((int) l);
                progressDialog.setProgress((int) l1);
            }
        });
    }

    /**
     * 请求网络数据是否需要变更
     */
    private void requestUpdateData() {
        tv_update.setText("正在检测");
        RequestParams request = new RequestParams("http://169.254.239.3:8080/YuNiFang.apk");
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                versionInfo = gson.fromJson(s, VersionInfo.class);
                handler.obtainMessage(0, versionInfo).sendToTarget();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    /**
     * 获取当前的版本号
     *
     * @return
     */
    public String getCurrentVersionName() {

        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String packageName = packageInfo.packageName;
            return packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 退出登录
     */
    private void dialogData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setMessage("确定删除吗？");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                CommonUtils.removeSp("profile_image_url");
                CommonUtils.removeSp("screen_name");
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);

            }
        });

        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    /**
     * 缓存大小
     *
     * @param dir
     * @return
     */
    private long getCacheSize(File dir) {
        long size = 0;
        File[] listFiles = dir.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            // 是个文件夹
            if (listFiles[i].isDirectory()) {
                size = size + getCacheSize(listFiles[i]);
            }
            // 当前是个文件
            else {
                size = size + listFiles[i].length();
            }
        }
        return size;
    }

    /**
     * 清除缓存
     */
    private void deleteCache(File dir) {
        File[] listFiles = dir.listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    listFiles[i].delete();
                } else {
                    // 该文件夹为空
                    if (listFiles[i].listFiles().length == 0) {
                        listFiles[i].delete();
                    } else {
                        // 继续遍历该文件夹，进行删除操作
                        deleteCache(listFiles[i]);
                    }
                }
                //删除文件
                listFiles[i].delete();
            }
        }
    }
}
