package com.wd.winddots.mvp.widget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.confifg.Const;

import androidx.annotation.RequiresApi;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: WebViewActivity
 * Author: 郑
 * Date: 2020/6/17 11:46 AM
 * Description:
 */
public class WebViewActivity extends CommonActivity {

    private WebView mWebView;

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView() {
        super.initView();

        addBadyView(R.layout.activity_webview);
        mWebView = findViewById(R.id.webview);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        if (!StringUtils.isNullOrEmpty(title)) {
            setTitleText(title);
        } else {
            setTitleText("网页");
        }
        String webUrl = intent.getStringExtra(Const.WEB_ACTIVITY_URL_INTENT);
        if (webUrl.length() > 4) {
            if (!"http".equals(webUrl.substring(0, 4))) {
                webUrl = "http://" + webUrl;
            }
        }
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);


        mWebView.loadUrl(webUrl);
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        mWebView.setWebViewClient(new WebViewClient() {

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
