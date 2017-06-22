package cn.com.hfrjl.readheatmeter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hfrjl.readheatmeter.R;
import cn.com.hfrjl.readheatmeter.HfrjlApplication;
import cn.com.hfrjl.readheatmeter.bean.MBUS;
import cn.com.hfrjl.readheatmeter.bean.ParameterProtocol;
import cn.com.hfrjl.readheatmeter.bean.Protocol;
import cn.com.hfrjl.readheatmeter.bean.SSumHeat;
import cn.com.hfrjl.readheatmeter.fragment.ReadMeterDataFragment;
import cn.com.hfrjl.readheatmeter.fragment.ShowMeterLcdFragment;
import cn.com.hfrjl.readheatmeter.utils.Analysise;
import cn.com.hfrjl.readheatmeter.utils.CommonUtils;
import cn.com.hfrjl.readheatmeter.view.ActionBarView;
import cn.com.hfrjl.readheatmeter.view.NoScrollViewPager;

public class MainActivity extends MyBaseActivity {
    @BindViews({R.id.tv_a, R.id.tv_b})
    TextView[] textViews;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;
    private ActionBarView actionBarView;
    private List<String> titles;
    private BluetoothAdapter bluetoothAdapter;
    private Context context;

    private final static int REQUEST_CONNECT_DEVICE = 1;    //宏定义查询设备句柄
    private final static String SSP_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    private InputStream inputStream;    //输入流，用来接收蓝牙数据
    public static String smsg = "";    //显示用数据缓存
    private static String fmsg = "";    //保存用数据缓存
    public int time = 0;
    private BluetoothDevice bluetoothDevice = null;     //蓝牙设备
    private static BluetoothSocket bluetoothSocket = null;      //蓝牙通信socket
    private boolean bRun = true;
    private boolean bThread = false;
    private static boolean flag = false;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //沉浸式通知栏,透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_read_meter_by_bluetooth);
        ButterKnife.bind(this);
        HfrjlApplication.getInstance().addActivity(this);
        titles = new ArrayList<>();
        titles.add("抄表");
        titles.add("显示");
        actionBarView = findViewById(R.id.actionBar_readMeter);
        actionBarView.initActionBar(titles.get(0), -1, -1, onClickListener);
        ImageView search_bluetooth_device = findViewById(R.id.iv_search_bluetooth_device);
        search_bluetooth_device.setOnClickListener(onClickListener);
        viewPager.setNoScroll(false);
        init();
        try {
            //获取本地蓝牙适配器
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            //如果打开本地蓝牙设备不成功，提示信息，结束程序
            if (bluetoothAdapter == null) {
                CommonUtils.showToast(context, "无法打开手机蓝牙，请确认手机是否有蓝牙功能！");
                finish();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置设备可以被搜索
        new Thread() {
            public void run() {
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();
                }
            }
        }.start();
        initFinishReceiver();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_search_bluetooth_device:
                    try {
                        Connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //初始化视图
    private void init() {
        viewPager.setAdapter(viewPagerAdapter);
        //设置Fragment预加载，非常重要,可以保存每个页面fragment已有的信息,防止切换后原页面信息丢失
        viewPager.setOffscreenPageLimit(2);
        //刚进来默认选择第一个
        textViews[0].setSelected(true);
        //viewPager添加滑动监听，用于控制TextView的展示
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //textView全部未选择
                for (TextView textView : textViews) {
                    textView.setSelected(false);
                }
                //更改textViews选择效果
                textViews[position].setSelected(true);
                actionBarView.initActionBar(titles.get(position), R.drawable.back_white, -1, onClickListener);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //点击除了EditText以外的所有地方都可以隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    //根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    //获取InputMethodManager，隐藏软键盘
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //ViewPager适配器
    private FragmentStatePagerAdapter viewPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ReadMeterDataFragment();
                case 1:
                    return new ShowMeterLcdFragment();
            }
            return null;
        }
    };

    //TextView点击事件
    @OnClick({R.id.tv_a, R.id.tv_b})
    public void onClick(TextView view) {
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setSelected(false);
            textViews[i].setTag(i);
        }
        //设置选择效果
        view.setSelected(true);
        //参数false代表瞬间切换，true表示平滑过渡
        viewPager.setCurrentItem((Integer) view.getTag(), false);
    }

    //连接功能函数
    private void Connect() {
        if (!bluetoothAdapter.isEnabled()) {
            //如果蓝牙服务不可用则提示
            CommonUtils.showToast(context, "打开蓝牙中...");
            return;
        }
        //如未连接设备则打开DeviceListActivity进行设备搜索
        if (bluetoothSocket == null) {
            Intent serverIntent = new Intent(this, DeviceListActivity.class); //跳转程序设置
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);  //设置返回宏定义
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.app_notice).setIcon(android.R.drawable.ic_dialog_info).setNegativeButton(R.string.amenderr_setCancle, null);
            builder.setPositiveButton(R.string.amenderr_setOk, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //关闭连接socket
                    try {
                        if (flag) {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                    inputStream = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (bluetoothSocket != null) {
                                try {
                                    bluetoothSocket.close();
                                    bluetoothSocket = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            bRun = false;
                            //Buttonconnect.setText("连接");

                            flag = false;
                        }
                        //连接标志位
//                        tvBTStatus.setText(R.string.app_bluetoothunconnect);
//                         TextViewconnect.setText("蓝牙未连接");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.show();
        }
    }

    //接收活动结果，响应startActivityForResult()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:     //连接结果，由DeviceListActivity设置返回
                // 响应返回结果
                if (resultCode == Activity.RESULT_OK) {   //连接成功，由DeviceListActivity设置返回
                    // MAC地址，由DeviceListActivity设置返回
                    String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 得到蓝牙设备句柄
                    bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);

                    // 用服务号得到socket
                    try {
                        bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString(SSP_UUID));
                    } catch (IOException e) {
                        CommonUtils.showToast(context, "连接失败！");
                    }
                    //连接socket
                    //Button Buttonconnect = (Button) findViewById(R.id.Buttonconnect);
                    try {
                        bluetoothSocket.connect();
                        CommonUtils.showToast(context, "连接" + bluetoothDevice.getName() + "成功！");
                        // Buttonconnect.setText("断开");
                        flag = true;                            //连接标志位
                        //TextViewconnect.setText("已连接到："+bluetoothDevice.getName());
//                        this.tvBTStatus.setText("已连接到：" + bluetoothDevice.getName());
                    } catch (IOException e) {
                        try {
                            CommonUtils.showToast(context, "连接失败！");
                            bluetoothSocket.close();
                            bluetoothSocket = null;
                        } catch (IOException ee) {
                            CommonUtils.showToast(context, "连接失败！");
                        }
                        return;
                    }
                    //打开接收线程
                    try {
                        inputStream = bluetoothSocket.getInputStream();   //得到蓝牙数据输入流
                    } catch (IOException e) {
                        CommonUtils.showToast(context, "接收数据失败！");
                        return;
                    }
                    if (!bThread) {
                        ReadThread.start();
                        bThread = true;
                    } else {
                        bRun = true;
                    }
                }
                break;
            default:
                break;
        }
    }

    //接收数据线程
    Thread ReadThread = new Thread() {
        public void run() {
            int num;
            byte[] buffer = new byte[1024];
            int i;
            bRun = true;
            //接收线程
            while (true) {
                try {
                    while (true) {
                        num = inputStream.read(buffer);         //读入数据
                        String s0 = "";
                        for (i = 0; i < num; i++) {
                            int b = (int) buffer[i];
                            if (b < 0) b = 256 + b;
                            s0 = s0 + Integer.toHexString(b / 16) + Integer.toHexString(b % 16);
                        }
                        fmsg += s0;    //保存收到数据
                        smsg += s0;   //写入接收缓存
                        if (inputStream.available() == 0) break;  //短时间没有数据才跳出进行显示
                    }
                } catch (IOException e) {
                    handler.sendMessage(handler.obtainMessage(1));
                    break;
                }
                //发送显示消息，进行显示刷新
                handler.sendMessage(handler.obtainMessage(2));
            }
        }
    };


    public Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    //消息处理队列
    Handler handler = new Handler() {
        @SuppressLint("DefaultLocale")
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    CommonUtils.showToast(context, "蓝牙连接断开！");
                    break;
                case 2:
                    currentFragment = getCurrentFragment();
                    Protocol protocol = new Protocol();
                    MBUS mbus = new MBUS();
                    SSumHeat ssumheat = new SSumHeat();
                    ParameterProtocol parameterprotocol = new ParameterProtocol();
                    String data = "";
                    if (currentFragment instanceof ReadMeterDataFragment) {
                        ReadMeterDataFragment readMeterDataFragment = (ReadMeterDataFragment) currentFragment;
                        data = smsg.toUpperCase().replace(" ", "");
                        switch (Analysise.analysiseData(data, protocol, ssumheat, mbus, parameterprotocol)) {
                            case 1: {
                                Analysise.dataunittostandard(protocol);
                                ((TextView) readMeterDataFragment.getView().findViewById(R.id.tv_meterId)).setText("");
                                ((Button) readMeterDataFragment.getView().findViewById(R.id.Buttonreadparameter)).setText("获取表号");
                                readMeterDataFragment.addListView(protocol);
                                smsg = "";
                                break;
                            }
                            case 11: {
                                ((TextView) readMeterDataFragment.getView().findViewById(R.id.tv_meterId)).setText(parameterprotocol.getMeterid());
                                ((Button) readMeterDataFragment.getView().findViewById(R.id.Buttonreadparameter)).setText("读表数据");
                                smsg = "";
                                break;
                            }
                        }
                    } else if (currentFragment instanceof ShowMeterLcdFragment) {
                        smsg = "";
                    }
                    break;
            }
        }
    };


    //发送数据功能函数
    static int HexS1ToInt(char ch) {
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        throw new IllegalArgumentException(String.valueOf(ch));
    }

    static int HexS2ToInt(String S) {
        char a[] = S.toCharArray();
        return HexS1ToInt(a[0]) * 16 + HexS1ToInt(a[1]);
    }

    public static void writeData(final String tx) {
        if (flag) {
            int i;
            try {
                OutputStream os = bluetoothSocket.getOutputStream();   //蓝牙连接输出流
                byte[] bos = new byte[tx.length() / 2];
                for (i = 0; i < (tx.length() / 2); i++) { //手机中换行为0a,将其改为0d 0a后再发送
                    bos[i] = (byte) HexS2ToInt(tx.substring(2 * i, 2 * i + 2));
                }
                os.write(bos);
                //发送显示消息，进行显示刷新
                //handler.sendMessage(handler.obtainMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //点击两次返回退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            CommonUtils.showToast(getApplicationContext(), getString(R.string.click_again_exit));
            exitTime = System.currentTimeMillis();
        } else {
            if (bluetoothAdapter != null) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("友情提醒").setMessage("关闭蓝牙可以节约用电，是否关闭？").setIcon(android.R.drawable.ic_dialog_info);
                    builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭连接socket
                            try {
                                if (bluetoothSocket != null) {
                                    //关闭连接socket
                                    try {
                                        bluetoothSocket.close();
                                        bluetoothSocket = null;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (bluetoothDevice != null) {
                                    bluetoothDevice = null;
                                }
                                if (bluetoothAdapter != null) {
                                    bluetoothAdapter.disable();  //关闭蓝牙服务
                                }
                                HfrjlApplication.getInstance().exit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setNegativeButton("不关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HfrjlApplication.getInstance().exit();
                        }
                    });
                    builder.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                HfrjlApplication.getInstance().exit();
            }
        }
    }
}
