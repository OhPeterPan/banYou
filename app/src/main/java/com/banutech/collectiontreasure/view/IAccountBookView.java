package com.banutech.collectiontreasure.view;

import com.banutech.collectiontreasure.response.AccountBookResponse;
import com.banutech.collectiontreasure.response.OperatePersonResponse;
import com.banutech.collectiontreasure.response.OrderQueryResponse;

public interface IAccountBookView extends IView {
    void getStoreResult(AccountBookResponse response);

    void getPersonResult(OperatePersonResponse response);

    void getOrderQueryResult(OrderQueryResponse response);
}
