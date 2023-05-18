package com.gbase.mode;

import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;

public class Mode1 {
    private ConnectionUtils connectionUtils;
    public Mode1(){
        connectionUtils = new ConnectionUtils();
        connectionUtils.init();
        try(Connection connection = connectionUtils.establishConnection()){
            SqlUtils.printResultSet(SqlUtils.query("show tables",connection));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
