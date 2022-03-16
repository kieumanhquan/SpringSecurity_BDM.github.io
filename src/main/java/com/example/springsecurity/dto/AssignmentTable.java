package com.example.springsecurity.dto;

import com.example.springsecurity.entity.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentTable {

    private Driver driver;

    private List<LineTurn> lineTurns;
}
