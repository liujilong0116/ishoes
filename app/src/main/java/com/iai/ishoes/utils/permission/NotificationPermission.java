package com.iai.ishoes.utils.permission;//package com.zssupersense.supersenseshoes.utils.permission;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.AppOpsManager;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.ApplicationInfo;
//import android.net.Uri;
//import android.os.Build;
//
//import com.zssupersense.supersenseshoes.utils.DialogUtil;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
//public class NotificationPermission {
//
//    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
//    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
//
//    public static void notificationAuthorize(Activity activity) {
//        if (!isNotificationEnabled(activity)) {
//            setNotificationPermission(activity);
//        }
//    }
//
//    @SuppressLint("NewApi")
//    private static boolean isNotificationEnabled(Context context) {
//
//        AppOpsManager mAppOps =
//                (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
//
//        ApplicationInfo appInfo = context.getApplicationInfo();
//        String pkg = context.getApplicationContext().getPackageName();
//        int uid = appInfo.uid;
//        Class appOpsClass = null;
//
//        /* Context.APP_OPS_MANAGER */
//        try {
//            appOpsClass = Class.forName(AppOpsManager.class.getName());
//
//            Method checkOpNoThrowMethod =
//                    appOpsClass.getMethod(CHECK_OP_NO_THROW,
//                            Integer.TYPE, Integer.TYPE, String.class);
//
//            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
//            int value = (Integer) opPostNotificationValue.get(Integer.class);
//
//            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) ==
//                    AppOpsManager.MODE_ALLOWED);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    private static void setNotificationPermission(Activity activity) {
//        DialogUtil.alertDialog(activity, "是否前往手机设置打开通知权限", "进入设置", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//                Intent localIntent = new Intent();
//                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (Build.VERSION.SDK_INT >= 9) {
//                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                    localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
//                } else if (Build.VERSION.SDK_INT <= 8) {
//                    localIntent.setAction(Intent.ACTION_VIEW);
//
//                    localIntent.setClassName("com.android.settings",
//                            "com.android.settings.InstalledAppDetails");
//
//                    localIntent.putExtra("com.android.settings.ApplicationPkgName",
//                            activity.getPackageName());
//                }
//                activity.startActivity(localIntent);
//            }
//        }, "否", null);
//    }
//}
