package com.iai.ishoes.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.common.base.Strings;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.google.common.util.concurrent.AtomicLongMap;
import com.iai.ishoes.R;
import com.iai.ishoes.adapter.NavBarAdapter;
import com.iai.ishoes.bean.MessageEvent;
import com.iai.ishoes.bean.SensorValueMap;
import com.iai.ishoes.fragment.DataFragment;
import com.iai.ishoes.fragment.HomeFragment;
import com.iai.ishoes.fragment.MyFragment;
import com.iai.ishoes.fragment.ReportFragment;
import com.iai.ishoes.service.BluetoothLeServiceLeft;
import com.iai.ishoes.service.BluetoothLeServiceRight;
import com.iai.ishoes.utils.DialogUtil;
import com.iai.ishoes.utils.DigitalVoltageToPressureUtils;
import com.iai.ishoes.utils.permission.BlueToothPermission;
import com.iai.ishoes.utils.permission.PermissionList;
import com.iai.ishoes.utils.fragmentUtils.HomeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener{
    private RadioGroup mMainRadioGroup;
    private ViewPager mViewpager;
    private RadioButton mMainHome;
    private RadioButton mMainData;
    private RadioButton mMainReport;
    private RadioButton mMainMy;
    private HomeFragment homeFragment;
    public ImageButton mIbSearchConnection;

    private List<Integer> tabs;
    private List<Fragment> fragments;
    private BluetoothAdapter mBluetoothAdapter;

    private NavBarAdapter navBarAdapter;
    private HomeUtils homeUtils = new HomeUtils();

    private boolean isLogin = false;
    private long firstTime;

    private HashSet<BluetoothDevice> mBleDevicesSet = new HashSet<>();
    private String leftUUID;
    private String rightUUID;

    public boolean leftShoesConnectedStatus;//左脚连接状态
    public boolean rightShoesConnectedStatus;//右脚连接状态

    private int bleActionStatus=0;//0=未连接，1=主动连接，2=主动断连

    private boolean isHome = true;
    private boolean isData;
    private boolean isShowTrack;
    private boolean isRegisterL;
    private boolean isRegisterR;

    private Integer leftPower;
    private Integer rightPower;
    public Integer sensorType = -1;

    private List<Double> currentLeftList = new ArrayList<>();
    private List<Double> currentRightList = new ArrayList<>();
//    private StepCountLeft stepCountLeft = new StepCountLeft();
//    private StepCountRight stepCountRight = new StepCountRight();
    private Boolean stepDataInitStatus = false;

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

    public AtomicLongMap<String> sportAlertDataMap = AtomicLongMap.create();//运动期间報警次數
    private double[] lF0 = {10, 10, 10, 10, 10, 10, 10, 10};//基准压力
    private double[] rF0 = {10, 10, 10, 10, 10, 10, 10, 10};//基准压力
    double[] leftPressureArr = new double[8];
    double[] rightPressureArr = new double[8];

    private ScheduledFuture leftShoesConnectDaemon = null;
    private ScheduledFuture rightShoesConnectDaemon = null;

    private BluetoothLeServiceLeft mBluetoothLeServiceLeft;//守护进程
    private BluetoothLeServiceRight mBluetoothLeServiceRight;//守护进程

    private int todayLeftStepCount = 0;//今日左脚步数统计
    private int todayRightStepCount = 0;//今日右脚步数统计

    private AlertDialog alertDialog;

    // 多线程可同时访问的整数
    private AtomicInteger leftDaemonCount = new AtomicInteger(0);
    private AtomicInteger rightDaemonCount = new AtomicInteger(0);

    ScheduledFuture findShoes =null;

    private final static int FIND_SHOES = 0x01;
    private final static int LINK_COMPLETED = 0x02;
    private final static int NOTIFY_TIP = 0x03;
    private static final int DOWNLOAD_ERROR = 0x04;
    private static final int DOWNLOAD_SUCCEED = 0x05;
    private static final int PROGRESS_OVER = 0x06;
    private static final int LEFT_EARLY_WARNING = 0x07;
    private static final int RIGHT_EARLY_WARNING = 0x08;
    private static final int LEFT_SERIOUS = 0x09;
    private static final int RIGHT_SERIOUS = 0x0a;
    private static final int LEFT_DANGER = 0x0b;
    private static final int RIGHT_DANGER = 0x0c;
    private static final int REFRESH_SPORT_COUNT = 0x0d;
    private static final int CONNECT_LEFT_SHOES_SUCCESS = 0x0E;
    private static final int DISCONNECT_LEFT_SHOES = 0x0F;
    private static final int RETRY_CONNECT_LEFT_SHOES = 0x10;
    private static final int CONNECT_RIGHT_SHOES_SUCCESS = 0x11;
    private static final int DISCONNECT_RIGHT_SHOES = 0x12;
    private static final int RETRY_CONNECT_RIGHT_SHOES = 0x13;
    private static final int UPDATE_LEFT_SHOES_PRESSURE_COLOR = 0x14;
    private static final int UPDATE_RIGHT_SHOES_PRESSURE_COLOR = 0x15;
    private static final int ADJUST_ALERT_COUNT = 0x16;
    private static final int POWER_DOWN_WARNING = 0x17;
    public static final int ACTIVE_DISCONNECT = 0x18;

    private Boolean isShowPowerDownWarning = false;

    protected boolean checkLogin() {
        return isLogin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);    // 去掉标题栏
        setContentView(R.layout.activity_main);
        PermissionList.judgePermission(this);
        initView();
        initAdapter();
        initData();
        searchConnection();

        //创建用于各组件之间通信的eventbus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        // 定时任务   定视刷新压力值
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isHome) {
                    if (checkConnectStatus(true)) {
                        handler.sendEmptyMessage(UPDATE_LEFT_SHOES_PRESSURE_COLOR);
                    }
                    if (checkConnectStatus(false)) {
                        handler.sendEmptyMessage(UPDATE_RIGHT_SHOES_PRESSURE_COLOR);
                    }
                }
            }
        }, 0, 50, TimeUnit.MILLISECONDS);

        // 数据写入本地excel    暂时不需要
//        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                if(leftShoesConnectedStatus && rightShoesConnectedStatus){
//                    if(homeFragment.startToRec){
//                        EventBus.getDefault().postSticky(new ExportExcelEvent(leftPressureArr,rightPressureArr,new Date()));
//                    }
//                }
//            }
//        },1,50,TimeUnit.MILLISECONDS);

        //数据接收守护线程
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(leftShoesConnectedStatus && leftDaemonCount.get()>5){
                    leftDaemonCount.set(0);
                    handler.sendEmptyMessageDelayed(RETRY_CONNECT_LEFT_SHOES,new Random(1000).nextInt(1000));
                }
                if(rightShoesConnectedStatus && rightDaemonCount.get()>5){
                    rightDaemonCount.set(0);
                    handler.sendEmptyMessageDelayed(RETRY_CONNECT_RIGHT_SHOES,new Random(1000).nextInt(1000));
                }
            }
        },0,10,TimeUnit.SECONDS);

        //每秒leftDaemonCount和rightDaemonCount + 1
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                leftDaemonCount.incrementAndGet();
                rightDaemonCount.incrementAndGet();
            }
        },10,1,TimeUnit.SECONDS);

        // 每秒刷新记步
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(REFRESH_SPORT_COUNT);
            }
        },0,1,TimeUnit.SECONDS);

    }

    private void initView() {
        initViewPager();
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mViewpager.addOnPageChangeListener(this);
        mMainRadioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
        mMainRadioGroup.setOnCheckedChangeListener(this);

        mMainHome = (RadioButton) findViewById(R.id.main_home);
        mMainHome.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        mMainHome.setOnClickListener(this);
        mMainData = (RadioButton) findViewById(R.id.main_data);
        mMainData.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        mMainData.setOnClickListener(this);
        mMainReport = (RadioButton) findViewById(R.id.main_report);
        mMainReport.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        mMainReport.setOnClickListener(this);
        mMainMy = (RadioButton) findViewById(R.id.main_my);
        mMainMy.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        mMainMy.setOnClickListener(this);
        mIbSearchConnection = (ImageButton) findViewById(R.id.ib_search_connection);
        mIbSearchConnection.setOnClickListener(this);


        // 暂时不明白这段是干啥的
        alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage("当前鞋子电量过低,无法正常工作,请充电后再使用")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isShowPowerDownWarning = false;
                    }
                }).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(0, 145, 222));
                ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(0, 145, 222));
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void initViewPager() {
        tabs = new ArrayList<>();
        tabs.add(R.id.main_home);
        tabs.add(R.id.main_data);
        tabs.add(R.id.main_report);
        tabs.add(R.id.main_my);
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new DataFragment());
        fragments.add(new ReportFragment());
        fragments.add(new MyFragment());
    }

    // 初始化adapter  适配器   页面的缓存？
    private void initAdapter() {
        navBarAdapter = new NavBarAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(navBarAdapter);
        mViewpager.setOffscreenPageLimit(3);//设置缓存页面数，防止从第一个到第四个界面时，第一个fragment会被销毁
    }

    // 数据初始化  初始跳转home页面（页面0）
    public void initData() {
        homeFragment = (HomeFragment) navBarAdapter.getItem(0);
        //GPS broadcast receiver
//        if (positioningReceiver == null) {
//            positioningReceiver = new PositioningReceiver();
//            IntentFilter positioningFilter = new IntentFilter();
//            positioningFilter.addAction("android.location.MODE_CHANGED");
//            registerReceiver(positioningReceiver, positioningFilter);
//        }
        registerReceiver();
    }

    // 注册接收器？     接收蓝牙信号
    private void registerReceiver() {
        Intent gattServiceIntentL = new Intent(this, BluetoothLeServiceLeft.class);
        bindService(gattServiceIntentL, mServiceConnectionLeft, BIND_AUTO_CREATE);
        registerReceiver(mGattUpdateReceiverLeft, homeUtils.makeGattUpdateIntentFilterLeft());
        isRegisterL = true;
        Intent gattServiceIntentR = new Intent(this, BluetoothLeServiceRight.class);
        bindService(gattServiceIntentR, mServiceConnectionRight, BIND_AUTO_CREATE);
        registerReceiver(mGattUpdateReceiverRight, homeUtils.makeGattUpdateIntentFilterRight());
        isRegisterR = true;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        mViewpager.setCurrentItem(tabs.indexOf(i));
//        startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 1);
//        System.out.println("asdfasdfasdfasdfasdfasdfasfdasdf");
//        mViewpager.setCurrentItem(tabs.indexOf(i));
    }

    // 左脚蓝牙服务
    private final ServiceConnection mServiceConnectionLeft = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeServiceLeft = ((BluetoothLeServiceLeft.LocalBinder) service).getService();
            if (!mBluetoothLeServiceLeft.initialize()) {
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeServiceLeft = null;
            leftShoesConnectedStatus = false;
        }
    };

    // 右脚蓝牙服务
    private final ServiceConnection mServiceConnectionRight = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeServiceRight = ((BluetoothLeServiceRight.LocalBinder) service).getService();
            if (!mBluetoothLeServiceRight.initialize()) {
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeServiceRight = null;
            rightShoesConnectedStatus = false;
        }
    };

    //右脚广播接收器
    public final BroadcastReceiver mGattUpdateReceiverLeft = new BroadcastReceiver() {
        @SneakyThrows
        @Override
        public void onReceive(final Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothAdapter.STATE_OFF == intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)) {
                //Bluetooth status dialog
                homeUtils.bluetoothStateDialog(MainActivity.this);
            }
            if (BluetoothLeServiceLeft.ACTION_GATT_CONNECTED.equals(action)) {  //连接成功
                Log.e("connect ble", "left ACTION_GATT_CONNECTED 连接成功");
//                isConnectL = true;
                handler.sendEmptyMessage(CONNECT_LEFT_SHOES_SUCCESS);
            } else if (BluetoothLeServiceLeft.ACTION_GATT_DISCONNECTED.equals(action)) { //断开连接,断电算断开吗？
                Log.e("connect ble", "left ACTION_GATT_DISCONNECTED 断开连接");
//                handler.sendEmptyMessage(DISCONNECT_LEFT_SHOES);
//                leftShoesConnectedStatus = false;
//                connect_1();
                if(bleActionStatus!=2){
                    handler.sendEmptyMessage(RETRY_CONNECT_LEFT_SHOES);
                }
            } else if (BluetoothLeServiceLeft.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {//可以开始干活了
                Log.e("connect ble", "left ACTION_GATT_SERVICES_DISCOVERED 可以开始干活了");
            } else if (BluetoothLeServiceLeft.ACTION_DATA_AVAILABLE.equals(action)) { //收到数据
                leftDaemonCount.set(0);
                String data = intent.getStringExtra(BluetoothLeServiceLeft.EXTRA_DATA);
                if (data.length() == 18) {
                    if (isHome) {
                        String power = data.replaceAll(" ", "");
                        int left_right = Integer.parseInt(power.substring(4, 6), 16);
                        int status = Integer.parseInt(power.substring(6, 8), 16);
                        int percent = Integer.parseInt(power.substring(8, 10), 16);
//                        if (percent < powerDangerThreshold) {
//                            handler.sendEmptyMessage(POWER_DOWN_WARNING);
//                        }
//                        if (left_right == 0x0a) {
                        leftPower = percent;
//                        }
//                        homeFragment.showEle(data);
                        homeFragment.showPower(true,status,percent);
                        homeFragment.mTvStateL.setText("已连接");
                    }
                } else if (data.length() == 21) {

                } else {
                    try {

                        refreshUI(data, true);

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
    //右脚广播接收器
    private final BroadcastReceiver mGattUpdateReceiverRight = new BroadcastReceiver() {
        @SneakyThrows
        @Override
        public void onReceive(final Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeServiceRight.ACTION_GATT_CONNECTED.equals(action)) {  //连接成功
                Log.e("connect ble", "right ACTION_GATT_CONNECTED 连接成功");
//                isConnectR = true;
//                bleConnectDaemon.cancel(false);
//                rightShoesConnectedStatus=true;
                handler.sendEmptyMessage(CONNECT_RIGHT_SHOES_SUCCESS);
            } else if (BluetoothLeServiceRight.ACTION_GATT_DISCONNECTED.equals(action)) { //断开连接
                Log.e("connect ble", "right ACTION_GATT_DISCONNECTED 断开连接");
//                rightShoesConnectedStatus = false;
//                connect_2();
                if(bleActionStatus!=2){
                    handler.sendEmptyMessage(RETRY_CONNECT_RIGHT_SHOES);
                }
            } else if (BluetoothLeServiceRight.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {//可以开始干活了
                Log.e("connect ble", "right ACTION_GATT_SERVICES_DISCOVERED 可以开始干活了");

            } else if (BluetoothLeServiceRight.ACTION_DATA_AVAILABLE.equals(action)) { //收到数据
                String data = intent.getStringExtra(BluetoothLeServiceRight.EXTRA_DATA);
                rightDaemonCount.set(0);
                if (data != null) {
                    if (data.length() == 18) {
                        if (isHome) {
                            String power = data.replaceAll(" ", "");
                            int left_right = Integer.parseInt(power.substring(4, 6), 16);
                            int status = Integer.parseInt(power.substring(6, 8), 16);
                            int percent = Integer.parseInt(power.substring(8, 10), 16);

//                            if (percent < powerDangerThreshold) {
//                                handler.sendEmptyMessage(POWER_DOWN_WARNING);
//                            }
//                            if (left_right == 0x0b) {
                            rightPower = percent;
//                            }
//                            homeFragment.showEle(data);
                            homeFragment.showPower(false,status,percent);
                            homeFragment.mTvStateR.setText("已连接");
                        }
                    } else if (data.length() == 21) {
                        Log.e("data21", data);
                    } else {
                        try {
                            refreshUI(data, false);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    };

    // 实时刷新鞋底压力数据
    private void refreshUI(String data, boolean isLeft) throws ExecutionException {
        if (Strings.isNullOrEmpty(data)) {
            return;
        }
        if (sensorType<=0) {
            return;
        }
        data = data.replace(" ", "");
        int[] voltage = new int[8];
//        List<Integer> voltage=new ArrayList<>(8);
        double[] pressureArr = isLeft ? leftPressureArr : rightPressureArr;
        double[] cpPressureArr=new double[8];
        List<Double> pressureDoubleList = new ArrayList<>();
        double[] tmpCb = isLeft ? lF0 : rF0;
        for (int i = 0; i < 8; i++) {
            voltage[i] = Integer.parseInt(data.substring(4 * i, 4 * (i + 1)), 16);
            pressureArr[i] = DigitalVoltageToPressureUtils.sensorComputeValueCache.get(new SensorValueMap(sensorType, i, voltage[i]));
            if (pressureArr[i] > 40) {
                sportAlertDataMap.incrementAndGet((isLeft ? 0 : 1) + ":" + (i + 1));
            }

            if (pressureArr[i] < tmpCb[i]) {
                tmpCb[i] = pressureArr[i];
            }
            cpPressureArr[i]=pressureArr[i];
            pressureArr[i] = pressureArr[i] - tmpCb[i];
            pressureDoubleList.add(pressureArr[i]);
        }
        if (stepDataInitStatus) {
            if (isLeft) {
                currentLeftList = pressureDoubleList;
//                leftStepDetect.add(pressureDoubleList);
//                stepCountLeft.buttonClick(Collections.singletonList(pressureDoubleList.get(2)));
            } else {
                currentRightList = pressureDoubleList;
//                stepCountRight.buttonClick(Collections.singletonList(pressureDoubleList.get(2)));
//                rightStepDetect.add(pressureDoubleList);
            }
        }
        String log = (isLeft?"左斜":"右鞋") +String.format(" 压力数据 %.3f %.3f %.3f %.3f %.3f %.3f %.3f %.3f || 校准数据  %.3f %.3f %.3f %.3f %.3f %.3f %.3f %.3f",
                cpPressureArr[0],cpPressureArr[1],cpPressureArr[2],cpPressureArr[3],cpPressureArr[4],cpPressureArr[5],cpPressureArr[6],cpPressureArr[7],
                tmpCb[0],tmpCb[1],tmpCb[2],tmpCb[3],tmpCb[4],tmpCb[5],tmpCb[6],tmpCb[7]
        );
//        Log.e("实时数据",log);
//        pressurePointData(isLeft, voltage);//put data into the list for upload
    }

    // 设置搜索按钮开启监听
    private void searchConnection() {
        mIbSearchConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bleReset();
                bleActionStatus=1;
                BlueToothPermission blueToothPermission = new BlueToothPermission();
                blueToothPermission.BluetoothJudge(MainActivity.this);
                BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                if (bluetoothManager != null) {
                    System.out.println("BluetoothAdapter 初始化");
                    mBluetoothAdapter = bluetoothManager.getAdapter();

                    Toast.makeText(MainActivity.this, "正在搜索设备...", Toast.LENGTH_LONG).show();
                    scanBleDevice();
                }
            }
        });
    }

    public void bleReset(){
        disableShoesDaemon(true);
        disableShoesDaemon(false);
        isRegisterL = false;
        isRegisterR = false;
        leftUUID=null;
        rightUUID=null;
        resetLeft();
        resetRight();
    }
    public void resetLeft(){
        mBluetoothLeServiceLeft.disconnect();
        leftShoesConnectedStatus = false;
        handler.sendEmptyMessage(DISCONNECT_LEFT_SHOES);
//        homeFragment.tvLeftBackendTemp.setText("");
//        homeFragment.tvLeftBackendHum.setText("");
//        homeFragment.tvLeftFontTemp.setText("");
//        homeFragment.tvLeftFontHum.setText("");
    }
    public void resetRight(){
        mBluetoothLeServiceRight.disconnect();
        rightShoesConnectedStatus=false;
        handler.sendEmptyMessage(DISCONNECT_RIGHT_SHOES);
//        homeFragment.tvRightBackendTemp.setText("");
//        homeFragment.tvRightBackendHum.setText("");
//        homeFragment.tvRightFontTemp.setText("");
//        homeFragment.tvRightFontHum.setText("");
    }

    /**
     * 扫描蓝牙设备
     */
    private void scanBleDevice() {
        if(findShoes!=null){
            findShoes.cancel(true);
            findShoes = null;
        }
        mBluetoothAdapter.startLeScan(mLeScanCallback);
        findShoes =  scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {

                Log.d("scanBleDevice", "ble scan loop");
                if (mBleDevicesSet.size() >= 2) {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    handler.sendEmptyMessage(FIND_SHOES);
                    Log.d("scanBleDevice", "ble stop scan");
                    findShoes.cancel(true);
                }
            }
        },2,3,TimeUnit.SECONDS);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Log.d("scanBleDevice", "ble scan loop");
//                if (mBleDevicesSet.size() >= 2) {
//                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
//                    handler.sendEmptyMessage(FIND_SHOES);
//                    Log.d("scanBleDevice", "ble stop scan");
//                    timer.cancel();
//                }
//            }
//        }, 3000);
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            Log.d("scanBleDevice","scanning");
            //通过名字过滤一次
            if (!Strings.isNullOrEmpty(bluetoothDevice.getName()) && bluetoothDevice.getName().startsWith("SS")) {
                mBleDevicesSet.add(bluetoothDevice);
            }

        }
    };

    private Boolean checkConnectStatus(Boolean isLeftShoes) {
        if (isLeftShoes) {
            return leftShoesConnectedStatus;
        } else {
            return rightShoesConnectedStatus;
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void getMessage(MessageEvent messageEvent) {
        if (messageEvent.isShowTrack()) {
            isShowTrack = true;//is time to show track
        }
    }

    @Override
    public void onClick(View v) {

    }

    public Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case ACTIVE_DISCONNECT:
                    disableShoesDaemon(true);
                    disableShoesDaemon(false);
                    mBluetoothLeServiceLeft.close();
                    mBluetoothLeServiceRight.close();
                    handler.sendEmptyMessage(DISCONNECT_LEFT_SHOES);
                    handler.sendEmptyMessage(DISCONNECT_RIGHT_SHOES);
                    break;
//                case POWER_DOWN_WARNING:
//                    bleActionStatus=2;
//                    if (isShowPowerDownWarning == false) {
//                        isShowPowerDownWarning = true;
////                        powerDownWarning();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                alertDialog.show();
//                            }
//                        });
//                    }
//
//
////                    DialogUtil.closeAlertDialog();
//                    break;
                case UPDATE_LEFT_SHOES_PRESSURE_COLOR:
//                    if (leftStepDetect != null) {
//                        List<Double> leftPressureArrList = leftStepDetect.getCurrentPressureValueList();
//                        homeFragment.setColors(leftPressureArrList, true);
//                    }
                    if (!currentLeftList.isEmpty()) {
                        homeFragment.setColors(currentLeftList, true);
                    }
                    break;
                case UPDATE_RIGHT_SHOES_PRESSURE_COLOR:
//                    if (rightStepDetect != null) {
//                        List<Double> rightPressureArrList = rightStepDetect.getCurrentPressureValueList();
//                        homeFragment.setColors(rightPressureArrList, false);
//                    }
                    if (!currentRightList.isEmpty()) {
                        homeFragment.setColors(currentRightList, false);
                    }
                    break;
                case CONNECT_LEFT_SHOES_SUCCESS:
                    leftShoesConnectedStatus = true;
                    Toast.makeText(MainActivity.this, "已连接左脚", Toast.LENGTH_SHORT).show();
                    homeFragment.calibrationTextColor(true);
                    disableShoesDaemon(true);
                    if (leftShoesConnectedStatus && rightShoesConnectedStatus) {
//                        mIbSearchConnection.setEnabled(false);
                        handler.sendEmptyMessage(LINK_COMPLETED);
                    }
//                    disableShoesDaemon(true);
                    break;
                case CONNECT_RIGHT_SHOES_SUCCESS:
                    rightShoesConnectedStatus = true;
                    Toast.makeText(MainActivity.this, "已连接右脚", Toast.LENGTH_SHORT).show();
                    homeFragment.calibrationTextColor(false);
                    disableShoesDaemon(false);
                    if (leftShoesConnectedStatus && rightShoesConnectedStatus) {
//                        mIbSearchConnection.setEnabled(false);
                        handler.sendEmptyMessage(LINK_COMPLETED);
                    }
//                    disableShoesDaemon(false);
                    break;
                case DISCONNECT_LEFT_SHOES:
                    leftShoesConnectedStatus = false;
                    homeFragment.mTvStateL.setText("未连接");
                    homeFragment.mBatDataL.setText("");
                    homeFragment.mBatL.setPower(0);
                    homeFragment.showNotConnected(true);
                    leftPressureArr= new double[8];
                    break;
                case DISCONNECT_RIGHT_SHOES:
                    rightShoesConnectedStatus = false;
                    homeFragment.mTvStateR.setText("未连接");
                    homeFragment.mTvBatDataR.setText("");
                    homeFragment.mBatR.setPower(0);
                    homeFragment.showNotConnected(false);
                    rightPressureArr = new double[8];
                    break;
                case RETRY_CONNECT_LEFT_SHOES:
                    leftShoesConnectedStatus = false;
                    homeFragment.mTvStateL.setText("未连接");
                    homeFragment.mBatDataL.setText("");
                    homeFragment.mBatL.setPower(0);
                    homeFragment.showNotConnected(true);
                    if(leftShoesConnectDaemon!=null){
                        leftShoesConnectDaemon.cancel(true);
                    }
                    leftShoesConnectDaemon = connectLeftShoesDaemon(true);
                    leftPressureArr= new double[8];
                    break;
                case RETRY_CONNECT_RIGHT_SHOES:
                    rightShoesConnectedStatus = false;
                    homeFragment.mTvStateR.setText("未连接");
                    homeFragment.mTvBatDataR.setText("");
                    homeFragment.mBatR.setPower(0);
                    homeFragment.showNotConnected(false);
                    if(rightShoesConnectDaemon!=null){
                        rightShoesConnectDaemon.cancel(true);
                    }
                    rightShoesConnectDaemon = connectLeftShoesDaemon(false);
                    rightPressureArr= new double[8];
                    break;

                case FIND_SHOES:
                    parseMacAndRegisterBle(null);
                    break;
                case LINK_COMPLETED:
//                    homeFragment.showLeftFootFlashing(1);
//                    homeFragment.showRightFootFlashing(1);
                    homeFragment.calibrationTextColor(true);
                    homeFragment.calibrationTextColor(false);
                    Toast.makeText(MainActivity.this, "可正常使用", Toast.LENGTH_SHORT).show();
                    initStepData();
                    break;
                case PROGRESS_OVER:
                    DialogUtil.closeProgressDialog();
                    break;
                case REFRESH_SPORT_COUNT:
//                    UserDailySport map= (UserDailySport) msg.obj;
                    int count = 0;
//                    try {
//                        count = todayLeftStepCount + todayRightStepCount + leftStepDetect.detect() + rightStepDetect.detect();


//                    count = todayLeftStepCount + todayRightStepCount + stepCountLeft.OndataReceived() + stepCountRight.OndataReceived();      // 步数统计
                    count = todayLeftStepCount + todayRightStepCount;


//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    String stepCount = String.valueOf(count);
                    String sDistance = String.valueOf(BigDecimal.valueOf(count).multiply(BigDecimal.valueOf(0.6)).divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP));
                    if(homeFragment.mTvStepCount==null||homeFragment.mTvDistance==null){
                        break;
                    }
                    homeFragment.mTvStepCount.setText(stepCount);
                    homeFragment.mTvDistance.setText(sDistance);
                    EventBus.getDefault().postSticky(new MessageEvent(stepCount, sDistance));//我的运动显示
                    break;
            }
        }
    };

    // 初始化记步数据，暂不知道 stepDataInitStatus 的意义
    private void initStepData() {
//        leftStepDetect = new StepDetect(this, false, sensorType, leftUUID);
//        rightStepDetect = new StepDetect(this, true, sensorType, rightUUID);
        stepDataInitStatus = true;
    }

    // 定时器创建
    public ScheduledFuture connectLeftShoesDaemon(boolean isLeftShoes) {
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(isLeftShoes ? leftShoesConnectRunable : rightShoesConnectRunable, 0, 2, TimeUnit.SECONDS);
        return scheduledFuture;
    }

    // 断开鞋子的链接
    public Boolean disableShoesDaemon(boolean isLeftShoes) {
        if (isLeftShoes) {
            if (leftShoesConnectDaemon != null) {
                boolean result = leftShoesConnectDaemon.cancel(true);
                leftShoesConnectDaemon = null;
                return result;
            }
        } else {
            if (rightShoesConnectDaemon != null) {
                boolean result = rightShoesConnectDaemon.cancel(true);
                rightShoesConnectDaemon = null;
                return result;
            }
        }
        return false;
    }

    // 创建线程
    public Runnable leftShoesConnectRunable = new Runnable() {
        @Override
        public void run() {
            if (!leftShoesConnectedStatus) {
                boolean status = mBluetoothLeServiceLeft.connect(leftUUID);
                Log.e("retry connect", " left status is " + status);
            }

        }
    };

    // 创建线程
    public Runnable rightShoesConnectRunable = new Runnable() {
        @Override
        public void run() {
            if (!rightShoesConnectedStatus) {
                boolean status = mBluetoothLeServiceRight.connect(rightUUID);
                Log.e("retry connect", " right status is " + status);
            }

        }
    };

    //链接蓝牙设备
    private void parseMacAndRegisterBle(String bleMacInfo) {
//        startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
//        if (true){
//            return;
//        }
        int shoesLeft = 0;
        int shoesRight = 0;
        String bindDeviceAddress = null;

        // 获取本地蓝牙信息缓存   若有输入   则将输入缓存
        SharedPreferences sharedPreferences = getSharedPreferences("cache", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!Strings.isNullOrEmpty(bleMacInfo)) {
            editor.putString("bind", bleMacInfo);
            editor.apply();
            bindDeviceAddress = bleMacInfo;
        } else {
            bindDeviceAddress = sharedPreferences.getString("bind", "");
        }
        for (BluetoothDevice ble : mBleDevicesSet) {
            if (Strings.isNullOrEmpty(ble.getName())) {
                continue;
            }
            if (ble.getName().startsWith("SSL")) {
                shoesLeft++;
            } else if (ble.getName().startsWith("SSR")) {
                shoesRight++;
            }
        }
        Log.d("parseMacAndRegisterBle", "start to parse addr ");
        if (shoesLeft > 0 && shoesRight > 0) {
            //check binding ble message
            //有就用本地的，没有就扫码
            if (!Strings.isNullOrEmpty(bindDeviceAddress)) {
                String[] s = bindDeviceAddress.trim().split(",");
                String[] ssLStrArr = s[0].split("=");
//                leftName = ssLStrArr[0];
                leftUUID = ssLStrArr[1];
                String[] ssRStrArr = s[1].split("=");
//                rightName = ssRStrArr[0];
                rightUUID = ssRStrArr[1];
                String[] sensorTypeArr = s[2].split("=");
                int type = Integer.parseInt(sensorTypeArr[1]);
                editor.putInt("sensorType", type).apply();
                sensorType = type;
//                if (!deviceNameList.contains(ssLStrArr[0]) && !deviceNameList.contains(ssRStrArr[0])) {
//                    Toast.makeText(MainActivity.this, "未匹配到该智能鞋，请尝试四处走动以唤醒设备", Toast.LENGTH_SHORT).show();
//                    //刷新蓝牙app状态 todo 重开蓝牙操作似乎无效
//                    BleStable.refreshBleAppFromSystem(MainActivity.this, "com.zssupersense.supersenseshoes");
//                }
                Log.d("parseMacAndRegisterBle", "ready to connect  ");
                //todo connect
                leftShoesConnectDaemon = connectLeftShoesDaemon(true);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rightShoesConnectDaemon = connectLeftShoesDaemon(false);
//                registerReceiver();
            } else {
                //Jump to scan page
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 1);
//                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
//                mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZBarView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

//                mZBarView.startSpotAndShowRect(); // 显示扫描框，并开始识别
            }
        } else if (shoesLeft == 0 && shoesRight == 0) {
            Toast.makeText(MainActivity.this, "未找到智能鞋，请尝试四处走动以唤醒设备", Toast.LENGTH_SHORT).show();
        } else {
            if (shoesLeft == 0) {
                Toast.makeText(MainActivity.this, "未找到左鞋，请尝试四处走动以唤醒设备", Toast.LENGTH_SHORT).show();
            } else if (shoesRight == 0) {
                Toast.makeText(MainActivity.this, "未找到右鞋，请尝试四处走动以唤醒设备", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 扫码回调处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        Log.d("onActivityResult"," requestCode is "+requestCode+"  resultCode is "+resultCode);
        if (resultCode != RESULT_OK) {
            Toast.makeText(this,"二维码扫码失败,请重试",Toast.LENGTH_SHORT).show();
            return;
        }
        if (data == null) {
            Toast.makeText(this,"二维码扫码失败,请重试",Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = data.getExtras();
        if (bundle == null) {
            Toast.makeText(this,"二维码扫码失败,请重试",Toast.LENGTH_SHORT).show();
            return;
        }
        String result = bundle.getString("result");

        if (result == null) {
            Toast.makeText(this,"二维码扫码失败,请重试",Toast.LENGTH_SHORT).show();
            Log.e("qr result","result is null");
            return;
        }
        Log.d("qr result ",result);
        parseMacAndRegisterBle(result);
    }



    /**
     双击退出
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtil.closeAlertDialog();
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (isRegisterL) {
            unregisterReceiver(mGattUpdateReceiverLeft);
            unbindService(mServiceConnectionLeft);
            isRegisterL = false;
        }
        if (isRegisterR) {
            unregisterReceiver(mGattUpdateReceiverRight);
            unbindService(mServiceConnectionRight);
            isRegisterR = false;
        }
        if (mBluetoothLeServiceLeft != null) {
            mBluetoothLeServiceLeft.close();
            mBluetoothLeServiceLeft = null;
        }
        if (mBluetoothLeServiceRight != null) {
            mBluetoothLeServiceRight.close();
            mBluetoothLeServiceRight = null;
        }
//        unregisterReceiver(positioningReceiver);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {//main page
            isHome = true;
            isData = false;
        } else if (position == 3) {//my page
            isHome = false;
            isData = false;
            Drawable drawableData = getResources().getDrawable(R.drawable.main_my);
            drawableData.setBounds(0, 0, drawableData.getMinimumWidth(), drawableData.getMinimumHeight());
            mMainMy.setCompoundDrawables(null, null, null, drawableData);
        } else {
            isHome = false;
            isData = false;
        }
        RadioButton radioButton = (RadioButton) mMainRadioGroup.getChildAt(position);
        radioButton.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}