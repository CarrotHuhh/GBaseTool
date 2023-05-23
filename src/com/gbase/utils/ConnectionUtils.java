package com.gbase.utils;

import com.gbase.Exceptions.LoadJarException;

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
    public static final String PROPERTIES_PATH = "./connection.properties";
    public static final String EXTERNAL_JAR_PATH = "./jar/";
    private Properties properties = new Properties();
    private String driver = null;
    private String url = null;
    private String user = null;
    private String password = null;
    private String jarName = null;

    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    public void init() throws Exception {
        File file = new File(PROPERTIES_PATH);
        try (InputStream inputStream = Files.newInputStream(file.toPath());
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            this.properties.load(reader);
            if (this.driver == null) {
                this.driver = this.properties.getProperty("driver");
            }
            loadProperties();
            System.out.println("配置文件读取完毕");
        } catch (IOException e) {
            System.out.println("读取配置文件失败");
            e.printStackTrace();
        }
    }

    public Connection establishConnection() throws LoadJarException {
        if (JarUtils.getAllJars().contains(this.jarName)) {
            JarUtils.loadJar(this.jarName);
        }
        try {
            Class.forName(this.driver);
            System.out.println("加载驱动成功");
        } catch (ClassNotFoundException e) {
            throw new LoadJarException();
        }
        try {
            Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("创建连接成功");
            return connection;
        } catch (SQLException e) {
            System.out.println("创建连接失败");
            return null;
        }
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

    public void loadProperties() throws Exception {
        if (this.driver.split("\\.").length >= 2) {
            String driverName = this.driver.split("\\.")[1];
            System.out.println("所选择驱动为" + driverName + "驱动，进行对应配置加载");
            this.url = this.properties.getProperty("url-" + driverName);
            this.user = this.properties.getProperty("user-" + driverName);
            this.password = this.properties.getProperty("password-" + driverName);
        } else throw new LoadJarException();
    }
}