package lk.ijse.crop_monitoring_systembackend.service.impl;

import lk.ijse.crop_monitoring_systembackend.dao.*;
import lk.ijse.crop_monitoring_systembackend.dto.FieldDTO;
import lk.ijse.crop_monitoring_systembackend.dto.LogDTO;
import lk.ijse.crop_monitoring_systembackend.dto.StaffLogDTO;
import lk.ijse.crop_monitoring_systembackend.entity.*;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.LogService;
import lk.ijse.crop_monitoring_systembackend.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LogServiceIMPL implements LogService {
    @Autowired
    private LogDAO logDAO;

    @Autowired
    private FieldDAO fieldDAO;

    @Autowired
    private FieldStaffDAO fieldStaffDAO;

    @Autowired
    private StaffLogDAO staffLogDAO;

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private MappingUtil mappingUtil;

    @Override
    public void saveLog(LogDTO log) {
        String logId = generateLogID();
        log.setLogId(logId);
        log.setDate(LocalDate.now());
        LogEntity logEntity = mappingUtil.logConvertToEntity(log);
        logDAO.save(logEntity);

        List<FieldStaffEntity> byFieldFieldId = fieldStaffDAO.findByField_FieldId(log.getFieldId());
        FieldDTO fieldDTO = mappingUtil.fieldConvertToDTO(fieldDAO.getReferenceById(log.getFieldId()));
        fieldDTO.setStaffs(byFieldFieldId.stream()
                .map(FieldStaffEntity::getStaff)
                .map(StaffEntity::getStaffId)
                .collect(Collectors.toList()));

        for (String staffId : fieldDTO.getStaffs()) {
            StaffLogDTO staffLogDTO = new StaffLogDTO();
            staffLogDTO.setStaffLogId(generateStaffLogID());
            staffLogDTO.setLogId(logId);
            staffLogDTO.setStaffId(staffId);
            StaffLogEntity staffLogEntity = staffLogConvertToEntity(staffLogDTO);
            staffLogDAO.save(staffLogEntity);

            System.out.println("Staff Log saved for staff: " + staffId);
        }

        System.out.println("Log saved successfully: " + log);
    }

    public StaffLogEntity staffLogConvertToEntity(StaffLogDTO staffLogDTO) {
        StaffLogEntity staffLogEntity = new StaffLogEntity();
        staffLogEntity.setStaffLogId(staffLogDTO.getStaffLogId());

        if (staffLogDTO.getStaffId() != null) {
            StaffEntity staffEntity = staffDAO.getReferenceById(staffLogDTO.getStaffId());
            staffLogEntity.setStaffEntity(staffEntity);
        }

        if (staffLogDTO.getLogId() != null) {
            LogEntity logEntity = logDAO.getReferenceById(staffLogDTO.getLogId());
            staffLogEntity.setLogEntity(logEntity);
        }

        return staffLogEntity;
    }

    @Override
    public void updateLog(String id, LogDTO log) {
        Optional<LogEntity> tmpLogEntity = logDAO.findById(id);
        if (tmpLogEntity.isPresent()) {
            LogEntity logEntity = mappingUtil.logConvertToEntity(log);
            tmpLogEntity.get().setDetails(logEntity.getDetails());
            tmpLogEntity.get().setTemperature(logEntity.getTemperature());
            tmpLogEntity.get().setObservedImg(logEntity.getObservedImg());
            tmpLogEntity.get().setField(logEntity.getField());
            tmpLogEntity.get().setCrop(logEntity.getCrop());

            logDAO.save(tmpLogEntity.get()); // Save the updated log details

            staffLogDAO.deleteByLogEntity_LogId(id); // Delete old staff logs for this log

            List<FieldStaffEntity> fieldStaffEntities = fieldStaffDAO.findByField_FieldId(log.getFieldId());
            List<String> staffIds = fieldStaffEntities.stream()
                    .map(FieldStaffEntity::getStaff)
                    .map(StaffEntity::getStaffId)
                    .collect(Collectors.toList());
            for (String staffId : staffIds) {
                StaffLogDTO staffLogDTO = new StaffLogDTO();
                staffLogDTO.setLogId(id);
                staffLogDTO.setStaffId(staffId);

                // Manually assign an ID if necessary
                StaffLogEntity staffLogEntity = staffLogConvertToEntity(staffLogDTO);
                staffLogEntity.setStaffLogId(generateStaffLogID());

                staffLogDAO.save(staffLogEntity);
            }

            System.out.println("Log updated successfully: " + log);
        } else {
            System.out.println("Log not found with id: " + id);
            throw new NotFoundException("Log not found with id: " + id);
        }
    }


    @Override
    public LogDTO searchLog(String id) {
        if (logDAO.existsById(id)) {
            List<StaffLogEntity> byLogEntityLogId = staffLogDAO.findByLogEntity_LogId(id);
            LogDTO logDTO = mappingUtil.logConvertToDTO(logDAO.getReferenceById(id));
            logDTO.setStaff(byLogEntityLogId.stream().map(StaffLogEntity::getStaffEntity).map(StaffEntity::getStaffId).collect(Collectors.toList()));
            return logDTO;
        } else {
            System.out.println("Log not found with id: " + id);
            throw new NotFoundException("Log not found with id: " + id);
        }
    }

    @Override
    public boolean deleteLog(String id) {
        if (logDAO.existsById(id)) {
            logDAO.deleteById(id);
            staffLogDAO.deleteByLogEntity_LogId(id);
            System.out.println("Log deleted successfully with id: " + id);
            return true;
        } else {
            System.out.println("Log not found with id: " + id);
            throw new NotFoundException("Log not found with id: " + id);
        }
    }

    @Override
    public List<LogDTO> getAllLogs() {
        return logDAO.findAll().stream().map(logEntity -> {
            List<StaffLogEntity> byLogEntityLogId = staffLogDAO.findByLogEntity_LogId(logEntity.getLogId());
            LogDTO logDTO = mappingUtil.logConvertToDTO(logEntity);
            logDTO.setStaff(byLogEntityLogId.stream().map(StaffLogEntity::getStaffEntity).map(StaffEntity::getStaffId).collect(Collectors.toList()));
            return logDTO;
        }).collect(Collectors.toList());
    }

    private String generateLogID() {
        if (logDAO.count() == 0) {
            return "L001";
        } else {
            String lastId = logDAO.findAll().get(logDAO.findAll().size() - 1).getLogId();
            int newId = Integer.parseInt(lastId.substring(1)) + 1;
            if (newId < 10) {
                return "L00" + newId;
            } else if (newId < 100) {
                return "L0" + newId;
            } else {
                return "L" + newId;
            }
        }
    }

    private String generateStaffLogID() {
        if (staffLogDAO.count() == 0) {
            return "SL001";
        } else {
            String lastId = staffLogDAO.findAll().get(staffLogDAO.findAll().size() - 1).getStaffLogId();
            int newId = Integer.parseInt(lastId.substring(2)) + 1;
            if (newId < 10) {
                return "SL00" + newId;
            } else if (newId < 100) {
                return "SL0" + newId;
            } else {
                return "SL" + newId;
            }
        }
    }
}
