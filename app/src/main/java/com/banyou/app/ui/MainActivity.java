package com.banyou.app.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.banyou.app.MainAdapter;
import com.banyou.app.R;
import com.banyou.app.bean.LoginBean;
import com.banyou.app.bean.MainCountBean;
import com.banyou.app.bean.MainListBean;
import com.banyou.app.common.impl.BaseResponse;
import com.banyou.app.presenter.MainPresenter;
import com.banyou.app.response.MainCountResponse;
import com.banyou.app.response.MainListResponse;
import com.banyou.app.rxBus.RxBus;
import com.banyou.app.util.Convert;
import com.banyou.app.util.IntentUtil;
import com.banyou.app.util.NumberUtil;
import com.banyou.app.util.SpUtil;
import com.banyou.app.util.ToastUtil;
import com.banyou.app.util.UIUtil;
import com.banyou.app.view.IMainView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, BaseQuickAdapter.RequestLoadMoreListener {
    private static final int REQUEST_CODE = 0x0010;
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
    @BindView(R.id.tvAccountBook)
    TextView tvAccountBook;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String date = "day";
    private String startTime = "";
    private String endTime = "";
    private String companyid;
    private String fromType;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean hasMore = true;
    private LoginBean account;
    private MainAdapter adapter;


    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(this);
    }

    @Override
    protected void initListener() {
        tvMainSetting.setOnClickListener(this);
        tvMainTime.setOnClickListener(this);
        tvAccountBook.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        RxBus.getInstance().register(presenter);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initAdapter();
        sendNet();
    }

    private void initAdapter() {
        adapter = new MainAdapter();
        recyclerView.setHasFixedSize(true);
        adapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void sendNet() {
        presenter.sendNet(date, startTime, endTime, account.companyid, account.fromType);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(presenter);
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
            case R.id.tvMainTime://时间
                startActivityForResult(new Intent(this, ShowTimeActivity.class), REQUEST_CODE);
                break;
            case R.id.tvAccountBook://账本
                startActivity(new Intent(this, AccountBookActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ShowTimeActivity.RESULT_CODE) {
            if (requestCode == REQUEST_CODE) {
                date = data.getStringExtra(IntentUtil.DATE);
                startTime = data.getStringExtra(IntentUtil.START_TIME);
                endTime = data.getStringExtra(IntentUtil.END_TIME);
                switch (date) {
                    case "":
                        tvMainTime.setText(String.format(UIUtil.getString(R.string.time_detail), startTime, endTime));
                        break;
                    case "day":
                        tvMainTime.setText("今日");
                        break;
                    case "week":
                        tvMainTime.setText("近7天");
                        break;
                    case "month":
                        tvMainTime.setText("近30天");
                        break;
                }
                isRefresh = true;
                page = 1;
                sendNet();
            }
        }
    }

    @Override
    public void getMainCountResult(MainCountResponse response) {
        if (response.returncode == BaseResponse.RESULT_SUCCESS) {
            MainCountBean mainCountBean = Convert.fromJson(response.returninfo, MainCountBean.class);
            tvDealCount.setText(String.format(UIUtil.getString(R.string.order_count), mainCountBean.count));
            String result = String.format(UIUtil.getString(R.string.main_money),
                    NumberUtil.add(NumberUtil.add(mainCountBean.aliTotal, mainCountBean.unionTotal),
                            mainCountBean.wxTotal));
            SpannableString spannableString = new SpannableString(result);
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.7f);
            StyleSpan styleSpan = new StyleSpan(Typeface.NORMAL);
            spannableString.setSpan(sizeSpan, 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableString.setSpan(styleSpan, 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tvIncomeMoney.setText(spannableString);

            presenter.sendNet(page, date, startTime, endTime, account.companyid, account.fromType, true);
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void getMainListResult(MainListResponse response) {
        if (response.returncode == BaseResponse.RESULT_SUCCESS) {
            page++;
            MainListBean mainListBean = Convert.fromJson(response.returninfo, MainListBean.class);
            hasMore = mainListBean.curPage != mainListBean.totalPage;
            if (isRefresh) {
                adapter.setNewData(mainListBean.dateList);
                adapter.setEmptyView(R.layout.empty_layout, recyclerView);
            } else {
                adapter.addData(mainListBean.dateList);
            }
            if (adapter.isLoading() && hasMore) {
                adapter.loadMoreComplete();
            }
            if (!hasMore) {
                adapter.loadMoreEnd();
            }
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        presenter.sendNet(page, date, startTime, endTime, account.companyid, account.fromType, false);
    }
}
