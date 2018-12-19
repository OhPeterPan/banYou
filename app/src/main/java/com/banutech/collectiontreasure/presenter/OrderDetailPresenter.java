package com.banutech.collectiontreasure.presenter;

import com.banutech.collectiontreasure.annotation.Register;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.common.impl.OkHttpClientImpl;
import com.banutech.collectiontreasure.model.OrderDetailModel;
import com.banutech.collectiontreasure.response.OrderDetailCountResponse;
import com.banutech.collectiontreasure.response.OrderDetailListResponse;
import com.banutech.collectiontreasure.view.IOrderDetailView;

public class OrderDetailPresenter extends BasePresenter<IOrderDetailView> {

    private final OrderDetailModel model;

    public OrderDetailPresenter(IOrderDetailView view) {
        super(view);
        model = new OrderDetailModel(new OkHttpClientImpl());
    }

    public void sendNet(String date, String startTime, String endTime, String companyId, String fromType) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetQueryCount(date, startTime, endTime, companyId, fromType);
    }

    public void sendNet(int page, String date, String startTime, String endTime, String companyId, String fromType, boolean showDialog) {
        if (model != null)
            model.sendNetQueryCount(page, date, startTime, endTime, companyId, fromType);
    }

    @Register
    private void getOrderInfo(OrderDetailCountResponse response) {
        if (mView != null) {
            mView.getOrderInfo(response);
        }
    }

    @Register
    private void getOrderListInfo(OrderDetailListResponse response) {
        if (mView != null) {
            mView.hideLoading();
            mView.getOrderListInfo(response);
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
