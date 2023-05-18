package com.gbase.mode;

import com.gbase.service.CharacterSetService;
import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;

public class Mode4 {
    private ConnectionUtils connectionUtils;
    public Mode4(){
        connectionUtils = new ConnectionUtils();
        connectionUtils.init();
        try(Connection connection = connectionUtils.establishConnection()){
            if(connection!=null){
                System.out.println("所执行SQL语句为："+connectionUtils.getSql());
                SqlUtils.insert(connectionUtils.getSql(),connection);
//                SqlUtils.update(connectionUtils.getSql(),connection);
//                SqlUtils.printResultSet(SqlUtils.query(connectionUtils.getSql(),connection),1);
                CharacterSetService.getCharacterSetInCluster(connectionUtils,connection);
                SqlUtils.insertChosenCode(connection,"UTF-16");
            }
        }catch (Exception e){
            System.out.println("连接出现异常，Mode1测试完毕，请检查登录配置");
            e.printStackTrace();
        }
    }
}
