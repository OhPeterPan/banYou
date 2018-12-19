package com.banutech.collectiontreasure.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.bean.LoginBean;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.presenter.SettingPresenter;
import com.banutech.collectiontreasure.response.VoiceReceiverResponse;
import com.banutech.collectiontreasure.rxBus.RxBus;
import com.banutech.collectiontreasure.util.SpUtil;
import com.banutech.collectiontreasure.util.SystemUtil;
import com.banutech.collectiontreasure.util.ToastUtil;
import com.banutech.collectiontreasure.util.UIUtil;
import com.banutech.collectiontreasure.view.ISettingView;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.suke.widget.SwitchButton;

import butterknife.BindView;


public class SettingActivity extends BaseActivity<SettingPresenter> implements ISettingView {

    @BindView(R.id.ivSettingLogo)
    ImageView ivSettingLogo;
    @BindView(R.id.tvSettingAccount)
    TextView tvSettingAccount;
    @BindView(R.id.tvSettingName)
    TextView tvSettingName;
    @BindView(R.id.switchSwingCard)
    SwitchButton switchSwingCard;
    @BindView(R.id.tvSettingVersionCode)
    TextView tvSettingVersionCode;
    @BindView(R.id.tvConfirmExit)
    TextView tvConfirmExit;
    @BindView(R.id.tvSettingAbout)
    TextView tvSettingAbout;
    private LoginBean account;

    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initPresenter() {
        presenter = new SettingPresenter(this);
    }

    @Override
    protected void initListener() {
        tvSettingAbout.setOnClickListener(this);
        tvConfirmExit.setOnClickListener(this);
        switchSwingCard.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                LogUtils.iTag("wak", "来吗？" + isChecked);
                String value = isChecked ? "1" : "0";
                presenter.sendNetSetVoiceBroadcast(account.id, account.userType, value);
            }
        });
    }

    @Override
    protected void initData() {
        RxBus.getInstance().register(presenter);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        if (account != null) {
            Glide.with(this).load(account.img).apply(new RequestOptions().centerCrop().skipMemoryCache(true)).into(ivSettingLogo);
            tvSettingName.setText(String.valueOf(account.TrueName));
            tvSettingAccount.setText(String.valueOf(account.mobile));
            tvSettingVersionCode.setText(String.format(UIUtil.getString(R.string.app_version_name), " ", AppUtils.getAppVersionName()));
            switchSwingCard.setChecked(TextUtils.equals("1", account.is_broadcast));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(presenter);
    }

    @Override
    protected void onInnerListener(View v) {
        switch (v.getId()) {
            case R.id.tvSettingAbout:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.tvConfirmExit://退出登录
                SystemUtil.exitApp(this);
                break;
        }
    }

    @Override
    public void getBroadcastChangeResultSuccess(VoiceReceiverResponse response) {
        if (response.returncode == BaseResponse.RESULT_SUCCESS) {
            ToastUtil.show(response.returninfo, Toast.LENGTH_SHORT);
            account.is_broadcast = switchSwingCard.isChecked() ? "1" : "0";
            SpUtil.save(SpUtil.ACCOUNT, account);
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }
}
