package com.gbase.mode;

import com.gbase.service.CharacterSetService;
import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mode5 {
    public final List<String> drivers = new ArrayList<String>() {
        {
            add("com.gbase.jdbc.Driver");
            add("com.mysql.cj.jdbc.Driver");
        }
    };
    private ConnectionUtils connectionUtils;

    public Mode5() {
        connectionUtils = new ConnectionUtils();
        label1:
        while (true) {
            for (String str : drivers) {
                System.out.print(str + " | ");
            }
            System.out.println();
            System.out.println("Mode5测试开始，请从以上支持的JDBC驱动中选择所使用的驱动，退出请输入quit：");
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
                    boolean flag = true;
                    label2:
                    while (connection != null && flag) {
                        System.out.println("该驱动连接数据库成功，输入charsettest进行字符集测试，输入sqltest进行sql测试，输入back返回切换驱动连接测试，输入quit退出程序：");
                        String sqlInput = scanner.nextLine();
                        switch (sqlInput) {
                            case "sqltest":
                                System.out.println("请输入要执行的SQl语句");
                                String sql = scanner.nextLine();
                                System.out.println("所执行SQL语句为：" + sql);
                                //功能未完成
                                flag = SqlUtils.sqlPretreat(sql, connection);
//                                SqlUtils.insert(sql, connection);
                                break;
                            case "back":
                                break label2;
                            case "quit":
                                connection.close();
                                break label1;
                            case "charsettest":
                                CharacterSetService.getCharacterSetInCluster(connection);
                                System.out.println("请输入要进行插入指定编码语句测试的表名");
                                String tableName = scanner.nextLine();
                                SqlUtils.insertChosenCode(connection, "GBK", tableName);
                                break label2;
                            default:
                                System.out.println("指令输入错误，请重新输入，输入sqltest进行sql测试，输入back返回切换驱动连接测试，输入quit退出程序：");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("连接出现异常，Mode5测试结束，请检查登录配置");
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
