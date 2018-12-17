package com.banyou.app.ui;


import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.banyou.app.R;
import com.banyou.app.dialog.TimePickDialog;
import com.banyou.app.util.IntentUtil;
import com.banyou.app.util.ToastUtil;
import com.blankj.utilcode.util.StringUtils;
import com.gyf.barlibrary.ImmersionBar;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;

public class ShowTimeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, TimePickDialog.OnChooseTimeListener {
    public static final int RESULT_CODE = 0x0011;
    @BindView(R.id.rbNowDay)
    RadioButton rbNowDay;
    @BindView(R.id.rbOneWeek)
    RadioButton rbOneWeek;
    @BindView(R.id.rbOneMonth)
    RadioButton rbOneMonth;
    @BindView(R.id.rgDefaultTime)
    RadioGroup rgDefaultTime;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvEndTime)
    TextView tvEndTime;
    @BindView(R.id.tvConfirmTime)
    TextView tvConfirmTime;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    private String startTime = "";
    private String endTime = "";
    private String date = "";
    private TimePickDialog timePickDialog;

    @Override
    public void initStatus() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.time_dialog;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initListener() {
        rgDefaultTime.setOnCheckedChangeListener(this);
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        tvConfirmTime.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onInnerListener(View v) {
        switch (v.getId()) {
            case R.id.tvStartTime:
            case R.id.tvEndTime:
                showTimeDialog(v);
                break;
            case R.id.tvConfirmTime:
                confirmTime();
            case R.id.tvCancel:
                finish();
                break;
        }
    }

    private void showTimeDialog(View v) {
        if (timePickDialog == null) {
            timePickDialog = new TimePickDialog(this, v);
            timePickDialog.setChooseTimeListener(this);
        }
        timePickDialog.setView(v);
        timePickDialog.show();
    }

    @Override
    public void chooseTimeListener(String time, View view) {
        date = "";
        switch (view.getId()) {
            case R.id.tvStartTime:
                if (!StringUtils.isEmpty(endTime)) {
                    startTime = time;
                    if (compareTime(startTime, endTime)) {
                        startTime = "";
                        ToastUtil.show("日期间隔不能大于6个月！", Toast.LENGTH_SHORT);
                    } else {
                        tvStartTime.setText(String.valueOf(time));
                    }
                } else {
                    startTime = time;
                    tvStartTime.setText(String.valueOf(time));
                }
                break;
            case R.id.tvEndTime:

                if (!StringUtils.isEmpty(startTime)) {
                    endTime = time;
                    if (compareTime(startTime, endTime)) {
                        endTime = "";
                        ToastUtil.show("日期间隔不能大于6个月！", Toast.LENGTH_SHORT);
                    } else {
                        tvEndTime.setText(String.valueOf(time));
                    }
                } else {
                    endTime = time;
                    tvEndTime.setText(String.valueOf(time));
                }
                break;
        }
    }

    private boolean compareTime(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime timeStart = formatter.parseDateTime(startTime);
        DateTime timeEnd = formatter.parseDateTime(endTime);
        int months = Months.monthsBetween(timeStart, timeEnd).getMonths();
        return Math.abs(months) >= 6;
    }

    private void confirmTime() {
        date = "";
        startTime = tvStartTime.getText().toString().trim();
        endTime = tvEndTime.getText().toString().trim();
        setTimeResult(date, startTime, endTime);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        startTime = "";
        endTime = "";
        switch (checkedId) {
            case R.id.rbNowDay:
                date = "day";
                break;
            case R.id.rbOneWeek:
                date = "week";
                break;
            case R.id.rbOneMonth:
                date = "month";
                break;
        }
        setTimeResult(date, startTime, endTime);
    }

    private void setTimeResult(String date, String startTime, String endTime) {
        Intent intent = new Intent();
        intent.putExtra(IntentUtil.DATE, date);
        intent.putExtra(IntentUtil.START_TIME, startTime);
        intent.putExtra(IntentUtil.END_TIME, endTime);
        setResult(RESULT_CODE, intent);
        finish();
    }
}
