package com.iai.ishoes.utils.permission;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

public class BlueToothPermission {
    //检查蓝牙权限
    public void BluetoothJudge(Activity activity) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        //判断手机是否具有蓝牙功能
        if (adapter == null) {
            AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("错误").setMessage("你的设备不具备蓝牙功能!").create();
            dialog.show();
            return;
        } else {
            //判断蓝牙开启状态
            if (!adapter.isEnabled()) {
                BluetoothPermission(activity);
            }
        }
    }

    private void BluetoothPermission(Activity context) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(enableBtIntent);
    }
}
