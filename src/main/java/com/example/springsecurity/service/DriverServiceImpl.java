package com.example.springsecurity.service;

import com.example.springsecurity.entity.Driver;
import com.example.springsecurity.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private DriverRepository driverRepository;

    @Override
    public void add(Driver driver) {
        driverRepository.save(driver);
    }

    @Override
    public Driver findById(int driverId) {
        return driverRepository.findOneById(driverId);
    }

    @Override
    public List<Driver> getAll(){
        return driverRepository.findAll();
    }

    @Override
    public void delete(int driverId){
        driverRepository.deleteById(driverId);
    }

    @Override
    public void update(Driver driver){
        driverRepository.save(driver);
    }
}
