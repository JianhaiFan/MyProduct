package com.xiaofan.product.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.xiaofan.product.R;
import com.xiaofan.product.util.LogUtil;


public class MainActivity extends BaseActivity {


    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        popupWindow();

    }

    private void popupWindow() {
        View view = getLayoutInflater().inflate(R.layout.pop_right_of_titlebar, (ViewGroup) null);
        // 创建PopupWindow实例, 分别是宽度和高度
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //这两句是为了解决点击系统后退按钮PopupWindow不关闭的问题
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 这里是位置显示方式,在屏幕的位置
        int[] location = new int[2];
//        v.getLocationOnScreen(location);// v是title的右部，撑满整个title的高度，是一样高的
        mPopupWindow.showAtLocation(findViewById(R.id.title), Gravity.NO_GRAVITY, location[0], location[1] + findViewById(R.id.title).getHeight() + 5);
        // 点击其他地方消失
        view.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                }
                return false;
            }
        });
        // 创建关系
        view.findViewById(R.id.ll_add_friends).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.e(".........one");
                        mPopupWindow.dismiss();
                    }
                });
        // 分享病例
        view.findViewById(R.id.tv_share_case).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.e(".........two");
                        mPopupWindow.dismiss();
                    }
                });
        // 分享话题
        view.findViewById(R.id.tv_share_topic).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.e(".........three");
                        mPopupWindow.dismiss();
                    }
                });
    }




}
