package com.gbase.controller;

import com.gbase.service.ConnectionService;

import java.sql.ResultSet;

public class ConnectionController {
    private ConnectionService connectionService;

    public void checkConnection() {
        ResultSet resultSet = connectionService.checkConnection();
    }
}
