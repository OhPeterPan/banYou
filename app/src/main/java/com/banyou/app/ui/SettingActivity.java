package com.banyou.app.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banyou.app.R;
import com.banyou.app.bean.LoginBean;
import com.banyou.app.util.SpUtil;
import com.banyou.app.util.UIUtil;
import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.suke.widget.SwitchButton;

import butterknife.BindView;


public class SettingActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        LoginBean account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        if (account != null) {
            Glide.with(this).load(account.img).apply(new RequestOptions().centerCrop().skipMemoryCache(true)).into(ivSettingLogo);
            tvSettingName.setText(String.valueOf(account.TrueName));
            tvSettingAccount.setText(String.valueOf(account.mobile));
            tvSettingVersionCode.setText(String.format(UIUtil.getString(R.string.app_version_name), " ", AppUtils.getAppVersionName()));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onInnerListener(View v) {

    }

}
