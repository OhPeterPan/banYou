package com.banutech.collectiontreasure;

import android.text.TextUtils;

import com.banutech.collectiontreasure.bean.MainListDetailBean;
import com.banutech.collectiontreasure.util.UIUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class MainAdapter extends BaseQuickAdapter<MainListDetailBean, BaseViewHolder> {
    public MainAdapter() {
        super(R.layout.adapter_main, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainListDetailBean item) {
        if (TextUtils.equals("支付宝支付", item.paytype)) {
            helper.setImageResource(R.id.ivMainAdapterPic, R.mipmap.ic_ali_pay);
        } else {
            helper.setImageResource(R.id.ivMainAdapterPic, R.mipmap.ic_wxpay);
        }
        helper.setText(R.id.tvMainAdapterOrderPrice, String.format(UIUtil.getString(R.string.main_money), item.price));
        helper.setText(R.id.tvMainAdapterOrderTime, item.completedate);
    }
}
