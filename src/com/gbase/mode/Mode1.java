package com.gbase.mode;

import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.JarUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName: Mode1.class
 * @Description: 本类用于测试客户端与集群连接是否正常，程序将自动读取connection.properties文件中配置与数据库进行连接。
 */
public class Mode1 {

    private ConnectionUtils connectionUtils;

    public Mode1() {
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
            } else if (!jarsInput.equals("") && ConnectionUtils.isNumber(jarsInput) && Integer.valueOf(jarsInput) <= jars.size() && Integer.valueOf(jarsInput) > 0) {
                connectionUtils.setJarName(jars.get(Integer.valueOf(jarsInput) - 1));
                try {
                    // 读取配置文件
                    connectionUtils.init();
                } catch (Exception e) {
                    System.out.println(e);
                }
                try (Connection connection = connectionUtils.establishConnection()) {
                    // 测试数据库连接
                    if (connection != null)
                        System.out.println("---------连接正常，Mode1测试完毕---------");
                } catch (Exception e) {
                    // 捕获连接异常并输出提示信息
                    System.out.println("连接出现异常，Mode1测试完毕，请检查登录配置");
                    e.printStackTrace();
                }
            }
        }
    }
}
