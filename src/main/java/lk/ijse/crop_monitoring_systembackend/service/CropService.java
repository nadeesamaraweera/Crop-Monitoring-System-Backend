package lk.ijse.crop_monitoring_systembackend.service;

import lk.ijse.crop_monitoring_systembackend.dto.CropDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO crop);
    void updateCrop(String id, CropDTO crop);
    CropDTO searchCrop(String id);
    boolean deleteCrop(String id);
    List<CropDTO> getAllCrops();
}
