package com.gbase.controller;

import java.sql.Connection;
import java.util.Properties;

public class SqlController {
    Properties properties = new Properties();
    String Driver = "com.gbase.jdbc.Driver";
    Connection conn = null;
    String url = null;
    String user = null;
    String password = null;
}
