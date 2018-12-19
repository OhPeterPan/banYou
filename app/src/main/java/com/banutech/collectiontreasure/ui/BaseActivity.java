package com.banutech.collectiontreasure.ui;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.common.IResponse;
import com.banutech.collectiontreasure.dialog.LoadDialog;
import com.banutech.collectiontreasure.presenter.BasePresenter;
import com.banutech.collectiontreasure.util.LogUtil;
import com.banutech.collectiontreasure.util.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

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
        if (response.getException() != null) {
            ToastUtil.show(response.getException().getMessage(), Toast.LENGTH_SHORT);
        }
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

    //申请麦克风权限
    public void checkVoicePermission() {
        AndPermission.with(this)
                .permission(Manifest.permission.RECORD_AUDIO)
                // 准备方法，和 okhttp 的拦截器一样，在请求权限之前先运行改方法，已经拥有权限不会触发该方法
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
                        executor.execute();
                    }
                })
                // 用户给权限了
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        LogUtil.logI("wak", "来吗？");
                    }
                })
                // 用户拒绝权限，包括不再显示权限弹窗也在此列
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(BaseActivity.this, permissions)) {
                            // 打开权限设置页
                            AndPermission.permissionSetting(BaseActivity.this).execute();
                            return;
                        }
                    }
                })
                .start();
    }
}
