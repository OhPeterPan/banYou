package com.banutech.collectiontreasure.ui;

import android.view.View;
import android.widget.FrameLayout;

import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.util.UIUtil;
import com.banutech.collectiontreasure.web_setting.CommentAgentWebSetting;
import com.gyf.barlibrary.ImmersionBar;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.IAgentWebSettings;

import butterknife.BindView;

public class UserSchemeActivity extends BaseActivity {
    @BindView(R.id.flWebViewContainer)
    FrameLayout flWebViewContainer;
    private AgentWeb mAgentWeb;
    private String url = "https://www.banutech.com/apply/agreement.html";

    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_scheme;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(flWebViewContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(UIUtil.getColor(R.color.colorAccent))
                .setAgentWebWebSettings(getSetting())
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    private IAgentWebSettings getSetting() {
        return new CommentAgentWebSetting();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAgentWeb != null) mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAgentWeb != null) mAgentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onInnerListener(View v) {

    }
}
