package com.banyou.app.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.banyou.app.R;
import com.banyou.app.bean.LoginBean;
import com.banyou.app.bean.OperatePersonBean;
import com.banyou.app.bean.QueryDetailBean;
import com.banyou.app.bean.StoreBean;
import com.banyou.app.common.impl.BaseResponse;
import com.banyou.app.dialog.TimePickDialog;
import com.banyou.app.popup_window.PersonPopupWindow;
import com.banyou.app.popup_window.StorePopupWindow;
import com.banyou.app.presenter.AccountBookPresenter;
import com.banyou.app.response.AccountBookResponse;
import com.banyou.app.response.OperatePersonResponse;
import com.banyou.app.response.OrderQueryResponse;
import com.banyou.app.rxBus.RxBus;
import com.banyou.app.util.Convert;
import com.banyou.app.util.SpUtil;
import com.banyou.app.util.TimeUtil;
import com.banyou.app.util.ToastUtil;
import com.banyou.app.util.UIUtil;
import com.banyou.app.view.IAccountBookView;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import butterknife.BindView;

public class AccountBookActivity extends BaseActivity<AccountBookPresenter> implements IAccountBookView, StorePopupWindow.IOnChooseStoreListener, PersonPopupWindow.IOnChoosePersonListener, TimePickDialog.OnChooseTimeListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvAccountBookDate)
    TextView tvAccountBookDate;
    @BindView(R.id.tvAccountBookStore)
    TextView tvAccountBookStore;
    @BindView(R.id.tvAccountBookOperatePerson)
    TextView tvAccountBookOperatePerson;
    @BindView(R.id.tvAccountBookQuery)
    TextView tvAccountBookQuery;
    @BindView(R.id.tvAccountBookQueryCondition)
    TextView tvAccountBookQueryCondition;
    @BindView(R.id.tvAccountBookInfo1)
    TextView tvAccountBookInfo1;
    @BindView(R.id.tvAccountBookInfo2)
    TextView tvAccountBookInfo2;
    @BindView(R.id.tvAccountBookInfo3)
    TextView tvAccountBookInfo3;
    @BindView(R.id.tvAccountBookInfo4)
    TextView tvAccountBookInfo4;
    @BindView(R.id.tvAccountBookInfo5)
    TextView tvAccountBookInfo5;
    @BindView(R.id.tvAccountBookInfo6)
    TextView tvAccountBookInfo6;
    @BindView(R.id.tvAccountBookInfo7)
    TextView tvAccountBookInfo7;
    @BindView(R.id.tvAccountBookInfo8)
    TextView tvAccountBookInfo8;
    @BindView(R.id.tvAccountBookInfo9)
    TextView tvAccountBookInfo9;
    @BindView(R.id.tvAccountBookInfo10)
    TextView tvAccountBookInfo10;
    @BindView(R.id.tvAccountBookInfo11)
    TextView tvAccountBookInfo11;
    @BindView(R.id.tvAccountBookInfo12)
    TextView tvAccountBookInfo12;
    @BindView(R.id.tvAccountBookInfo13)
    TextView tvAccountBookInfo13;
    @BindView(R.id.tvAccountBookInfo14)
    TextView tvAccountBookInfo14;
    @BindView(R.id.tvAccountBookInfo15)
    TextView tvAccountBookInfo15;
    @BindView(R.id.tvAccountBookInfo16)
    TextView tvAccountBookInfo16;
    private LoginBean account;
    private List<StoreBean> stores;
    private StorePopupWindow storePopupWindow;
    private String storeId = "";
    private List<OperatePersonBean> persons;
    private PersonPopupWindow personPopupWindow;
    private String operatorId = "";
    private TimePickDialog timePickDialog;
    private String searchDate = TimeUtil.getNowTime(TimeUtil.YEAR_MONTH_DAY);

    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_book;
    }

    @Override
    protected void initPresenter() {
        presenter = new AccountBookPresenter(this);
    }

    @Override
    protected void initData() {
        RxBus.getInstance().register(presenter);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        tvAccountBookDate.setText(searchDate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(presenter);
    }

    @Override
    protected void initListener() {
        tvAccountBookStore.setOnClickListener(this);
        tvAccountBookOperatePerson.setOnClickListener(this);
        tvAccountBookDate.setOnClickListener(this);
        tvAccountBookQuery.setOnClickListener(this);
    }

    @Override
    protected void onInnerListener(View v) {
        switch (v.getId()) {
            case R.id.tvAccountBookDate://选择时间
                showTimeDialog(v);
                break;
            case R.id.tvAccountBookStore://选择门店
                if (stores == null || stores.isEmpty())
                    presenter.sendNetStore(account.companyid, account.fromType);
                else
                    showChoosePopupWindow();
                break;
            case R.id.tvAccountBookOperatePerson:
                if (persons == null || persons.isEmpty())
                    presenter.sendNetOperatePerson(account.companyid, account.fromType);
                else
                    showChoosePersonPopupWindow();
                break;
            case R.id.tvAccountBookQuery://查询
                queryInfo();
                break;
        }
    }

    private void queryInfo() {
        if (StringUtils.isEmpty(storeId)) {
            ToastUtil.show("请选择所属门店", Toast.LENGTH_SHORT);
            return;
        }
        if (StringUtils.isEmpty(operatorId)) {
            ToastUtil.show("请选择所属门店", Toast.LENGTH_SHORT);
            return;
        }
        String condition = String.format(UIUtil.getString(R.string.order_query_condition), searchDate, " ",
                tvAccountBookStore.getText(), " ", tvAccountBookOperatePerson.getText());
        tvAccountBookQueryCondition.setText(condition);
        presenter.sendNetTaking(searchDate, storeId, operatorId, account.companyid, account.fromType);
    }

    private void showTimeDialog(View v) {
        if (timePickDialog == null) {
            timePickDialog = new TimePickDialog(this, v);
            timePickDialog.setChooseTimeListener(this);
        }
        timePickDialog.show();
    }

    @Override
    public void chooseTimeListener(String time, View view) {
        searchDate = time;
        tvAccountBookDate.setText(time);
    }

    private void showChoosePopupWindow() {
        if (storePopupWindow == null) {
            storePopupWindow = new StorePopupWindow(this, stores);
            storePopupWindow.setOnChooseStoreListener(this);
        }
        storePopupWindow.showPop(tvAccountBookStore);
    }

    private void showChoosePersonPopupWindow() {
        if (personPopupWindow == null) {
            personPopupWindow = new PersonPopupWindow(this, persons);
            personPopupWindow.setOnChoosePersonListener(this);
        }
        personPopupWindow.showPop(tvAccountBookOperatePerson);
    }

    @Override
    public void onChoosePersonListener(OperatePersonBean bean, int position) {
        operatorId = bean.operator_id;
        tvAccountBookOperatePerson.setText(bean.operator_name);
    }

    @Override
    public void onChooseStoreListener(StoreBean storeBean, int position) {
        storeId = storeBean.store_id;
        tvAccountBookStore.setText(storeBean.store_name);
    }

    @Override
    public void getStoreResult(AccountBookResponse response) {
        if (response.returncode == BaseResponse.RESULT_SUCCESS) {
            stores = Convert.fromJson(response.returninfo, new TypeToken<List<StoreBean>>() {
            }.getType());
            if (stores != null && !stores.isEmpty())
                showChoosePopupWindow();
            else
                ToastUtil.show("没有店铺！", Toast.LENGTH_SHORT);
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void getPersonResult(OperatePersonResponse response) {//
        if (response.returncode == BaseResponse.RESULT_SUCCESS) {
            persons = Convert.fromJson(response.returninfo, new TypeToken<List<OperatePersonBean>>() {
            }.getType());
            if (persons != null && !persons.isEmpty())
                showChoosePersonPopupWindow();
            else
                ToastUtil.show("没有操作人！", Toast.LENGTH_SHORT);
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void getOrderQueryResult(OrderQueryResponse response) {
        if (response.returncode == BaseResponse.RESULT_SUCCESS) {
            List<QueryDetailBean> querys = Convert.fromJson(response.returninfo, new TypeToken<List<QueryDetailBean>>() {
            }.getType());
            QueryDetailBean queryDetailBean = querys.get(0);
            initPageInfo(queryDetailBean);
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    private void initPageInfo(QueryDetailBean queryBean) {
        tvAccountBookInfo1.setText(String.format(UIUtil.getString(R.string.account_book_info1), String.valueOf(queryBean.viprecharge)));
        tvAccountBookInfo2.setText(String.format(UIUtil.getString(R.string.account_book_info2), String.valueOf(queryBean.vipcardconsume)));
        tvAccountBookInfo3.setText(String.format(UIUtil.getString(R.string.account_book_info3), String.valueOf(queryBean.novipcardconsume)));
        tvAccountBookInfo4.setText(String.format(UIUtil.getString(R.string.account_book_info4), String.valueOf(queryBean.viprefund)));
        tvAccountBookInfo5.setText(String.format(UIUtil.getString(R.string.account_book_info5), String.valueOf(queryBean.vipcardrefund)));

        tvAccountBookInfo6.setText(String.format(UIUtil.getString(R.string.account_book_info6), String.valueOf(queryBean.consume)));
        tvAccountBookInfo7.setText(String.format(UIUtil.getString(R.string.account_book_info7), String.valueOf(queryBean.refund)));

        tvAccountBookInfo8.setText(String.format(UIUtil.getString(R.string.account_book_info8), String.valueOf(queryBean.income_mendian)));
        tvAccountBookInfo9.setText(String.format(UIUtil.getString(R.string.account_book_info9), String.valueOf(queryBean.income_mall)));

        tvAccountBookInfo10.setText(String.format(UIUtil.getString(R.string.account_book_info10), String.valueOf(queryBean.salecash)));
        tvAccountBookInfo11.setText(String.format(UIUtil.getString(R.string.account_book_info11), String.valueOf(queryBean.salepos)));
        tvAccountBookInfo12.setText(String.format(UIUtil.getString(R.string.account_book_info12), String.valueOf(queryBean.salebank)));
        tvAccountBookInfo13.setText(String.format(UIUtil.getString(R.string.account_book_info13), String.valueOf(queryBean.web_alipay)));
        tvAccountBookInfo14.setText(String.format(UIUtil.getString(R.string.account_book_info14), String.valueOf(queryBean.web_weixin)));
        tvAccountBookInfo15.setText(String.format(UIUtil.getString(R.string.account_book_info15), String.valueOf(queryBean.web_union)));
        tvAccountBookInfo16.setText(String.format(UIUtil.getString(R.string.account_book_info16), String.valueOf(queryBean.ditch_price)));
    }
}
