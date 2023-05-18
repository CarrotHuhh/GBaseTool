package com.gbase.mode;

import com.gbase.utils.ConnectionUtils;
import com.gbase.utils.SqlUtils;

import java.sql.Connection;
import java.util.Scanner;

public class Mode2 {
    private ConnectionUtils connectionUtils;
    public Mode2(){
        connectionUtils = new ConnectionUtils();
        connectionUtils.init();

        try(Connection connection = connectionUtils.establishConnection()) {
            Scanner scanner = new Scanner(System.in);
            String sqlin = scanner.nextLine();
            System.out.println("请输入SQL语句：");
            String sql = connectionUtils.setSql(sqlin);
//            sql = String.valueOf(sqlin);
//            System.out.println( sql );
            if (sql.trim().toLowerCase().startsWith("select")) {
                //查询语句
                SqlUtils.printResultSet(SqlUtils.query(sql,connection));
            } else if (sql.trim().toLowerCase().startsWith("insert")) {
                //插入语句
                System.out.println(SqlUtils.insert(sql,connection));
//                SqlUtils.insert(sql,connection);
            } else if (sql.trim().toLowerCase().startsWith("update")) {
                //更新语句
            } else if (sql.trim().toLowerCase().startsWith("delete")) {
                //删除语句
            } else if (sql.trim().toLowerCase().startsWith("create table")) {
                //创建表语句
            } else if (sql.trim().toLowerCase().startsWith("drop table")) {
                //删除表语句
            } else if (sql.trim().toLowerCase().startsWith("alter table")) {
                //修改表结构语句
            } else {
                //其他类型的语句
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
