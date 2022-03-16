package com.example.springsecurity.repository;

import com.example.springsecurity.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line,Integer> {
    Line findOneById(int id);
}
