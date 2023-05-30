package com.gbase.mode;

import com.gbase.utils.ConnectionUtils;

import java.sql.Connection;

/**
 * @ClassName: Mode1.class
 * @Description: 本类用于测试客户端与集群连接是否正常，程序将自动读取connection.properties文件中配置与数据库进行连接。
 */
public class Mode1 {

    private ConnectionUtils connectionUtils;

    public Mode1() {
        System.out.println("----------------Mode2-----------------");
        System.out.println();
        // 建立连接
        connectionUtils = new ConnectionUtils();
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
