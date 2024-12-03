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
@Table(name = "logs")
@Entity
public class LogEntity implements Serializable {
    @Id
    private String logId;
    private LocalDate date;
    private String details;
    private String temperature;
    @Column(columnDefinition = "LONGTEXT")
    private String observedImg;
    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private FieldEntity field;
    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    private CropEntity crop;
    @OneToMany(mappedBy = "logEntity", cascade = CascadeType.ALL)
    private List<StaffLogEntity> staffLog = new ArrayList<>();
}
