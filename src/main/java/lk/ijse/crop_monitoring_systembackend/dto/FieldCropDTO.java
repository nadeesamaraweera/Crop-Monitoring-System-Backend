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
public class FieldCropDTO implements Serializable {
    private String fieldCropId;
    @NotEmpty(message = "Crop IDs cannot be empty")
    private String cropId;
    @NotEmpty(message = "Field IDs cannot be empty")
    private String fieldId;
    private LocalDate assignedDate;
}
