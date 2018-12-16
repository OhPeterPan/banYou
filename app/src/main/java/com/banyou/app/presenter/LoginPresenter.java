package com.banyou.app.presenter;

import android.util.Log;

import com.banyou.app.annotation.Register;
import com.banyou.app.common.IHttpClient;
import com.banyou.app.common.impl.BaseResponse;
import com.banyou.app.common.impl.OkHttpClientImpl;
import com.banyou.app.model.LoginModel;
import com.banyou.app.response.UserLoginResponse;
import com.banyou.app.view.ILoginView;

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
    private void setLoginResults(BaseResponse response) {
        Log.e("wak", "", response.getException());
        if (mView != null) {
            mView.hideLoading();
            mView.error(response);
        }
    }
}
