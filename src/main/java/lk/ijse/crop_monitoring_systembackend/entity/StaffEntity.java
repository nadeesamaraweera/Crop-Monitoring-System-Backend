package lk.ijse.crop_monitoring_systembackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "staff")
@Entity
public class StaffEntity implements Serializable {
    @Id
    private String staffId;
    private String firstName;
    private String lastName;
    private String designation;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate joinedDate;
    private LocalDate dateOfBirth;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String mobile;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<EquipmentEntity> equipment;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicleId;
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<FieldStaffEntity> fieldStaff = new ArrayList<>();
    @OneToMany(mappedBy = "staffEntity", cascade = CascadeType.ALL)
    private List<StaffLogEntity> staffLog = new ArrayList<>();

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum Role {
        MANAGER, ADMINISTRATIVE, SCIENTIST, OTHER
    }
}
