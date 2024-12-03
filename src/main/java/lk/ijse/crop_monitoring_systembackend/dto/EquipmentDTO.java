package lk.ijse.crop_monitoring_systembackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO implements Serializable, Response {
    private String equipmentId;
    @NotBlank(message = "Category cannot be blank")
    @Size(max = 50, message = "Category must be at most 50 characters")
    private String category;
    @NotBlank(message = "Type cannot be blank")
    @Size(max = 50, message = "Type must be at most 50 characters")
    private String type;
    @NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status must be at most 50 characters")
    private String status;
    private String eqStaff;
    private String eqField; 
}
