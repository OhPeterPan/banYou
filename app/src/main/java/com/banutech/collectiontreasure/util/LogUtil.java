package com.banutech.collectiontreasure.util;

import com.blankj.utilcode.util.LogUtils;

/**
 * Created by Administrator on 2018/1/22 0022.
 * 对logutils再次进行封装
 */

public class LogUtil {

    public static void logE(String tag, Throwable e) {
        if (SystemUtil.getDebug())
            LogUtils.eTag(tag, e);
    }

    public static void logI(String tag, Object o) {
        if (SystemUtil.getDebug())
            LogUtils.iTag(tag, o);
    }
}
