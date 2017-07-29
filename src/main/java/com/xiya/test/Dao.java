package com.xiya.test;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao {

    /**
     * 获取结果集的 ColumnLabel 对应的 List
     * @param rs
     * @return
     * @throws SQLException
     */
    private List<String> getColumnLabels(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = null;
        List<String> labels = new ArrayList<>();
        metaData = rs.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            labels.add(metaData.getColumnLabel(i + 1));
        }
        return labels;
    }

    /**
     * 通用查询方法
     * @param tClass
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> query(Class<T> tClass, String sql, Object ...args) {
        T entity = null;
        List<T> list = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = JDBCTools.getConnection();
            statement = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            rs = statement.executeQuery();

            //数据库字段别名列表
            List<String> labels = getColumnLabels(rs);

            // 创建一个 Map<String, Object> 对象
            // 键: SQL 查询的列的别名
            // 值: SQL 查询的列的值
            Map<String, Object> map = new HashMap<>();
            //保存查询得到的数据
            list = new ArrayList<>();

            while (rs.next()) {
                map.clear();
                //每个 Map 对象对应一条数据库记录
                for (String columnLabel : labels) {
                    map.put(columnLabel, rs.getObject(columnLabel));
                }
                //若 Map 不为空集, 利用反射创建 tClass 对应的对象
                if (!map.isEmpty()) {
                    try {
                        entity = tClass.newInstance();
                        //遍历 Map 对象, 利用反射为 Class 对象的对应的属性赋值
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            String name = entry.getKey();
                            Object value = entry.getValue();
                            try {

                                //利用反射设置实体类的字段
                                //Field field = tClass.getDeclaredField(name);
                                //field.setAccessible(true);
                                //field.set(entity, value);

                                //自定义转换格式（不设置，如果有Date字段为null，则出现异常）
                                ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                                BeanUtils.setProperty(entity, name, value);
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    list.add(entity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(conn, statement, rs);
        }
        return list;
    }

    /**
     * 通用更新方法
     * @param sql sql语句
     * @param args  参数
     */
    public int update(String sql, Object ...args) {
        Connection conn = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            conn = JDBCTools.getConnection();
            statement = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(statement);
            JDBCTools.closeQuietly(conn);
        }
        return result;
    }

    public <E> E getValue(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = JDBCTools.getConnection();
            statement = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            rs = statement.executeQuery();
            if (rs.next()) {
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(conn, statement, rs);
        }
        return null;
    }

}
