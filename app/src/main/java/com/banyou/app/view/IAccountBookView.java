package com.banyou.app.view;

import com.banyou.app.response.AccountBookResponse;
import com.banyou.app.response.OperatePersonResponse;
import com.banyou.app.response.OrderQueryResponse;

public interface IAccountBookView extends IView {
    void getStoreResult(AccountBookResponse response);

    void getPersonResult(OperatePersonResponse response);

    void getOrderQueryResult(OrderQueryResponse response);
}
