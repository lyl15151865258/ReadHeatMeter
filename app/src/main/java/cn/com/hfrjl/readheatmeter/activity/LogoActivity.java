package cn.com.hfrjl.readheatmeter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import cn.com.hfrjl.readheatmeter.HfrjlApplication;
import cn.com.hfrjl.readheatmeter.R;

public class LogoActivity extends Activity {
    private TextView tv_time;
    private Boolean threadIsRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HfrjlApplication.getInstance().addActivity(this);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_logo);
        tv_time = findViewById(R.id.tv_time);
        RelativeLayout rl_skip = findViewById(R.id.rl_skip);
        rl_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });
        showCountdown();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (threadIsRun) {
                    startActivity();
                }
            }
        }, 4000);
    }

    //跳转到新的Activity
    private void startActivity() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_left_out);
        //避免出现打开两次activity
        threadIsRun = false;
        finish();
    }

    //显示5秒倒计时
    private void showCountdown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 5; i > 0; i--) {
                    Message msg = myHandler.obtainMessage();
                    msg.arg1 = 1;
                    msg.obj = i;
                    myHandler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //自定义的Handler，Handler类应该定义成静态类，否则可能导致内存泄露。
    private static class MyHandler extends Handler {
        WeakReference<LogoActivity> mActivity;

        MyHandler(LogoActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LogoActivity theActivity = mActivity.get();
            theActivity.tv_time.setText(String.format(theActivity.getString(R.string.skip_seconds), msg.obj));
        }
    }

    MyHandler myHandler = new MyHandler(this);

    //Logo页面不允许退出
    @Override
    public void onBackPressed() {

    }
}
