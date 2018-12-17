package com.banyou.app.popup_window;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.banyou.app.R;
import com.banyou.app.bean.OperatePersonBean;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class PersonPopupWindow extends BasePopupWindow<OperatePersonBean> {
    private IOnChoosePersonListener listener;

    public PersonPopupWindow(Context context, List<OperatePersonBean> data) {
        super(context, data, R.layout.item_text);
    }

    @Override
    protected void binderAdapter(BaseViewHolder helper, OperatePersonBean item) {
        helper.setText(R.id.tvAdapterText, item.operator_name);

    }

    @Override
    protected void onItemListener(OperatePersonBean bean, int position) {
        if (listener != null)
            listener.onChoosePersonListener(bean, position);
        dismiss();
    }

    public void setOnChoosePersonListener(IOnChoosePersonListener listener) {
        this.listener = listener;
    }

    public interface IOnChoosePersonListener {
        void onChoosePersonListener(OperatePersonBean bean, int position);
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
