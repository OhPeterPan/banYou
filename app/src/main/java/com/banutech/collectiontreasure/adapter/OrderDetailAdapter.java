package com.banutech.collectiontreasure.adapter;

import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.bean.MainListDetailBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class OrderDetailAdapter extends BaseQuickAdapter<MainListDetailBean, BaseViewHolder> {
    public OrderDetailAdapter() {
        super(R.layout.adapter_order_detail, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainListDetailBean item) {
        helper.setText(R.id.tvOrderAdapterStoreName, String.valueOf(item.store_name));
        helper.setText(R.id.tvOrderAdapterPayMoney, String.valueOf(item.price));
        helper.setText(R.id.tvOrderAdapterPayType, String.valueOf(item.paytype));
        helper.setText(R.id.tvOrderAdapterPayTime, String.valueOf(item.completedate));
        helper.setText(R.id.tvOrderAdapterOperatePerson, String.valueOf(item.username));
    }
}
