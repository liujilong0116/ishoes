package com.iai.ishoes.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

//import com.google.common.base.Strings;
//import com.j256.ormlite.dao.Dao;


import com.iai.ishoes.R;
import com.iai.ishoes.activity.MainActivity;
import com.iai.ishoes.battery.BatteryView;


//import com.iai.ishoes.bean.DbShoesData;
//import com.iai.ishoes.db.DB;
//import com.iai.ishoes.utils.ExcelUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment implements View.OnClickListener {

    public TextView mTvStateL;
    public TextView mTvStateR;
    public TextView mTvStepCount;
    public TextView mTvDistance;
    public TextView mBatDataL;
    public TextView mTvBatDataR;
    public BatteryView mBatL;
    public BatteryView mBatR;
    public ImageView mIvLeftFoot;
    public ImageView mIvRightFoot;
    public TextView mTvLeftState;
    public TextView mTvRightState;

    private TextView mDataRealTimeL1;
    private TextView mDataRealTimeL2;
    private TextView mDataRealTimeL3;
    private TextView mDataRealTimeL4;
    private TextView mDataRealTimeL5;
    private TextView mDataRealTimeL6;
    private TextView mDataRealTimeL7;
    private TextView mDataRealTimeL8;
    private TextView mDataRealTimeR1;
    private TextView mDataRealTimeR2;
    private TextView mDataRealTimeR3;
    private TextView mDataRealTimeR4;
    private TextView mDataRealTimeR5;
    private TextView mDataRealTimeR6;
    private TextView mDataRealTimeR7;
    private TextView mDataRealTimeR8;


    public boolean init;

    public HomeFragment() {
    }
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_home, container, false);
        mainActivity = (MainActivity) getActivity();
        initView(view);
        return view;
    }

    public Boolean startToRec=false;
    public Boolean firstTimeRec=true;

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.btn_calibration:
//                boolean mConnectedL = mainActivity.leftShoesConnectedStatus;
//                boolean mConnectedR = mainActivity.rightShoesConnectedStatus;
//
//                if(!mConnectedL || !mConnectedR){
//                    Toast.makeText(getContext(),"请先等待鞋子连接完毕",Toast.LENGTH_SHORT).show();
////                    mBtnCalibration.setEnabled(true);
//                    return;
//                }
////                List<Double> leftPressureArr= mainActivity.leftStepDetect.getCurrentPressureValueList();
////                List<Double> rightPressureArr= mainActivity.rightStepDetect.getCurrentPressureValueList();
//                Dao dao= DB.getInstance(getContext()).getDao(DbShoesData.class);
//                if(!startToRec){
//                    startToRec=true;
//                    firstTimeRec=false;
//                    mBtnCalibration.setText("结束记录");
//                    try {
//                        long size=dao.queryBuilder().countOf();
//                        if(size!=0){
//                            dao.deleteBuilder().delete();
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                        Log.e("export",e.getMessage());
//                    }
//                }else{
//                    //结束记录，导出数据
//                    final EditText editText = new EditText(getContext());
//                    editText.setHint("请输入文件名称(请输入数字或字母,若为空,默认为当前时间)");
//                    new AlertDialog.Builder(getActivity())
//                            .setTitle("保存数据文件")
//                            .setView(editText)
////                            .setMessage("请输入文件名称")
//                            .setNegativeButton("取消", null)
//                            .setPositiveButton("保存",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            startToRec = false;
//                                            firstTimeRec = true;
//                                            mBtnCalibration.setEnabled(false);
//                                            mBtnCalibration.setText("开始记录");
//                                            Toast.makeText(getContext(), "正在导出，请稍后", Toast.LENGTH_SHORT).show();
//                                            try {
//                                                List<DbShoesData> shoesDataList = dao.queryBuilder().query();
//                                                if (!shoesDataList.isEmpty()) {
//                                                    ExcelUtil excelUtil = new ExcelUtil();
//                                                    String tempPath = Environment.getExternalStorageDirectory().getPath() + "/优感鞋数据/" + new SimpleDateFormat(" yyyy年MM月dd日", Locale.CHINA).format(new Date());
//                                                    File p = new File(tempPath);
//                                                    if (!p.exists()) {
//                                                        p.mkdirs();
//                                                    }
//                                                    String userInputName = editText.getText().toString();
//                                                    String filePath= "";
//                                                    if(Strings.isNullOrEmpty(userInputName)){
//                                                        filePath = tempPath + "/" + new SimpleDateFormat(" yyyy年MM月dd日 HH:mm:ss", Locale.CHINA).format(new Date()) + ".xls";
//                                                    }else{
//                                                        filePath = tempPath + "/"+userInputName + new SimpleDateFormat(" yyyy年MM月dd日 HH:mm:ss", Locale.CHINA).format(new Date()) + ".xls";
//                                                    }
//
//                                                    File file = new File(filePath);
//                                                    if (!file.exists()) {
////                                file.mkdirs();
//                                                        file.createNewFile();
//                                                    }
//
//                                                    String[] title = {"time", "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8"};
//                                                    excelUtil.initExcel(filePath, title);
//                                                    excelUtil.writeExcel2(shoesDataList, filePath, getContext());
//                                                    Toast.makeText(getContext(), "导出成功", Toast.LENGTH_SHORT).show();
//                                                    mBtnCalibration.setEnabled(true);
//                                                }
//                                            } catch (SQLException | IOException e) {
//                                                e.printStackTrace();
//                                                Log.e("export", e.getMessage());
//                                            }
//                                        }
//                                    })
//                            .setCancelable(false)
//                            .show();
//                }
//        }
    }

    public void showPower(Boolean isLeft,Integer status,Integer percent){
        String info = "";
        BatteryView batteryView = isLeft?mBatL:mBatR;
        TextView mTvState = isLeft?mTvLeftState:mTvRightState;
        TextView mBatData = isLeft?mBatDataL:mTvBatDataR;
        if (status == 32) {
            info = "充电中";
            batteryView.setState(1);
        } else if (status == 16) {
//                info = "未充电";
            batteryView.setState(2);
            if (percent <= 20) {
                batteryView.setState(5);
            }
        } else if (status == 48) {
            info = "已充满";
            batteryView.setState(3);

        } else if (status == 64) {
            info = "充电异常";
            batteryView.setState(4);
        }
        mTvState.setText(info);
        batteryView.setPower(100);
        mBatData.setText(percent+"%");

//        if (state == 48) {
//            mBatDataL.setText("100%");
//            mBatL.setPower(100);
//        } else {
//            mBatDataL.setText(percent + "%");
//            mBatL.setPower(percent);
//        }
    }

    public void showEle(String data) {
        try {
            String data1 = data.replace(" ", "");
            int left_right = Integer.parseInt(data1.substring(4, 6), 16); //左右脚，10是左，11是右
            int state = Integer.parseInt(data1.substring(6, 8), 16);
            int percent = Integer.parseInt(data1.substring(8, 10), 16);  //百分数电量
            String info = "";
            int msg = 0;
            if (state == 32) {
                info = "充电中";
                msg = 1;
            } else if (state == 16) {
//                info = "未充电";
                msg = 2;
                if (percent <= 20) {
                    msg = 5;
                }
            } else if (state == 48) {
                info = "已充满";
                msg = 3;
            } else if (state == 64) {
                info = "充电异常";
                msg = 4;
            }
            if (left_right == 10) {
                mTvStateL.setText(info);
                if (state == 48) {
                    mBatDataL.setText("100%");
                    mBatL.setPower(100);
                } else {
                    mBatDataL.setText(percent + "%");
                    mBatL.setPower(percent);
                }
                switch (msg) {
                    case 1:
                        mBatL.setState(1);
                        break;
                    case 2:
                        mBatL.setState(2);
                        break;
                    case 3:
                        mBatL.setState(3);
                        break;
                    case 4:
                        mBatL.setState(4);
                        break;
                    case 5:
                        mBatL.setState(5);
                        break;
                    default:
                        break;
                }
            } else {
                mTvStateR.setText(info);
                if (state == 48) {
                    mTvBatDataR.setText("100%");
                    mBatR.setPower(100);
                } else {
                    mTvBatDataR.setText(percent + "%");
                    mBatR.setPower(percent);
                }
                switch (msg) {
                    case 1:
                        mBatR.setState(1);
                        break;
                    case 2:
                        mBatR.setState(2);
                        break;
                    case 3:
                        mBatR.setState(3);
                        break;
                    case 4:
                        mBatR.setState(4);
                        break;
                    case 5:
                        mBatR.setState(5);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(View view) {
        mTvStepCount = (TextView) view.findViewById(R.id.tv_step_count);
        mTvDistance = (TextView) view.findViewById(R.id.tv_distance);
        mBatL = (BatteryView) view.findViewById(R.id.battery_l);
        mBatDataL = (TextView) view.findViewById(R.id.battery_data_l);
        mBatR = (BatteryView) view.findViewById(R.id.battery_r);
        mTvBatDataR = (TextView) view.findViewById(R.id.tv_battery_data_r);
        mTvStateL = (TextView) view.findViewById(R.id.tv_state_l);
        mTvStateR = (TextView) view.findViewById(R.id.tv_state_r);

//        mIvLeftFoot = view.findViewById(R.id.iv_left_foot);
//        mIvRightFoot = view.findViewById(R.id.iv_right_foot);
        mTvLeftState = view.findViewById(R.id.left_state);
        mTvRightState = view.findViewById(R.id.right_state);

        mDataRealTimeL1 = view.findViewById(R.id.data_real_time_l1);
        mDataRealTimeL2 = view.findViewById(R.id.data_real_time_l2);
        mDataRealTimeL3 = view.findViewById(R.id.data_real_time_l3);
        mDataRealTimeL4 = view.findViewById(R.id.data_real_time_l4);
        mDataRealTimeL5 = view.findViewById(R.id.data_real_time_l5);
        mDataRealTimeL6 = view.findViewById(R.id.data_real_time_l6);
        mDataRealTimeL7 = view.findViewById(R.id.data_real_time_l7);
        mDataRealTimeL8 = view.findViewById(R.id.data_real_time_l8);
        mDataRealTimeR1 = view.findViewById(R.id.data_real_time_r1);
        mDataRealTimeR2 = view.findViewById(R.id.data_real_time_r2);
        mDataRealTimeR3 = view.findViewById(R.id.data_real_time_r3);
        mDataRealTimeR4 = view.findViewById(R.id.data_real_time_r4);
        mDataRealTimeR5 = view.findViewById(R.id.data_real_time_r5);
        mDataRealTimeR6 = view.findViewById(R.id.data_real_time_r6);
        mDataRealTimeR7 = view.findViewById(R.id.data_real_time_r7);
        mDataRealTimeR8 = view.findViewById(R.id.data_real_time_r8);


        init = true;
    }


    /**
     * @param f 电阻1-R/R0
     */
    public void setColors(List<Double> f, boolean isLeft) {
//        calibrationTextColor();
        if(isLeft){
            setColor(f.get(0), isLeft, mDataRealTimeL1);
            setColor(f.get(1), isLeft, mDataRealTimeL2);
            setColor(f.get(2), isLeft, mDataRealTimeL3);
            setColor(f.get(3), isLeft, mDataRealTimeL4);
            setColor(f.get(4), isLeft, mDataRealTimeL5);
            setColor(f.get(5), isLeft, mDataRealTimeL6);
            setColor(f.get(6), isLeft, mDataRealTimeL7);
            setColor(f.get(7), isLeft, mDataRealTimeL8);
        }else{
            setColor(f.get(0), isLeft, mDataRealTimeR1);
            setColor(f.get(1), isLeft, mDataRealTimeR2);
            setColor(f.get(2), isLeft, mDataRealTimeR3);
            setColor(f.get(3), isLeft, mDataRealTimeR4);
            setColor(f.get(4), isLeft, mDataRealTimeR5);
            setColor(f.get(5), isLeft, mDataRealTimeR6);
            setColor(f.get(6), isLeft, mDataRealTimeR7);
            setColor(f.get(7), isLeft, mDataRealTimeR8);
        }
    }
    public void setColors(double[] f, boolean isLeft) {
//        calibrationTextColor();
        if(isLeft){
            setColor(f[0], isLeft, mDataRealTimeL1);
            setColor(f[1], isLeft, mDataRealTimeL2);
            setColor(f[2], isLeft, mDataRealTimeL3);
            setColor(f[3], isLeft, mDataRealTimeL4);
            setColor(f[4], isLeft, mDataRealTimeL5);
            setColor(f[5], isLeft, mDataRealTimeL6);
            setColor(f[6], isLeft, mDataRealTimeL7);
            setColor(f[7], isLeft, mDataRealTimeL8);
        }else{
            setColor(f[0], isLeft, mDataRealTimeR1);
            setColor(f[1], isLeft, mDataRealTimeR2);
            setColor(f[2], isLeft, mDataRealTimeR3);
            setColor(f[3], isLeft, mDataRealTimeR4);
            setColor(f[4], isLeft, mDataRealTimeR5);
            setColor(f[5], isLeft, mDataRealTimeR6);
            setColor(f[6], isLeft, mDataRealTimeR7);
            setColor(f[7], isLeft, mDataRealTimeR8);
        }
    }
    private void setColor(double f, boolean isLeft, TextView textView) {
//        textView.getBackground().setCallback(null);
        if (isLeft) {
            if (textView == mDataRealTimeL1) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.left1_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.left1_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.left1_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.left1_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.left1_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.left1_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.left1_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.left1_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.left1_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.left1_1);
                }
            } else if (textView == mDataRealTimeL2) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.left2_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.left2_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.left2_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.left2_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.left2_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.left2_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.left2_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.left2_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.left2_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.left2_1);
                }
            } else if (textView == mDataRealTimeL3) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.left3_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.left3_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.left3_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.left3_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.left3_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.left3_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.left3_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.left3_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.left3_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.left3_1);
                }
            } else if (textView == mDataRealTimeL4) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.left4_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.left4_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.left4_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.left4_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.left4_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.left4_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.left4_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.left4_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.left4_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.left4_1);
                }
            } else if (textView == mDataRealTimeL5) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.left5_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.left5_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.left5_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.left5_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.left5_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.left5_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.left5_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.left5_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.left5_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.left5_1);
                }
            } else if (textView == mDataRealTimeL6) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.left6_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.left6_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.left6_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.left6_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.left6_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.left6_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.left6_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.left6_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.left6_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.left6_1);
                }
            } else if (textView == mDataRealTimeL7) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.left7_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.left7_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.left7_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.left7_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.left7_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.left7_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.left7_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.left7_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.left7_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.left7_1);
                }
            } else if (textView == mDataRealTimeL8) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.left8_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.left8_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.left8_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.left8_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.left8_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.left8_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.left8_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.left8_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.left8_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.left8_1);
                }
            }
        } else {
            if (textView == mDataRealTimeR1) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.right1_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.right1_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.right1_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.right1_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.right1_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.right1_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.right1_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.right1_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.right1_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.right1_1);
                }
            } else if (textView == mDataRealTimeR2) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.right2_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.right2_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.right2_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.right2_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.right2_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.right2_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.right2_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.right2_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.right2_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.right2_1);
                }
            } else if (textView == mDataRealTimeR3) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.right3_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.right3_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.right3_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.right3_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.right3_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.right3_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.right3_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.right3_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.right3_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.right3_1);
                }
            } else if (textView == mDataRealTimeR4) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.right4_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.right4_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.right4_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.right4_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.right4_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.right4_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.right4_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.right4_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.right4_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.right4_1);
                }
            } else if (textView == mDataRealTimeR5) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.right5_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.right5_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.right5_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.right5_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.right5_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.right5_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.right5_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.right5_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.right5_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.right5_1);
                }
            } else if (textView == mDataRealTimeR6) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.right6_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.right6_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.right6_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.right6_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.right6_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.right6_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.right6_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.right6_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.right6_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.right6_1);
                }
            } else if (textView == mDataRealTimeR7) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.right7_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.right7_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.right7_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.right7_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.right7_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.right7_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.right7_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.right7_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.right7_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.right7_1);
                }
            } else if (textView == mDataRealTimeR8) {
                if (0 <= f && f < 5) {
                    textView.setBackgroundResource(R.drawable.right8_10);
                } else if (5 <= f && f < 10) {
                    textView.setBackgroundResource(R.drawable.right8_9);
                } else if (10 <= f && f < 15) {
                    textView.setBackgroundResource(R.drawable.right8_8);
                } else if (15 <= f && f < 20) {
                    textView.setBackgroundResource(R.drawable.right8_7);
                } else if (20 <= f && f < 25) {
                    textView.setBackgroundResource(R.drawable.right8_6);
                } else if (25 <= f && f < 30) {
                    textView.setBackgroundResource(R.drawable.right8_5);
                } else if (30 <= f && f < 40) {
                    textView.setBackgroundResource(R.drawable.right8_4);
                } else if (40 <= f && f < 60) {
                    textView.setBackgroundResource(R.drawable.right8_3);
                } else if (60 <= f && f < 80) {
                    textView.setBackgroundResource(R.drawable.right8_2);
                } else if (80 <= f) {
                    textView.setBackgroundResource(R.drawable.right8_1);
                }
            }
        }
    }

    //Connection complete display
    public void calibrationTextColor(boolean isLeft) {
        if (isLeft) {
            mDataRealTimeL1.setTextColor(Color.WHITE);
            mDataRealTimeL2.setTextColor(Color.WHITE);
            mDataRealTimeL3.setTextColor(Color.WHITE);
            mDataRealTimeL4.setTextColor(Color.WHITE);
            mDataRealTimeL5.setTextColor(Color.WHITE);
            mDataRealTimeL6.setTextColor(Color.WHITE);
            mDataRealTimeL7.setTextColor(Color.WHITE);
            mDataRealTimeL8.setTextColor(Color.WHITE);
        } else {
            mDataRealTimeR1.setTextColor(Color.WHITE);
            mDataRealTimeR2.setTextColor(Color.WHITE);
            mDataRealTimeR3.setTextColor(Color.WHITE);
            mDataRealTimeR4.setTextColor(Color.WHITE);
            mDataRealTimeR5.setTextColor(Color.WHITE);
            mDataRealTimeR6.setTextColor(Color.WHITE);
            mDataRealTimeR7.setTextColor(Color.WHITE);
            mDataRealTimeR8.setTextColor(Color.WHITE);
        }
    }

    //Unconnected display
    public void showNotConnected(boolean isLeft) {
        if (isLeft) {
            mDataRealTimeL1.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeL2.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeL3.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeL4.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeL5.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeL6.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeL7.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeL8.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeL1.setBackgroundResource(R.drawable.left1_null);
            mDataRealTimeL2.setBackgroundResource(R.drawable.left2_null);
            mDataRealTimeL3.setBackgroundResource(R.drawable.left3_null);
            mDataRealTimeL4.setBackgroundResource(R.drawable.left4_null);
            mDataRealTimeL5.setBackgroundResource(R.drawable.left5_null);
            mDataRealTimeL6.setBackgroundResource(R.drawable.left6_null);
            mDataRealTimeL7.setBackgroundResource(R.drawable.left7_null);
            mDataRealTimeL8.setBackgroundResource(R.drawable.left8_null);
        } else {
            mDataRealTimeR1.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeR2.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeR3.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeR4.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeR5.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeR6.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeR7.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeR8.setTextColor(Color.rgb(115, 115, 115));
            mDataRealTimeR1.setBackgroundResource(R.drawable.right1_null);
            mDataRealTimeR2.setBackgroundResource(R.drawable.right2_null);
            mDataRealTimeR3.setBackgroundResource(R.drawable.right3_null);
            mDataRealTimeR4.setBackgroundResource(R.drawable.right4_null);
            mDataRealTimeR5.setBackgroundResource(R.drawable.right5_null);
            mDataRealTimeR6.setBackgroundResource(R.drawable.right6_null);
            mDataRealTimeR7.setBackgroundResource(R.drawable.right7_null);
            mDataRealTimeR8.setBackgroundResource(R.drawable.right8_null);
        }
    }
}
