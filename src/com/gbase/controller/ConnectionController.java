package com.gbase.controller;

import com.gbase.service.ConnectionService;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

public class ConnectionController {
    private ConnectionService connectionService;
    public void checkConnection(){
        ResultSet resultSet = connectionService.checkConnection();
    }
}
