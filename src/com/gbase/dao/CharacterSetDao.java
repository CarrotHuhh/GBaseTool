package com.gbase.dao;

import com.gbase.util.Preparations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CharacterSetDao {
    private Preparations preparations = new Preparations();
    public ResultSet getCharacterSetInCluster() throws SQLException {
        preparations.init();
        try {
            Class.forName(preparations.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("注册驱动失败");
            e.printStackTrace();
        }
        Connection conn = preparations.establishConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show variables like '%character_set'");
        conn.close();
        return rs;
    }

    public int loadFileIn(String filename) {
        return 0;
    }

    public ResultSet checkLoadedFile(String filename) {
        return null;
    }
}
