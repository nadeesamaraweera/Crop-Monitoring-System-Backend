package lk.ijse.crop_monitoring_systembackend.customResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse implements Response, Serializable {
    private String message;
    private HttpStatus error;
}
