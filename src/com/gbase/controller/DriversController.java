package com.gbase.controller;

import com.gbase.service.DriversService;

public class DriversController {
    private DriversService driversService;

    public void loadDrivers() {
        driversService.loadDrivers();
    }
}
