package lk.ijse.crop_monitoring_systembackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO implements Serializable, Response {
    private String vehicleId;
    @NotBlank(message = "Category cannot be blank")
    @Size(max = 30, message = "Category must be at most 30 characters")
    private String category;
    @NotBlank(message = "Number plate cannot be blank")
    @Pattern(regexp = "^[A-Z0-9-]{1,15}$", message = "Number plate must be alphanumeric and at most 15 characters")
    private String numberPlate;
    @NotBlank(message = "Fuel type cannot be blank")
    @Size(max = 15, message = "Fuel type must be at most 15 characters")
    private String fuelType;
    @NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status must be at most 50 characters")
    private String status;
    @Size(max = 255, message = "Remarks must be at most 255 characters")
    private String remarks;
}