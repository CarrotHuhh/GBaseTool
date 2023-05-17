package com.gbase.controller;

import com.gbase.service.SqlService;

public class SqlController {
    private SqlService sqlService;
    public void testSql(){
        sqlService.sqlTest();
    }
}
