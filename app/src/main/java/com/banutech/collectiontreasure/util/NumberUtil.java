package com.banutech.collectiontreasure.util;

import android.text.TextUtils;

import com.blankj.utilcode.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by wak on 2018/2/9.
 */

public class NumberUtil {
    public static DecimalFormat zeroFormat = new DecimalFormat("###################.##");
    public static NumberFormat currencyFormat = DecimalFormat.getCurrencyInstance(Locale.CHINA);
    public static NumberFormat percentFormat = DecimalFormat.getPercentInstance(Locale.CHINA);

    /**
     * 获取百分数
     *
     * @param d1
     * @param d2
     * @param scale    设置百分数小数点后最小保留位数
     * @param divScale 设置除法运算之后最小保留位数
     * @return
     */
    public static String getPercentValue(String d1, String d2, int scale, int divScale) {
        if (StringUtils.isEmpty(d1) || StringUtils.isEmpty(d2)) {
            throw new NullPointerException("运算数据不能为空！");
        }

        percentFormat.setMinimumFractionDigits(scale);
        return percentFormat.format(divDown(d1, d2, divScale));
    }

    /**
     * 去除小数点后都是0的情况  传入String格式
     *
     * @param number
     * @return
     */
    public static String replaceZero(String number) {

        return zeroFormat.format(StringUtils.isEmpty(number) ? "0.00" : number);
    }

    /**
     * 去除小数点后都是0的情况
     *
     * @param number
     * @return
     */
    public static String replaceZero(double number) {
        return zeroFormat.format(number);
    }

    /**
     * 获得当前环境下的货币格式
     */
    public static String getCurrency(float number) {
        currencyFormat.setMaximumFractionDigits(2);
        return currencyFormat.format(number);
    }

    /**
     * 获得当前环境下的货币格式  解决精度损失
     */
    public static String getCurrency(double number) {
        currencyFormat.setMaximumFractionDigits(2);
        return currencyFormat.format(number);
    }

    /**
     * 获得当前环境下的货币格式  传入String格式
     */
    public static String getCurrency(String number) {
        currencyFormat.setMaximumFractionDigits(2);

        return currencyFormat.format(Double.valueOf((StringUtils.isEmpty(number) || TextUtils.equals("null", number)) ? "0.00" : number));
    }

    /**
     * 获得当前环境下的货币格式  传入String格式  这个如果不是小数不显示 .00
     */
    public static String getIntegerCurrency(String number) {
        NumberFormat currencyFormat = DecimalFormat.getCurrencyInstance(Locale.CHINA);
        if ((!StringUtils.isEmpty(number)) && number.indexOf('.') == -1) {  //查不到字符'.'，返回-1。另外注意是单引号。
            currencyFormat.setMaximumFractionDigits(0);
        } else {
            currencyFormat.setMaximumFractionDigits(2);
        }
        return currencyFormat.format(Double.valueOf(StringUtils.isEmpty(number) ? "0" : number));
    }

    public static float divDown(int d1, int d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Float.toString(d1));
        BigDecimal b2 = new BigDecimal(Float.toString(d2));
        return (float) (b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue());
    }

    public static double divDown(String d1, String d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();//ROUND_HALF_UP 等于四舍五入，但是有区别 比如2.35=>2.4
    }

    public static float sub(float d1, float d2) {
        BigDecimal b1 = new BigDecimal(Float.toString(d1));
        BigDecimal b2 = new BigDecimal(Float.toString(d2));
        return b1.subtract(b2).floatValue();
    }

    public static double add(double d1, double d2) {//解决精度问题
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    /**
     * @param d1
     * @param d2
     * @return -1  d1小于d2  0 d1=d2  1 d1大于d2
     */
    public static int compare(String d1, String d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.compareTo(b2);
    }

    public static float mul(float d1, float d2) {
        BigDecimal b1 = new BigDecimal(Float.toString(d1));
        BigDecimal b2 = new BigDecimal(Float.toString(d2));
        return b1.multiply(b2).floatValue();
    }

    /**
     * @param data
     * @param scale        小数点后的位数
     * @param roundingMode 进位模式
     * @return
     */
    public static String changeScale(float data, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(String.valueOf(data));
        bd = bd.setScale(scale, roundingMode);
        return bd.toString();
    }

    //RoundingMode.HALF_EVEN  银行家舍入法  BigDecimal.ROUND_HALF_EVEN  银行家舍入法
    public static float mul(String d1, String d2, int scale, int model) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).setScale(scale, model).floatValue();
    }

    public static float add(String d1, String d2, int scale, int model) {//解决精度问题
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).setScale(scale, model).floatValue();
    }

    public static float getFloatNumber(String data) {
        return new BigDecimal(data).floatValue();
    }

    public static float sub(String d1, String d2, int scale, int model) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).setScale(scale, model).floatValue();
    }
}
