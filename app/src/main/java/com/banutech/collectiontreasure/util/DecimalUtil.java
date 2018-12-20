package com.banutech.collectiontreasure.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DecimalUtil {
    private static NumberFormat nf = new DecimalFormat("#.##");

    public static String replaceZero(double result) {
        return nf.format(result);
    }
}
