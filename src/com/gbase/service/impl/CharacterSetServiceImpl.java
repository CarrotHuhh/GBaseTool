package com.gbase.service.impl;

import com.gbase.dao.CharacterSetDao;
import com.gbase.service.CharacterSetService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CharacterSetServiceImpl implements CharacterSetService {
    private CharacterSetDao characterSetDao;

    @Override
    public ResultSet getCharacterSetInCluster() throws SQLException {
        return characterSetDao.getCharacterSetInCluster();
    }

    @Override
    public int loadFileIn() {
        String filename = null;
        return characterSetDao.loadFileIn(filename);
    }

    @Override
    public ResultSet checkLoadedFile() {
        String filename = null;
        return characterSetDao.checkLoadedFile(filename);
    }
}
