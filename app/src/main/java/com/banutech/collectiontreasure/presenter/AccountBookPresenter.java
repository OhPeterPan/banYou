package com.banutech.collectiontreasure.presenter;

import com.banutech.collectiontreasure.annotation.Register;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.common.impl.OkHttpClientImpl;
import com.banutech.collectiontreasure.model.AccountBookModel;
import com.banutech.collectiontreasure.response.AccountBookResponse;
import com.banutech.collectiontreasure.response.OperatePersonResponse;
import com.banutech.collectiontreasure.response.OrderQueryResponse;
import com.banutech.collectiontreasure.view.IAccountBookView;

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
    @Register
    public void error(BaseResponse response) {
/*
        if (response.getException() != null) {
            LogUtils.iTag("wak", response.getException().getMessage());
        }*/
        if (mView != null) {
            mView.hideLoading();
            mView.error(response);
        }
    }
}
