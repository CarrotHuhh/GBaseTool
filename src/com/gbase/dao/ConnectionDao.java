package com.gbase.dao;

import com.gbase.util.Preparations;

import java.sql.ResultSet;

public class ConnectionDao {
    public ResultSet checkConnection(){
        Preparations preparations = new Preparations();
        preparations.init();
        try {
            Class.forName(preparations.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("注册驱动失败");
            e.printStackTrace();
        }
        return null;
    }
}
