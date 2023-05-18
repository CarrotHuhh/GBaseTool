package com.gbase.service;

import com.gbase.dao.CharacterSetDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class CharacterSetService {
    private CharacterSetDao characterSetDao = new CharacterSetDao();

    public static void getCharacterSetInCluster(Connection connection) {
        HashMap<String, String> map = CharacterSetDao.getCharacterSetInCluster(connection);
        System.out.println("集群中字符集配置为：");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void loadFileIn() {
        String filename = null;
    }

    public ResultSet checkLoadedFile() {
        String filename = null;
        return characterSetDao.checkLoadedFile(filename);
    }

    public void checkCharset() {
        loadFileIn();
    }
}
