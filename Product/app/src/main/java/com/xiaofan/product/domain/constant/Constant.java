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
    /**
     * 项目本地缓存路径
     */
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

    /**
     * 用户相关信息常量
     */
    // 用户登录信息所在的本地文件(系统会自动添加后缀名.xml)
    public static final String USER_INFO_FILE_NAME = "userInfo";
    // 用户ID对应的Key常量(用户的唯一标识)
    public static final String USER_ID = "userId";
    // 上一个用户的Id(不向数据库里面存,为了创建数据库用)
    public static final String OLD_USER_ID = "oldUserId";
    // 用户登录密码key常量
    public static final String USER_PSD = "userPassword";
    // 默认用户ID (游客身份ID)
    public static final String DEFAULT_USER_ID = "0000000000";



}
