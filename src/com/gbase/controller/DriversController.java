package com.gbase.controller;

import com.gbase.service.DriversService;

import java.sql.Connection;
import java.util.Properties;

public class DriversController {
    private DriversService driversService;
    public void loadDrivers(){
        driversService.loadDrivers();
    }
}
