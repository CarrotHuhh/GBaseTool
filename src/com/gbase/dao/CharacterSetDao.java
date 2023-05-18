package com.gbase.dao;

import com.gbase.util.Preparations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class CharacterSetDao {
    private Preparations preparations = new Preparations();

    public HashMap<String, String> getCharacterSetInCluster() throws SQLException {
        preparations.init();
        try {
            Class.forName(preparations.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("注册驱动失败");
            e.printStackTrace();
        }
        try (Connection conn = preparations.establishConnection();
             Statement stmt = conn.createStatement()) {
            HashMap<String, String> map = new HashMap<>();
            ResultSet resultSet = stmt.executeQuery("show variables like '%character_set%'");
            while (resultSet.next()) {
                map.put(resultSet.getString(1), resultSet.getString(2));
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int loadFileIn(String filename) {
        return 0;
    }

    public ResultSet checkLoadedFile(String filename) {
        return null;
    }
}
