package cn.com.hfrjl.readheatmeter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.hfrjl.readheatmeter.R;

/**
 * Created by Li Yuliang on 2017/2/13 0013.
 * 扫描二维码页面
 * 最上方导航条
 */

public class ActionBarView extends LinearLayout {
    private ImageView iv_actionbar_left;// 左边按钮
    private ImageView iv_actionbar_right;// 右边按钮
    private TextView tv_actionbar_title;// 中间文本

    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.activity_actionbar, this);
        tv_actionbar_title = findViewById(R.id.tv_title);
        iv_actionbar_left = findViewById(R.id.iv_left);
        iv_actionbar_right = findViewById(R.id.iv_right);
    }

    /**
     * 初始化ActionBar
     */
    public void initActionBar(String title, int leftResID, int rightResID, OnClickListener listener) {
        tv_actionbar_title.setText(title);
        if (leftResID == -1) {
            iv_actionbar_left.setVisibility(View.GONE);
        } else {
            iv_actionbar_left.setImageResource(leftResID);
            iv_actionbar_left.setOnClickListener(listener);
        }
        if (rightResID == -1) {
            iv_actionbar_right.setVisibility(View.GONE);
        } else {
            iv_actionbar_right.setImageResource(rightResID);
            iv_actionbar_right.setOnClickListener(listener);
        }
    }
}
