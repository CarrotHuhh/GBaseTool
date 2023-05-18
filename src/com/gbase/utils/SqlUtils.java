package com.gbase.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlUtils {
    public static ResultSet query(String sql, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("查询失败");
            return null;
        }
    }

    public static int update(String sql, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("更新失败");
            return 0;
        }
    }

    public static boolean insert(String sql, Connection connection) {
        try(Statement statement = connection.createStatement();) {
            return statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("插入失败");
            return false;
        }
    }

    public static void printResultSet(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            System.out.println(resultSet.getString(1));
        }
    }
}
