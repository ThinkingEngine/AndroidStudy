package com.chengsheng.cala.htcm.module;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.widget.AppTitleBar;

/**
 * Author: 任和
 * CreateDate: 2018/12/129 9:30 AM
 * Description: WEB页
 */
public class WebActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar loadingProgressBar;

    /**
     * @param context 上下文对象
     * @param url     url
     * @param title   标题
     */
    public static void start(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_basic_web;
    }

    @Override
    public void initView() {
        FrameLayout layoutWebContent = findViewById(R.id.layoutWebContent);
        AppTitleBar titleBar = findViewById(R.id.titleBar);
        loadingProgressBar = findViewById(R.id.loading_progressBar);
        webView = new WebView(this);
        layoutWebContent.addView(webView);

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");

        if (titleBar != null) {
            titleBar.setTitle(title);
        }

        if (webView != null) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setUseWideViewPort(true);
            webSettings.setTextZoom(100);
            webSettings.setLoadWithOverviewMode(true);
            webView.setWebChromeClient(new CustomWebChromeClient());
        }

        webView.loadUrl(url);
    }

    private class CustomWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView webView, int i) {
            if (null != loadingProgressBar) {
                loadingProgressBar.setProgress(i);
                loadingProgressBar.setVisibility(i == 100 ? View.GONE : View.VISIBLE);
            }
        }
    }

    @Override
    public void getData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView = null;
        }
    }
}
