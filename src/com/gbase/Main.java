package com.gbase;
import com.gbase.ConnectionTest;

public class Main {
    public static void main(String[] args) throws Exception {
        ConnectionTest connectionTest = null;
        try {
            connectionTest = new ConnectionTest();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (connectionTest != null) {
            connectionTest.query();
        }
    }
}
