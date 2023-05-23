package com.gbase.mode;

import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @ClassName: Mode2.class
 * @Description: 本类用于测试所连接数据库对SQL语句的处理是否正常运行，用户可通过命令行中相关引导进行SQL语句的输入。
 */
public class Mode2 {
    private ConnectionUtils connectionUtils;

    public Mode2() {
        connectionUtils = new ConnectionUtils();
        try {
            connectionUtils.init();
        } catch (Exception e) {
            System.out.println(e);
        }
        Connection connection = null;
        try {
            connection = connectionUtils.establishConnection();
            boolean flag = true;
            while (connection != null && flag) {
                System.out.println("请输入SQL语句，若要退出则输入quit：");
                Scanner scanner = new Scanner(System.in);
                String sql = scanner.nextLine();
                flag = SqlUtils.sqlPretreat(sql, connection);
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
