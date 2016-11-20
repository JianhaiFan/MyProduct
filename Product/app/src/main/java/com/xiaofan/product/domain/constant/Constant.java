package com.xiaofan.product.domain.constant;

import com.xiaofan.product.util.ExternalStorageUtil;

import java.io.File;

/**
 * @author: 范建海
 * @createTime: 2016/10/30 14:10
 * @className:  StringUtil
 * @description: 字符串常量类
 * @changed by:
 */
public class Constant {
    // 项目名称
    public static final String PROJECT_NAME = "product" + File.separator;
    // 应用外部存储的根路径
    public static final String APP_LOCAL_PATH_ROOT = ExternalStorageUtil.getExternalStoragePath() + File.separator +  PROJECT_NAME ;
    // 奔溃日志信息路径
    public static final String CRASH_PATH = APP_LOCAL_PATH_ROOT + "crash" + File.separator;
    // 缓存信息路径
    public static final String CACHE_PATH = APP_LOCAL_PATH_ROOT + "cache" + File.separator;
    // 音频路径
    public static final String AUTIO_PATH = CACHE_PATH + "audio" + File.separator;
    // 视频路径
    public static final String VIDEO_PATH = CACHE_PATH + "video" + File.separator;
    // 数据库路径
    public static final String DB_PATH = CACHE_PATH + "database" + File.separator;
    // 图片路径
    public static final String IMAGE_PATH = CACHE_PATH + "image" + File.separator;

    /**
     * 阿里云路径常量
     */
    // 门诊图片类型
    public static final String TYPE_IMG_OF_TREATMENT = "treatment/images/";
    // 门诊音频类型
    public static final String TYPE_AUDIO_OF_TREATMENT = "treatment/audio/";
    // 门诊视频类型
    public static final String TYPE_VIDEO_OF_TREATMENT = "treatment/video/";
    // 门诊视频第一帧类型
    public static final String TYPE_VIDEO_FIRST_FRAME_OF_TREATMENT = "treatment/video/firstFrame/";






}
