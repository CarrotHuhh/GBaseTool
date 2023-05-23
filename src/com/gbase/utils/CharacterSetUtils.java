package com.gbase.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class CharacterSetUtils {
    public static void getCharacterSetInCluster(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            HashMap<String, String> map = new HashMap<>();
            ResultSet resultSet = stmt.executeQuery("show variables like '%character_set%'");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + ": " + resultSet.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
