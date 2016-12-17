package bw.com.yunifangstore.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

import bw.com.yunifangstore.R;

public class WebActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private WebView webView;
    private TextView tv_title;
    private ImageView iv_title_back;
    private ImageView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rool_web);
        initView();

        String webUrl = getIntent().getStringExtra("url");

        webView.loadUrl(webUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                String title = view.getTitle();
                if (TextUtils.isEmpty(title)) {
                    share.setVisibility(View.INVISIBLE);
                } else {
                    tv_title.setText(title);
                }
            }
        });
        //针对设置进度的监听
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        //设置
        initSettings();
    }

    private void initSettings() {
        WebSettings settings = webView.getSettings();
        //是否自动打开窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //是否识别jsp
        settings.setJavaScriptEnabled(true);
        //设置如何缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //是否展示缩放按钮
        settings.setBuiltInZoomControls(true);
        //设置默认的缩放比例
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
            }
        }

        return true;

    }


    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView = (WebView) findViewById(R.id.webView);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        share = (ImageView) findViewById(R.id.share);
        share.setOnClickListener(this);
        iv_title_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            case R.id.share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
