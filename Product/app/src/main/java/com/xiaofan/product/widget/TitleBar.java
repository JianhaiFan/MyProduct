package com.xiaofan.product.widget;

import android.view.View;
import android.view.ViewGroup;

import com.xiaofan.product.activity.AbstractBaseActivity;

/**
 * @author: 范建海
 * @createTime: 2016/12/28 15:58
 * @className:  TitleBar
 * @description: 菜单工具类
 * @changed by:
 */
public class TitleBar {
    // 上下文
    private AbstractBaseActivity bActivity;
    // TitleBar根布局
    private View root;

    public TitleBar(AbstractBaseActivity bAct) {
        if (bAct != null) {
            bActivity = bAct;
            root = ((ViewGroup) bActivity.findViewById(android.R.id.content)).getChildAt(0);


        }
    }


//    public <T extends View> T getView(int...viewId) {
//
//           View view = mConvertView.findViewById(viewId);
//        return (T) view;
//    }

}
