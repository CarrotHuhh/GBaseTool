package com.gbase;
import com.gbase.controller.ConnectionController;

public class Main {
    public static void main(String[] args) throws Exception {
        ConnectionController connectionTest = null;
        try {
            connectionTest = new ConnectionController();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (connectionTest != null) {
            connectionTest.query();
        }
    }
}
