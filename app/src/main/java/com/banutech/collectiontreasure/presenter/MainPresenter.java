package com.banutech.collectiontreasure.presenter;

import com.banutech.collectiontreasure.annotation.Register;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.common.impl.OkHttpClientImpl;
import com.banutech.collectiontreasure.model.MainInfoModel;
import com.banutech.collectiontreasure.response.MainCountResponse;
import com.banutech.collectiontreasure.response.MainListResponse;
import com.banutech.collectiontreasure.response.QRcodeResponse;
import com.banutech.collectiontreasure.view.IMainView;
import com.blankj.utilcode.util.LogUtils;

public class MainPresenter extends BasePresenter<IMainView> {

    private final MainInfoModel model;

    public MainPresenter(IMainView view) {
        super(view);
        model = new MainInfoModel(new OkHttpClientImpl());
    }

    public void sendNet(String date, String startTime, String endTime, String companyId, String fromType, String storeId, boolean showDialog) {
        if (mView != null && showDialog)
            mView.showLoading();
        if (model != null)
            model.sendNetQueryCount(date, startTime, endTime, companyId, fromType, storeId);
    }

    public void sendNet(int page, String date, String startTime, String endTime, String companyId, String fromType, String storeId, boolean showDialog) {
        if (mView != null)
            // mView.showLoading();
            if (model != null)
                model.sendNetQueryCount(page, date, startTime, endTime, companyId, fromType, storeId);
    }

    @Register
    private void getMainCount(MainCountResponse response) {
        if (mView != null) {
            // mView.hideLoading();
            mView.getMainCountResult(response);
        }
    }

    @Register
    private void getQRcode(QRcodeResponse response) {
        if (mView != null) {
            mView.hideLoading();
            mView.getQRcodeResult(response);
        }
    }

    public void sendNetPayCode(String storeId, String companyId, String fromType) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetPay(storeId, companyId, fromType);
    }

    @Register
    private void getMainCount(MainListResponse response) {
        if (mView != null) {
            mView.hideLoading();
            mView.getMainListResult(response);
        }
    }

    //极光收到通知后回调
    @Register
    private void speak(String message) {
        if (mView != null)
            mView.speak(message);
    }

    @Register
    public void error(BaseResponse response) {
        if (response.getException() != null) {
            LogUtils.iTag("wak", response.getException().getMessage());
        }
        if (mView != null) {
            mView.hideLoading();
            mView.error(response);
        }
    }
}
