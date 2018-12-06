package myz.graduation_design.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.net.URI;

import butterknife.OnClick;
import myz.graduation_design.R;
import myz.graduation_design.Utils.GpsUtils;

public class WebActivity extends BaseActivity {

private WebView mWeb;
private TextView mTvHeaderLeft;
private TextView mTvHeaderCenter;
private TextView mTvHeaderRight;
private String url;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mWeb = (WebView)findViewById(R.id.web);
        mTvHeaderLeft = (TextView)findViewById(R.id.tv_header_left);
        mTvHeaderCenter = (TextView)findViewById(R.id.tv_header_center);
        mTvHeaderRight = (TextView)findViewById(R.id.tv_header_right);
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("详情");
        mTvHeaderRight.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        url = getIntent().getStringExtra("url");
        GpsUtils.openGPS(this);
        mWeb.getSettings().setJavaScriptEnabled(true);
        mWeb.getSettings().setUseWideViewPort(true);
        mWeb.getSettings().setLoadWithOverviewMode(true);
        mWeb.getSettings().setDatabaseEnabled(true);
        mWeb.getSettings().setGeolocationEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        mWeb.getSettings().setGeolocationDatabasePath(dir);
        mWeb.getSettings().setDomStorageEnabled(true);
        // 开启 Application Caches 功能
        mWeb.getSettings().setAppCacheEnabled(true);
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        mWeb.setWebChromeClient(new MyWbChromeClient());
        mWeb.loadUrl(url);

    }

    private class MyWbChromeClient extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, true);
            super.onGeolocationPermissionsShowPrompt(origin, callback);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWeb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    protected void initEvent() {
        setBack(R.id.tv_header_left);
    }

}
