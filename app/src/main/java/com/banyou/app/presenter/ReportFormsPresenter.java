package com.banyou.app.presenter;

import com.banyou.app.annotation.Register;
import com.banyou.app.common.impl.OkHttpClientImpl;
import com.banyou.app.model.ReportFormsModel;
import com.banyou.app.response.ReportChartResponse;
import com.banyou.app.response.ReportFormsResponse;
import com.banyou.app.view.IReportFormsView;

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
}
