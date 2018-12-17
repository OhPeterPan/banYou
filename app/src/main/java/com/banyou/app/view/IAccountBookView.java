package com.banyou.app.view;

import com.banyou.app.response.AccountBookResponse;

public interface IAccountBookView extends IView {
    void getStoreResult(AccountBookResponse response);
}
