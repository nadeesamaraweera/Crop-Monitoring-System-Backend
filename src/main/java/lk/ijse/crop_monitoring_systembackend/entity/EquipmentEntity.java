package lk.ijse.crop_monitoring_systembackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "equipment")
@Entity
public class EquipmentEntity implements Serializable {
    @Id
    private String equipmentId;
    private String category;
    private String type;
    private String status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "field_id")
    private FieldEntity field;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id")
    private StaffEntity staff;
}
