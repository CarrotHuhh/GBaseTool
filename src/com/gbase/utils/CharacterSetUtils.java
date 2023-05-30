package com.gbase.utils;

import javafx.util.Pair;

import java.sql.*;
import java.util.HashMap;
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
            HashMap<String, String> map = new HashMap<>();
            ResultSet resultSet = stmt.executeQuery("show variables like '%character_set%'");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + ": " + resultSet.getString(2));
            }
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
            preparedStatement.execute();
            System.out.println("插入指定编码字段成功");
            System.out.println("从数据库中查询插入的数据为：");
            String result_sql = "";
            if (pair1.getValue() != null && pair2.getValue() != null) {
                result_sql = "select * from " + tableName + " where " + pair1.getKey() + "='" + pair1.getValue() + "' and " + pair2.getKey() + "='" + pair2.getValue() + "'";
            } else {
                result_sql = "select * from " + tableName + " where " + pair1.getKey() + "='" + pair1.getValue() + "'";
            }
            printResultSet(query(result_sql, connection), list.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("插入指定编码字段失败");
        }
    }
}
