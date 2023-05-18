package com.gbase.service;

import com.gbase.dao.CharacterSetDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CharacterSetService {
    private CharacterSetDao characterSetDao = new CharacterSetDao();

    public HashMap<String, String> getCharacterSetInCluster() throws SQLException {
        return characterSetDao.getCharacterSetInCluster();
    }

    public int loadFileIn() {
        String filename = null;
        return characterSetDao.loadFileIn(filename);
    }

    public ResultSet checkLoadedFile() {
        String filename = null;
        return characterSetDao.checkLoadedFile(filename);
    }
}
