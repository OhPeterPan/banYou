package com.banyou.app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.banyou.app.R;
import com.banyou.app.bean.LoginBean;
import com.banyou.app.bean.ReportChartBean;
import com.banyou.app.bean.ReportFormsBean;
import com.banyou.app.chart_formatter.CustomFormatter;
import com.banyou.app.common.impl.BaseResponse;
import com.banyou.app.presenter.ReportFormsPresenter;
import com.banyou.app.response.ReportChartResponse;
import com.banyou.app.response.ReportFormsResponse;
import com.banyou.app.rxBus.RxBus;
import com.banyou.app.util.Convert;
import com.banyou.app.util.IntentUtil;
import com.banyou.app.util.SpUtil;
import com.banyou.app.util.ToastUtil;
import com.banyou.app.util.UIUtil;
import com.banyou.app.view.IReportFormsView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class ReportFormsActivity extends BaseActivity<ReportFormsPresenter> implements RadioGroup.OnCheckedChangeListener, IReportFormsView {
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
    /*    @BindView(R.id.viewStub)
        ViewStub viewStub;*/
    @BindView(R.id.reportData)
    FrameLayout reportData;
    @BindView(R.id.tvReportFormsOrderDetail)
    TextView tvReportFormsOrderDetail;
    private String date = "day";
    private String chartDate = "week";
    private String startTime = "";
    private String endTime = "";
    private LoginBean account;
    private boolean isShowChart = true;
    private ArrayList<Float> yValues;
    private List<String> xValues;

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
        presenter = new ReportFormsPresenter(this);
    }

    @Override
    protected void initData() {
        RxBus.getInstance().register(presenter);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        sendNet();
    }

    private void initTest(List<Float> list, List<String> dateList) {
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        if (list != null && dateList != null) {
            for (int i = 0; i < list.size(); i++) {
                yValues.add(Float.valueOf(list.get(i)));
                xValues.add(dateList.get(i));
            }
        }
        initLineChart(new LineChart(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(presenter);
    }

    private void sendNet() {
        presenter.sendNetQueryOrderResult(date, startTime, endTime, account.companyid, account.fromType);
    }

    private void sendNetChart() {
        presenter.sendNetQueryOrderResult(chartDate, account.companyid, account.fromType);
    }

    @Override
    public void getOrderInfoSuccess(ReportFormsResponse response) {
        if (response.returncode == BaseResponse.RESULT_SUCCESS) {
            ReportFormsBean reportFormsBean = Convert.fromJson(response.returninfo, ReportFormsBean.class);
            tvReportOrderMoney.setText(String.valueOf(reportFormsBean.money));
            tvReportOrderCount.setText(String.valueOf(reportFormsBean.num));
            if (isShowChart) {
                isShowChart = false;
                sendNetChart();
            }
            hideLoading();
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void getOrderChartSuccess(ReportChartResponse response) {
        if (response.returncode == BaseResponse.RESULT_SUCCESS) {
            ReportChartBean reportFormsBean = Convert.fromJson(response.returninfo, ReportChartBean.class);

            List<Float> list = reportFormsBean.data;
            List<String> dateList = reportFormsBean.dateList;
            initTest(list, dateList);
        } else {
            ToastUtil.show(response.errormsg, Toast.LENGTH_SHORT);
        }
    }

    private void initLineChart(LineChart lineChart) {
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);

        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否可以拖动
        lineChart.setDragEnabled(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setExtraBottomOffset(5);
        lineChart.setNoDataText("暂无数据");
        lineChart.animateX(1000);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(-0.2f);
        //xAxis.setAxisMaximum(xAxis.getAxisMaximum() + 0.2f);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setLabelCount(xValues.size(), false);
        xAxis.setTextSize(10);
        //设置x轴label
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));

        lineChart.getAxisRight().setEnabled(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawGridLines(true);
        yAxis.setGranularity(1f);
        yAxis.setLabelCount(6, true);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(Collections.max(yValues) + 1);
        yAxis.setDrawAxisLine(false);

 /*       yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.format("%.2f", value);
            }
        });*/

        yAxis.setValueFormatter(new CustomFormatter());

        /***折线图例 标签 设置***/
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
        showLineChart(lineChart, yValues, "交易数据趋势图", Color.BLACK);
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
/*        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);*/
    }

    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public void showLineChart(LineChart lineChart, List<Float> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        Entry entry;
        for (int i = 0; i < dataList.size(); i++) {
            entry = new Entry(i, dataList.get(i));
            entries.add(entry);
        }

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        reportData.removeAllViews();
        reportData.addView(lineChart, new FrameLayout.LayoutParams(-1, -1));
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {

        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);

        lineDataSet.setHighlightEnabled(true);
        //设置折线图填充
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(13.f);

        lineDataSet.setValueFormatter(new DefaultValueFormatter(2));
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
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
                chartDate = "week";
                break;
            case R.id.rbReportFormsMonth:
                chartDate = "month";
                break;
            case R.id.rbReportFormsYear:
                chartDate = "years";
                break;
        }
        sendNetChart();
    }
}
