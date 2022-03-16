package com.example.springsecurity.controller;

import com.example.springsecurity.dto.AssignmentDto;
import com.example.springsecurity.entity.Assignment;
import com.example.springsecurity.entity.Driver;
import com.example.springsecurity.entity.Line;
import com.example.springsecurity.service.AssignmentService;
import com.example.springsecurity.service.DriverService;
import com.example.springsecurity.service.LineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    private final DriverService driverService ;

    private final LineService lineService;

    @RequestMapping(value = "/list-assignments", method = RequestMethod.GET)
    public String showAssignments(ModelMap model) {
        model.put("assignmentTables", assignmentService.getAssigmentTable(assignmentService.getAll()));
        return "assignment-list";
    }

    @RequestMapping(value = "/list-assignment-tables", method = RequestMethod.GET)
    public String showAssignmentTables(ModelMap model) {
        model.put("assignmentTables", assignmentService.distanceStatistics());
        return "assignmentTable-list";
    }

    @RequestMapping(value = "/add-assignment", method = RequestMethod.GET)
    public String showAddAssignmentPage(ModelMap model) {
        model.addAttribute("assignment", new AssignmentDto());
        model.put("drivers", driverService.getAll());
        model.put("lines", lineService.getAll());
        return "assignment-form";
    }

    @RequestMapping(value = "/delete-assignment", method = RequestMethod.GET)
    public String deleteAssignment(@RequestParam int driverId, @RequestParam int lineId) {
        assignmentService.delete(driverId, lineId);
        return "redirect:/list-assignments";
    }

    @RequestMapping(value = "/update-assignment", method = RequestMethod.GET)
    public String showUpdateAssignmentPage(@RequestParam int driverId, @RequestParam int lineId, ModelMap model) {
        model.put("assignment", new AssignmentDto());
        List<Driver> driverList = new ArrayList<>();
        driverList.add(driverService.findById(driverId));
        List<Line> lineList = new ArrayList<>();
        lineList.add(lineService.findById(lineId));
        model.put("drivers", driverList);
        model.put("lines", lineList);
        return "assignment-form";
    }

    @RequestMapping(value = "/update-assignment", method = RequestMethod.POST)
    public String updateAssignment(@RequestParam int driverId, @RequestParam int lineId, @Valid AssignmentDto assignmentDto) {
        String message = assignmentService.update(assignmentDto);
        System.out.println(message);
        if(Objects.equals(message, "success")){
            return "redirect:/list-assignments";
        }
        return "redirect:/response"+"?message="+message;
    }

    @RequestMapping(value = "/response", method = RequestMethod.GET)
    public String showResponse(@RequestParam String message,ModelMap model) {
        model.put("message", message);
        return "response";
    }

    @RequestMapping(value = "/add-assignment", method = RequestMethod.POST)
    public String addAssignment(@Valid AssignmentDto assignmentDto) {
        Driver driver = driverService.findById(assignmentDto.getDriverId());
        Line line = lineService.findById(assignmentDto.getLineId());
        Assignment assignment = new Assignment(driver,line, assignmentDto.getTurnNumber());
        String message = assignmentService.add(assignment);
        System.out.println(message);
        if(Objects.equals(message, "success")){
            return "redirect:/list-assignments";
        }
        return "redirect:/response"+"?message="+message;
    }

    @RequestMapping(value = "/list-assignment-drivers", method = RequestMethod.GET)
    public String showAssignmentsByDriverName(ModelMap model, @RequestParam String name) {
        model.put("assignmentTables", assignmentService.findByDriverName(name));
        return "assignment-list";
    }

    @RequestMapping(value = "/list-filter-assignments", method = RequestMethod.GET)
    public String sortAssignments(ModelMap model, @RequestParam String filter) {
        if(filter.equals("driver-name")){
            model.put("assignmentTables", assignmentService.sortByNameDriver());
        }
        else {
            model.put("assignmentTables", assignmentService.sortByTurnNumber());
        }
        return "assignment-list";
    }
}
