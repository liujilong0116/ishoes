package com.iai.ishoes.utils.fragmentUtils;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.provider.Settings;

import com.iai.ishoes.service.BluetoothLeServiceLeft;
import com.iai.ishoes.service.BluetoothLeServiceRight;

public class HomeUtils {
    public void bluetoothStateDialog(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage("蓝牙已关闭，请打开蓝牙以连接优感鞋")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("取消",null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(0, 145, 222));
                ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(0, 145, 222));
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public IntentFilter makeGattUpdateIntentFilterLeft() {                        //注册接收的事件
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeServiceLeft.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeServiceLeft.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeServiceLeft.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeServiceLeft.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothDevice.ACTION_UUID);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        return intentFilter;
    }

    public IntentFilter makeGattUpdateIntentFilterRight() {                        //注册接收的事件
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeServiceRight.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeServiceRight.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeServiceRight.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeServiceRight.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_UUID);
        return intentFilter;
    }
}
