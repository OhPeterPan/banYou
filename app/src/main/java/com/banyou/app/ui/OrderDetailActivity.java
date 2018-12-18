package com.banyou.app.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.banyou.app.R;
import com.banyou.app.adapter.OrderDetailAdapter;
import com.banyou.app.bean.LoginBean;
import com.banyou.app.bean.MainCountBean;
import com.banyou.app.bean.MainListBean;
import com.banyou.app.common.impl.BaseResponse;
import com.banyou.app.presenter.OrderDetailPresenter;
import com.banyou.app.response.OrderDetailCountResponse;
import com.banyou.app.response.OrderDetailListResponse;
import com.banyou.app.rxBus.RxBus;
import com.banyou.app.util.Convert;
import com.banyou.app.util.IntentUtil;
import com.banyou.app.util.SpUtil;
import com.banyou.app.util.ToastUtil;
import com.banyou.app.util.UIUtil;
import com.banyou.app.view.IOrderDetailView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements BaseQuickAdapter.RequestLoadMoreListener, IOrderDetailView {
    private static final int REQUEST_CODE = 0x0010;
    @BindView(R.id.tvOrderDetailCalendar)
    TextView tvOrderDetailCalendar;
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
    private View headView;
    private TextView tvOrderDetailInfo;
    private OrderDetailAdapter adapter;

    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initPresenter() {
        presenter = new OrderDetailPresenter(this);
    }

    @Override
    protected void initData() {
        RxBus.getInstance().register(presenter);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initAdapter();
        initHeadView();
        sendNet();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(presenter);
    }

    private void initHeadView() {
        headView = LayoutInflater.from(this).inflate(R.layout.layout_order_detail_head, null);
        tvOrderDetailInfo = headView.findViewById(R.id.tvOrderDetailInfo);
        adapter.addHeaderView(headView);
    }

    private void initAdapter() {
        adapter = new OrderDetailAdapter();
        recyclerView.setHasFixedSize(true);
        adapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setHeaderFooterEmpty(true, false);
        recyclerView.setAdapter(adapter);
    }

    private void sendNet() {
        presenter.sendNet(date, startTime, endTime, account.companyid, account.fromType);
    }

    @Override
    public void getOrderInfo(OrderDetailCountResponse response) {
        if (response.returncode == BaseResponse.RESULT_SUCCESS) {
            MainCountBean mainCountBean = Convert.fromJson(response.returninfo, MainCountBean.class);
            tvOrderDetailInfo.setText(String.format(UIUtil.getString(R.string.order_info), mainCountBean.count, mainCountBean.wxTotal, mainCountBean.aliTotal, mainCountBean.unionTotal));
            sendNetOrderList(true);
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void getOrderListInfo(OrderDetailListResponse response) {
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

    /**
     * 获取订单详细列表
     *
     * @param showDialog 是否显示loading框
     */
    private void sendNetOrderList(boolean showDialog) {
        presenter.sendNet(page, date, startTime, endTime, account.companyid, account.fromType, showDialog);
    }

    @Override
    protected void initListener() {
        tvOrderDetailCalendar.setOnClickListener(this);
    }

    @Override
    protected void onInnerListener(View v) {
        switch (v.getId()) {
            case R.id.tvOrderDetailCalendar:
                startActivityForResult(new Intent(this, ShowTimeActivity.class), REQUEST_CODE);
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
                        tvOrderDetailCalendar.setText(String.format(UIUtil.getString(R.string.time_detail), startTime, endTime));
                        break;
                    case "day":
                        tvOrderDetailCalendar.setText("今日");
                        break;
                    case "week":
                        tvOrderDetailCalendar.setText("近7天");
                        break;
                    case "month":
                        tvOrderDetailCalendar.setText("近30天");
                        break;
                }
                isRefresh = true;
                page = 1;
                sendNet();
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        sendNetOrderList(false);
    }
}
