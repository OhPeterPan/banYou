package com.banutech.collectiontreasure.view;

import com.banutech.collectiontreasure.response.MainCountResponse;
import com.banutech.collectiontreasure.response.MainListResponse;
import com.banutech.collectiontreasure.response.QRcodeResponse;

public interface IMainView extends IView {
    void getMainCountResult(MainCountResponse response);

    void getMainListResult(MainListResponse response);

    void speak(String message);

    void getQRcodeResult(QRcodeResponse response);
}
