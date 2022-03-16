package com.example.springsecurity.service;

import com.example.springsecurity.dto.AssigmentTableDto;
import com.example.springsecurity.dto.AssignmentDto;
import com.example.springsecurity.dto.AssignmentTable;
import com.example.springsecurity.dto.LineTurn;
import com.example.springsecurity.entity.Assignment;
import com.example.springsecurity.repository.AssignmentDao;
import com.example.springsecurity.util.CollectionUtil;
import com.example.springsecurity.util.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentDao assignmentDao;

    @Override
    public List<Assignment> getAll() {
        return assignmentDao.getAll();
    }

    @Override
    public String add(Assignment assignment) {
        List<Assignment> exitsAssignmentList = assignmentDao.findByDriverId(assignment.getDriver().getId());

        if (CollectionUtil.isNullOrEmpty(exitsAssignmentList)) {
            // Thêm mới
            if (assignment.getTurnNumber() <= 15) {
                assignmentDao.add(assignment);
                return "success";
            } else {
                return "over-15-turn";
            }
        } else {
            // update bảng phân công đã có trong hệ thống
            Assignment exitsAssignment = assignmentDao.findById(assignment.getDriver().getId(), assignment.getLine().getId());
            if (DataUtil.isEmptyOrNull(exitsAssignment)) {
                int turnSumCurrent = exitsAssignmentList.stream().mapToInt(Assignment::getTurnNumber).sum();
                if (turnSumCurrent + assignment.getTurnNumber() <= 15) {
                    assignmentDao.add(assignment);
                    return "success";
                } else {
                    return "over-15-turn";
                }
            } else {
                int turnSumCurrent = exitsAssignmentList.stream().mapToInt(Assignment::getTurnNumber).sum() - exitsAssignment.getTurnNumber();
                if (turnSumCurrent + assignment.getTurnNumber() <= 15) {
                    assignmentDao.update(assignment);
                    return "override-assignment-exits";
                } else {
                    return "over-15-turn";
                }
            }
        }
    }

    @Override
    public String update(AssignmentDto assignmentDto) {
        List<Assignment> exitsAssignmentList = assignmentDao.findByDriverId(assignmentDto.getDriverId());
        Assignment exitsAssignment = assignmentDao.findById(assignmentDto.getDriverId(), assignmentDto.getLineId());
        int turnSumCurrent = exitsAssignmentList.stream().mapToInt(Assignment::getTurnNumber).sum() - exitsAssignment.getTurnNumber();
        if (turnSumCurrent + assignmentDto.getTurnNumber() <= 15) {
            exitsAssignment.setTurnNumber(assignmentDto.getTurnNumber());
            assignmentDao.update(exitsAssignment);
            return "success";
        } else {
            return "over-15-turn";
        }
    }

    @Override
    public void delete(int driverId, int lineId) {
        assignmentDao.delete(driverId, lineId);
    }

    @Override
    public Assignment findById(int driverId, int lineId) {
        return assignmentDao.findById(driverId, lineId);
    }

    @Override
    public List<AssignmentTable> findByDriverName(String driverName) {
        return getAssigmentTable(assignmentDao.findByDriverName(driverName));
    }

    @Override
    public List<AssignmentTable> sortByNameDriver() {
        List<AssignmentTable> assignmentTableList = getAssigmentTable(assignmentDao.getAll());

        assignmentTableList.sort((o1, o2) -> {
            String[] ten1 = o1.getDriver().getFullName().split("\\s+");
            String[] ten2 = o2.getDriver().getFullName().split("\\s+");
            if (ten1[ten1.length - 1].equalsIgnoreCase(ten2[ten2.length - 1])) {
                return o1.getDriver().getFullName().compareToIgnoreCase(o2.getDriver().getFullName());
            } else {
                return ten1[ten1.length - 1].compareToIgnoreCase(ten2[ten2.length - 1]);
            }
        });
        return assignmentTableList;
    }

    @Override
    public List<AssignmentTable> sortByTurnNumber() {
        List<AssignmentTable> assignmentTableList = getAssigmentTable(assignmentDao.getAll());
        assignmentTableList.sort((o1, o2) -> o2.getLineTurns().size() - o1.getLineTurns().size());
        return assignmentTableList;
    }


    @Override
    public List<AssignmentTable> getAssigmentTable(List<Assignment> assignmentList) {
        List<AssignmentTable> assignmentTableList = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            boolean checkExits = false;
            for (AssignmentTable assignmentTable : assignmentTableList) {
                if (assignmentTable.getDriver().getId() == assignment.getDriver().getId()) {
                    assignmentTable.getLineTurns().add(new LineTurn(assignment.getLine(), assignment.getTurnNumber()));
                    checkExits = true;
                    break;
                }
            }
            if (!checkExits) {
                List<LineTurn> lineTurns = new ArrayList<>();
                lineTurns.add(new LineTurn(assignment.getLine(), assignment.getTurnNumber()));
                assignmentTableList.add(new AssignmentTable(assignment.getDriver(), lineTurns));
            }
        }
        return assignmentTableList;
    }

    @Override
    public List<AssigmentTableDto> distanceStatistics() {
        List<AssignmentTable> assignmentTableList = getAssigmentTable(getAll());
        List<AssigmentTableDto> assigmentTableDtoList = new ArrayList<>();
        for (AssignmentTable assignmentTable : assignmentTableList) {
            assigmentTableDtoList.add(new AssigmentTableDto(assignmentTable.getDriver(), assignmentTable.getLineTurns().stream().mapToDouble(LineTurn::getDistance).sum()));
        }
        return assigmentTableDtoList;
    }
}
