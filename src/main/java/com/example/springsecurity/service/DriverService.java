package com.example.springsecurity.service;


import com.example.springsecurity.entity.Driver;

import java.util.List;

public interface DriverService {

    void add(Driver driver);

    Driver findById(int driverId);

    List<Driver> getAll();

    void delete(int driverId);

    void update(Driver driver);
}
