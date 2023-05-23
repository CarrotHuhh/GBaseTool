package com.gbase.mode;

import com.gbase.Exceptions.LoadJarException;
import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.JarUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName: Mode3.class
 * @Description: 本类用于测试不同JDBC驱动在客户端能否顺利运行,并可在连接后测试不同SQL语句的运行情况。
 */
public class Mode3 {
    private ConnectionUtils connectionUtils;

    public Mode3() {
        connectionUtils = new ConnectionUtils();
        label1:
        while (true) {
            System.out.println("Mode3测试开始，请从以下支持的JDBC驱动中选择所使用的驱动序号，退出请输入0：");
            List<String> jars = JarUtils.getAllJars();
            for (int i = 0; i < jars.size(); i++) {
                System.out.println(i + 1 + ". " + jars.get(i));
            }
            System.out.println();
            Scanner scanner = new Scanner(System.in);
            String jarsInput = scanner.nextLine();
            if (jarsInput.equals("0")) {
                scanner.close();
                break label1;
            } else if (isNumber(jarsInput) && Integer.valueOf(jarsInput) <= jars.size() && Integer.valueOf(jarsInput) > 0) {
                connectionUtils.setJarName(jars.get(Integer.valueOf(jarsInput) - 1));
                System.out.println("请输入驱动类名：");
                String driverInput = scanner.nextLine();
                connectionUtils.setDriver(driverInput);
                try {
                    connectionUtils.init();
                } catch (Exception e) {
                    System.out.println(e);
                    continue;
                }
                try (Connection connection = connectionUtils.establishConnection()){
                    label2:
                    while (true) {
                        System.out.println("该驱动连接数据库成功，输入1进行sql测试，输入2返回切换驱动连接测试，输入0退出程序：");
                        String sqlInput = scanner.nextLine();
                        switch (sqlInput) {
                            case "1":
                                boolean flag_tmp = true;
                                while (flag_tmp) {
                                    System.out.println("请输入要执行的SQl语句,或输入2返回切换驱动连接测试，输入0退出程序：");
                                    String sql = scanner.nextLine();
                                    if (sql.equals("2")) {
                                        break label2;
                                    } else if (sql.equals("0")) {
                                        break label1;
                                    }
                                    System.out.println("所执行SQL语句为：" + sql);
                                    try {
                                        flag_tmp = SqlUtils.sqlPretreat(sql, connection);
                                        flag_tmp = false;
                                    } catch (SQLException e) {
                                        System.out.println("sql语句输入错误，请重新输入");
                                    }
                                }
                                System.out.println();
                                break;
                            case "2":
                                break label2;
                            case "0":
                                connection.close();
                                break label1;
                            default:
                                System.out.println("指令输入错误，请重新输入，输入sqltest进行sql测试，输入back返回切换驱动连接测试，输入quit退出程序：");
                        }
                    }
                } catch (Exception e) {
                    if (e instanceof LoadJarException) {
                        continue;
                    }
                    System.out.println("连接出现异常，Mode3测试结束，请检查登录配置");
                    e.printStackTrace();
                    scanner.close();
                    break label1;
                }
            } else {
                System.out.println("指令输入错误，选择正确的驱动序号，退出请输入0：");
            }
        }
    }

    public boolean isNumber(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
