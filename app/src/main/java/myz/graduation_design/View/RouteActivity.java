package myz.graduation_design.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import java.io.IOException;

import myz.graduation_design.Model.AmapModel.LocationModel;
import myz.graduation_design.R;
import myz.graduation_design.Utils.GpsUtils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 10246 on 2018/4/13.
 */

public class RouteActivity extends AppCompatActivity{

    private String startAddress;
    private String endAddress;
    private String urlStart;
    private String urlEnd;
    private String url;
    private String LocationStart;
    private String LocationEnd;

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        url = getIntent().getStringExtra("url");
        //endAddress = getIntent().getStringExtra("endAddress");
        //LocationStart = getIntent().getStringExtra("LocationStart");
        //LocationEnd = getIntent().getStringExtra("LocationEnd");

//        url = "http://m.amap.com/navi/?start="+LocationStart+"&dest="+LocationEnd+"&destName="+endAddress+"&naviBy=car&key=f85fb3519bb821437196610c16617d5a&platform=mobile";
//        http://m.amap.com/navi/?start121.574466,29.814989&dest=121.554194,29.869457destName=%E5%AE%81%E6%B3%A2%E5%A4%A9%E4%B8%80%E5%B9%BF%E5%9C%BA&naviBy=car&key=f85fb3519bb821437196610c16617d5a&platform=mobile
        //http://m.amap.com/navi/?start=121.574466,29.814989&dest=121.554194,29.869457&destName=宁波理工学院&naviBy=car&key=
        initVeiw();
    }

    private void initVeiw(){
        mWebView = (WebView)findViewById(R.id.web_route);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//设置缓存问题。
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        webSettings.setAppCachePath(getCacheDir().getPath());
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.setWebChromeClient(new MyWbChromeClient());
//设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
//启用地理定位
        webSettings.setGeolocationEnabled(true);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        // 如果是图片频道，则必须设置该接口为true，否则页面无法展现
        webSettings.setDomStorageEnabled(true);
        GpsUtils.openGPS(this);
        Log.e("mao", "initVeiw: "+GpsUtils.isOPen(this) );
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(
                new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                }
        );
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri= Uri.parse(url);
                Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //mTvHeaderCenter.setText(title);
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键
                        mWebView.goBack();   //后退
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private class MyWbChromeClient extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, true);
            super.onGeolocationPermissionsShowPrompt(origin, callback);

        }
    }

}
