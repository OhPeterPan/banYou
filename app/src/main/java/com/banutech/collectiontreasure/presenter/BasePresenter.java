package com.banutech.collectiontreasure.presenter;

import com.banutech.collectiontreasure.view.IView;

public abstract class BasePresenter<T extends IView> {
    public T mView;

    public BasePresenter(T view) {
        this.mView = view;
    }

    public void destroy() {
        if (mView != null) {
            mView = null;
        }
    }

    /*
    *     @Register  注解类继承 方法不继承
    public void error(BaseResponse response) {
        Log.e(getClass().getSimpleName(), "", response.getException());
        if (mView != null) {
            mView.hideLoading();
            mView.error(response);
        }
    }*/
}
