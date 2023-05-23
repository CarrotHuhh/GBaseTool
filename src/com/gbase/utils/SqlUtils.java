package com.gbase.utils;

import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlUtils {
    public static ResultSet query(String sql, Connection connection) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static int update(String sql, Connection connection) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean insert(String sql, Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }


    public static void insertChosenCode(Connection connection, String code, String tableName) throws SQLException {
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

    public static void printResultSet(ResultSet resultSet, int numOfColumn) throws SQLException {
        System.out.println("查询结果如下：");
        while (resultSet.next()) {
            for (int i = 1; i <= numOfColumn; i++) {
                if (i != numOfColumn) {
                    System.out.print(resultSet.getString(i) + "-----");
                } else {
                    System.out.print(resultSet.getString(i));
                }
            }
            System.out.println();
        }
    }

    public static String getTableName(String sql) {
        String tableName = null;
        String[] strings = sql.toLowerCase().split(" ");
        List<String> list = new ArrayList<>();
        for (String str : strings) {
            list.add(str);
        }
        if (list.contains("into")) {
            tableName = list.get(list.indexOf("into") + 1);
        } else if (list.contains("delete")) {
            tableName = list.get(list.indexOf("delete") + 1);
        } else if (list.contains("update")) {
            tableName = list.get(list.indexOf("update") + 1);
        } else if (list.contains("from")) {
            tableName = list.get(list.indexOf("from") + 1);
        } else {
            tableName = null;
        }
        return tableName;
    }

    public static List<Pair<String, String>> getTableStructure(Connection connection, String tableName) throws SQLException {
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
            } catch (SQLException e) {
                System.out.println("获取表结构失败");
                throw e;
            }
        }
        return null;
    }

    public static void sqlPretreat(String sql, Connection connection) throws SQLException {
        String tableName = getTableName(sql);
        String sub = sql.toLowerCase().split(" ")[0];
        switch (sub) {
            case "show":
            case "select":
            case "desc":
                if (tableName != null)
                    SqlUtils.printResultSet(SqlUtils.query(sql, connection), getTableStructure(connection, tableName).size());
                else SqlUtils.printResultSet(SqlUtils.query(sql, connection), 1);
            case "alter":
            case "insert":
                SqlUtils.insert(sql, connection);
            case "drop":
            case "create":
            case "delete":
            case "truncate":
            case "update":
                SqlUtils.update(sql, connection);
            default:
                SQLException ex = new SQLException();
                throw ex;
        }
    }
}
