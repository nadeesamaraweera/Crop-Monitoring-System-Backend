package lk.ijse.crop_monitoring_systembackend.util;

import lk.ijse.crop_monitoring_systembackend.dto.FieldCropDTO;
import lk.ijse.crop_monitoring_systembackend.dto.FieldDTO;
import lk.ijse.crop_monitoring_systembackend.dto.FieldStaffDTO;
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

}
