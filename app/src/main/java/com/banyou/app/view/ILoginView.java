package com.banyou.app.view;

import com.banyou.app.response.UserLoginResponse;

public interface ILoginView extends IView {
    void getLoginResult(UserLoginResponse response);
}
