package lk.ijse.crop_monitoring_systembackend.dao;

import lk.ijse.crop_monitoring_systembackend.entity.FieldStaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldStaffDAO extends JpaRepository<FieldStaffEntity, String > {
    void deleteByField_FieldId(String fieldId);
    List<FieldStaffEntity> findByField_FieldId(String fieldId);
}
