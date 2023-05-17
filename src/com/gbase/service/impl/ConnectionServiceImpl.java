package com.gbase.service.impl;

import com.gbase.dao.ConnectionDao;
import com.gbase.service.ConnectionService;

import java.sql.ResultSet;

public class ConnectionServiceImpl implements ConnectionService {
    private ConnectionDao connectionDao;

    public ResultSet checkConnection() {
        return connectionDao.checkConnection();
    }
}
