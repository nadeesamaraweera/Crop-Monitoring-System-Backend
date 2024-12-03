package lk.ijse.crop_monitoring_systembackend.service.impl;

import lk.ijse.crop_monitoring_systembackend.dao.FieldDAO;
import lk.ijse.crop_monitoring_systembackend.dao.FieldStaffDAO;
import lk.ijse.crop_monitoring_systembackend.dao.LogDAO;
import lk.ijse.crop_monitoring_systembackend.dao.StaffDAO;
import lk.ijse.crop_monitoring_systembackend.dto.FieldDTO;
import lk.ijse.crop_monitoring_systembackend.dto.LogDTO;
import lk.ijse.crop_monitoring_systembackend.entity.FieldStaffEntity;
import lk.ijse.crop_monitoring_systembackend.entity.LogEntity;
import lk.ijse.crop_monitoring_systembackend.entity.StaffEntity;
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


        System.out.println("Log saved successfully: " + log);
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


            List<FieldStaffEntity> fieldStaffEntities = fieldStaffDAO.findByField_FieldId(log.getFieldId());
            List<String> staffIds = fieldStaffEntities.stream()
                    .map(FieldStaffEntity::getStaff)
                    .map(StaffEntity::getStaffId)
                    .collect(Collectors.toList());

        System.out.println("Log updated successfully: " + log);
    } else {
        System.out.println("Log not found with id: " + id);
        throw new NotFoundException("Log not found with id: " + id);
    }
}


    @Override
    public LogDTO searchLog(String id) {
        if (logDAO.existsById(id)) {
            LogDTO logDTO = mappingUtil.logConvertToDTO(logDAO.getReferenceById(id));
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
            LogDTO logDTO = mappingUtil.logConvertToDTO(logEntity);
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


}
