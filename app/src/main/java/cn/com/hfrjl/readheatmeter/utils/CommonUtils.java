package cn.com.hfrjl.readheatmeter.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.hfrjl.readheatmeter.R;


public class CommonUtils {

    public static String getSystime() {
        String systime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEEE");
        systime = dateFormat.format(new Date(System.currentTimeMillis()));
        return systime;
    }

    public static String getFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        StringBuffer sb = new StringBuffer();
        if (fileSize < 1024) {
            sb.append(fileSize);
            sb.append(" B");
        } else if (fileSize < 1048576) {
            sb.append(df.format((double) fileSize / 1024));
            sb.append(" K");
        } else if (fileSize < 1073741824) {
            sb.append(df.format((double) fileSize / 1048576));
            sb.append(" M");
        } else {
            sb.append(df.format((double) fileSize / 1073741824));
            sb.append(" G");
        }
        return sb.toString();
    }

    /**
     * 获取当前日期
     *
     * @return 20140716
     */
    public static String getDate() {
        Date date = new Date(System.currentTimeMillis());
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 验证邮箱格式
     *
     * @param email email
     * @return
     */
    public static boolean verifyEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)"
                + "|(([a-zA-Z0-9\\-]+\\.)+))" + "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 验IP地址格式
     * 由于正则表达式判断不准确，这里采用String操作
     *
     * @param ip ip
     * @return
     */
    public static boolean verifyIp(String ip) {
        //注意转义字符的使用
        String[] string = ip.split("\\.");
        if (string.length == 4) {
            for (int i = 0; i < 4; i++) {
                //每一段数值应在0~255之间，而且长度不大于3
                if (!(Integer.parseInt(string[i]) >= 0 && Integer.parseInt(string[i]) <= 255 && string[i].length() < 4)) {
                    return false;
                }
                //循环判断，当判断到最后一个时仍正确，则返回true
                if (i == 3) {
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * 验证密码格式
     *
     * @param password
     * @return
     */
    public static boolean verifyPassword(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 获取当前的版本号
     *
     * @param context 上下文对象
     * @return 当前版本
     */
    public static int getVersionCode(Context context)// 获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    //自定义的Toast
    public static void showToast(Context context, String msg) {
        Toast toast = new Toast(context);
        //设置Toast显示位置，居中，向 X、Y轴偏移量均为0
//        toast.setGravity(Gravity.CENTER, 0, 0);
        //获取自定义视图
        View view = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_toast_text);
        //设置文本
        tvMessage.setText(msg);
        //设置视图
        toast.setView(view);
        //设置显示时长
        toast.setDuration(Toast.LENGTH_SHORT);
        //显示
        toast.show();
    }

}
