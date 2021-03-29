package com.wd.winddots.activity.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.cons.Constant;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * webview
 *
 * @author zhou
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_content)
    WebView mContentWv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initView() {
        Intent intent = getIntent();
        String webUrl = intent.getStringExtra(Constant.WEB_ACTIVITY_URL_INTENT);
        if (!webUrl.startsWith(Constant.HTTP_PROTOCOL_PREFIX)){
            webUrl = "http://" + webUrl;
        }

        WebSettings settings = mContentWv.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);

        mContentWv.loadUrl(webUrl);
        // 系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        mContentWv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }

        });

        if (intent != null) {
            String url = intent.getStringExtra("data");
        }
    }
}
