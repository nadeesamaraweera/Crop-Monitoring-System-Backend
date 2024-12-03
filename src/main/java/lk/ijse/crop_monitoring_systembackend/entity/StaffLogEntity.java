package lk.ijse.crop_monitoring_systembackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "staff_log")
@Entity
public class StaffLogEntity implements Serializable {
    @Id
    private String staffLogId;
    @ManyToOne
    @JoinColumn(name = "staff_id" ,referencedColumnName = "staffId")
    private StaffEntity staffEntity;
    @ManyToOne
    @JoinColumn(name = "log_id", referencedColumnName = "logId")
    private LogEntity logEntity;
}
