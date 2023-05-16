package com.gbase.controller;

import com.gbase.service.CharacterSetService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CharacterSetController {
    private CharacterSetService characterSetService;

    public void getCharacterSetInCluster() throws SQLException {
        ResultSet resultSet = characterSetService.getCharacterSetInCluster();
        System.out.println("集群中字符集配置为：");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + ": " + resultSet.getString(2));
        }
    }

    public int loadFileIn() {
        return characterSetService.loadFileIn();
    }

    public ResultSet checkLoadedFile() {
        return characterSetService.checkLoadedFile();
    }
}
