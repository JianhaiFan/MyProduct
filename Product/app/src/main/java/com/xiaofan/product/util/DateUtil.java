package com.xiaofan.product.util;

import android.content.Context;
import android.text.TextUtils;

import com.xiaofan.product.R;
import com.xiaofan.product.domain.DateUtilBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: 范建海
 * @createTime: 2016/9/8 11:44
 * @className:  DateUtil
 * @Description: 日期相关的工具类
 */
public class DateUtil {
    /** 12(月) **/
    public static final String FORMAT_MM = "MM";
    /** 19(日) **/
    public static final String FORMAT_DD = "dd";
    /** 2015(年) **/
    public static final String FORMAT_YYYY = "yyyy";
    /** 20120219150334 **/
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /** 20120219150334 **/
    public static final String FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    /** 20120219 **/
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    /** 12/12 **/
    public static final String FORMAT_MM_SLASH_DD = "MM/dd";
    /** 2012/02/19 **/
    public static final String FORMAT_YYYY_SLASH_MM_SLASH_DD = "yyyy/MM/dd";
    /** 2012/02/19 05:11 **/
    public static final String FORMAT_YYYY_SLASH_MM_SLASH_DD_HH_MM = "yyyy/MM/dd HH:mm";
    /** 02-19 05:11 **/
    public static final String FORMAT_MM_LINE_DD_HH_MM = "MM-dd HH:mm";
    /** 15:03:34 */
    public static final String FORMAT_HH_MM_SS = "HH:mm:ss";
    /** 15:03 */
    public static final String FORMAT_HH_MM = "HH:mm";
    /** 2012-02-19 15:03:34 */
    public static final String FORMAT_YYYY_LINE_MM_LINE_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /** 2012-02-19 15:03:34.876 */
    public static final String FORMAT_YYYY_LINE_MM_LINE_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    /** 12-12 */
    public static final String FORMAT_MM_LINE_DD = "MM-dd";
    /** 2012-02 */
    public static final String FORMAT_YYYY_LINE_MM = "yyyy-MM";
    /** 2012-02-19 */
    public static final String FORMAT_YYYY_LINE_MM_LINE_DD = "yyyy-MM-dd";
    /** 12月12日 */
    public static final String FORMAT_MM_DD = "MM月dd日";
    /** 2012年02月 */
    public static final String FORMAT_YYYY_MM = "yyyy年MM月";
    /** 2012年02月19日 */
    public static final String FORMAT_YYYY_MM_DD = "yyyy年MM月dd日";
    /** 2012-02-19 12时19分 */
    public static final String FORMAT_YYYY_LINE_MM_LINE_DD_HH_MM = "yyyy-MM-dd HH时mm分";
    /** yyyy年MM月dd日HH时mm分ss秒 */
    public static final String FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 仿微信朋友圈时间列表排序
     *      前提：1.后台返回的数据是排好序的
     *           2.含有时间的实体Bean必须继承自DateUtilBean
     *      核心：多态
     * @param mDUBDatas 待排序的数据集合(实体中含有createdStamp时间字段)
     * @param ctx 上下文
     * @param format 时间格式
     *
     * @return (测试通过)
     */
    public static <T> void replaceRepeatTime(List<? extends DateUtilBean> mDUBDatas, Context ctx,String format) {
        if(mDUBDatas.size() > 0) {
            String tempString1 = "";
            String tempString2 = "";
            String tempString3 = "";
            int yesterdayFlag = 0;
            int todayFlag = 0;
            //  先过滤今天昨天
            for (int i = 0; i < mDUBDatas.size(); i++) {
                if (ResourceUtil.getString(ctx,R.string.today).equals(DateUtil.isTodayOrYesterday(ctx,mDUBDatas.get(i).getCreatedStamp(),format))) {
                    todayFlag ++;
                    if (todayFlag > 1) {
                        mDUBDatas.get(i).setCreatedStamp("");
                    }else {
                        tempString1 = mDUBDatas.get(i).getCreatedStamp();
                        mDUBDatas.get(i).setCreatedStamp(ResourceUtil.getString(ctx,R.string.today));
                    }
                }else if(ResourceUtil.getString(ctx,R.string.yesterday).equals(DateUtil.isTodayOrYesterday(ctx,mDUBDatas.get(i).getCreatedStamp(),format))) {
                    yesterdayFlag ++;
                    if (yesterdayFlag > 1) {
                        mDUBDatas.get(i).setCreatedStamp("");
                    }else {
                        tempString1 = mDUBDatas.get(i).getCreatedStamp();
                        mDUBDatas.get(i).setCreatedStamp(ResourceUtil.getString(ctx,R.string.yesterday));
                    }
                }else {
                    break;
                }
            }
            // 去除一天当中重复的日期
            if (TextUtils.isEmpty(tempString1)) {
                tempString2 = mDUBDatas.get(0).getCreatedStamp();
                tempString3 = mDUBDatas.get(0).getCreatedStamp();
            }else {
                tempString2 = tempString1;
            }

            for (int i = 0; i < mDUBDatas.size(); i++) {
                if (!TextUtils.isEmpty(mDUBDatas.get(i).getCreatedStamp()) &&
                        !ResourceUtil.getString(ctx,R.string.today).equals(mDUBDatas.get(i).getCreatedStamp()) &&
                        !ResourceUtil.getString(ctx,R.string.yesterday).equals(mDUBDatas.get(i).getCreatedStamp())) {
                    if (TextUtils.isEmpty(tempString1)) {
                        // 没有今天和昨天字符
                        if ( !TextUtils.isEmpty(tempString2)) {
                            if (i < mDUBDatas.size()) {
                                boolean isSame = DateUtil.compareDateByYMD(tempString2,mDUBDatas.get(i).getCreatedStamp(),format);
                                if(!isSame) {
                                    tempString2 = mDUBDatas.get(i).getCreatedStamp();
                                }else {
                                    // 相等
                                    tempString2 = mDUBDatas.get(i).getCreatedStamp();
                                    mDUBDatas.get(i).setCreatedStamp("");
                                }
                            }
                        }

                    }else {
                        // 有今天或者昨天字符
                        boolean isSame = DateUtil.compareDateByYMD(tempString2,mDUBDatas.get(i).getCreatedStamp(),format);
                        if(!isSame) {
                            tempString2 = mDUBDatas.get(i).getCreatedStamp();
                        }else {
                            // 相等
                            tempString2 = mDUBDatas.get(i).getCreatedStamp();
                            mDUBDatas.get(i).setCreatedStamp("");
                        }
                    }
                }
            }
            // 如果没有今天和昨天数据，则需要把第一个元素的时间进行复原
            if (TextUtils.isEmpty(tempString1) && TextUtils.isEmpty(mDUBDatas.get(0).getCreatedStamp())) {
                mDUBDatas.get(0).setCreatedStamp(tempString3);
            }
        }
    }

    /**
     * 判断指定日期是否为今天或者昨天
     * @param ctx   上下文
     * @param time  给定时间的字符串
     * @param format 指定时间的格式
     * @return 返回null 、昨天或者今天(测试通过)
     */
    public static String isTodayOrYesterday(Context ctx,String time,String format) {
        String resultTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        Calendar calendar1 = null;
        Calendar calendar2 = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if(date != null) {
                calendar1 = Calendar.getInstance();
                calendar1.setTime(new Date(System.currentTimeMillis()));
                calendar2 = Calendar.getInstance();
                calendar2.setTime(date);
                if (isTheSameDay(calendar1,calendar2)) {
                    resultTime = ResourceUtil.getString(ctx,R.string.today);
                }else {
                    // 判断是否为昨天
                    calendar1.add(Calendar.DAY_OF_MONTH, -1);
                    if (isTheSameDay(calendar1,calendar2)) {
                        resultTime = ResourceUtil.getString(ctx,R.string.yesterday);
                    }
                }
            }
        }
        return resultTime;
    }

    /**
     * 比较给定时间字符串是否为同一天
     * @param srcDate   时间字符串1
     * @param oldDate   时间字符串2
     * @param format    时间格式
     * @return true、false (测试通过)
     */
    public static boolean compareDateByYMD(String srcDate,String oldDate,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d1 = null;
        Date d2 = null;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        try {
            d1 = sdf.parse(srcDate);
            d2 = sdf.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (d1 != null && d2 != null) {
                calendar1.setTime(d1);
                calendar2.setTime(d2);
                return isTheSameDay(calendar1,calendar2);
            }
        }
        return false;
    }

    /**
     * 判定给定的两个时间是否是同一天
     * @param calendar1 时间1
     * @param calendar2 时间2
     * @return true、false（测试通过）
     */
    public static boolean isTheSameDay(Calendar calendar1,Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 将指定的时间对象转换成指定格式的时间字符串
     * @param dateStr 原日期字符串
     * @param fromFormat 原日期时间格式
     * @param toFormat 转换后的时间格式
     * @return 转换后的时间字符串、null(测试通过)
     */
    public static String getNewFormatDateString(String dateStr, String fromFormat, String toFormat) {
        // 1、将原始日期字符串转换成Date对象
        Date date = formatString2Date(dateStr, fromFormat);

        // 2、将Date对象转换成目标样式字符串
        return formatDate2String(date, toFormat);
    }

    /**
     * 指定格式的时间串转换成Date对象
     * @param dateStr 日期字符串
     * @param format 日期格式
     * @return 日期对象或者null(测试通过)
     */
    public static Date formatString2Date(String dateStr, String format) {
        if (!TextUtils.isEmpty(dateStr)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(dateStr);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 将日期转换成指定格式的字符串
     * @param date 待转换的日期
     * @param format 时间格式
     * @return 返回时间字符串或者null(测试通过)
     */
    public static String formatDate2String(Date date, String format) {
        if(date == null)
            return null;
        try {
            SimpleDateFormat formatPattern = new SimpleDateFormat(format);
            return formatPattern.format(date);
        } catch (Exception e) {
            return null;
        }
    }



}
