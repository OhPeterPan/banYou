package com.banyou.app.presenter;

import com.banyou.app.annotation.Register;
import com.banyou.app.common.impl.OkHttpClientImpl;
import com.banyou.app.model.AccountBookModel;
import com.banyou.app.response.AccountBookResponse;
import com.banyou.app.response.OperatePersonResponse;
import com.banyou.app.response.OrderQueryResponse;
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

    public void sendNetOperatePerson(String companyId, String fromType) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetOperatePerson(companyId, fromType);
    }

    public void sendNetTaking(String searchDate, String storeId, String operatorId, String companyId, String fromType) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetTaking(searchDate, storeId, operatorId, companyId, fromType);
    }

    @Register
    private void getStoreResult(AccountBookResponse response) {
        if (mView != null) {
            mView.hideLoading();
            mView.getStoreResult(response);
        }
    }

    @Register
    private void getPersonResult(OperatePersonResponse response) {
        if (mView != null) {
            mView.hideLoading();
            mView.getPersonResult(response);
        }
    }
    @Register
    private void getOrderQueryResult(OrderQueryResponse response) {
        if (mView != null) {
            mView.hideLoading();
            mView.getOrderQueryResult(response);
        }
    }
}
