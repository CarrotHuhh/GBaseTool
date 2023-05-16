package com.gbase.service.impl;

import com.gbase.dao.SqlDao;
import com.gbase.service.SqlService;

public class SqlServiceImpl implements SqlService {
    @Override
    public void sqlTest() {
        SqlDao sqlDao = new SqlDao();
        sqlDao.sqlTest();
    }
}
