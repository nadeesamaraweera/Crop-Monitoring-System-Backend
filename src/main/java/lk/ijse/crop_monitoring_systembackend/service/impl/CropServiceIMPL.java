package lk.ijse.crop_monitoring_systembackend.service.impl;

import lk.ijse.crop_monitoring_systembackend.dao.CropDAO;
import lk.ijse.crop_monitoring_systembackend.dao.FieldCropDAO;
import lk.ijse.crop_monitoring_systembackend.dto.CropDTO;
import lk.ijse.crop_monitoring_systembackend.dto.FieldCropDTO;
import lk.ijse.crop_monitoring_systembackend.entity.CropEntity;
import lk.ijse.crop_monitoring_systembackend.entity.FieldCropEntity;
import lk.ijse.crop_monitoring_systembackend.entity.FieldEntity;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.CropService;
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
public class CropServiceIMPL implements CropService {
    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private FieldCropDAO fieldCropDAO;

    @Autowired
    private MappingUtil mappingUtil;

    @Override
    @Transactional
    public void saveCrop(CropDTO cropDTO) {
        String cropId = generateCropID();
        cropDTO.setCropId(cropId);
        CropEntity cropEntity = mappingUtil.cropConvertToEntity(cropDTO);
        cropDAO.save(cropEntity);
        for (String fieldId : cropDTO.getFields()) {
            FieldCropDTO fieldCropDTO = new FieldCropDTO(generateFieldCropID(), cropId, fieldId, LocalDate.now());
            fieldCropDAO.save(mappingUtil.fieldCropConvertToEntity(fieldCropDTO));
        }
        System.out.println("Crop saved successfully: " + cropDTO);
    }

    @Override
    public void updateCrop(String id, CropDTO crop) {
        Optional<CropEntity> tmpCropEntityOptional = cropDAO.findById(id);
        if (tmpCropEntityOptional.isPresent()) {
            CropEntity existingCropEntity = tmpCropEntityOptional.get();
            CropEntity updatedCropEntity = mappingUtil.cropConvertToEntity(crop);

            existingCropEntity.setCommonName(updatedCropEntity.getCommonName());
            existingCropEntity.setScientificName(updatedCropEntity.getScientificName());
            existingCropEntity.setCategory(updatedCropEntity.getCategory());
            existingCropEntity.setSeason(updatedCropEntity.getSeason());
            existingCropEntity.setCropImg(updatedCropEntity.getCropImg());

            cropDAO.save(existingCropEntity);

            fieldCropDAO.deleteByCrop_CropId(id);
            for (String fieldId : crop.getFields()) {
                FieldCropDTO fieldCropDTO = new FieldCropDTO(generateFieldCropID(), id, fieldId, LocalDate.now());
                fieldCropDAO.save(mappingUtil.fieldCropConvertToEntity(fieldCropDTO));
            }

            System.out.println("Crop updated successfully: " + crop);
        } else {
            throw new NotFoundException("Crop not found with id: " + id);
        }
    }

    @Override
    public CropDTO searchCrop(String id) {
        if (cropDAO.existsById(id)) {
            List<FieldCropEntity> byCropCropId = fieldCropDAO.findByCrop_CropId(id);
            CropDTO cropDTO = mappingUtil.cropConvertToDTO(cropDAO.getReferenceById(id));
            cropDTO.setFields(byCropCropId.stream().map(FieldCropEntity::getField).map(FieldEntity::getFieldId).collect(Collectors.toList()));
            return cropDTO;
        } else {
            throw new NotFoundException("Crop not found with id: " + id);
        }
    }

    @Override
    public boolean deleteCrop(String id) {
        if (cropDAO.existsById(id)) {
            cropDAO.deleteById(id);
            fieldCropDAO.deleteByCrop_CropId(id);
            System.out.println("Crop deleted successfully with id: " + id);
            return true;
        } else {
            throw new NotFoundException("Crop not found with id: " + id);
        }
    }

    @Override
    public List<CropDTO> getAllCrops() {
        return cropDAO.findAll().stream().map(cropEntity -> {
            List<FieldCropEntity> byCropCropId = fieldCropDAO.findByCrop_CropId(cropEntity.getCropId());
            CropDTO cropDTO = mappingUtil.cropConvertToDTO(cropEntity);
            cropDTO.setFields(byCropCropId.stream().map(FieldCropEntity::getField).map(FieldEntity::getFieldId).collect(Collectors.toList()));
            return cropDTO;
        }).collect(Collectors.toList());
    }

    private String generateCropID() {
        if (cropDAO.count() == 0) {
            return "C001";
        } else {
            String lastId = cropDAO.findAll().get(cropDAO.findAll().size() - 1).getCropId();
            int newId = Integer.parseInt(lastId.substring(1)) + 1;
            if (newId < 10) {
                return "C00" + newId;
            } else if (newId < 100) {
                return "C0" + newId;
            } else {
                return "C" + newId;
            }
        }
    }

    private String generateFieldCropID() {
        if (fieldCropDAO.count() == 0) {
            return "FC001";
        } else {
            String lastId = fieldCropDAO.findAll().get(fieldCropDAO.findAll().size() - 1).getFieldCropId();
            int newId = Integer.parseInt(lastId.substring(2)) + 1;
            if (newId < 10) {
                return "FC00" + newId;
            } else if (newId < 100) {
                return "FC0" + newId;
            } else {
                return "FC" + newId;
            }
        }
    }
}
