### Android WebView提交form表单  简单写一个demo

#### 附上Demo地址：https://github.com/langsun/WebViewSubmitFormDemo

###### 1.先看java代码，getData()返回的就是一个PersonBean的json字符串，这个就是要提交表单的数据，字段和后面form.html中字段相对应，其实最主要的就是onPageFinished方法中的view.loadUrl("javascript:submit(" + data + ")");，这个就是调用js，提交form表单



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
           		public void onReceivedError(WebView view, WebResourceRequest request, 					WebResourceError error) {

            	}
        	});

        	if (!TextUtils.isEmpty(data)) {
            	mWebView.loadUrl("file:///android_asset/web/form.html");
        	}
    	}
	}

###### 2.再看form.html代码，其中js中的submit(data)就是你在java中所调用的方法，然后调用setData(data)将数据填充的form表单，通过post提交，其中actionUrl就是你要跳转的页面


	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    	<title>跳转中....</title>
    	<script language="javascript">
        	function submit(data) {
			setData(data);
			document.getElementById('actionUrl').submit();
       		}
		
			function setData(data){
		   		document.getElementById('userName').value =data.UserName;
            	document.getElementById('userId').value =data.UserId;
            	document.getElementById('token').value =data.Token;
            	document.getElementById('age').value =data.Age;
            	document.getElementById('actionUrl').action =data.ActionUrl;
			}
    	</script>
	</head>
	<body>
	<form action="" method="post" id="actionUrl">
    	<input type="hidden" name="userName" id="userName" value=""/>
    	<input type="hidden" name="userId" id="userId" value=""/>
    	<input type="hidden" name="token" id="token" value=""/>
 		<input type="hidden" name="age" id="age" value="">
	</form>
	</body>
	</html>
	
	
	
	
###### 3.其实提交form表单就这么简单，OVER
	
