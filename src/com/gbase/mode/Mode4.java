package com.gbase.mode;

import com.gbase.service.CharacterSetService;
import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.util.Scanner;

public class Mode4 {
    private ConnectionUtils connectionUtils;

    public Mode4() {
        connectionUtils = new ConnectionUtils();
        connectionUtils.init();
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = connectionUtils.establishConnection();
            boolean flag = true;
            label:
            while (connection != null && flag) {
                System.out.println("进行sql测试请输入sqltest，进行字符集测试请输入charsettest，输入quit退出程序：");
                String sqlInput = scanner.nextLine();
                switch (sqlInput) {
                    case "sqltest":
                        System.out.println("请输入要执行的SQl语句");
                        String sql = scanner.nextLine();
                        System.out.println("所执行SQL语句为：" + sql);
                        //功能未完成
                        flag = SqlUtils.sqlPretreat(sql, connection);
//                        SqlUtils.printResultSet(SqlUtils.query(sql, connection), 1);
                        System.out.println();
                        break;
                    case "back":
                        break label;
                    case "quit":
                        connection.close();
                        connection = null;
                        break;
                    case "charsettest":
                        CharacterSetService.getCharacterSetInCluster(connection);
                        System.out.println("请输入要进行插入指定编码语句测试的表名");
                        String tableName = scanner.nextLine();
                        SqlUtils.insertChosenCode(connection, "GBK", tableName);
                        break;
                    default:
                        System.out.println("指令输入错误，请重新输入，输入sqltest进行sql测试，输入back返回切换驱动连接测试，输入quit退出程序：");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("连接出现异常，Mode4测试结束，请检查登录配置");
            e.printStackTrace();
            scanner.close();
        }
    }

}
