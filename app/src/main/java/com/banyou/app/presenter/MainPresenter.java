package com.banyou.app.presenter;

import com.banyou.app.annotation.Register;
import com.banyou.app.common.impl.OkHttpClientImpl;
import com.banyou.app.model.MainInfoModel;
import com.banyou.app.response.MainCountResponse;
import com.banyou.app.response.MainListResponse;
import com.banyou.app.view.IMainView;

public class MainPresenter extends BasePresenter<IMainView> {

    private final MainInfoModel model;

    public MainPresenter(IMainView view) {
        super(view);
        model = new MainInfoModel(new OkHttpClientImpl());
    }

    public void sendNet(String date, String startTime, String endTime, String companyId, String fromType) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetQueryCount(date, startTime, endTime, companyId, fromType);
    }

    public void sendNet(int page, String date, String startTime, String endTime, String companyId, String fromType, boolean showDialog) {
        if (mView != null)
            // mView.showLoading();
            if (model != null)
                model.sendNetQueryCount(page, date, startTime, endTime, companyId, fromType);
    }

    @Register
    private void getMainCount(MainCountResponse response) {
        if (mView != null) {
            // mView.hideLoading();
            mView.getMainCountResult(response);
        }
    }

    @Register
    private void getMainCount(MainListResponse response) {
        if (mView != null) {
            mView.hideLoading();
            mView.getMainListResult(response);
        }
    }
}
