package com.banutech.collectiontreasure.view;

import com.banutech.collectiontreasure.response.OrderDetailCountResponse;
import com.banutech.collectiontreasure.response.OrderDetailListResponse;

public interface IOrderDetailView extends IView {

    void getOrderInfo(OrderDetailCountResponse response);

    void getOrderListInfo(OrderDetailListResponse response);
}
