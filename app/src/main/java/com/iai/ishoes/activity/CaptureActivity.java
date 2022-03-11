package com.iai.ishoes.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iai.ishoes.R;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class CaptureActivity extends AppCompatActivity implements QRCodeView.Delegate, View.OnClickListener {
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private ZBarView mZBarView;
    private TextView tvFlashLight;
    private TextView tvChooseQrCode;
    private boolean flashLightIsOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scan);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);    // 去掉标题栏
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mZBarView = findViewById(R.id.zbarview);
        mZBarView.setDelegate(this);
        tvFlashLight = findViewById(R.id.open_flashlight);
        tvChooseQrCode = findViewById(R.id.choose_qrcde_from_gallery);
        tvFlashLight.setOnClickListener(this);
        tvChooseQrCode.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZBarView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZBarView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
    }

    private static final String TAG = "BarCode";

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        vibrate();
        Bundle bundle = new Bundle();
        bundle.putString("result", result);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        this.setResult(RESULT_OK, intent);
        CaptureActivity.this.finish();
//        getParent().startActivityForResult(intent,1);
//        mZBarView.startSpot(); // 开始识别
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        String tipText = mZBarView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZBarView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZBarView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_qrcde_from_gallery:
                Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                        .cameraFileDir(null)
                        .maxChooseCount(1)
                        .selectedPhotos(null)
                        .pauseOnScroll(false)
                        .build();
                startActivityForResult(photoPickerIntent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                break;
            case R.id.open_flashlight:
                flashLightIsOpen=!flashLightIsOpen;
                if(flashLightIsOpen){
                    mZBarView.openFlashlight(); // 打开闪光灯
                    tvFlashLight.setText("轻触关闭");
                }else{
                    mZBarView.closeFlashlight();
                    tvFlashLight.setText("轻触照亮");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mZBarView.startSpotAndShowRect(); // 显示扫描框，并开始识别

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            final String picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0);
            // 本来就用到 QRCodeView 时可直接调 QRCodeView 的方法，走通用的回调
            mZBarView.decodeQRCode(picturePath);

            /*
            没有用到 QRCodeView 时可以调用 QRCodeDecoder 的 syncDecodeQRCode 方法

            这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
            请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github
            .com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
             */
//            new AsyncTask<Void, Void, String>() {
//                @Override
//                protected String doInBackground(Void... params) {
//                    return QRCodeDecoder.syncDecodeQRCode(picturePath);
//                }
//
//                @Override
//                protected void onPostExecute(String result) {
//                    if (TextUtils.isEmpty(result)) {
//                        Toast.makeText(TestScanActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(TestScanActivity.this, result, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }.execute();
        }
    }
}
