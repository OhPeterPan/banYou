package com.banutech.collectiontreasure.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.bean.LoginBean;
import com.banutech.collectiontreasure.presenter.LoginPresenter;
import com.banutech.collectiontreasure.response.UserLoginResponse;
import com.banutech.collectiontreasure.rxBus.RxBus;
import com.banutech.collectiontreasure.util.Convert;
import com.banutech.collectiontreasure.util.IntentUtil;
import com.banutech.collectiontreasure.util.SpUtil;
import com.banutech.collectiontreasure.util.ToastUtil;
import com.banutech.collectiontreasure.view.ILoginView;
import com.blankj.utilcode.util.StringUtils;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements RadioGroup.OnCheckedChangeListener, ILoginView {
    @BindView(R.id.tvLoginMobile)
    EditText tvLoginMobile;
    @BindView(R.id.tvLoginPwd)
    EditText tvLoginPwd;
    @BindView(R.id.tvConfirmLogin)
    TextView tvConfirmLogin;
    @BindView(R.id.rgLoginSelect)
    RadioGroup rgLoginSelect;

    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter(this);
    }

    @Override
    protected void initListener() {
        rgLoginSelect.setOnCheckedChangeListener(this);
        tvConfirmLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        RxBus.getInstance().register(presenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInnerListener(View v) {
        switch (v.getId()) {
            case R.id.tvConfirmLogin:
                login();
                break;
        }
    }

    private void login() {
        String mobile = tvLoginMobile.getText().toString().trim();
        String pwd = tvLoginPwd.getText().toString().trim();
        if (StringUtils.isEmpty(mobile)) {
            ToastUtil.show("请输入电话号码！", Toast.LENGTH_SHORT);
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            ToastUtil.show("请输入密码！", Toast.LENGTH_SHORT);
            return;
        }
        presenter.sendNetLogin(mobile, pwd);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public void getLoginResult(UserLoginResponse response) {
        if (response.returncode == 1) {
            ToastUtil.show("登陆成功!", Toast.LENGTH_SHORT);
            LoginBean loginBean = Convert.fromJson(response.returninfo, LoginBean.class);
            loginBean.mobile = tvLoginMobile.getText().toString().trim();
            SpUtil.save(SpUtil.ACCOUNT, loginBean);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    public static void launchNewFlag(Context mContext) {
        Intent in = new Intent(mContext, LoginActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        in.putExtra(IntentUtil.FLAG, 1);
        mContext.startActivity(in);
    }

}
