package com.banutech.collectiontreasure.view;

import com.banutech.collectiontreasure.response.VoiceReceiverResponse;

public interface ISettingView extends IView {

    void getBroadcastChangeResultSuccess(VoiceReceiverResponse response);
}
