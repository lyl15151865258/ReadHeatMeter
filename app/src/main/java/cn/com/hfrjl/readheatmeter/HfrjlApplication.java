package cn.com.hfrjl.readheatmeter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Li Yuliang on 2017/03/01.
 * 初始化
 */

public class HfrjlApplication extends Application {
    private static HfrjlApplication instance;
    private static Context context;
    private List<Activity> activityList = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    //设置字体不随系统字体大小改变
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    //单例模式中获取唯一的MyApplication实例
    public static HfrjlApplication getInstance() {
        if (instance == null) {
            instance = new HfrjlApplication();
        }
        return instance;
    }

    //单例模式中获取唯一的MyApplication实例
    public static Context getContext() {
        return context;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
