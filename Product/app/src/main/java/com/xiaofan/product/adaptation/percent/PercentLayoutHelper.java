package com.xiaofan.product.adaptation.percent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaofan.product.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//http://blog.csdn.net/lmj623565791/article/details/46767825
//xmlns:app="http://schemas.android.com/apk/res-auto"
//app:layout_textSizePercent="3%"
//app:layout_widthPercent="70%"
//app:layout_heightPercent="10%"
//app:layout_marginBottomPercent="5%"

public class PercentLayoutHelper {

	private final ViewGroup mHost;

	public PercentLayoutHelper(ViewGroup host) {
		mHost = host;
	}

	public static void fetchWidthAndHeight(ViewGroup.LayoutParams params, TypedArray array, int widthAttr, int heightAttr) {
		params.width = array.getLayoutDimension(widthAttr, 0);
		params.height = array.getLayoutDimension(heightAttr, 0);
	}

	public void adjustChildren(int widthMeasureSpec, int heightMeasureSpec) {
		int widthHint = View.MeasureSpec.getSize(widthMeasureSpec);
		int heightHint = View.MeasureSpec.getSize(heightMeasureSpec);

		for (int i = 0, N = mHost.getChildCount(); i < N; i++) {
			View view = mHost.getChildAt(i);
			ViewGroup.LayoutParams params = view.getLayoutParams();
			if (params instanceof PercentLayoutParams) {
				PercentLayoutInfo info = ((PercentLayoutParams) params).getPercentLayoutInfo();
				if (info != null) {
					supportTextSize(widthHint, heightHint, view, info);
					supportMinOrMaxDimesion(widthHint, heightHint, view, info);

					if (params instanceof ViewGroup.MarginLayoutParams) {
						info.fillMarginLayoutParams((ViewGroup.MarginLayoutParams) params, widthHint, heightHint);
					} else {
						info.fillLayoutParams(params, widthHint, heightHint);
					}
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void supportMinOrMaxDimesion(int widthHint, int heightHint, View view, PercentLayoutInfo info) {
		try {
			Class clazz = view.getClass();
			invokeMethod("setMaxWidth", widthHint, heightHint, view, clazz, info.maxWidthPercent);
			invokeMethod("setMaxHeight", widthHint, heightHint, view, clazz, info.maxHeightPercent);
			invokeMethod("setMinWidth", widthHint, heightHint, view, clazz, info.minWidthPercent);
			invokeMethod("setMinHeight", widthHint, heightHint, view, clazz, info.minHeightPercent);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void invokeMethod(String methodName, int widthHint, int heightHint, View view, Class clazz, PercentLayoutInfo.PercentVal percentVal) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (percentVal != null) {
			Method setMaxWidthMethod = clazz.getMethod(methodName, int.class);
			setMaxWidthMethod.setAccessible(true);
			int base = percentVal.isBaseWidth ? widthHint : heightHint;
			setMaxWidthMethod.invoke(view, (int) (base * percentVal.percent));
		}
	}

	private void supportTextSize(int widthHint, int heightHint, View view, PercentLayoutInfo info) {
		if (view instanceof TextView) {
			PercentLayoutInfo.PercentVal textSizePercent = info.textSizePercent;
			if (textSizePercent != null) {
				int base = textSizePercent.isBaseWidth ? widthHint : heightHint;
				float textSize = (int) (base * textSizePercent.percent);
				((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
			}
		}
	}

	public static PercentLayoutInfo getPercentLayoutInfo(Context context, AttributeSet attrs) {
		PercentLayoutInfo info = null;
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PercentLayout_Layout);
		
		int index = R.styleable.PercentLayout_Layout_layout_widthPercent;
		String sizeStr = array.getString(index);
		PercentLayoutInfo.PercentVal percentVal = getPercentVal(sizeStr, true);
		if (percentVal != null) {
			info = checkForInfoExists(info);
			info.widthPercent = percentVal;
		}
		sizeStr = array.getString(R.styleable.PercentLayout_Layout_layout_heightPercent);
		percentVal = getPercentVal(sizeStr, false);

		if (sizeStr != null) {
			info = checkForInfoExists(info);
			info.heightPercent = percentVal;
		}

		sizeStr = array.getString(R.styleable.PercentLayout_Layout_layout_marginPercent);

		percentVal = getPercentVal(sizeStr, false);

		if (percentVal != null) {
			info = checkForInfoExists(info);
			info.leftMarginPercent = getPercentVal(sizeStr, true);
			info.topMarginPercent = getPercentVal(sizeStr, false);
			info.rightMarginPercent = getPercentVal(sizeStr, true);
			info.bottomMarginPercent = getPercentVal(sizeStr, false);
		}

		sizeStr = array.getString(R.styleable.PercentLayout_Layout_layout_marginLeftPercent);
		percentVal = getPercentVal(sizeStr, true);
		if (percentVal != null) {
			info = checkForInfoExists(info);
			info.leftMarginPercent = percentVal;
		}

		sizeStr = array.getString(R.styleable.PercentLayout_Layout_layout_marginTopPercent);
		percentVal = getPercentVal(sizeStr, false);
		if (percentVal != null) {
			info = checkForInfoExists(info);
			info.topMarginPercent = percentVal;
		}

		sizeStr = array.getString(R.styleable.PercentLayout_Layout_layout_marginRightPercent);
		percentVal = getPercentVal(sizeStr, true);
		if (percentVal != null) {
			info = checkForInfoExists(info);
			info.rightMarginPercent = percentVal;
		}

		sizeStr = array.getString(R.styleable.PercentLayout_Layout_layout_marginBottomPercent);
		percentVal = getPercentVal(sizeStr, false);
		if (percentVal != null) {
			info = checkForInfoExists(info);
			info.bottomMarginPercent = percentVal;
		}

		sizeStr = array.getString(R.styleable.PercentLayout_Layout_layout_marginStartPercent);
		percentVal = getPercentVal(sizeStr, true);
		if (percentVal != null) {
			info = checkForInfoExists(info);
			info.startMarginPercent = percentVal;
		}

		sizeStr = array.getString(R.styleable.PercentLayout_Layout_layout_marginEndPercent);
		percentVal = getPercentVal(sizeStr, true);
		if (percentVal != null) {
			info = checkForInfoExists(info);
			info.endMarginPercent = percentVal;
		}

		sizeStr = array.getString(R.styleable.PercentLayout_Layout_layout_textSizePercent);
		percentVal = getPercentVal(sizeStr, false);
		if (percentVal != null) {
			info = checkForInfoExists(info);
			info.textSizePercent = percentVal;
		}

		percentVal = getPercentVal(array, R.styleable.PercentLayout_Layout_layout_maxWidthPercent, true);
		if (percentVal != null) {
			checkForInfoExists(info);
			info.maxWidthPercent = percentVal;
		}

		percentVal = getPercentVal(array, R.styleable.PercentLayout_Layout_layout_maxHeightPercent, false);
		if (percentVal != null) {
			checkForInfoExists(info);
			info.maxHeightPercent = percentVal;
		} 

		percentVal = getPercentVal(array, R.styleable.PercentLayout_Layout_layout_minWidthPercent, true);
		if (percentVal != null) {
			checkForInfoExists(info);
			info.minWidthPercent = percentVal;
		}

		percentVal = getPercentVal(array, R.styleable.PercentLayout_Layout_layout_minHeightPercent, false);
		if (percentVal != null) {
			checkForInfoExists(info);
			info.minHeightPercent = percentVal;
		}

		array.recycle();
		return info;
	}

	private static PercentLayoutInfo.PercentVal getPercentVal(TypedArray array, int index, boolean baseWidth) {
		String sizeStr = array.getString(index);
		PercentLayoutInfo.PercentVal percentVal = getPercentVal(sizeStr, baseWidth);
		return percentVal;
	}

	private static PercentLayoutInfo checkForInfoExists(PercentLayoutInfo info) {
		info = info != null ? info : new PercentLayoutInfo();
		return info;
	}

	private static final String REGEX_PERCENT = "^(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)%([wh]?)$";

	private static PercentLayoutInfo.PercentVal getPercentVal(String percentStr, boolean isOnWidth) {
		if (percentStr == null) {
			return null;
		}
		Pattern p = Pattern.compile(REGEX_PERCENT);
		Matcher matcher = p.matcher(percentStr);
		if (!matcher.matches()) {
			throw new RuntimeException("the value of layout_xxxPercent invalid! ==>" + percentStr);
		}
		int len = percentStr.length();

		String floatVal = matcher.group(1);
		String lastAlpha = percentStr.substring(len - 1);

		float percent = Float.parseFloat(floatVal) / 100f;
		boolean isBasedWidth = (isOnWidth && !lastAlpha.equals("h")) || lastAlpha.equals("w");

		return new PercentLayoutInfo.PercentVal(percent, isBasedWidth);
	}

	public void restoreOriginalParams() {
		for (int i = 0, N = mHost.getChildCount(); i < N; i++) {
			View view = mHost.getChildAt(i);
			ViewGroup.LayoutParams params = view.getLayoutParams();
			if (params instanceof PercentLayoutParams) {
				PercentLayoutInfo info = ((PercentLayoutParams) params).getPercentLayoutInfo();
				if (info != null) {
					if (params instanceof ViewGroup.MarginLayoutParams) {
						info.restoreMarginLayoutParams((ViewGroup.MarginLayoutParams) params);
					} else {
						info.restoreLayoutParams(params);
					}
				}
			}
		}
	}

	public boolean handleMeasuredStateTooSmall() {
		boolean needsSecondMeasure = false;
		for (int i = 0, N = mHost.getChildCount(); i < N; i++) {
			View view = mHost.getChildAt(i);
			ViewGroup.LayoutParams params = view.getLayoutParams();
			if (params instanceof PercentLayoutParams) {
				PercentLayoutInfo info = ((PercentLayoutParams) params).getPercentLayoutInfo();
				if (info != null) {
					if (shouldHandleMeasuredWidthTooSmall(view, info)) {
						needsSecondMeasure = true;
						params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
					}
					if (shouldHandleMeasuredHeightTooSmall(view, info)) {
						needsSecondMeasure = true;
						params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
					}
				}
			}
		}
		return needsSecondMeasure;
	}

	private static boolean shouldHandleMeasuredWidthTooSmall(View view, PercentLayoutInfo info) {
		int state = ViewCompat.getMeasuredWidthAndState(view) & ViewCompat.MEASURED_STATE_MASK;

		if (null == info.widthPercent) {
			return false;
		}

		return state == ViewCompat.MEASURED_STATE_TOO_SMALL && info.widthPercent.percent >= 0 && info.mPreservedParams.width == ViewGroup.LayoutParams.WRAP_CONTENT;
	}

	private static boolean shouldHandleMeasuredHeightTooSmall(View view, PercentLayoutInfo info) {
		int state = ViewCompat.getMeasuredHeightAndState(view) & ViewCompat.MEASURED_STATE_MASK;
		if (null == info.heightPercent) {
			return false;
		}
		return state == ViewCompat.MEASURED_STATE_TOO_SMALL && info.heightPercent.percent >= 0 && info.mPreservedParams.height == ViewGroup.LayoutParams.WRAP_CONTENT;
	}

	public static class PercentLayoutInfo {
		public static class PercentVal {
			public float percent = -1;
			public boolean isBaseWidth;
			public PercentVal() {
			}

			public PercentVal(float percent, boolean isBaseWidth) {
				this.percent = percent;
				this.isBaseWidth = isBaseWidth;
			}
		}

		public PercentVal widthPercent;
		public PercentVal heightPercent;
		public PercentVal leftMarginPercent;
		public PercentVal topMarginPercent;
		public PercentVal rightMarginPercent;
		public PercentVal bottomMarginPercent;
		public PercentVal startMarginPercent;
		public PercentVal endMarginPercent;
		public PercentVal textSizePercent;
		public PercentVal maxWidthPercent;
		public PercentVal maxHeightPercent;
		public PercentVal minWidthPercent;
		public PercentVal minHeightPercent;

		final ViewGroup.MarginLayoutParams mPreservedParams;

		public PercentLayoutInfo() {
			mPreservedParams = new ViewGroup.MarginLayoutParams(0, 0);
		}

		public void fillLayoutParams(ViewGroup.LayoutParams params, int widthHint, int heightHint) {
			mPreservedParams.width = params.width;
			mPreservedParams.height = params.height;

			if (widthPercent != null) {
				int base = widthPercent.isBaseWidth ? widthHint : heightHint;
				params.width = (int) (base * widthPercent.percent);
			}
			if (heightPercent != null) {
				int base = heightPercent.isBaseWidth ? widthHint : heightHint;
				params.height = (int) (base * heightPercent.percent);
			}

		}

		public void fillMarginLayoutParams(ViewGroup.MarginLayoutParams params, int widthHint, int heightHint) {
			fillLayoutParams(params, widthHint, heightHint);

			mPreservedParams.leftMargin = params.leftMargin;
			mPreservedParams.topMargin = params.topMargin;
			mPreservedParams.rightMargin = params.rightMargin;
			mPreservedParams.bottomMargin = params.bottomMargin;
			MarginLayoutParamsCompat.setMarginStart(mPreservedParams, MarginLayoutParamsCompat.getMarginStart(params));
			MarginLayoutParamsCompat.setMarginEnd(mPreservedParams, MarginLayoutParamsCompat.getMarginEnd(params));

			if (leftMarginPercent != null) {
				int base = leftMarginPercent.isBaseWidth ? widthHint : heightHint;
				params.leftMargin = (int) (base * leftMarginPercent.percent);
			}
			if (topMarginPercent != null) {
				int base = topMarginPercent.isBaseWidth ? widthHint : heightHint;
				params.topMargin = (int) (base * topMarginPercent.percent);
			}
			if (rightMarginPercent != null) {
				int base = rightMarginPercent.isBaseWidth ? widthHint : heightHint;
				params.rightMargin = (int) (base * rightMarginPercent.percent);
			}
			if (bottomMarginPercent != null) {
				int base = bottomMarginPercent.isBaseWidth ? widthHint : heightHint;
				params.bottomMargin = (int) (base * bottomMarginPercent.percent);
			}
			if (startMarginPercent != null) {
				int base = startMarginPercent.isBaseWidth ? widthHint : heightHint;
				MarginLayoutParamsCompat.setMarginStart(params, (int) (base * startMarginPercent.percent));
			}
			if (endMarginPercent != null) {
				int base = endMarginPercent.isBaseWidth ? widthHint : heightHint;
				MarginLayoutParamsCompat.setMarginEnd(params, (int) (base * endMarginPercent.percent));
			}
		}

		@SuppressLint("DefaultLocale")
		@Override
		public String toString() {
			return String.format("PercentLayoutInformation width: %f height %f, margins (%f, %f, " + " %f, %f, %f, %f)", widthPercent, heightPercent, leftMarginPercent, topMarginPercent, rightMarginPercent, bottomMarginPercent, startMarginPercent, endMarginPercent);
		}

		public void restoreMarginLayoutParams(ViewGroup.MarginLayoutParams params) {
			restoreLayoutParams(params);
			params.leftMargin = mPreservedParams.leftMargin;
			params.topMargin = mPreservedParams.topMargin;
			params.rightMargin = mPreservedParams.rightMargin;
			params.bottomMargin = mPreservedParams.bottomMargin;
			MarginLayoutParamsCompat.setMarginStart(params, MarginLayoutParamsCompat.getMarginStart(mPreservedParams));
			MarginLayoutParamsCompat.setMarginEnd(params, MarginLayoutParamsCompat.getMarginEnd(mPreservedParams));
		}

		public void restoreLayoutParams(ViewGroup.LayoutParams params) {
			params.width = mPreservedParams.width;
			params.height = mPreservedParams.height;
		}
	}

	public interface PercentLayoutParams {
		PercentLayoutInfo getPercentLayoutInfo();
	}
}
