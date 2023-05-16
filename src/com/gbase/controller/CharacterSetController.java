package com.gbase.controller;

import com.gbase.service.CharacterSetService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CharacterSetController {
    private CharacterSetService characterSetService;

    public void getCharacterSetInCluster() throws SQLException {
        Map<String, String> map = characterSetService.getCharacterSetInCluster();
        for (Map.Entry<String, String> meta : map.entrySet()) {
            System.out.println(meta.getKey() + ":" + meta.getValue());
        }
    }

    public int loadFileIn(){
        return characterSetService.loadFileIn();
    }

    public ResultSet checkLoadedFile(){
        return characterSetService.checkLoadedFile();
    }
}
