package com.banutech.collectiontreasure.view;

import com.banutech.collectiontreasure.response.UserLoginResponse;

public interface ILoginView extends IView {
    void getLoginResult(UserLoginResponse response);
}
