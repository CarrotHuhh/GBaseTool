package com.gbase.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlOperations {
    public ResultSet query(String sql, Connection connection) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeQuery(sql);
        } catch (Exception e) {
            System.out.println("查询失败");
            return null;
        }
    }

    public int update(String sql, Connection connection){
        return 0;
    }


}
