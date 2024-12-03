package lk.ijse.crop_monitoring_systembackend.dao;

import lk.ijse.crop_monitoring_systembackend.entity.FieldCropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldCropDAO extends JpaRepository<FieldCropEntity, String> {
    void deleteByCrop_CropId(String cropId);
    List<FieldCropEntity> findByCrop_CropId(String cropId);
}
