package bw.com.yunifangstore.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

import java.io.File;

import bw.com.yunifangstore.R;

public class SettingActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView but_title_left_image;
    private LinearLayout clearCache_llt;
    private TextView tv_clearCache;
    private File cacheDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        but_title_left_image = (ImageView) findViewById(R.id.but_title_left_image);
        but_title_left_image.setOnClickListener(this);
        clearCache_llt = (LinearLayout) findViewById(R.id.clearCache_llt);
        clearCache_llt.setOnClickListener(this);
        tv_clearCache = (TextView) findViewById(R.id.tv_clearCache);
        cacheDir = getCacheDir();
        long cacheSize = getCacheSize(cacheDir);
        tv_clearCache.setText("已缓存" + cacheSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_title_left_image:
                finish();
                break;
            case R.id.clearCache_llt:
                // 清除缓存
                deleteCache(cacheDir);
                // 刷新textView
                // 获取当前缓存的大小
                long cacheSize = getCacheSize(cacheDir);
                // 计算182222byte 进行转换
                tv_clearCache.setText("已缓存" +cacheSize + "M");
                Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
                break;
        }
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
     *
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
