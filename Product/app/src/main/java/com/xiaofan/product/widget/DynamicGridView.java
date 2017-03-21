package com.xiaofan.product.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ScrollView;

/**
 * @author: 范建海
 * @createTime: 2016/9/8 11:08
 * @className:  DynamicGridView
 * @Description: 自定义GridView GridView高度不确定时，需要动态计算高度
 */
public class DynamicGridView extends GridView {

    public DynamicGridView(Context context) {
        super(context);
    }

    public DynamicGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    /**
     * 计算GridView的高度
     * @param columnCount GridView的列数
     * @param verticalSpacing 为了需要兼容到API16以下的版本，如果有垂直间距，需要传递一个垂直间距
     */
    public void fixGridViewHeight(int columnCount,int verticalSpacing){
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = this.getAdapter();

        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listViewItem = listAdapter.getView(i , null, this);
            // 计算子项View 的宽高
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(this.getWidth(), View.MeasureSpec.AT_MOST);
            listViewItem.measure(desiredWidth, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }
        totalHeight = totalHeight/columnCount;
        ViewGroup.LayoutParams params = this.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            // 版本兼容16
            params.height = totalHeight+ (verticalSpacing * (listAdapter.getCount()/columnCount - 1));
        }else {
            params.height = totalHeight+ (this.getVerticalSpacing() * (listAdapter.getCount() / columnCount - 1));
        }
        this.setLayoutParams(params);
    }

    /**
     * ScrollView和GridView嵌套，允许里面的GridView滑动
     * @param sv
     */
    public void setOnSelfTouchListener(final ScrollView sv) {

        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        sv.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        sv.requestDisallowInterceptTouchEvent(false);
                    case MotionEvent.ACTION_CANCEL:
                        sv .requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;

            }
        });
    }

}
