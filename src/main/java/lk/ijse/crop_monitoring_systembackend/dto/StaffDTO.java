package lk.ijse.crop_monitoring_systembackend.dto;

import jakarta.validation.constraints.*;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO implements Serializable, Response {
    private String staffId;
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    @NotBlank(message = "Designation is required")
    private String designation;
    @NotNull(message = "Gender is required")
    private Gender gender;
    private LocalDate joinedDate;
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Address Line 1 is required")
    private String addressLine1;
    @NotBlank(message = "Address Line 2 is required")
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid mobile number format")
    private String mobile;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Role is required")
    private Role role;
    private String vehicleId;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum Role {
        MANAGER, ADMINISTRATIVE, SCIENTIST, OTHER
    }
}