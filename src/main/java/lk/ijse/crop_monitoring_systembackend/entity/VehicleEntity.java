package lk.ijse.crop_monitoring_systembackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vehicle")
@Entity
public class VehicleEntity implements Serializable {
    @Id
    private String vehicleId;
    private String category;
    private String numberPlate;
    private String fuelType;
    private String status;
    private String remarks;
    @OneToMany(mappedBy = "vehicleId", cascade = CascadeType.ALL)
    private List<StaffEntity> staff;
}
