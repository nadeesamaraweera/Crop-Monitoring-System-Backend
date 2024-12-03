package lk.ijse.crop_monitoring_systembackend.service.impl;

import lk.ijse.crop_monitoring_systembackend.dao.FieldDAO;
import lk.ijse.crop_monitoring_systembackend.dao.FieldStaffDAO;
import lk.ijse.crop_monitoring_systembackend.dto.FieldDTO;
import lk.ijse.crop_monitoring_systembackend.dto.FieldStaffDTO;
import lk.ijse.crop_monitoring_systembackend.entity.FieldEntity;
import lk.ijse.crop_monitoring_systembackend.entity.FieldStaffEntity;
import lk.ijse.crop_monitoring_systembackend.entity.StaffEntity;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.FieldService;
import lk.ijse.crop_monitoring_systembackend.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FieldServiceIMPL implements FieldService {
    @Autowired
    private FieldDAO fieldDAO;

    @Autowired
    private FieldStaffDAO fieldStaffDAO;

    @Autowired
    private MappingUtil mappingUtil;

    @Override
    public void saveField(FieldDTO field) {
        String fieldId = generateFieldID();
        field.setFieldId(fieldId);
        fieldDAO.save(mappingUtil.fieldConvertToEntity(field));

        for (String staffId : field.getStaffs()) {
            FieldStaffDTO fieldStaffDTO = new FieldStaffDTO();
            fieldStaffDTO.setFieldStaffId(generateFieldStaffID());
            fieldStaffDTO.setFieldId(fieldId);
            fieldStaffDTO.setStaffId(staffId);
            fieldStaffDTO.setAssignedDate(LocalDate.now());
            FieldStaffEntity fieldStaffEntity = mappingUtil.fieldStaffConvertToEntity(fieldStaffDTO);
            fieldStaffDAO.save(fieldStaffEntity);
        }
        System.out.println("Field saved successfully: " + field);
    }

    @Override
    public void updateField(String id, FieldDTO field) {
        FieldEntity existingFieldEntity = fieldDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Field not found with id: " + id));
        existingFieldEntity.setFieldName(field.getFieldName());
        existingFieldEntity.setLocation(field.getLocation());
        existingFieldEntity.setSize(field.getSize());
        existingFieldEntity.setFieldImg1(field.getFieldImg1());
        existingFieldEntity.setFieldImg2(field.getFieldImg2());

        fieldDAO.save(existingFieldEntity);
        updateFieldStaffAssignments(id, field.getStaffs());
        System.out.println("Field updated successfully: " + field);
    }

    private void updateFieldStaffAssignments(String fieldId, List<String> staffIds) {
        fieldStaffDAO.deleteByField_FieldId(fieldId);

        for (String staffId : staffIds) {
            FieldStaffDTO fieldStaffDTO = new FieldStaffDTO();
            fieldStaffDTO.setFieldStaffId(generateFieldStaffID());
            fieldStaffDTO.setFieldId(fieldId);
            fieldStaffDTO.setStaffId(staffId);
            fieldStaffDTO.setAssignedDate(LocalDate.now());
            FieldStaffEntity fieldStaffEntity = mappingUtil.fieldStaffConvertToEntity(fieldStaffDTO);
            fieldStaffDAO.save(fieldStaffEntity);
        }
    }

    @Override
    public FieldDTO searchField(String id) {
        if (fieldDAO.existsById(id)) {
            List<FieldStaffEntity> byFieldFieldId = fieldStaffDAO.findByField_FieldId(id);
            FieldDTO fieldDTO = mappingUtil.fieldConvertToDTO(fieldDAO.getReferenceById(id));
            fieldDTO.setStaffs(byFieldFieldId.stream().map(FieldStaffEntity::getStaff).map(StaffEntity::getStaffId).collect(Collectors.toList()));
            return fieldDTO;
        } else {
            throw new NotFoundException("Field not found with id: " + id);
        }
    }

    @Override
    public boolean deleteField(String id) {
        if (fieldDAO.existsById(id)) {
            fieldDAO.deleteById(id);
            fieldStaffDAO.deleteByField_FieldId(id);
            System.out.println("Field deleted successfully with id: " + id);
            return true;
        } else {
            throw new NotFoundException("Field not found with id: " + id);
        }
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return fieldDAO.findAll().stream().map(fieldEntity -> {
            List<FieldStaffEntity> byFieldFieldId = fieldStaffDAO.findByField_FieldId(fieldEntity.getFieldId());
            FieldDTO fieldDTO = mappingUtil.fieldConvertToDTO(fieldEntity);
            fieldDTO.setStaffs(byFieldFieldId.stream().map(FieldStaffEntity::getStaff).map(StaffEntity::getStaffId).collect(Collectors.toList()));
            return fieldDTO;
        }).collect(Collectors.toList());
    }

    private String generateFieldID() {
        if (fieldDAO.count() == 0) {
            return "F001";
        } else {
            String lastId = fieldDAO.findAll().get(fieldDAO.findAll().size() - 1).getFieldId();
            int newId = Integer.parseInt(lastId.substring(1)) + 1;
            if (newId < 10) {
                return "F00" + newId;
            } else if (newId < 100) {
                return "F0" + newId;
            } else {
                return "F" + newId;
            }
        }
    }

    private String generateFieldStaffID() {
        if (fieldStaffDAO.count() == 0) {
            return "FS001";
        } else {
            String lastId = fieldStaffDAO.findAll().get(fieldStaffDAO.findAll().size() - 1).getFieldStaffId();
            int newId = Integer.parseInt(lastId.substring(2)) + 1;
            if (newId < 10) {
                return "FS00" + newId;
            } else if (newId < 100) {
                return "FS0" + newId;
            } else {
                return "FS" + newId;
            }
        }
    }
}