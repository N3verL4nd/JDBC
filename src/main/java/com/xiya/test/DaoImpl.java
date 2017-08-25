package com.xiya.test;

import com.xiya.entity.Person;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DaoImpl implements FinalDAO<Person> {
    private QueryRunner queryRunner;

    public DaoImpl() {
        queryRunner = new QueryRunner();
    }

    @Override
    public Person get(Connection connection, String sql, Object... args) {
        try {
            return queryRunner.query(connection,sql, new BeanHandler<>(Person.class), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> getList(Connection connection, String sql, Object... args) {
        try {
            return queryRunner.query(connection, sql, new BeanListHandler<Person>(Person.class), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <E> E getForValue(Connection connection, String sql, Object... args) {
        try {
            return queryRunner.query(connection, sql, new ScalarHandler<E>(), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Connection connection, String sql, Object... args) {
        try {
            queryRunner.update(connection, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void batch(Connection connection, String sql, Object[]... args) {
        try {
            queryRunner.batch(connection, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
