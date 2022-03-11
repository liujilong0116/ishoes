package com.iai.ishoes.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查位置权限，并设置
 */
public class PermissionList {

    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static void openGPS(String permission, final Activity activity) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
            case Manifest.permission.READ_PHONE_STATE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen(activity)) {
                    new AlertDialog.Builder(activity)
                            .setTitle("提示")
                            .setMessage("请开启手机GPS定位")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("前往设置",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            activity.startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);

                                        }
                                    })
                            .setCancelable(false)
                            .show();
                }
                break;
        }
    }

    private static boolean checkGPSIsOpen(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断是否授予定位权限，手机状态权限，读写权限，没有则授予权限
     * @param activity
     * @return
     */
    public static boolean judgePermission(Activity activity) {
        boolean check = false;
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                openGPS(permission, activity);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(activity, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
            check = true;
        }
        return check;
    }

}
