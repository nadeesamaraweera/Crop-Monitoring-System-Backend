package lk.ijse.crop_monitoring_systembackend.service;

import lk.ijse.crop_monitoring_systembackend.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicle);
    void updateVehicle(String id, VehicleDTO vehicle);
    VehicleDTO searchVehicle(String id);
    boolean deleteVehicle(String id);
    List<VehicleDTO> getAllVehicles();
}
