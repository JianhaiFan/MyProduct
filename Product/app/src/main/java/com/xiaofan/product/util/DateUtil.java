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
    // TODO 测试
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
    // TODO 测试
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
    //TODO 测试
    public static final String FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 仿微信朋友圈时间列表排序
     *      前提：1.后台返回的数据是排好序的
     *           2.含有时间的实体Bean必须继承自DateUtilBean
     * @param mDUBDatas 待排序的数据集合(实体中含有createdStamp时间字段)
     */
    public static void replaceRepeatTime(List<DateUtilBean> mDUBDatas,Context ctx) {
        if(mDUBDatas.size() > 0) {
            String tempString1 = "";
            String tempString2 = "";
            String tempString3 = "";
            int yesterdayFlag = 0;
            int todayFlag = 0;
            //  先过滤今天昨天
            for (int i = 0; i < mDUBDatas.size(); i++) {
                if ("今天".equals(DateUtil.isTodayOrYesterday(mDUBDatas.get(i).getCreatedStamp()))) {
                    todayFlag ++;
                    if (todayFlag > 1) {
                        mDUBDatas.get(i).setCreatedStamp("");
                    }else {
                        tempString1 = mDUBDatas.get(i).getCreatedStamp();
                        mDUBDatas.get(i).setCreatedStamp(ResourceUtil.getString(ctx,R.string.today));
                    }
                }else if("昨天".equals(DateUtil.isTodayOrYesterday(mDUBDatas.get(i).getCreatedStamp()))) {
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
                        !"今天".equals(mDUBDatas.get(i).getCreatedStamp()) &&
                        !"昨天".equals(mDUBDatas.get(i).getCreatedStamp())) {
                    if (TextUtils.isEmpty(tempString1)) {
                        // 没有今天和昨天字符
                        if ( !TextUtils.isEmpty(tempString2)) {
                            if (i < mDUBDatas.size()) {
                                boolean isSame = DateUtil.compareDateByYMD(tempString2,mDUBDatas.get(i).getCreatedStamp());
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
                        boolean isSame = DateUtil.compareDateByYMD(tempString2,mDUBDatas.get(i).getCreatedStamp());
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
     * @Description: 判断当前时间是否是昨天或者是今天
     * @param time 要判断的时间
     * @return
     * @return: String 返回today，yesterday or null
     */
    public static String isTodayOrYesterday(String time) {
        String resultTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH);
        Date d = null;
        Calendar calendar1 = null;
        Calendar calendar2 = null;
        try {
            d = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if(d != null) {
                calendar1 = Calendar.getInstance();
                calendar1.setTime(new Date(System.currentTimeMillis()));
                calendar2 = Calendar.getInstance();
                calendar2.setTime(d);
                if (isTheSameDay(calendar1,calendar2)) {
                    resultTime = "今天";
                }else {
                    // 判断是否为昨天
                    calendar1.add(Calendar.DAY_OF_MONTH, -1);
                    if (isTheSameDay(calendar1,calendar2)) {
                        resultTime = "昨天";
                    }
                }
            }
        }
        return resultTime;
    }

    /**
     * @Description: 判断两个时间的年月日是否相等
     * @param calendar1
     * @param calendar2
     * @return
     * @return: boolean 相等则返回true 否则返回false
     */
    public static boolean isTheSameDay(Calendar calendar1,Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @Description: 根据年月日比较两个时间是否相等
     * @param srcDate
     * @param oldDate
     * @return
     * @return: boolean 如果两个时间的年月日都相等则返回true 否则返回false
     */
    public static boolean compareDateByYMD(String srcDate,String oldDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH);
        Date tempDate1 = null;
        Date tempDate2 = null;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        try {
            tempDate1 = sdf.parse(srcDate);
            tempDate2 = sdf.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (tempDate1 != null && tempDate2 != null) {
                calendar1.setTime(tempDate1);
                calendar2.setTime(tempDate2);
                return isTheSameDay(calendar1,calendar2);
            }
        }
        return false;
    }
}
