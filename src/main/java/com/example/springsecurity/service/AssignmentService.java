package com.example.springsecurity.service;

import com.example.springsecurity.dto.AssigmentTableDto;
import com.example.springsecurity.dto.AssignmentDto;
import com.example.springsecurity.dto.AssignmentTable;
import com.example.springsecurity.entity.Assignment;

import java.util.List;


public interface AssignmentService {

    List<Assignment> getAll();

    String add(Assignment assignment);

    String update(AssignmentDto assignmentDto);

    void delete(int driverId, int lineId);

    Assignment findById(int driverId, int lineId);

    List<AssignmentTable> findByDriverName(String driverName);

    List<AssignmentTable> sortByNameDriver();

    List<AssignmentTable> sortByTurnNumber();

    List<AssignmentTable> getAssigmentTable(List<Assignment> assignmentList);

    List<AssigmentTableDto> distanceStatistics();
}
