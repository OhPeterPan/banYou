package com.banutech.collectiontreasure.chart_formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class CustomFormatter implements IValueFormatter, IAxisValueFormatter {

    private static String[] SUFFIX = new String[]{
            "", "k", "m", "b", "t"
    };
    private static final int MAX_LENGTH = 5;
    private DecimalFormat mFormat;
    private String mText = "";

    public CustomFormatter() {
        mFormat = new DecimalFormat("###E00");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return makePretty(value);
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return makePretty(value);
    }


    private String makePretty(double number) {

        float result = Float.valueOf(String.format("%.2f", number));

        if (result < 1000) {
            return String.format("%.2f", number);
        }

        String r = mFormat.format(number);

        int numericValue1 = Character.getNumericValue(r.charAt(r.length() - 1));
        int numericValue2 = Character.getNumericValue(r.charAt(r.length() - 2));
        int combined = Integer.valueOf(numericValue2 + "" + numericValue1);

        r = r.replaceAll("E[0-9][0-9]", SUFFIX[combined / 3]);

        while (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }

        return r;
    }

}
