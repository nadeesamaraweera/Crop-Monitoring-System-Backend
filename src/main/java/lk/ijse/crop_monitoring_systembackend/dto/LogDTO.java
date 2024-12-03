package lk.ijse.crop_monitoring_systembackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogDTO implements Serializable, Response {
    private String logId;
    private LocalDate date;
    @NotBlank(message = "Details cannot be blank")
    @Size(max = 255, message = "Details must be at most 255 characters")
    private String details;
    @NotBlank(message = "Temperature cannot be blank")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?°?[CF]$", message = "Temperature must be a valid format, e.g., 36.5°C or 98°F")
    private String temperature;
    @Size(max = 10485760, message = "Image size exceeds maximum allowed length")
    private String observedImg;
    @NotEmpty(message = "Fields list cannot be empty")
    private String fieldId;
    @NotEmpty(message = "Crops list cannot be empty")
    private String cropId;
    private List<String> staff;
}