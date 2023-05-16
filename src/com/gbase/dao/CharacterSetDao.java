package com.gbase.dao;

import com.gbase.util.Preparations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CharacterSetDao {
    public ResultSet getCharacterSetInCluster() throws SQLException {
        Preparations preparations = new Preparations();
        try {
            Class.forName(preparations.getDriver());
        } catch (ClassNotFoundException e){
            System.out.println("注册驱动失败");
            e.printStackTrace();
        }
        Connection conn = preparations.establishConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show variables like '%character_set'");
        conn.close();
        return rs;
    }
}
