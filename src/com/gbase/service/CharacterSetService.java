package com.gbase.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CharacterSetService {
    public ResultSet getCharacterSetInCluster() throws SQLException;

    public int loadFileIn();

    public ResultSet checkLoadedFile();
}
