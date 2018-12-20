package com.banutech.collectiontreasure.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
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

import com.banutech.collectiontreasure.MainAdapter;
import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.bean.LoginBean;
import com.banutech.collectiontreasure.bean.MainCountBean;
import com.banutech.collectiontreasure.bean.MainListBean;
import com.banutech.collectiontreasure.bean.QRcodeBean;
import com.banutech.collectiontreasure.code.encode.CodeCreator;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.dialog.QRcodeDialog;
import com.banutech.collectiontreasure.permission.PermissionManager;
import com.banutech.collectiontreasure.presenter.MainPresenter;
import com.banutech.collectiontreasure.response.MainCountResponse;
import com.banutech.collectiontreasure.response.MainListResponse;
import com.banutech.collectiontreasure.response.QRcodeResponse;
import com.banutech.collectiontreasure.rxBus.RxBus;
import com.banutech.collectiontreasure.util.Convert;
import com.banutech.collectiontreasure.util.IntentUtil;
import com.banutech.collectiontreasure.util.LogUtil;
import com.banutech.collectiontreasure.util.NumberUtil;
import com.banutech.collectiontreasure.util.SpUtil;
import com.banutech.collectiontreasure.util.ToastUtil;
import com.banutech.collectiontreasure.util.UIUtil;
import com.banutech.collectiontreasure.view.IMainView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.zxing.WriterException;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

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
    @BindView(R.id.tvReportForms)
    TextView tvReportForms;
    @BindView(R.id.tvMainPay)
    TextView tvMainPay;
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
    private QRcodeDialog QRcode;

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
        tvReportForms.setOnClickListener(this);
        tvMainPay.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        RxBus.getInstance().register(presenter);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        JPushInterface.setAlias(this, 1, account.mobile);//设置别名  想不到什么好地方使用这个别名
        initAdapter();
        sendNet();
        showCheckNotification();
        checkPermissionManager();
    }

    private void checkPermissionManager() {
        PermissionManager.getInstance().startRequestPermission(this, null, Manifest.permission.RECORD_AUDIO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(presenter);
    }

    private void showCheckNotification() {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {//判断是否有通知权限  android4.4以上通用
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog);
            builder.setMessage("应用通知权限未打开，打开后才能收到通知！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 9) {
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    } else if (Build.VERSION.SDK_INT <= 8) {
                        localIntent.setAction(Intent.ACTION_VIEW);
                        localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                        localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                    }
                    startActivity(localIntent);
                }
            });
            builder.setCancelable(false);
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    }

    private void initAdapter() {
        adapter = new MainAdapter();
        recyclerView.setHasFixedSize(true);
        adapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void sendNet() {
        page = 1;
        isRefresh = true;
        presenter.sendNet(date, startTime, endTime, account.companyid, account.fromType, account.storeId);
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
            case R.id.tvReportForms://报表
                startActivity(new Intent(this, ReportFormsActivity.class));
                break;
            case R.id.tvMainPay://收款  暂时用来测试支付宝或微信收款
                presenter.sendNetPayCode(account.storeId, account.companyid, account.fromType);
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
            presenter.sendNet(page, date, startTime, endTime, account.companyid, account.fromType, account.storeId, true);
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
    protected void adapterError() {
        super.adapterError();
        if (adapter != null && adapter.isLoading()) {
            adapter.loadMoreFail();
        }
    }

    @Override
    public void speak(final String message) {//语音播报(播放逻辑已经挪到服务中)
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        if (QRcode != null && QRcode.isShowing())
            QRcode.dismiss();
        sendNet();
    }

    @Override
    public void getQRcodeResult(QRcodeResponse response) {
        if (response.returncode == BaseResponse.RESULT_SUCCESS) {
            QRcodeBean qRcodeBean = Convert.fromJson(response.returninfo, QRcodeBean.class);
            showQRcodeDialog(qRcodeBean);
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    private void showQRcodeDialog(QRcodeBean qRcodeBean) {
        try {
            Bitmap bitmap = CodeCreator.createQRCode(qRcodeBean.code);
            QRcodeDialog qRcodeDialog = new QRcodeDialog(this, bitmap);
            this.QRcode = qRcodeDialog;
            qRcodeDialog.show();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        LogUtil.logI("wak", "页码：" + page);
        presenter.sendNet(page, date, startTime, endTime, account.companyid, account.fromType, account.storeId, false);
    }
}
