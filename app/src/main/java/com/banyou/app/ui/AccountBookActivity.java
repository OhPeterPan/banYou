package com.banyou.app.ui;

import android.view.View;

import com.banyou.app.R;
import com.gyf.barlibrary.ImmersionBar;

public class AccountBookActivity extends BaseActivity {
    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_book;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onInnerListener(View v) {

    }
}
