package lk.ijse.crop_monitoring_systembackend.util;

import lk.ijse.crop_monitoring_systembackend.dto.*;
import lk.ijse.crop_monitoring_systembackend.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MappingUtil {
    @Autowired
    private ModelMapper modelMapper;


    // Maters of FieldEntity & FieldDTO
    public FieldDTO fieldConvertToDTO(FieldEntity fieldEntity) {
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setFieldId(fieldEntity.getFieldId());
        fieldDTO.setFieldName(fieldEntity.getFieldName());
        fieldDTO.setLocation(fieldEntity.getLocation());
        fieldDTO.setSize(fieldEntity.getSize());
        fieldDTO.setFieldImg1(fieldEntity.getFieldImg1());
        fieldDTO.setFieldImg2(fieldEntity.getFieldImg2());
        fieldDTO.setStaffs(new ArrayList<>());
        return fieldDTO;
    }

    public FieldEntity fieldConvertToEntity(FieldDTO fieldDTO) {
        FieldEntity entity = new FieldEntity();
        entity.setFieldId(fieldDTO.getFieldId());
        entity.setFieldName(fieldDTO.getFieldName());
        entity.setLocation(fieldDTO.getLocation());
        entity.setSize(fieldDTO.getSize());
        entity.setFieldImg1(fieldDTO.getFieldImg1());
        entity.setFieldImg2(fieldDTO.getFieldImg2());
        return entity;
    }

    public List<FieldDTO> fieldConvertToDTOList(List<FieldEntity> fieldEntities) {
        return fieldEntities.stream().map(this::fieldConvertToDTO).toList();
    }

    // Maters of FieldCropEntity & FieldCropDTO
    public FieldCropDTO fieldCropConvertToDTO(FieldCropEntity fieldCropEntity) {
        return modelMapper.map(fieldCropEntity, FieldCropDTO.class);
    }

    public FieldCropEntity fieldCropConvertToEntity(FieldCropDTO fieldCropDTO) {
        return modelMapper.map(fieldCropDTO, FieldCropEntity.class);
    }

    public List<FieldCropDTO> fieldCropConvertToDTOList(List<FieldCropEntity> fieldCropEntities) {
        return fieldCropEntities.stream().map(this::fieldCropConvertToDTO).toList();
    }

    // Maters of FieldStaffEntity & FieldStaffDTO
    public FieldStaffDTO fieldStaffConvertToDTO(FieldStaffEntity fieldStaffEntity) {
        return modelMapper.map(fieldStaffEntity, FieldStaffDTO.class);
    }

    public FieldStaffEntity fieldStaffConvertToEntity(FieldStaffDTO fieldStaffDTO) {
        return modelMapper.map(fieldStaffDTO, FieldStaffEntity.class);
    }

    public List<FieldStaffDTO> fieldStaffConvertToDTOList(List<FieldStaffEntity> fieldStaffEntities) {
        return fieldStaffEntities.stream().map(this::fieldStaffConvertToDTO).toList();
    }

    // Maters of CropEntity & CropDTO
    public CropDTO cropConvertToDTO(CropEntity cropEntity) {
        return modelMapper.map(cropEntity, CropDTO.class);
    }

    public CropEntity cropConvertToEntity(CropDTO cropDTO) {
        return modelMapper.map(cropDTO, CropEntity.class);
    }

    public List<CropDTO> cropConvertToDTOList(List<CropEntity> cropEntities) {
        return cropEntities.stream().map(this::cropConvertToDTO).toList();
    }

    // Matters of StaffEntity & StaffDTO
    public StaffDTO staffConvertToDTO(StaffEntity staffEntity) {
        return modelMapper.map(staffEntity, StaffDTO.class);
    }

    public StaffEntity staffConvertToEntity(StaffDTO staffDTO) {
        return modelMapper.map(staffDTO, StaffEntity.class);
    }

    public List<StaffDTO> staffConvertToDTOList(List<StaffEntity> staffEntities) {
        return staffEntities.stream().map(this::staffConvertToDTO).toList();
    }

    // Maters of EquipmentEntity & EquipmentDTO
    public EquipmentEntity equipmentConvertToEntity(EquipmentDTO equipmentDTO) {
        EquipmentEntity entity = new EquipmentEntity();
        entity.setEquipmentId(equipmentDTO.getEquipmentId());
        entity.setCategory(equipmentDTO.getCategory());
        entity.setType(equipmentDTO.getType());
        entity.setStatus(equipmentDTO.getStatus());

        if (equipmentDTO.getEqStaff() != null) {
            StaffEntity staffEntity = new StaffEntity();
            staffEntity.setStaffId(equipmentDTO.getEqStaff());
            entity.setStaff(staffEntity);;
        }

        if (equipmentDTO.getEqField() != null) {
            FieldEntity fieldEntity = new FieldEntity();
            fieldEntity.setFieldId(equipmentDTO.getEqField());
            entity.setField(fieldEntity);
        }

        return entity;
    }

    public EquipmentDTO equipmentConvertToDTO(EquipmentEntity entity) {
        EquipmentDTO dto = new EquipmentDTO();
        dto.setEquipmentId(entity.getEquipmentId());
        dto.setCategory(entity.getCategory());
        dto.setType(entity.getType());
        dto.setStatus(entity.getStatus());

        if (entity.getStaff() != null) {
            dto.setEqStaff(entity.getStaff().getStaffId());
        }
        if (entity.getField() != null) {
            dto.setEqField(entity.getField().getFieldId());
        }

        return dto;
    }

    public List<EquipmentDTO> equipmentConvertToDTOList(List<EquipmentEntity> equipmentEntities) {
        return equipmentEntities.stream().map(this::equipmentConvertToDTO).toList();
    }

    // Maters of LogEntity & LogDTO
    public LogDTO logConvertToDTO(LogEntity logEntity) {
        LogDTO logDTO = new LogDTO();
        logDTO.setLogId(logEntity.getLogId());
        logDTO.setDate(logEntity.getDate());
        logDTO.setDetails(logEntity.getDetails());
        logDTO.setObservedImg(logEntity.getObservedImg());
        logDTO.setTemperature(logEntity.getTemperature());
        logDTO.setCropId(logEntity.getCrop().getCropId());
        logDTO.setFieldId(logEntity.getField().getFieldId());
        logDTO.setStaff(new ArrayList<>());
        return logDTO;
    }

    public LogEntity logConvertToEntity(LogDTO logDTO) {
        return modelMapper.map(logDTO, LogEntity.class);
    }

    public List<LogDTO> logConvertToDTOList(List<LogEntity> logEntities) {
        return logEntities.stream().map(this::logConvertToDTO).toList();
    }

    // Maters of StaffLogEntity & StaffLogDTO
    public StaffLogDTO staffLogConvertToDTO(StaffLogEntity staffLogEntity) {
        return modelMapper.map(staffLogEntity, StaffLogDTO.class);
    }

    public StaffLogEntity staffLogConvertToEntity(StaffLogDTO staffLogDTO) {
        return modelMapper.map(staffLogDTO, StaffLogEntity.class);
    }

    public List<StaffLogDTO> staffLogConvertToDTOList(List<StaffLogEntity> staffLogEntities) {
        return staffLogEntities.stream().map(this::staffLogConvertToDTO).toList();
    }

    // Maters of VehicleEntity & VehicleDTO
    public VehicleDTO vehicleConvertToDTO(VehicleEntity vehicleEntity) {
        return modelMapper.map(vehicleEntity, VehicleDTO.class);
    }

    public VehicleEntity vehicleConvertToEntity(VehicleDTO vehicleDTO) {
        return modelMapper.map(vehicleDTO, VehicleEntity.class);
    }

    public List<VehicleDTO> vehicleConvertToDTOList(List<VehicleEntity> vehicleEntities) {
        return vehicleEntities.stream().map(this::vehicleConvertToDTO).toList();
    }




}
