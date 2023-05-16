package com.gbase.service.impl;

import com.gbase.dao.CharacterSetDao;
import com.gbase.service.CharacterSetService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CharacterSetServiceImpl implements CharacterSetService {
    private CharacterSetDao characterSetDao;

    @Override
    public Map<String, String> getCharacterSetInCluster() throws SQLException {
        ResultSet resultSet = characterSetDao.getCharacterSetInCluster();
        Map<String, String> map = new HashMap<>();
        while (resultSet.next()){
            map.put(resultSet.getString(1),resultSet.getString(2));
        }
        return map;
    }

    @Override
    public int loadFileIn() {
        return 0;
    }

    @Override
    public ResultSet checkLoadedFile() {
        return null;
    }
}
