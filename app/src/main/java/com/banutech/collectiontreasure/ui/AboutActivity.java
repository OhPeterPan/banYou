package com.banutech.collectiontreasure.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.util.TimeUtil;
import com.banutech.collectiontreasure.util.UIUtil;
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
        tvAboutCompanyInfo.setText(String.format(UIUtil.getString(R.string.about_bottom_info), TimeUtil.getNowTime(TimeUtil.YEAR)));
    }

    @Override
    protected void initListener() {
        tvAboutUserScheme.setOnClickListener(this);
    }

    @Override
    protected void onInnerListener(View v) {
        switch (v.getId()) {
            case R.id.tvAboutUserScheme:
                startActivity(new Intent(this, UserSchemeActivity.class));
                break;
        }
    }
}
