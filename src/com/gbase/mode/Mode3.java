package com.gbase.mode;

import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mode3 {

    public final List<String> drivers = new ArrayList<String>() {
        {
            add("com.gbase.jdbc.Driver");
            add("com.mysql.cj.jdbc.Driver");
        }
    };
    private ConnectionUtils connectionUtils;

    public Mode3() {
        connectionUtils = new ConnectionUtils();
        label1:
        while (true) {
            for (String str : drivers) {
                System.out.print(str + " | ");
            }
            System.out.println();
            System.out.println("Mode3测试开始，请从以上支持的JDBC驱动中选择所使用的驱动，退出请输入quit：");
            Scanner scanner = new Scanner(System.in);
            String driverInput = scanner.nextLine();
            if (driverInput.equals("quit")) {
                scanner.close();
                break label1;
            } else if (drivers.contains(driverInput)) {
                connectionUtils.setDriver(driverInput);
                connectionUtils.init();
                try {
                    Connection connection = connectionUtils.establishConnection();
                    label2:
                    while (connection != null) {
                        System.out.println("该驱动连接数据库成功，输入sqltest进行sql测试，输入back返回切换驱动连接测试，输入quit退出程序：");
                        String sqlInput = scanner.nextLine();
                        switch (sqlInput) {
                            case "sqltest":
                                System.out.println("请输入要执行的SQl语句");
                                String sql = scanner.nextLine();
                                System.out.println("所执行SQL语句为：" + sql);
                                //功能未完成
                                SqlUtils.printResultSet(SqlUtils.query(sql, connection), 1);
                                break;
                            case "back":
                                break label2;
                            case "quit":
                                connection.close();
                                break label1;
                            default:
                                System.out.println("指令输入错误，请重新输入，输入sqltest进行sql测试，输入back返回切换驱动连接测试，输入quit退出程序：");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("连接出现异常，Mode3测试结束，请检查登录配置");
                    e.printStackTrace();
                    scanner.close();
                    break label1;
                }
            } else {
                System.out.println("指令输入错误，请从以上支持的JDBC驱动中选择所使用的驱动，退出请输入quit：");
            }
        }
    }
}
