package com.banutech.collectiontreasure.presenter;

import com.banutech.collectiontreasure.annotation.Register;
import com.banutech.collectiontreasure.common.IHttpClient;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.common.impl.OkHttpClientImpl;
import com.banutech.collectiontreasure.model.LoginModel;
import com.banutech.collectiontreasure.response.UserLoginResponse;
import com.banutech.collectiontreasure.view.ILoginView;

public class LoginPresenter extends BasePresenter<ILoginView> {

    private final LoginModel model;

    public LoginPresenter(ILoginView view) {
        super(view);
        IHttpClient client = new OkHttpClientImpl();
        model = new LoginModel(client);
    }

    public void sendNetLogin(String mobile, String pwd) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendLogin(mobile, pwd);
    }

    @Register
    private void setLoginResult(UserLoginResponse response) {
        if (mView != null) {
            mView.hideLoading();
            mView.getLoginResult(response);
        }
    }

    @Register
    public void error(BaseResponse response) {
/*
        if (response.getException() != null) {
            LogUtils.iTag("wak", response.getException().getMessage());
        }*/
        if (mView != null) {
            mView.hideLoading();
            mView.error(response);
        }
    }
}
