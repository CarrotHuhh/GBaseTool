package com.gbase.mode;

import com.gbase.utils.ConnectionUtils;

import java.sql.Connection;

/**
 * @ClassName: Mode1.class
 * @Description: 本类用于测试客户端与集群连接是否正常
 */
public class Mode1 {
    private ConnectionUtils connectionUtils;

    public Mode1() {
        connectionUtils = new ConnectionUtils();
        connectionUtils.init();
        try (Connection connection = connectionUtils.establishConnection()) {
            if (connection != null)
                System.out.println("连接正常，Mode1测试完毕");
        } catch (Exception e) {
            System.out.println("连接出现异常，Mode1测试完毕，请检查登录配置");
            e.printStackTrace();
        }
    }
}