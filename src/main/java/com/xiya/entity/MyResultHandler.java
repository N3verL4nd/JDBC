package com.xiya.entity;

import org.apache.commons.dbutils.BaseResultSetHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyResultHandler extends BaseResultSetHandler<List<String>> {
    private int indexColumn;

    public MyResultHandler() {
        indexColumn = 1;
    }

    public MyResultHandler(int indexColumn) {
        this.indexColumn = indexColumn;
    }

    @Override
    protected List<String> handle() throws SQLException {
        List<String> list = new ArrayList<>();
        while (this.next()) {
            list.add(handlerRow());
        }
        return list;
    }

    private String handlerRow() throws SQLException {
        return this.getString(indexColumn);
    }
}
