package com.banyou.app.popup_window;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.banyou.app.R;
import com.banyou.app.bean.StoreBean;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class StorePopupWindow extends BasePopupWindow<StoreBean> {
    private IOnChooseStoreListener listener;

    public StorePopupWindow(Context context, List<StoreBean> data) {
        super(context, data, R.layout.item_text);
    }

    @Override
    protected void binderAdapter(BaseViewHolder helper, StoreBean item) {
        helper.setText(R.id.tvAdapterText, item.store_name);

    }

    @Override
    protected void onItemListener(StoreBean storeBean, int position) {
        if (listener != null)
            listener.onChooseStoreListener(storeBean, position);
        dismiss();
    }

    public void setOnChooseStoreListener(IOnChooseStoreListener listener) {
        this.listener = listener;
    }

    public interface IOnChooseStoreListener {
        void onChooseStoreListener(StoreBean storeBean, int position);
    }

    public void showPop(View v) {
        setWidth(v.getWidth());
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            v.getGlobalVisibleRect(rect);
            showAtLocation(v, Gravity.LEFT | Gravity.TOP, rect.left, rect.bottom);
        } else {
            showAsDropDown(v, 0, SizeUtils.dp2px(1f));
        }
    }
}
