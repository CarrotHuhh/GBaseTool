package com.gbase.utils;

import java.sql.*;

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
//            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("插入失败");

        }
        return false;
    }
    public static void insertChosenCode(Connection connection,String code) {
        String tmp_sql = "insert into testtable value(?,?,?,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(tmp_sql)){
            preparedStatement.setBytes(1,"李四".getBytes(code));
            preparedStatement.setBytes(2,"张三".getBytes(code));
            preparedStatement.setBytes(3,"王五".getBytes(code));
            preparedStatement.execute();
            System.out.println("插入指定编码字段成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("插入指定编码字段失败");
        }
    }
    public static void printResultSet(ResultSet resultSet,int numOfClolumn) throws SQLException {
        while(resultSet.next()){
            System.out.println(resultSet.getString(numOfClolumn));
        }
    }
}
