package com.gbase.utils;

import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SqlUtils.class
 * @Description: 本类为SQL语句方法类，封装了与SQL语句执行操作相关的方法。
 */
public class SqlUtils {
    /**
     * @param sql        String，需要执行的SQL语句
     * @param connection Connection，与数据库的连接
     * @return ResultSet，包含执行传入的SQL查询语句后的结果
     * @throws SQLException 查询语句可能出现的异常或其他SQL查询过程异常
     * @Description: 传入要进行查询的SQL语句，可返回查询结果集ResultSet
     */
    public static ResultSet query(String sql, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    /**
     * @param sql        String，需要执行的SQL语句
     * @param connection Connection，与数据库的连接
     * @return int，数据库更新行数
     * @throws SQLException 执行SQL语句可能出现的异常或其他SQL过程异常
     * @Description: 传入要进行更新的SQL语句，返回数据库中的影响行数
     */
    public static int update(String sql, Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        }
    }

    /**
     * @param sql        String，需要执行的SQL语句
     * @param connection Connection，与数据库的连接
     * @return boolean，插入是否成功
     * @throws SQLException 执行SQL语句可能出现的异常或其他SQL过程异常
     * @Description: 传入执行插入的SQL语句，返回插入是否成功结果
     */
    public static boolean insert(String sql, Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            return true;
        }
    }

    /**
     * @param resultSet   ResultSet，需要打印的SQL查询ResultSet结果
     * @param numOfColumn int，打印的表的列数
     * @throws SQLException 执行SQL语句可能出现的异常或其他SQL过程异常
     * @Description: 本方法用于打印某一结果集的指定列数
     */
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

    /**
     * @param sql String，要执行的SQL语句
     * @return String，SQL语句中目标表格的表名
     * @Description: 本方法用于获取某一条SQL语句中所要执行的表格名
     */
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

    /**
     * @param connection Connection，与数据库的连接
     * @param tableName  String，表名
     * @return List<Pair < String, String>>，List中存储了每一列的字段名和字段类型组合而成的键值对，每一个键值对在List中的序号对应他们的列序号
     * @throws SQLException 执行SQL语句可能出现的异常或其他SQL过程异常
     * @Description: 本方法用于获取某一表格的结构，获取每一列的字段名和字段类型，并存于List<Pair<String,String>>中，
     * 每个键值对中key为字段名，value为字段类型
     */
    public static List<Pair<String, String>> getTableStructure(Connection connection, String tableName) throws SQLException {
        String sql = "select * from " + tableName + " limit 1";
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
                return list;
            } catch (SQLException e) {
                System.err.println("获取表结构失败");
                throw new SQLException();
            }
        }
        return null;
    }

    /**
     * @param sql        String，需要执行的SQL语句
     * @param connection Connection，与数据库的连接
     * @throws SQLException
     * @Description: 对输入的SQL语句进行预处理，根据SQL语句调用对应的SQL执行方法
     */
    public static void sqlPretreat(String sql, Connection connection) throws SQLException {
        String tableName = getTableName(sql);
        // 对输入的SQL语句进行识别
        String sub = sql.toLowerCase().split(" ")[0];
        switch (sub) {
            case "show":
            case "select":
            case "desc":
                // 如果输入的 SQL 语句是 show、select 或 desc，则查询数据库并打印结果
                if (tableName != null)
                    SqlUtils.printResultSet(SqlUtils.query(sql, connection), getTableStructure(connection, tableName).size());
                else SqlUtils.printResultSet(SqlUtils.query(sql, connection), 1);
                break;
            case "alter":
            case "insert":
                // 如果输入的 SQL 语句是 alter 或 insert，则向数据库中修改数据
                SqlUtils.insert(sql, connection);
                break;
            case "drop":
            case "create":
            case "delete":
            case "truncate":
            case "update":
                // 如果输入的 SQL 语句是 drop、create、delete、truncate 或 update，则更新数据库中的数据
                SqlUtils.update(sql, connection);
                break;
            default:
                // 如果输入的 SQL 语句不是以上任何一种类型，则抛出异常
                SQLException ex = new SQLException();
                throw ex;
        }
    }
}
