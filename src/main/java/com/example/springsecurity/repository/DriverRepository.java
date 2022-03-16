package com.example.springsecurity.repository;

import com.example.springsecurity.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Driver findOneById(int driverId);
}
