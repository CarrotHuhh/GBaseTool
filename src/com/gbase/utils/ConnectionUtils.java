package com.gbase.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {
    private Properties properties = new Properties();
    private String driver = null;
    private String url = null;
    private String user = null;
    private String password = null;
    private String sql = null;

    public void init() {
        File file = new File("./resource/connection.properties");
        try (InputStream inputStream = Files.newInputStream(file.toPath());
             InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");) {
            this.properties.load(reader);
            System.out.println("配置文件读取完毕");
            if(this.driver==null){
                this.driver = this.properties.getProperty("driver");
            }
            this.sql = this.properties.getProperty("sql");
            if (this.driver.equals("com.gbase.jdbc.Driver")) {
                System.out.println("所选择驱动为GBase8a驱动，进行对应配置加载");
                this.url = this.properties.getProperty("url-gbase");
                this.user = this.properties.getProperty("user-gbase");
                this.password = this.properties.getProperty("password-gbase");
            } else if (this.driver.equals("com.mysql.cj.jdbc.Driver")) {
                System.out.println("所选择驱动为MySQL驱动，进行对应配置加载");
                this.url = this.properties.getProperty("url-mysql");
                this.user = this.properties.getProperty("user-mysql");
                this.password = this.properties.getProperty("password-mysql");
            }
        } catch (IOException e) {
            System.out.println("读取配置文件失败");
            e.printStackTrace();
        }
    }

    public Connection establishConnection() {
        try {
            System.out.println("注册驱动成功");
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            System.out.println("注册驱动失败");
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("创建连接成功");
            return connection;
        } catch (SQLException e) {
            System.out.println("创建连接失败");
            e.printStackTrace();
            return null;
        }
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
