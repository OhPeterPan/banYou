package com.banutech.collectiontreasure.view;

import com.banutech.collectiontreasure.response.ReportChartResponse;
import com.banutech.collectiontreasure.response.ReportFormsResponse;

public interface IReportFormsView extends IView {
    void getOrderInfoSuccess(ReportFormsResponse response);

    void getOrderChartSuccess(ReportChartResponse response);
}
