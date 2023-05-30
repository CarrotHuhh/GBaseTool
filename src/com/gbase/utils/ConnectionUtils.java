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

/**
 * @ClassName: ConnectionUtils.class
 * @Description: 本类为连接配置类，封装了用户与数据库连接的相关配置以及常用方法。
 */
public class ConnectionUtils {
    //配置文件的相对路径
    public static final String PROPERTIES_PATH = "dist/connection.properties";
    //导入jar包目录的相对路径
    public static final String EXTERNAL_JAR_PATH = "dist/jar/";
    private String driver = null;
    private String url = null;
    private String user = null;
    private String password = null;
    //jar包的文件名
    private String jarName = null;
    private Properties properties = new Properties();


    /**
     * @throws Exception 由loadProperties抛出的LoadJarException异常，暂不做处理，留待具体业务流程处理
     * @Description: 本方法用于创建ConnectionUtil对象之后的初始化工作，读取配置文件中的变量配置，为成员变量赋值
     */
    public void init() throws LoadJarException {
        File file = new File(PROPERTIES_PATH);
        try (InputStream inputStream = Files.newInputStream(file.toPath());
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            this.properties.load(reader);
            if (this.jarName == null) {
                this.jarName = this.properties.getProperty("jarName");
            }
            this.driver = JarUtils.getJDBCDriverContents().get(this.jarName.split("-")[0]);
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

    /**
     * @return Connection，与数据库的连接
     * @throws LoadJarException 加载驱动时可能抛出LoadJarException异常，暂不做处理，留待具体业务流程处理
     * @Description: 本方法用于加载驱动以及创建与数据库的连接
     */
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

    /**
     * @throws LoadJarException 抛出加载驱动包异常，暂不做处理，留待具体业务进行处理
     * @Description: 本方法用于根据驱动类名加载配置文件中对应数据库的配置
     */
    public void loadProperties() throws LoadJarException {
        if (this.driver.split("\\.").length >= 2) {
            String driverName = this.driver.split("\\.")[1];
            System.out.println("所选择驱动为" + driverName + "驱动，进行对应配置加载");
            this.url = this.properties.getProperty("url-" + driverName);
            this.user = this.properties.getProperty("user-" + driverName);
            this.password = this.properties.getProperty("password-" + driverName);
        } else throw new LoadJarException();
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

    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }
}