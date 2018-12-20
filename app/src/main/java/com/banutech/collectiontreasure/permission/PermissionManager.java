package com.banutech.collectiontreasure.permission;

import android.content.Context;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

public class PermissionManager {
    private static PermissionManager sPermissionManager;

    private PermissionManager() {

    }

    public static PermissionManager getInstance() {
        if (sPermissionManager == null) {
            synchronized (PermissionManager.class) {
                if (sPermissionManager == null)
                    sPermissionManager = new PermissionManager();
            }
        }
        return sPermissionManager;
    }

    public void startRequestPermission(final Context context, final Runnable runnable, String... permissions) {
        AndPermission.with(context)
                .permission(permissions)//
                // 准备方法，和 okhttp 的拦截器一样，在请求权限之前先运行改方法，已经拥有权限不会触发该方法
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
                        executor.execute();
                    }
                })
                // 用户给权限了
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (runnable != null)
                            runnable.run();
                    }
                })
                // 用户拒绝权限，包括不再显示权限弹窗也在此列
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            // 打开权限设置页
                            AndPermission.permissionSetting(context).execute();
                            return;
                        }
                    }
                })
                .start();
    }
}
