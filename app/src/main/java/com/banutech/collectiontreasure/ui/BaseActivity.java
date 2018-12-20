package com.banutech.collectiontreasure.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.common.IResponse;
import com.banutech.collectiontreasure.dialog.LoadDialog;
import com.banutech.collectiontreasure.presenter.BasePresenter;
import com.banutech.collectiontreasure.util.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends
        AppCompatActivity implements View.OnClickListener {

    private View back;
    public T presenter;
    private LoadDialog loadDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initStatus();
        initPresenter();
        initView();
        initData();
        initListener();
    }

    protected abstract int getLayoutId();

    public void showLoading() {
        if (loadDialog != null && !loadDialog.isShowing()) {
            loadDialog.show();
        }
    }

    public void hideLoading() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

    public void error(IResponse response) {
        hideLoading();
        adapterError();
        if (response.getException() != null) {
            ToastUtil.show(response.getException().getMessage(), Toast.LENGTH_SHORT);
        }
    }

    protected void adapterError() {

    }

    protected abstract void initPresenter();

    public void initStatus() {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        if (presenter != null) {
            presenter.destroy();
        }
    }

    private void initView() {
        back = findViewById(R.id.back);
        if (back != null) {
            back.setOnClickListener(this);
        }
        loadDialog = new LoadDialog(this);
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void onInnerListener(View v);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
        onInnerListener(v);
    }
}
