package lk.ijse.crop_monitoring_systembackend.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO implements Serializable, Response {
    private String fieldId;
    @NotBlank(message = "Field name cannot be blank")
    @Size(max = 100, message = "Field name must be at most 100 characters")
    private String fieldName;
    @NotNull(message = "Location cannot be null")
    private Point location;
    @NotBlank(message = "Size cannot be blank")
    @Size(max = 20, message = "Size must be at most 20 characters")
    private String size;
    @Size(max = 10485760, message = "Image 1 size exceeds maximum allowed length")
    private String fieldImg1;
    @Size(max = 10485760, message = "Image 2 size exceeds maximum allowed length")
    private String fieldImg2;
    private List<@NotNull(message = "Staff list contains null elements") String> staffs;
}