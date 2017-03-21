package com.xiaofan.product.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.xiaofan.product.R;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.ResourceUtil;

/**
 * @author: 范建海
 * @createTime: 2016/12/5 19:56
 * @className:  LoadingDialog
 * @description: 加载数据的等待框
 * @changed by:
 */
public class LoadingDialog extends AlertDialog {

	private TextView tips_loading_msg;
	private int layoutResId;
	private String message;

	/**
	 * 构造方法
	 * @param ctx 上下文
	 * @param layoutResId 等待框的布局
	 * @param strResId 显示字符串的ID
     */
	public LoadingDialog(Context ctx, int layoutResId,int strResId) {
		super(ctx);
		this.layoutResId = layoutResId;
		try {
			message = ResourceUtil.getString(ctx,strResId);
		} catch (Exception e) {
			LogUtil.e("LoadingDialog: " + e.toString());
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(layoutResId);
		tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
		if (!TextUtils.isEmpty(message)) {
			tips_loading_msg.setText(message);
		}

		setCancelable(true);
		setCanceledOnTouchOutside(false);
	}

}
