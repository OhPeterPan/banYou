package com.banyou.app.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.banyou.app.R;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tvMainTitle)
    TextView tvMainTitle;
    @BindView(R.id.tvMainSetting)
    TextView tvMainSetting;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvMainTime)
    TextView tvMainTime;
    @BindView(R.id.tvIncomeMoney)
    TextView tvIncomeMoney;
    @BindView(R.id.tvDealCount)
    TextView tvDealCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initListener() {
        tvMainSetting.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInnerListener(View v) {
        switch (v.getId()) {
            case R.id.tvMainSetting://设置
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
