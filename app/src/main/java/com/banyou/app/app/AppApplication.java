package com.banyou.app.app;

import android.app.Application;

import com.banyou.app.util.UIUtil;
import com.blankj.utilcode.util.Utils;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.Locale;

public class AppApplication extends Application {
    private static Application app;

    public static Application getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Utils.init(this);
        Album.initialize(
                AlbumConfig.newBuilder(UIUtil.getContext())
                        .setLocale(Locale.getDefault())
                        .build()
        );
    }
}
