package com.banutech.collectiontreasure.view;

import com.banutech.collectiontreasure.common.IResponse;

public interface IView {
    void showLoading();

    void hideLoading();

    void error(IResponse response);
}
