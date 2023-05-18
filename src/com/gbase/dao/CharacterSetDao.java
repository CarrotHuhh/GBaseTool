package com.gbase.dao;

import com.gbase.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class CharacterSetDao {

    public static HashMap<String, String> getCharacterSetInCluster(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
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

    public ResultSet checkLoadedFile(String filename) {
        return null;
    }
}
