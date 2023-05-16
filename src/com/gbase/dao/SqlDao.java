package com.gbase.dao;

import com.gbase.util.Preparations;

public class SqlDao {
    public void sqlTest(){
        Preparations preparations = new Preparations();
        preparations.init();
        try {
            Class.forName(preparations.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("注册驱动失败");
            e.printStackTrace();
        }
    }
}
