package com.banyou.app.view;

import com.banyou.app.response.OrderDetailCountResponse;
import com.banyou.app.response.OrderDetailListResponse;

public interface IOrderDetailView extends IView {

    void getOrderInfo(OrderDetailCountResponse response);

    void getOrderListInfo(OrderDetailListResponse response);
}
