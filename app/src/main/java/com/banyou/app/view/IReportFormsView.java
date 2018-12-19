package com.banyou.app.view;

import com.banyou.app.response.ReportChartResponse;
import com.banyou.app.response.ReportFormsResponse;

public interface IReportFormsView extends IView {
    void getOrderInfoSuccess(ReportFormsResponse response);

    void getOrderChartSuccess(ReportChartResponse response);
}
