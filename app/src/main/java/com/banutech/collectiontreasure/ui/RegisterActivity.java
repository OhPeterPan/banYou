package com.banutech.collectiontreasure.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.web_setting.CommentAgentWebSetting;
import com.gyf.barlibrary.ImmersionBar;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.IAgentWebSettings;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    private AgentWeb mAgentWeb;
    private String url = "https://www.banutech.com/apply/apply_receipt_code.html";
    //private WebView webView;

    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        // webView = new WebView(this);


        // 打开网址 这个是通过打开android自带的浏览器进行的打开网址



        webView.loadUrl(url);
        //flRegisterWebViewContainer.addView(webView, new FrameLayout.LayoutParams(-1, -1));
    /*    mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(flRegisterWebViewContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(UIUtil.getColor(R.color.colorAccent))
                .setAgentWebWebSettings(getSetting())
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go(url);*/
    }

    private IAgentWebSettings getSetting() {
        return new CommentAgentWebSetting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAgentWeb != null) mAgentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) mAgentWeb.getWebLifeCycle().onDestroy();
        if (webView != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onInnerListener(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
