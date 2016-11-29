package com.xiaofan.product.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xiaofan.product.domain.constant.Constant;
import com.xiaofan.product.domain.constant.SqlConstant;
import com.xiaofan.product.util.ExternalStorageUtil;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.SharedPreferencesUtil;
import com.xiaofan.product.util.ValidateUtil;

/**
 * @author: 范建海
 * @createTime: 2016/11/27 15:24
 * @className:  PersonalInfomationHelper
 * @description: 个人信息数据库帮助类
 * @changed by:
 */
public class PersonalInfomationHelper extends SQLiteOpenHelper{

    private static PersonalInfomationHelper instance;

    private PersonalInfomationHelper(Context ctx, String name,int version) {
        super(ctx, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            //开启事务  
            db.beginTransaction();
            try {
                db.execSQL(SqlConstant.TB_BASIC_PERSONAL_INFO_SQL);
                db.execSQL(SqlConstant.TB_LANGUAGE_SQL);

                db.setTransactionSuccessful();
            } finally {
                // 结束事务
                db.endTransaction();
            }

        } catch (Exception e) {
            LogUtil.e("onCreate Exception :" + e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            updateDataBase(db,oldVersion,newVersion);
        }
    }


    /**
     * 升级数据库
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void updateDataBase(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.e("updateDataBase...");
    }

    /**
     * 获取个人信息数据库帮助类实例
     * @param ctx 上下文
     * @return
     */
    public static PersonalInfomationHelper getInstance(Context ctx) {
        /**
         * 用户角色客户端控制思路:(游客也要缓存相关信息)
         * 1.用户第一次游客身份进入,oldUserId 和 userId 默认都是游客身份的ID(清除数据之后也都变为游客身份)
         * 2.游客身份进入后，遇到登录界面,游客 --> 当前登录用户，sp变换userId为当前用户的Id，当为当前用户创建数据库成功之后，sp变换oldUserId 为userId的值
         * 3.当前用户通过设置界面手动退出应用的时候，sp变换userId为游客身份的userId
         * 4.当用户从设置里面清除应用内存数据时，当前用户的角色又变成了游客身份(从新走游客身份流程)
         * 5.当sdcard不存在数据库文件的时候(用户手动删除数据库),重新创建数据库实例
         */

        // 上一个用户的ID
        String oldUserId = SharedPreferencesUtil.get(ctx, Constant.USER_INFO_FILE_NAME,Constant.OLD_USER_ID,Constant.DEFAULT_USER_ID);
        // 当前用户的ID(创建数据库的时候依据当前用户的ID)
        String userId = SharedPreferencesUtil.get(ctx, Constant.USER_INFO_FILE_NAME,Constant.USER_ID,Constant.DEFAULT_USER_ID);

        /**
         * 个人基本信息数据库切换的思路：
         * 1.instance实例为空
         *      2.切换账号,登录不同用户
         *          3.清除数据，删除sp中的数据，即变为游客身份的时候，需要进行创建游客身份的数据实例
         *              4.手动删除SdCard中的数据库文件
         */

        String dbPath = Constant.DB_PATH + SqlConstant.DB_PERSONAL_INFO_NAME_PREFIX + userId + SqlConstant.DB_EXTENSION_NAME;

        if(instance == null || !oldUserId.equals(userId) || ValidateUtil.isTourist(oldUserId,userId) || !ExternalStorageUtil.fileIsExists(dbPath))

            synchronized (PersonalInfomationHelper.class) {

                if(instance == null || !oldUserId.equals(userId) || ValidateUtil.isTourist(oldUserId,userId) || !ExternalStorageUtil.fileIsExists(dbPath)) {

                    instance = new PersonalInfomationHelper(ctx,dbPath,SqlConstant.DB_PERSONAL_INFO_VERSION);

                    try {
                        instance.getWritableDatabase();
                    }catch (Exception e) {

                        LogUtil.e(" getInstance Exception: " + e.toString());
                    }

                }

            }
        return instance;
    }


}
