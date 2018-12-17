package com.banyou.app.view;

import com.banyou.app.response.MainCountResponse;
import com.banyou.app.response.MainListResponse;

public interface IMainView extends IView {
    void getMainCountResult(MainCountResponse response);

    void getMainListResult(MainListResponse response);
}
