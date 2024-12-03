package lk.ijse.crop_monitoring_systembackend.service.impl;

import lk.ijse.crop_monitoring_systembackend.dao.StaffDAO;
import lk.ijse.crop_monitoring_systembackend.dto.StaffDTO;
import lk.ijse.crop_monitoring_systembackend.entity.StaffEntity;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.StaffService;
import lk.ijse.crop_monitoring_systembackend.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffServiceIMPL implements StaffService {
    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private MappingUtil mappingUtil;

    @Override
    public void saveStaff(StaffDTO staff) {
        staff.setStaffId(generateStaffID());
        staff.setJoinedDate(LocalDate.now());
        StaffEntity staffEntity = mappingUtil.staffConvertToEntity(staff);
        staffDAO.save(staffEntity);
        System.out.println("Staff saved successfully: " + staffEntity);
    }

    @Override
    public void updateStaff(String id, StaffDTO staff) {
        Optional<StaffEntity> tmpStaffEntity = staffDAO.findById(id);
        if (tmpStaffEntity.isPresent()) {
            StaffEntity staffEntity = mappingUtil.staffConvertToEntity(staff);
            tmpStaffEntity.get().setFirstName(staffEntity.getFirstName());
            tmpStaffEntity.get().setLastName(staffEntity.getLastName());
            tmpStaffEntity.get().setDesignation(staffEntity.getDesignation());
            tmpStaffEntity.get().setGender(staffEntity.getGender());
            tmpStaffEntity.get().setDateOfBirth(staffEntity.getDateOfBirth());
            tmpStaffEntity.get().setAddressLine1(staffEntity.getAddressLine1());
            tmpStaffEntity.get().setAddressLine2(staffEntity.getAddressLine2());
            tmpStaffEntity.get().setAddressLine3(staffEntity.getAddressLine3());
            tmpStaffEntity.get().setAddressLine4(staffEntity.getAddressLine4());
            tmpStaffEntity.get().setAddressLine5(staffEntity.getAddressLine5());
            tmpStaffEntity.get().setMobile(staffEntity.getMobile());
            tmpStaffEntity.get().setEmail(staffEntity.getEmail());
            tmpStaffEntity.get().setRole(staffEntity.getRole());
            tmpStaffEntity.get().setVehicleId(staffEntity.getVehicleId());
            System.out.println("Staff updated successfully: " + tmpStaffEntity.get());
        } else {
            throw new NotFoundException("Staff not found with id: " + id);
        }
    }

    @Override
    public StaffDTO searchStaff(String id) {
        if (staffDAO.existsById(id)) {
            return mappingUtil.staffConvertToDTO(staffDAO.getReferenceById(id));
        } else {
            throw new NotFoundException("Staff not found with id: " + id);
        }
    }

    @Override
    public List<StaffDTO> getAllStaffs() {
        return mappingUtil.staffConvertToDTOList(staffDAO.findAll());
    }

    @Override
    public boolean deleteStaff(String id) {
        if (staffDAO.existsById(id)) {
            staffDAO.deleteById(id);
            System.out.println("Staff deleted successfully: " + id);
            return true;
        } else {
            throw new NotFoundException("Staff not found with id: " + id);
        }
    }

    private String generateStaffID() {
        if (staffDAO.count() == 0) {
            return "S001";
        } else {
            String lastId = staffDAO.findAll().get(staffDAO.findAll().size() - 1).getStaffId();
            int newId = Integer.parseInt(lastId.substring(1)) + 1;
            if (newId < 10) {
                return "S00" + newId;
            } else if (newId < 100) {
                return "S0" + newId;
            } else {
                return "S" + newId;
            }
        }
    }
}
