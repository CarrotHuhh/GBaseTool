package com.gbase.mode;

import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.util.Locale;
import java.util.Scanner;

public class Mode2 {
    private ConnectionUtils connectionUtils;
    public Mode2(){
        connectionUtils = new ConnectionUtils();
        connectionUtils.init();
        String sql = connectionUtils.getSql();
        try {
            Connection connection = connectionUtils.establishConnection();
            while(connection != null){
                System.out.println("请输入SQL语句，若要退出则输入close：");
                Scanner scanner = new Scanner(System.in);
                String SqlIn = scanner.nextLine();
                connectionUtils.setSql(SqlIn);
                sql = SqlIn;
                String sub = sql.toLowerCase().split(" ")[0];
                switch (sub) {
                    case "show":
                    case "select":
                        SqlUtils.printResultSet(SqlUtils.query(sql, connection),1);
                        break;
                    case "insert":
                        SqlUtils.insert(sql, connection);
                        break;
                    case "update":
                        SqlUtils.update(sql, connection);
                        break;
                    case "close": {
                        connection.close();
                        connection = null;
                        break;
                    }
                    default:
                        System.out.println("SQL语句输入错误，请重新输入：");
                }
            }






//            if (sql.trim().toLowerCase().startsWith("select")) {
//                //查询语句
//                SqlUtils.printResultSet(SqlUtils.query(sql,connection));
//            } else if (sql.trim().toLowerCase().startsWith("inser t")) {
//                //插入语句
//                SqlUtils.insert(sql,connection);
////                SqlUtils.insert(sql,connection);
//            } else if (sql.trim().toLowerCase().startsWith("update")) {
//                //更新语句
//            } else if (sql.trim().toLowerCase().startsWith("delete")) {
//                //删除语句
//            } else if (sql.trim().toLowerCase().startsWith("create table")) {
//                //创建表语句
//            } else if (sql.trim().toLowerCase().startsWith("drop table")) {
//                //删除表语句
//            } else if (sql.trim().toLowerCase().startsWith("alter table")) {
//                //修改表结构语句
//            } else {
//                //其他类型的语句
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
