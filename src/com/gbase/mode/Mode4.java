package com.gbase.mode;

import com.gbase.utils.CharacterSetUtils;
import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static com.gbase.utils.CharacterSetUtils.insertChosenCode;

/**
 * @ClassName: Mode4.class
 * @Description: 本类用于测试数据库对不同SQL语句的处理情况，以及字符集相关配置是否正常。
 */
public class Mode4 {
    private ConnectionUtils connectionUtils;

    public Mode4() {
        connectionUtils = new ConnectionUtils();
        Scanner scanner = new Scanner(System.in);
        try {
            connectionUtils.init();
        } catch (Exception e) {
            System.out.println(e);
        }
        try (Connection connection = connectionUtils.establishConnection()) {
            //进入测试模式一级界面，引导用户进行测试模式选择
            label:
            while (connection != null) {
                System.out.println("请输入1进行sql测试，请输入2进行字符集测试，输入0退出程序：");
                String sqlInput = scanner.nextLine();
                switch (sqlInput) {
                    case "0":
                        break;
                    //进行SQL测试，使用flag_tmp标记是否继续循环，flag_tmp在SQL语句执行完成后失效，循环退出
                    case "1":
                        boolean flag_tmp = true;
                        while (flag_tmp) {
                            System.out.println("请输入要执行的SQl语句，输入0退出程序：");
                            String sql = scanner.nextLine();
                            if (sql.equals("0")) {
                                break label;
                            }
                            System.out.println("所执行SQL语句为：" + sql);
                            try {
                                SqlUtils.sqlPretreat(sql, connection);
                                flag_tmp = false;
                            } catch (SQLException e) {
                                System.out.println("sql语句输入错误，请重新输入");
                            }
                        }
                        System.out.println();
                        break;
                    //进行字符集测试
                    case "2":
                        CharacterSetUtils.getCharacterSetInCluster(connection);
                        System.out.println("请输入要进行插入指定编码语句测试的表名");
                        String tableName = scanner.nextLine();
                        System.out.println("请输入要进行插入指定语句测试所使用的编码");
                        String code = scanner.nextLine();
                        try {
                            insertChosenCode(connection, code, tableName);
                            break;
                        } catch (SQLException e) {
                            continue;
                        }
                    default:
                        System.out.println("指令输入错误，请重新输入，输入1进行sql测试,输入0退出程序：");
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
