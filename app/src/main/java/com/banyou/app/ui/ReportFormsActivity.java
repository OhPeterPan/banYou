package com.banyou.app.ui;

import android.content.Intent;
import android.view.View;
import android.view.ViewStub;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.banyou.app.R;
import com.banyou.app.util.IntentUtil;
import com.banyou.app.util.UIUtil;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class ReportFormsActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private static final int REQUEST_CODE = 0x0010;
    @BindView(R.id.tvReportCalendar)
    TextView tvReportCalendar;
    @BindView(R.id.tvReportOrderMoney)
    TextView tvReportOrderMoney;
    @BindView(R.id.tvReportOrderCount)
    TextView tvReportOrderCount;
    @BindView(R.id.rbReportFormsWeek)
    RadioButton rbReportFormsWeek;
    @BindView(R.id.rbReportFormsMonth)
    RadioButton rbReportFormsMonth;
    @BindView(R.id.rbReportFormsYear)
    RadioButton rbReportFormsYear;
    @BindView(R.id.rgReportForms)
    RadioGroup rgReportForms;
    @BindView(R.id.viewStub)
    ViewStub viewStub;
    @BindView(R.id.tvReportFormsOrderDetail)
    TextView tvReportFormsOrderDetail;
    private String date = "day";
    private String startTime = "";
    private String endTime = "";

    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_forms;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        sendNet();
    }

    private void sendNet() {

    }

    @Override
    protected void initListener() {
        rgReportForms.setOnCheckedChangeListener(this);
        tvReportFormsOrderDetail.setOnClickListener(this);
        tvReportCalendar.setOnClickListener(this);
    }

    @Override
    protected void onInnerListener(View v) {
        switch (v.getId()) {
            case R.id.tvReportFormsOrderDetail://订单明细
                startActivity(new Intent(this, OrderDetailActivity.class));
                break;
            case R.id.tvReportCalendar:
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
                        tvReportCalendar.setText(String.format(UIUtil.getString(R.string.time_detail), startTime, endTime));
                        break;
                    case "day":
                        tvReportCalendar.setText("今日");
                        break;
                    case "week":
                        tvReportCalendar.setText("近7天");
                        break;
                    case "month":
                        tvReportCalendar.setText("近30天");
                        break;
                }
                sendNet();
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbReportFormsWeek:

                break;
            case R.id.rbReportFormsMonth:

                break;
            case R.id.rbReportFormsYear:

                break;
        }
    }
}
