package lk.ijse.crop_monitoring_systembackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "field")
@Entity
public class FieldEntity implements Serializable {
    @Id
    private String fieldId;
    private String fieldName;
    @ToString.Exclude
    private Point location;
    private String size;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImg1;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImg2;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<EquipmentEntity> equipment;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<LogEntity> logs;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<FieldCropEntity> fieldCrops = new ArrayList<>();
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<FieldStaffEntity> fieldStaffs = new ArrayList<>();
}