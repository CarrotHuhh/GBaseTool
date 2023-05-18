package com.gbase.mode;

import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Mode2 {
    private ConnectionUtils connectionUtils;

    public Mode2() {
        connectionUtils = new ConnectionUtils();
        connectionUtils.init();
        Connection connection = null;
        try {
            connection = connectionUtils.establishConnection();
            while (connection != null) {
                System.out.println("请输入SQL语句，若要退出则输入quit：");
                Scanner scanner = new Scanner(System.in);
                String sql = scanner.nextLine();
                String sub = sql.toLowerCase().split(" ")[0];
                switch (sub) {
                    case "show":
                    case "select":
                        SqlUtils.printResultSet(SqlUtils.query(sql, connection), 1);
                        break;
                    case "insert":
                        SqlUtils.insert(sql, connection);
                        break;
                    case "update":
                        SqlUtils.update(sql, connection);
                        break;
                    case "quit": {
                        connection.close();
                        connection = null;
                        break;
                    }
                    default:
                        System.out.println("SQL语句输入错误，请重新输入：");
                }
            }
        } catch (Exception e) {
            System.out.println("连接出现异常，Mode2测试完毕，请检查登录配置");
            e.printStackTrace();
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
