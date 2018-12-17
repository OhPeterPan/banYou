package com.banyou.app.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.banyou.app.R;
import com.banyou.app.bean.LoginBean;
import com.banyou.app.bean.StoreBean;
import com.banyou.app.common.impl.BaseResponse;
import com.banyou.app.presenter.AccountBookPresenter;
import com.banyou.app.response.AccountBookResponse;
import com.banyou.app.rxBus.RxBus;
import com.banyou.app.util.Convert;
import com.banyou.app.util.SpUtil;
import com.banyou.app.util.ToastUtil;
import com.banyou.app.view.IAccountBookView;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import butterknife.BindView;

public class AccountBookActivity extends BaseActivity<AccountBookPresenter> implements IAccountBookView {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(presenter);
    }

    @Override
    protected void initListener() {
        tvAccountBookStore.setOnClickListener(this);
    }

    @Override
    protected void onInnerListener(View v) {
        switch (v.getId()) {
            case R.id.tvAccountBookStore://选择门店
                if (stores == null || stores.isEmpty())
                    presenter.sendNetStore(account.companyid, account.fromType);
                else
                    showChoosePopupWindow();
                break;
        }
    }

    private void showChoosePopupWindow() {

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
}
