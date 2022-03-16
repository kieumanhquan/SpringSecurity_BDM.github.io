package com.example.springsecurity.service;

import com.example.springsecurity.entity.Line;

import java.util.List;

public interface LineService {
    void add(Line line);

    Line findById(int id);

    List<Line> getAll();

    void delete(int id);

    void update(Line line);
}
