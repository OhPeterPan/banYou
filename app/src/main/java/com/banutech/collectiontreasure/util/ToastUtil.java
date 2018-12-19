package com.banutech.collectiontreasure.util;

import android.widget.Toast;

public class ToastUtil {

    private static Toast toast;

    public static void show(String msg, int duration) {
        if (toast == null)
            toast = Toast.makeText(UIUtil.getContext(), "", duration);
        toast.setDuration(duration);
        toast.setText(msg);
        toast.show();
    }
}
