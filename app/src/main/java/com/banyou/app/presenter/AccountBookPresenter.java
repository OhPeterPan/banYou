package com.banyou.app.presenter;

import com.banyou.app.annotation.Register;
import com.banyou.app.common.impl.OkHttpClientImpl;
import com.banyou.app.model.AccountBookModel;
import com.banyou.app.response.AccountBookResponse;
import com.banyou.app.view.IAccountBookView;

public class AccountBookPresenter extends BasePresenter<IAccountBookView> {

    private AccountBookModel model;

    public AccountBookPresenter(IAccountBookView view) {
        super(view);
        model = new AccountBookModel(new OkHttpClientImpl());
    }

    public void sendNetStore(String companyId, String fromType) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetStore(companyId, fromType);
    }

    @Register
    private void getStoreResult(AccountBookResponse response) {
        if (mView != null) {
            mView.hideLoading();
            mView.getStoreResult(response);
        }
    }
}
