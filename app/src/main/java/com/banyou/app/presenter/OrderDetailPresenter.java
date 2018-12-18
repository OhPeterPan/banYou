package com.banyou.app.presenter;

import com.banyou.app.annotation.Register;
import com.banyou.app.common.impl.OkHttpClientImpl;
import com.banyou.app.model.OrderDetailModel;
import com.banyou.app.response.OrderDetailCountResponse;
import com.banyou.app.response.OrderDetailListResponse;
import com.banyou.app.view.IOrderDetailView;

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
}
