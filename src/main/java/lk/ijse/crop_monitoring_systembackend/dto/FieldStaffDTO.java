package lk.ijse.crop_monitoring_systembackend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldStaffDTO implements Serializable {
    private String fieldStaffId;
    @NotEmpty(message = "Field ID cannot be empty")
    private String fieldId;
    @NotEmpty(message = "Staff ID cannot be empty")
    private String staffId;
    private LocalDate assignedDate;
}
