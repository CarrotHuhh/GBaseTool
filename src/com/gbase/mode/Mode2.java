package com.gbase.mode;

import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.util.Scanner;

/**
 * @ClassName: Mode2.class
 * @Description: 本类用于测试所连接数据库对SQL语句的处理是否正常运行，用户可通过命令行中相关引导进行SQL语句的输入。
 */
public class Mode2 {
    private ConnectionUtils connectionUtils;

    public Mode2() {
        Scanner scanner = new Scanner(System.in);
        connectionUtils = new ConnectionUtils();
        try {
            connectionUtils.init();
        } catch (Exception e) {
            System.out.println(e);
        }
        try (Connection connection = connectionUtils.establishConnection()) {
            while (true) {
                System.out.println("请输入SQL语句，若要退出则输入0");
                String sql = scanner.nextLine();
                System.out.println("所执行SQL语句为：" + sql);
                if (sql.equals("0")) {
                    break;
                }
                try {
                    SqlUtils.sqlPretreat(sql, connection);
                } catch (Exception e) {
                    System.out.println("sql语句输入错误，请重新输入");
                }
            }
        } catch (Exception e) {
            System.out.println("连接出现异常，Mode2测试完毕，请检查登录配置");
        }
    }
}
