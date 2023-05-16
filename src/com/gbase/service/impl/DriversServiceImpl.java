package com.gbase.service.impl;

import com.gbase.dao.DriversDao;
import com.gbase.service.DriversService;

public class DriversServiceImpl implements DriversService {
    @Override
    public void loadDrivers() {
        DriversDao driversDao = new DriversDao();
        driversDao.loadDrivers();
    }
}
