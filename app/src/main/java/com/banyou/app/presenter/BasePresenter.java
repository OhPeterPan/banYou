package com.banyou.app.presenter;

import com.banyou.app.view.IView;

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
}
