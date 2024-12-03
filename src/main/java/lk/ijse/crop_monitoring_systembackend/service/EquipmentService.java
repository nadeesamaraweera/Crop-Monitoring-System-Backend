package lk.ijse.crop_monitoring_systembackend.service;

import lk.ijse.crop_monitoring_systembackend.dto.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipment);
    void updateEquipment(String id, EquipmentDTO equipment);
    EquipmentDTO searchEquipment(String id);
    boolean deleteEquipment(String id);
    List<EquipmentDTO> getAllEquipments();
}
