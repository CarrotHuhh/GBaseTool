package com.gbase.service;

import com.gbase.dao.DriversDao;

public class DriversService {
    public void loadDrivers() {
        DriversDao driversDao = new DriversDao();
        driversDao.loadDrivers();
    }
}
