package com.banutech.collectiontreasure.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.bean.LoginBean;
import com.banutech.collectiontreasure.bean.StoreBean;
import com.banutech.collectiontreasure.presenter.LoginPresenter;
import com.banutech.collectiontreasure.response.UserLoginResponse;
import com.banutech.collectiontreasure.rxBus.RxBus;
import com.banutech.collectiontreasure.util.Convert;
import com.banutech.collectiontreasure.util.IntentUtil;
import com.banutech.collectiontreasure.util.SpUtil;
import com.banutech.collectiontreasure.util.ToastUtil;
import com.banutech.collectiontreasure.view.ILoginView;
import com.blankj.utilcode.util.StringUtils;

import java.util.List;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements RadioGroup.OnCheckedChangeListener, ILoginView {
    @BindView(R.id.tvLoginMobile)
    EditText tvLoginMobile;
    @BindView(R.id.tvLoginPwd)
    EditText tvLoginPwd;
    @BindView(R.id.tvConfirmLogin)
    TextView tvConfirmLogin;
    @BindView(R.id.tvLoginRegister)
    TextView tvLoginRegister;
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
        tvLoginRegister.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        RxBus.getInstance().register(presenter);
        tvLoginRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvLoginRegister.getPaint().setAntiAlias(true);
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
            case R.id.tvLoginRegister:
                userRegister();
                //startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    private String url = "https://www.banutech.com/apply/apply_receipt_code.html";

    private void userRegister() {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) { // 网址正确 跳转成功
            startActivity(intent);
        } else { //网址不正确 跳转失败 提示错误
            ToastUtil.show("网址输入错误，请重新输入！", Toast.LENGTH_SHORT);
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
            List<StoreBean> storeList = loginBean.store_list;
       /*     StoreBean bean = new StoreBean();
            bean.store_id = "123";
            bean.store_name = "hahaha";
            storeList.add(bean);*/
            if (storeList != null) {
                if (storeList.size() == 1) {
                    loginBean.storeId = storeList.get(0).store_id;
                    startActivityMain(loginBean);
                } else {
                    showSelectStoreDialog(loginBean, storeList);
                }
            } else {
                ToastUtil.show("无店铺", Toast.LENGTH_SHORT);
            }

        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    private void showSelectStoreDialog(final LoginBean loginBean, final List<StoreBean> storeList) {
        final String[] strName = new String[storeList.size()];
        for (int i = 0; i < storeList.size(); i++) {
            strName[i] = storeList.get(i).store_name;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog);

        builder.setItems(strName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loginBean.storeId = storeList.get(which).store_id;
                startActivityMain(loginBean);
            }
        });

        builder.show();
    }

    private void startActivityMain(LoginBean loginBean) {
        SpUtil.save(SpUtil.ACCOUNT, loginBean);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public static void launchNewFlag(Context mContext) {
        Intent in = new Intent(mContext, LoginActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        in.putExtra(IntentUtil.FLAG, 1);
        mContext.startActivity(in);
    }

}
