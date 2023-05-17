package com.gbase.service;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CharacterSetService {
    public ResultSet getCharacterSetInCluster() throws SQLException;

    public int loadFileIn();

    public ResultSet checkLoadedFile();
}
