package com.example.springsecurity.service;

import com.example.springsecurity.entity.Line;
import com.example.springsecurity.repository.LineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LineServiceImpl implements LineService {

    private final LineRepository lineRepository;

    @Override
    public void add(Line line) {
        lineRepository.save(line);
    }

    @Override
    public Line findById(int id) {
        return lineRepository.findOneById(id);
    }

    @Override
    public List<Line> getAll(){
        return lineRepository.findAll();
    }

    @Override
    public void delete(int id){
        lineRepository.deleteById(id);
    }

    @Override
    public void update(Line line){
        lineRepository.save(line);
    }
}
