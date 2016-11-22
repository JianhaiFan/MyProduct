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
    // 数据库扩展名
    public static final String DB_EXTENSION_NAME = ".sqlite";
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
    // Android规定协议
    public static final String SCHEME = "content://";
    // 主机/域名
    public static final String AUTHORITY = "com.xiaofan.product.provider.ContactContentProvider";
    // 操作数据库的协议码
    public static final int URI_TB_TEST_CODE = 0;
    // 操作数据库的协议
    public static final Uri URI_TB_TEST = Uri.parse( SCHEME + AUTHORITY + "/" + TB_TEST_NAME);



}
