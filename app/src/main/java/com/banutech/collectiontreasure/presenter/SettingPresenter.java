package com.banutech.collectiontreasure.presenter;

import android.util.Log;

import com.banutech.collectiontreasure.annotation.Register;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.common.impl.OkHttpClientImpl;
import com.banutech.collectiontreasure.model.SettingModel;
import com.banutech.collectiontreasure.response.VoiceReceiverResponse;
import com.banutech.collectiontreasure.view.ISettingView;

public class SettingPresenter extends BasePresenter<ISettingView> {

    private final SettingModel model;

    public SettingPresenter(ISettingView view) {
        super(view);
        model = new SettingModel(new OkHttpClientImpl());
    }

    public void sendNetSetVoiceBroadcast(String id, String userType, String value) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.setVoiceBroadcast(id, userType, value);
    }

    @Register
    private void getBroadcastChangeResult(VoiceReceiverResponse response){
        if (mView != null)
        {
            mView.hideLoading();
            mView.getBroadcastChangeResultSuccess(response);
        }

    }

    @Register
    public void error(BaseResponse response) {
        Log.e(getClass().getSimpleName(), "", response.getException());
        if (mView != null) {
            mView.hideLoading();
            mView.error(response);
        }
    }
}
