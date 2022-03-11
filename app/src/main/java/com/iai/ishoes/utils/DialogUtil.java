package com.iai.ishoes.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

/**
 * 会话框
 */
public class DialogUtil {
    private static AlertDialog alertDialog;
    public static void alertDialog(Context context,String message,String positiveMsg,DialogInterface.OnClickListener positiveListener,String negativeMsg,
                             DialogInterface.OnClickListener negativeListener) {
        alertDialog = new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveMsg, positiveListener)
                .setNegativeButton(negativeMsg, negativeListener)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ((AlertDialog)dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(0, 145, 222));
                ((AlertDialog)dialogInterface).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(0, 145, 222));
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static void closeAlertDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private static ProgressDialog progressDialog;
    public static void progressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("更新");
        progressDialog.setMessage("当前下载进度");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressNumberFormat("%1d Mb/%2d Mb");
        progressDialog.setCancelable(false);//press the back button to cancel
        progressDialog.show();
    }

    public static void progress(int max,int progress) {
        progressDialog.setMax(max/1024/1024);
        progressDialog.setProgress(progress/1024/1024);
    }

    public static void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
