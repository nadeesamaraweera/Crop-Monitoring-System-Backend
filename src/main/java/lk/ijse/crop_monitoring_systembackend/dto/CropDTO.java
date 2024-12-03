package lk.ijse.crop_monitoring_systembackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CropDTO implements Serializable, Response {
    private String cropId;
    @NotBlank(message = "Common name cannot be blank")
    @Size(max = 100, message = "Common name must be at most 100 characters")
    private String commonName;
    @NotBlank(message = "Scientific name cannot be blank")
    @Size(max = 100, message = "Scientific name must be at most 100 characters")
    private String scientificName;
    @NotBlank(message = "Category cannot be blank")
    private String category;
    @NotBlank(message = "Season cannot be blank")
    private String season;
    @Size(max = 10485760, message = "Image size exceeds maximum allowed length")
    private String cropImg;
    @NotEmpty(message = "Fields cannot be empty")
    private List<String> fields;
}
