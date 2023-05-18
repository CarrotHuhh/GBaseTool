package com.gbase.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {
    private Properties properties = new Properties();
    private String driver = null;
    private Connection conn = null;
    private String url = null;
    private String user = null;
    private String password = null;
    private String sql = null;

    public void init() {
        File file = new File("./resource/connection.properties");
        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            this.properties.load(inputStream);
            System.out.println("配置文件读取完毕");
            this.url = this.properties.getProperty("url");
            this.user = this.properties.getProperty("user");
            this.password = this.properties.getProperty("password");
            this.driver = this.properties.getProperty("driver");
            this.sql = this.properties.getProperty("sql");
        } catch (IOException e) {
            System.out.println("读取配置文件失败");
            e.printStackTrace();
        }
    }

    public Connection establishConnection() throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password)) {
            System.out.println("创建连接成功");
            return connection;
        } catch (Exception e) {
            System.out.println("创建连接失败");
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
