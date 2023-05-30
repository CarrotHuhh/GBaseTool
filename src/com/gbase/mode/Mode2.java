package com.gbase.mode;

import com.gbase.utils.CharacterSetUtils;
import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @ClassName: Mode4.class
 * @Description: 本类用于测试数据库对不同SQL语句的处理情况，以及字符集相关配置是否正常。
 */
public class Mode2 {
    private ConnectionUtils connectionUtils;

    public Mode2() {
        System.out.println("-----------------------Mode2-----------------------");
        System.out.println();
        connectionUtils = new ConnectionUtils();
        Scanner scanner = new Scanner(System.in);
        try {
            connectionUtils.init();
        } catch (Exception e) {
            System.out.println(e);
        }
        try (Connection connection = connectionUtils.establishConnection()) {
            //进入测试模式一级界面，引导用户进行测试模式选择
            label1:
            while (connection != null) {
                System.out.println("----1.进行sql测试----2.进行字符集测试----0.退出程序----");
                System.out.print("请输入所选择操作前的序号：");
                String sqlInput = scanner.nextLine();
                System.out.println();
                switch (sqlInput) {
                    case "0":
                        break label1;
                    //进行SQL测试，使用flag_tmp标记是否继续循环，flag_tmp在SQL语句执行完成后失效，循环退出
                    case "1":
                        boolean flag_tmp = true;
                        label2:
                        while (flag_tmp) {
                            System.out.println("请输入要执行的SQl语句，输入0退出程序：");
                            String sql = scanner.nextLine();
                            System.out.println();
                            if (sql.equals("0")) {
                                break label1;
                            }
                            try {
                                SqlUtils.sqlPretreat(sql, connection);
                                flag_tmp = false;
                            } catch (SQLException e) {
                                System.err.println("sql语句输入错误，请重新输入");
                            }
                        }
                        System.out.println();
                        break;
                    //进行字符集测试
                    case "2":
                        CharacterSetUtils.getCharacterSetInCluster(connection);
                        System.out.println("请输入要进行插入指定编码语句测试的表名");
                        String tableName = scanner.nextLine();
                        System.out.println();
                        System.out.println("请输入要进行插入指定语句测试所使用的编码");
                        String code = scanner.nextLine();
                        System.out.println();
                        try {
                            CharacterSetUtils.insertChosenCode(connection, code, tableName);
                            continue;
                        } catch (SQLException e) {
                            System.err.println("返回上级菜单");
                            continue;
                        }
                    default:
                        System.err.println("指令输入错误，请重新输入");
                        continue;
                }
            }
        } catch (Exception e) {
            System.err.println("连接出现异常，Mode4测试结束，请检查登录配置");
            e.printStackTrace();
            scanner.close();
        }
    }

}
