package com.gbase.mode;

import com.gbase.Exceptions.LoadJarException;
import com.gbase.utils.CharacterSetUtils;
import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.JarUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName: Mode5.class
 * @Description: 本类集成了其他四个模式的测试，通过Mode5可覆盖连接、切换驱动、自定义SQL语句以及字符集配置情况。
 */
public class Mode2 {
    private ConnectionUtils connectionUtils;

    public Mode2() {
        System.out.println("---------------------------Mode2---------------------------");
        System.out.println();
        connectionUtils = new ConnectionUtils();
        label1:
        while (true) {
            List<String> jars = JarUtils.getAllJars();
            for (int i = 0; i < jars.size(); i++) {
                System.out.println(i + 1 + ". " + jars.get(i));
            }
            System.out.println();
            System.out.println("请输入以上驱动前的数字以导入程序中自带的数据库驱动，退出请输入0：");
            Scanner scanner = new Scanner(System.in);
            String jarsInput = scanner.nextLine();
            System.out.println();
            if (jarsInput.equals("0")) {
                scanner.close();
                break label1;
                //判断输入是否合法，需要满足为数字串且大小在扫描到的驱动数量内
            } else if (!jarsInput.equals("")&&ConnectionUtils.isNumber(jarsInput) && Integer.valueOf(jarsInput) <= jars.size() && Integer.valueOf(jarsInput) > 0) {
                connectionUtils.setJarName(jars.get(Integer.valueOf(jarsInput) - 1));
                try {
                    connectionUtils.init();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    continue;
                }
                try (Connection connection = connectionUtils.establishConnection()) {
                    label2:
                    while (connection != null) {
                        System.out.println("-----1.进行字符集测试-----2.进行sql测试-----3.返回驱动选择菜单-----0.退出程序-----");
                        System.out.print("请输入所选择操作前的序号：");
                        String sqlInput = scanner.nextLine();
                        System.out.println();
                        switch (sqlInput) {
                            case "1":
                                CharacterSetUtils.getCharacterSetInCluster(connection);
                                System.out.println("请输入要进行插入指定编码语句测试的表名：");
                                String tableName = scanner.nextLine();
                                System.out.println();
                                System.out.println("请输入要进行插入指定语句测试所使用的编码：");
                                String code = scanner.nextLine();
                                System.out.println();
                                try {
                                    CharacterSetUtils.insertChosenCode(connection, code, tableName);
                                    continue;
                                } catch (SQLException e) {
                                    System.err.println("返回上级菜单");
                                    continue;
                                }
                            case "2":
                                boolean flag_tmp = true;
                                while (flag_tmp) {
                                    System.out.println("请输入要执行的SQl语句,或输入1返回切换驱动连接测试，输入0退出程序：");
                                    String sql = scanner.nextLine();
                                    System.out.println();
                                    if (sql.equals("1")) {
                                        break label2;
                                    } else if (sql.equals("0")) {
                                        break label1;
                                    }
                                    try {
                                        SqlUtils.sqlPretreat(sql, connection);
                                        flag_tmp = false;
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        System.err.println("sql语句输入错误，请重新输入");
                                    }
                                }
                                System.out.println();
                                continue;
                            case "3":
                                break label2;
                            case "0":
                                connection.close();
                                break label1;
                            default:
                                System.err.println("指令输入错误，请重新输入");
                        }
                    }
                } catch (Exception e) {
                    if (e instanceof LoadJarException) {
                        continue;
                    }
                    System.err.println("连接出现异常，Mode5测试结束，请检查登录配置");
                    e.printStackTrace();
                    scanner.close();
                    break label1;
                }
            } else {
                System.err.println("指令输入错误");
            }
        }
    }
}
