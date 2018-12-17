package com.banyou.app.presenter;

import android.util.Log;

import com.banyou.app.annotation.Register;
import com.banyou.app.common.impl.BaseResponse;
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

    @Register
    private void setLoginResults(BaseResponse response) {
        Log.e(getClass().getSimpleName(), "", response.getException());
        if (mView != null) {
            mView.hideLoading();
            mView.error(response);
        }
    }
}
