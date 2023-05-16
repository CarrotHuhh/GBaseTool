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
    String driver = null;
    Connection conn = null;
    String url = null;
    String user = null;
    String password = null;

    public void checkConnection(){
    }
}
