package com.sun.webviewsubmitform;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = findViewById(R.id.web_view);
        getData();
        setWebView(getData());
    }

    private String getData() {
        PersonBean bean = new PersonBean();
        bean.UserName = "张三";
        bean.UserId = "1001";
        bean.Token = "SwyHTEx_RQppr97g4J5lKXtabJecpejuef8AqKYMAJc";
        bean.Age = 18;
        bean.ActionUrl = "http://www.baidu.com";
        Gson gson = new Gson();
        return gson.toJson(bean);
    }

    private void setWebView(final String data) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存
        webSettings.setUseWideViewPort(true);//适应分辨率
        webSettings.setJavaScriptEnabled(true);//设置能够解析Javascript
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setHapticFeedbackEnabled(false);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!TextUtils.isEmpty(data))
                    view.loadUrl("javascript:submit(" + data + ")");
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

            }
        });

        if (!TextUtils.isEmpty(data)) {
            mWebView.loadUrl("file:///android_asset/web/form.html");
        }
    }
}
