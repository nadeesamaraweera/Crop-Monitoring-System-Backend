package lk.ijse.crop_monitoring_systembackend.service.impl;

import lk.ijse.crop_monitoring_systembackend.dao.EquipmentDAO;
import lk.ijse.crop_monitoring_systembackend.dto.EquipmentDTO;
import lk.ijse.crop_monitoring_systembackend.entity.EquipmentEntity;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.EquipmentService;
import lk.ijse.crop_monitoring_systembackend.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipmentServiceIMPL implements EquipmentService {
    @Autowired
    private EquipmentDAO equipmentDAO;

    @Autowired
    private MappingUtil mappingUtil;

    @Override
    public void saveEquipment(EquipmentDTO equipment) {
        equipment.setEquipmentId(generateEquipmentID());
        equipmentDAO.save(mappingUtil.equipmentConvertToEntity(equipment));
        System.out.println("Equipment saved successfully: " + equipment);
    }

    @Override
    public void updateEquipment(String id, EquipmentDTO equipment) {
        Optional<EquipmentEntity> tmpEquipment = equipmentDAO.findById(id);
        if (tmpEquipment.isPresent()) {
            EquipmentEntity equipmentEntity = mappingUtil.equipmentConvertToEntity(equipment);
            tmpEquipment.get().setCategory(equipmentEntity.getCategory());
            tmpEquipment.get().setStatus(equipmentEntity.getStatus());
            tmpEquipment.get().setType(equipmentEntity.getType());
            tmpEquipment.get().setStaff(equipmentEntity.getStaff());
            tmpEquipment.get().setField(equipmentEntity.getField());
            System.out.println("Equipment updated successfully: " + tmpEquipment.get());
        } else {
            throw new NotFoundException("Equipment not found with id: " + id);
        }
    }

    @Override
    public EquipmentDTO searchEquipment(String id) {
        if (equipmentDAO.existsById(id)) {
            EquipmentDTO equipmentDTO = mappingUtil.equipmentConvertToDTO(equipmentDAO.getReferenceById(id));
            System.out.println("Equipment searched successfully: " + equipmentDTO);
            return equipmentDTO;
        } else {
            throw new NotFoundException("Equipment not found with id: " + id);
        }
    }

    @Override
    public boolean deleteEquipment(String id) {
        if (equipmentDAO.existsById(id)) {
            equipmentDAO.deleteById(id);
            System.out.println("Equipment deleted successfully: " + id);
            return true;
        } else {
            throw new NotFoundException("Equipment not found with id: " + id);
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquipments() {
        return mappingUtil.equipmentConvertToDTOList(equipmentDAO.findAll());
    }

    private String generateEquipmentID() {
        if (equipmentDAO.count() == 0) {
            return "E001";
        } else {
            String lastId = equipmentDAO.findAll().get(equipmentDAO.findAll().size() - 1).getEquipmentId();
            int newId = Integer.parseInt(lastId.substring(1)) + 1;
            if (newId < 10) {
                return "E00" + newId;
            } else if (newId < 100) {
                return "E0" + newId;
            } else {
                return "E" + newId;
            }
        }
    }
}
