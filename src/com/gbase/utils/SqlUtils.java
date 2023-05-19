package com.gbase.utils;

import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("插入失败");
            return false;
        }
    }


    public static void insertChosenCode(Connection connection, String code, String tableName) {
        List<Pair<String, String>> list = getTableStructure(connection, tableName);
        StringBuilder tmp_sql = new StringBuilder("insert into " + tableName + " value(");
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                tmp_sql.append("?)");
            } else {
                tmp_sql.append("?,");
            }
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(tmp_sql.toString())) {
            //健壮性不足
            for (int i = 1; i <= list.size(); i++) {
                if (list.get(i - 1).getValue().equals("VARCHAR")) {
                    preparedStatement.setBytes(i, "张三".getBytes(code));
                } else {
                    preparedStatement.setNull(i, Types.INTEGER);
                }
            }
            preparedStatement.execute();
            System.out.println("插入指定编码字段成功");
            System.out.println("从数据库中查询插入的数据为：");
            String result_sql = "select * from " + tableName + " order by id desc limit 1";
            printResultSet(query(result_sql, connection), list.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("插入指定编码字段失败");
        }
    }

    public static void printResultSet(ResultSet resultSet, int numOfClolumn) throws SQLException {
        while (resultSet.next()) {
            for (int i = 1; i <= numOfClolumn; i++) {
                if (i != numOfClolumn) {
                    System.out.print(resultSet.getString(i) + "-----");
                } else {
                    System.out.print(resultSet.getString(i));
                }
            }
            System.out.println();
        }
    }

    public static List<Pair<String, String>> getTableStructure(Connection connection, String tableName) {
        String sql = "select * from " + tableName + " limit 0,1";
        List<Pair<String, String>> list = new ArrayList<>();
        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int count = resultSetMetaData.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    Pair<String, String> pair = new Pair<>(resultSetMetaData.getColumnName(i), resultSetMetaData.getColumnTypeName(i));
                    list.add(pair);
                }
                System.out.println("所要操作表结构为：");
                System.out.println("-------------------------");
                System.out.println("列数-----字段名-----字段类型");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(i + 1 + "---------" + list.get(i).getKey() + "-----" + list.get(i).getValue());
                }
                System.out.println("-------------------------");
                return list;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("获取表结构失败");
                return null;
            }
        }
        return null;
    }

    public static boolean sqlPretreat(String sql, Connection connection) throws SQLException {
        String sub = sql.toLowerCase().split(" ")[0];
        switch (sub) {
            case "show":
            case "select":
            case "desc":
                SqlUtils.printResultSet(SqlUtils.query(sql, connection), 1);
                return true;
            case "alter":
            case "insert":
                SqlUtils.insert(sql, connection);
                return true;
            case "drop":
            case "create":
            case "delete":
            case "truncate":
            case "update":
                SqlUtils.update(sql, connection);
                return true;
            case "quit":
                connection.close();
                return false;
            default:
                System.out.println("SQL语句输入错误，请重新输入：");
                return true;
        }
    }
}
