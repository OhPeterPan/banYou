package com.banyou.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.banyou.app.R;
import com.banyou.app.util.TimeUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.Calendar;

public class TimePickDialog extends Dialog {
    private Context mContext;
    private View mView;
    private DatePicker datePicker;
    private Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);

    private int month = calendar.get(Calendar.MONTH);

    private int day = calendar.get(Calendar.DATE);
    private OnChooseTimeListener listener;

    public TimePickDialog(@NonNull Context context, View v) {
        super(context, R.style.commonDialog);
        this.mContext = context;
        this.mView = v;
    }

    public void setView(View mView) {
        this.mView = mView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        ImmersionBar.with((AppCompatActivity) mContext).fitsSystemWindows(true).statusBarColor(R.color.common_color).init();
        datePicker = findViewById(R.id.datePicker);
        initListener();
    }

    private void initListener() {
        try {
            if (Build.VERSION.SDK_INT < 21) {
                LinearLayout firstLayout = (LinearLayout) datePicker.getChildAt(0);
                firstLayout.getChildAt(0).setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker v, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                if (listener != null)
                    listener.chooseTimeListener(TimeUtil.date2String(calendar.getTime(), TimeUtil.YEAR_MONTH_DAY), mView);
                dismiss();
            }
        });
    }

    public void setChooseTimeListener(OnChooseTimeListener listener) {
        this.listener = listener;
    }

    public interface OnChooseTimeListener {
        void chooseTimeListener(String time, View view);
    }
}
