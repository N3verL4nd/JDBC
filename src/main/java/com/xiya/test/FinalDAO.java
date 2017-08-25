package com.xiya.test;

import java.sql.Connection;
import java.util.List;

/**
 * 访问数据的 DAO 接口
 * 里边定义好访问数据库表的各种方法
 * @param <T> DAO 处理的实体类的类型
 */
public interface FinalDAO<T> {
    /**
     * 返回一个 T 类型的对象
     * @param connection 数据库连接
     * @param sql SQL语句
     * @param args 填充占位符的可变参数
     * @return 一个 T 类型的对象
     */
    T get(Connection connection, String sql, Object... args);

    /**
     *
     * @param connection 数据库连接
     * @param sql SQL语句
     * @param args 填充占位符的可变参数
     * @return 一组 T 类型的对象
     */
    List<T> getList(Connection connection, String sql, Object... args);

    /**
     * 返回具体的一个值，例如 COUNT(*)
     * @param connection 数据库连接
     * @param sql SQL语句
     * @param args 填充占位符的可变参数
     * @param <E> 返回值的类型
     * @return 具体的一个值
     */
    <E> E getForValue(Connection connection, String sql, Object... args);

    /**
     *
     * @param connection 数据库连接
     * @param sql SQL语句
     * @param args 填充占位符的可变参数
     */
    void update(Connection connection, String sql, Object... args);

    /**
     * 批处理
     * @param connection 数据库连接
     * @param sql SQL语句
     * @param args 填充占位符的 Object[] 类型的可变参数
     */
    void batch(Connection connection, String sql, Object[]... args);
}
