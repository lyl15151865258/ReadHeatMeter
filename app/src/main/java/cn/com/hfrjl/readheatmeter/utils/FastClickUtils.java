package cn.com.hfrjl.readheatmeter.utils;

/**
 * Created by LiYuliang on 2017/2/26 0026.
 * 阻止用户连续点击
 */

public class FastClickUtils {
    //上次点击时间
    private static long lastClickTime;
    //限制的点击间隔时间
    private static long limitClickTime = 10000;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < limitClickTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
