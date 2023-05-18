package com.gbase.service;

import com.gbase.dao.ConnectionDao;

import java.sql.ResultSet;

public class ConnectionService {
    private ConnectionDao connectionDao;

    public ResultSet checkConnection() {
        return connectionDao.checkConnection();
    }
}
