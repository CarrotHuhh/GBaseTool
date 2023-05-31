package com.gbase.utils;

import javafx.util.Pair;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

import static com.gbase.utils.SqlUtils.*;

/**
 * @ClassName: CharacterSetUtils.class
 * @Description: 本类中封装了用于若干个字符集编码检测相关的方法。
 */
public class CharacterSetUtils {
    /**
     * @param connection Connection，与数据库的连接
     * @Description: 本方法用于查询数据库中字符集编码相关配置并进行打印
     */
    public static void getCharacterSetInCluster(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("show variables like '%character_set%'");
            System.out.println("+------------------------------------+");
            System.out.println("|         数据库字符集编码配置        |");
            System.out.println("+------------------------------------+");
            while (resultSet.next()) {
                if (resultSet.getString(2).length() < 10) {
                    System.out.print("|");
                    System.out.printf("%-26s", resultSet.getString(1));
                    System.out.printf("|");
                    System.out.printf("%-9s", resultSet.getString(2));
                    System.out.print("|");
                    System.out.println();
                }
            }
            System.out.println("+------------------------------------+");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getCharacterSetInTable(Connection connection, String tableName) {
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("show full columns from " + tableName);
            System.out.println("+------------------------------------------------+");
            System.out.println("|                表格字符集编码配置              |");
            System.out.println("+------------------------------------------------+");
            while (resultSet.next()) {
                if (resultSet.getString(2).length() < 16) {
                    System.out.print("|");
                    System.out.printf("%-16s", resultSet.getString(1));
                    System.out.printf("|");
                    System.out.printf("%-14s", resultSet.getString(2));
                    System.out.print("|");
                    System.out.printf("%-16s", resultSet.getString(3));
                    System.out.print("|");
                    System.out.println();
                }
            }
            System.out.println("+------------------------------------------------+");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param connection Connection，与数据库的连接
     * @param code       String，要插入数据库中的文字编码类型
     * @param tableName  要进行插入测试的表名
     * @throws SQLException 执行SQL语句可能出现的异常或其他SQL过程异常
     * @Description: 本方法根据指定的编码向数据库中的指定表插入模拟数据
     */
    public static void insertChosenCode(Connection connection, String code, String tableName) throws SQLException {
        List<Pair<String, String>> list = null;
        try {
            list = getTableStructure(connection, tableName);
        } catch (Exception e) {
            throw new SQLException();
        }
        StringBuilder tmp_sql = new StringBuilder("insert into " + tableName + " value(");
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                tmp_sql.append("?)");
            } else {
                tmp_sql.append("?,");
            }
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(tmp_sql.toString())) {
            Pair<String, String> pair1 = null;
            Pair<String, String> pair2 = null;
            int pairNum = 1;
            for (int i = 1; i <= list.size(); i++) {
                if (list.get(i - 1).getValue().equals("VARCHAR")) {
                    String columnName = list.get(i - 1).getKey();
                    String columnType = list.get(i - 1).getValue();
                    System.out.println("请输入列" + columnName + "(" + columnType + ")" + "所要插入的值：");
                    Scanner scanner = new Scanner(System.in);
                    String content = scanner.nextLine();
                    preparedStatement.setBytes(i, content.getBytes(code));
                    if (pairNum == 1) {
                        pair1 = new Pair<>(columnName, content);
                    }
                    if (pairNum == 2) {
                        pair2 = new Pair<>(columnName, content);
                    }
                    pairNum++;
                } else {
                    preparedStatement.setNull(i, Types.INTEGER);
                }
            }
            try {
                preparedStatement.execute();
            } catch (Exception e) {
                throw new Exception("插入失败");
            }
            System.out.println("插入指定编码字段成功");
            System.out.print("对插入数据库中数据查询结果");
            String result_sql = "";
            try {
                if (pair1 != null && pair2 != null) {
                    result_sql = "select * from " + tableName + " where " + pair1.getKey() + "='" + pair1.getValue() + "' and " + pair2.getKey() + "='" + pair2.getValue() + "'";
                } else {
                    result_sql = "select * from " + tableName + " where " + pair1.getKey() + "='" + pair1.getValue() + "'";
                }
                if (!query(result_sql, connection).next()) {
                    throw new Exception();
                }
                printResultSet(query(result_sql, connection), list.size());
            } catch (Exception e) {
                System.err.println("查询失败，数据库字符编码或该表格字符集编码可能与本次插入数据所使用编码不符，现输出该表格所有内容：");
                Statement statement = connection.createStatement();
                printResultSet(statement.executeQuery("select * from " + tableName),SqlUtils.getTableStructure(connection,tableName).size());
                throw new Exception();
            }
        } catch (Exception e) {

        }
    }
}
