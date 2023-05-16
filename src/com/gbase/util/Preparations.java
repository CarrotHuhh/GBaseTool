package com.gbase.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Preparations {
    private Properties properties = new Properties();
    private String driver = null;
    private Connection conn = null;
    private String url = null;
    private String user = null;
    private String password = null;
    private String sql = null;

    public Preparations(){
        File file = new File("./resource/connection.properties");
        try {
            InputStream inputStream = Files.newInputStream(file.toPath());
            this.properties.load(inputStream);
            inputStream.close();
        }catch (IOException e){
            System.out.println("读取配置文件失败");
            e.printStackTrace();
        }
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
        this.driver = properties.getProperty("driver");
        this.sql = this.properties.getProperty("sql");
    }

    public Connection establishConnection(){
        try {
            return DriverManager.getConnection(this.url);
        }catch (Exception e){
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
