package com.banyou.app.view;

import com.banyou.app.common.IResponse;

public interface IView {
    void showLoading();

    void hideLoading();

    void error(IResponse response);
}
