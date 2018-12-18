package com.banyou.app.ui;

import android.view.View;
import android.widget.TextView;

import com.banyou.app.R;
import com.banyou.app.util.UIUtil;
import com.blankj.utilcode.util.AppUtils;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.tvAboutVersionName)
    TextView tvAboutVersionName;
    @BindView(R.id.tvAboutUserScheme)
    TextView tvAboutUserScheme;
    @BindView(R.id.tvAboutCompanyInfo)
    TextView tvAboutCompanyInfo;

    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        tvAboutVersionName.setText(String.format(UIUtil.getString(R.string.about_version_name), AppUtils.getAppVersionName()));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onInnerListener(View v) {

    }
}
