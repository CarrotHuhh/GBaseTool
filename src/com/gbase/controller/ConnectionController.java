package com.gbase.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

public class ConnectionController {
    Properties properties = new Properties();
    String Driver = "com.gbase.jdbc.Driver";
    Connection conn = null;
    String url = null;
    String user = null;
    String password = null;

    public ConnectionController() throws Exception {
        File file = new File("./resource/connection.properties");
        InputStream inputStream = Files.newInputStream(file.toPath());
        this.properties.load(inputStream);
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
        this.conn = DriverManager.getConnection(url, user, password);
    }

    public void query() throws Exception {
        Class.forName(Driver);
        String sql = this.properties.getProperty("sql");
        ResultSet rs = this.conn.createStatement().executeQuery(sql);
        while (rs.next()){
            System.out.println(rs.getString(1));
        }
    }

    public void checkNetwork(){

    }
}
