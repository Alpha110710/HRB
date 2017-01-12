package com.hrb.ui.init;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.HuRongBaoApp;
import com.hrb.HuRongBaoConfig;
import com.hrb.R;
import com.hrb.ui.account.ActivityRechargeFast;
import com.hrb.ui.account.ActivityRechargeVerify;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.Util;

public class ActivityWebView extends BaseActivity implements OnClickListener {

    private String targetUrl;
    private WebView webView;
    private int webTitle;


    String ORDER_NO;
    String user_amount;
    String CONTRACTS;
    String user_recharge_amount;


    @SuppressWarnings("deprecation")
    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);

        webTitle = getIntent().getIntExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 0);
        targetUrl = getIntent().getStringExtra("LINKURL");
        ORDER_NO = getIntent().getStringExtra("ORDER_NO");
        CONTRACTS = getIntent().getStringExtra("CONTRACTS");
        user_amount = getIntent().getStringExtra("user_amount");
        user_recharge_amount = getIntent().getStringExtra("user_recharge_amount");
        if (webTitle == 1) {
            tvTitle.setText("开通资金账户");
        } else if (webTitle == 2) {
            tvTitle.setText("开通资金账户");
        } else if (webTitle == 3) {
            tvTitle.setText("帮助中心");
            targetUrl = HuRongBaoConfig.WS_BASE_DOMAIN + "iloanWebService/html/helpCenter.html";
        } else if (webTitle == 4) {
            tvTitle.setText("充值");
        } else if (webTitle == 5) {
            tvTitle.setText("新闻中心");
            targetUrl = HuRongBaoConfig.WS_BASE_DOMAIN + "iloanWebService/html/content.html?newsId=" + targetUrl;
        } else if (webTitle == 6) {
            tvTitle.setText("用户协议");
            targetUrl = HuRongBaoConfig.WS_BASE_DOMAIN + "iloanWebService/html/LoanAgreement.html";
        } else if (webTitle == 7) {
            tvTitle.setText("活动");
        } else if (webTitle == 8) {
            tvTitle.setText("品牌介绍");
            targetUrl = HuRongBaoConfig.WS_BASE_DOMAIN + "m/brand_introduction.html?type=app";
        } else if (webTitle == 9) {
            tvTitle.setText("新手引导");
            targetUrl = HuRongBaoConfig.WS_BASE_DOMAIN + "m/novice_guide.html?type=app";
        } else if (webTitle == 10) {
            tvTitle.setText("风控保障");
            targetUrl = HuRongBaoConfig.WS_BASE_DOMAIN + "m/risk_introduce.html?type=app";
        } else if (webTitle == 11) {
            tvTitle.setText("运营数据");
            targetUrl = HuRongBaoConfig.WS_BASE_DOMAIN + "m/operation_data.html?type=app";
        }


        webView = (WebView) findViewById(R.id.wv_webview);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        settings.setAllowFileAccess(true);
        settings.setSupportMultipleWindows(true);
        settings.setSupportZoom(true);
        // settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //优先加载缓存
        // settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true); // 必须进行这个设置
        // settings.setAppCachePath(this.getCacheDir().getAbsolutePath());
        // settings.setDatabaseEnabled(true);
        // settings.setDatabasePath(this.getCacheDir().getAbsolutePath());

        settings.setBuiltInZoomControls(true); // 支持缩放
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        // {
        // settings.setDisplayZoomControls(false); // 不显示缩放按钮
        // }
        // settings.setUseWideViewPort(true); // 无限缩放
        settings.setLoadWithOverviewMode(true); // 初始加载时，是web页面自适应屏幕
        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
        switch (screenDensity) {
            case DisplayMetrics.DENSITY_LOW:
                zoomDensity = WebSettings.ZoomDensity.CLOSE;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                zoomDensity = WebSettings.ZoomDensity.FAR;
                break;
        }
        settings.setDefaultZoom(zoomDensity);

        webView.requestFocus();
        webView.requestFocusFromTouch();

        webView.setWebChromeClient(new WebChromeClient() {
                                       @Override
                                       public void onProgressChanged(WebView view, int newProgress) {
                                           super.onProgressChanged(view, newProgress);
                                       }

                                       @Override
                                       public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                                           return super.onJsAlert(view, url, message, result);
                                       }

//            @Override
//            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//                WebView childView = new WebView(ActivityWebView.this);
//
////				childView.scrollTo(0, 0);
//                final WebSettings settings = childView.getSettings();
//                settings.setAllowFileAccess(true);
//                settings.setSupportMultipleWindows(true);
//                settings.setSupportZoom(true);
//                settings.setLoadWithOverviewMode(true);
//                settings.setUseWideViewPort(true);
//                settings.setLoadWithOverviewMode(true);
////				childView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//
//
//                childView.setLayoutParams(new ViewGroup.LayoutParams(
//                        WindowManager.LayoutParams.FILL_PARENT,
//                        WindowManager.LayoutParams.FILL_PARENT));
//
//
//                settings.setDomStorageEnabled(true); // 必须进行这个设置
//                settings.setBuiltInZoomControls(true); // 支持缩放
//                settings.setJavaScriptEnabled(true);
//                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
////				settings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
//                childView.setWebChromeClient(this);
//                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
//                transport.setWebView(childView);
//                resultMsg.sendToTarget();
//                webView.addView(childView);
//                // mHadOnCreateWindow = true;
//                // childView.addJavascriptInterface(new WebAppInterface(),
//                // "javaMethod");
//                return true;
//
//            }
                                   }
        );

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Log.e("url", url);
                if (webTitle == 4) {
                    if (url.contains("Success")) {
                        goToVerifyActivity();
                    } else if (url.contains("Fail")) {
                        goToRechargeFastActivity();
                    }
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }


        });

        webView.setDownloadListener(new DownloadListener() { // 资源下载

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        //添加Js可以调用Java方法
        // webView.addJavascriptInterface(new WebAppInterface(),"javaMethod");
        webView.loadUrl(targetUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (webTitle == 1) {
                    HuRongBaoApp.globalIndex = 0;
                    Util.gotoMain(ActivityWebView.this);
                    finish();
                } else {
                    finish();
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webTitle == 1) {
            HuRongBaoApp.globalIndex = 0;
            Util.gotoMain(ActivityWebView.this);
            finish();
        } else if (webTitle == 2) {
            HuRongBaoApp.globalIndex = 2;
            Util.gotoMain(ActivityWebView.this);
            finish();
        }
    }


    public void goToVerifyActivity() {
        //成功跳转页面
        Intent i = new Intent(ActivityWebView.this, ActivityRechargeVerify.class);
        i.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 4);
        i.putExtra("LINKURL", targetUrl);
        i.putExtra("ORDER_NO", ORDER_NO);
        i.putExtra("user_amount", user_amount);
        i.putExtra("user_recharge_amount", user_recharge_amount);
        i.putExtra("CONTRACTS", CONTRACTS);
        startActivity(i);
        ActivityWebView.this.finish();
    }

    public void goToRechargeFastActivity() {
        //失败跳转页面
        Intent i = new Intent(ActivityWebView.this, ActivityRechargeFast.class);
        i.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 4);
        i.putExtra("LINKURL", targetUrl);
        i.putExtra("user_amount", user_amount);
        startActivity(i);
        ActivityWebView.this.finish();
    }


    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface() {

        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void goToVerifyActivity() {
            //成功跳转页面
            Intent i = new Intent(ActivityWebView.this, ActivityRechargeFast.class);
            i.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 4);
            i.putExtra("LINKURL", targetUrl);
            i.putExtra("ORDER_NO", ORDER_NO);
            i.putExtra("CONTRACTS", CONTRACTS);
            startActivity(i);
            ActivityWebView.this.finish();
        }

        @JavascriptInterface
        public void goToRechargeFastActivity() {
            //失败跳转页面
            Intent i = new Intent(ActivityWebView.this, ActivityRechargeFast.class);
            i.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 4);
            i.putExtra("LINKURL", targetUrl);
            startActivity(i);
            ActivityWebView.this.finish();
        }

    }
}
