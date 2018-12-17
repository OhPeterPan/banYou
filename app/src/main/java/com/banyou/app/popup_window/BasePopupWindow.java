package com.banyou.app.popup_window;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.banyou.app.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public abstract class BasePopupWindow<T> extends PopupWindow implements BaseQuickAdapter.OnItemClickListener {

    private Context mContext;
    private int layoutId;
    private List<T> mData;
    private RecyclerView recyclerView;

    public BasePopupWindow(Context context, List<T> data, int layoutId) {
        super(context);
        this.mData = data;
        this.layoutId = layoutId;
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_popup_recycle, null);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setContentView(v);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.popupAnimation);
        recyclerView = v.findViewById(R.id.recyclerView);
        initAdapter();
    }

    private void initAdapter() {
        PopupAdapter adapter = new PopupAdapter(layoutId, mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        onItemListener(mData.get(position), position);
    }

    private class PopupAdapter extends BaseQuickAdapter<T, BaseViewHolder> {

        public PopupAdapter(int layoutResId, @Nullable List<T> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            binderAdapter(helper, item);
        }
    }

    protected abstract void binderAdapter(BaseViewHolder helper, T item);

    protected abstract void onItemListener(T t, int position);
}
