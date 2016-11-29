package com.xiaofan.product.domain.constant;

import android.net.Uri;

/**
 * @author: 范建海
 * @createTime: 2016/11/14 15:41
 * @className:  SqlConstant
 * @description: SQL语句常量
 * @changed by:
 */
public class SqlConstant {
    /**
     * 数据库相关的一些常量
     */
    // 数据库扩展名
    public static final String DB_EXTENSION_NAME = ".sqlite";
    // 个人信息相关数据库前缀
    public static final String DB_PERSONAL_INFO_NAME_PREFIX = "personal_info_";
    // 个人信息数据库版本
    public static final int DB_PERSONAL_INFO_VERSION = 1;
    // 用户基本信息表名
    public static final String TB_BASIC_PERSONAL_INFO_NAME = "tb_personal_info";
    // 语言表名
    public static final String TB_LANGUAGE_NAME = "tb_language";

    /**
     * SQL语句
     */

    // 创建用户基本信息表的Sql语句
    public static final String TB_BASIC_PERSONAL_INFO_SQL = " CREATE TABLE IF NOT EXISTS " + SqlConstant.TB_BASIC_PERSONAL_INFO_NAME
            + " (userId varchar(50) primary key,userName nvarchar(60),userPhone char(30),userPassword varchar(30)"
            + ",userHeadUrl ntext)";
    // 创建语言表的sql语句
    public static final String TB_LANGUAGE_SQL = " CREATE TABLE IF NOT EXISTS " + SqlConstant.TB_LANGUAGE_NAME
            + "(languageId varchar(50) primary key, languageName nvarchar(60), isSelected bit)";


    /**
     * 内容提供者相关常量
     */

    // Android规定协议
    public static final String SCHEME = "content://";
    // 内容提供者(个人信息库)主机/域名
    public static final String PERSONAL_INFO_AUTHORITY = "com.xiaofan.product.provider.PersonalInfomationProvider";
    // 操作个人信息表的Code
    public static final int URI_PERSONAL_INFO_CODE = 1;
    // 操作语言表的Code
    public static final int URI_LANGUAGE_CODE = 2;

    /**
     * 其他常量
     */


    // 测试数据库名称
    public static final String DB_TEST_NAME = "db_test_product" + DB_EXTENSION_NAME;
    // 测试数据库表名
    public static final String TB_TEST_NAME = "tb_test_product";
    // 测试数据库版本
    public static final int DB_TEST_VERSION = 1;
    // 在测试数据库下创建测试表
    public static final String TB_TEST_CREATE = "create table " + SqlConstant.TB_TEST_NAME + " (_id integer primary key autoincrement,name text,gender text,age integer)";
    // 在测试数据库下的测试表插入数据
    public static final String TB_TEST_INSERT = "insert into "+ SqlConstant.TB_TEST_NAME +" values(null,'zhangsan','boy',25)";
    // 查询测试库下的测试表数据
    public static final String TB_TEST_QUERY = "select * from " + SqlConstant.TB_TEST_NAME;

    // 操作数据库的协议码
    public static final int URI_TB_TEST_CODE = 0;



}
