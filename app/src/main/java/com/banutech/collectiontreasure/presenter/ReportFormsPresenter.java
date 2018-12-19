package com.banutech.collectiontreasure.presenter;

import com.banutech.collectiontreasure.annotation.Register;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.common.impl.OkHttpClientImpl;
import com.banutech.collectiontreasure.model.ReportFormsModel;
import com.banutech.collectiontreasure.response.ReportChartResponse;
import com.banutech.collectiontreasure.response.ReportFormsResponse;
import com.banutech.collectiontreasure.view.IReportFormsView;

public class ReportFormsPresenter extends BasePresenter<IReportFormsView> {

    private final ReportFormsModel model;

    public ReportFormsPresenter(IReportFormsView view) {
        super(view);
        model = new ReportFormsModel(new OkHttpClientImpl());
    }

    public void sendNetQueryOrderResult(String date, String startTime, String endTime, String companyId, String fromType) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetQueryCount(date, startTime, endTime, companyId, fromType);
    }

    @Register
    private void getOrderInfo(ReportFormsResponse response) {
        if (mView != null) {
            //mView.hideLoading();
            mView.getOrderInfoSuccess(response);
        }
    }

    @Register
    private void getOrderChart(ReportChartResponse response) {
        if (mView != null) {
            mView.hideLoading();
            mView.getOrderChartSuccess(response);
        }
    }

    public void sendNetQueryOrderResult(String chartDate, String companyId, String fromType) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetQueryCount(chartDate, companyId, fromType);
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
