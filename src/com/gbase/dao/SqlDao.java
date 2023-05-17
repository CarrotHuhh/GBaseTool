package com.gbase.dao;

import com.gbase.util.Preparations;

public class SqlDao {
    private Preparations preparations = new Preparations();

    public void sqlTest() {
        preparations.init();
        try {
            Class.forName(preparations.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("注册驱动失败");
            e.printStackTrace();
        }
    }
}
