package lk.ijse.crop_monitoring_systembackend.controller;

import jakarta.validation.Valid;
import lk.ijse.crop_monitoring_systembackend.customResponse.ErrorResponse;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lk.ijse.crop_monitoring_systembackend.dto.LogDTO;
import lk.ijse.crop_monitoring_systembackend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_systembackend.service.LogService;
import lk.ijse.crop_monitoring_systembackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/log")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LogController {
    @Autowired
    private LogService logService;

    private static final Logger logger = Logger.getLogger(LogController.class.getName());

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveLog(
            @Valid
            @RequestPart("details") String details,
            @RequestPart("temperature") String temperature,
            @RequestPart("observedImg") MultipartFile observedImg,
            @RequestPart("fieldId") String fieldId,
            @RequestPart("cropId") String cropId) {
        try {
            byte[] img = observedImg.getBytes();
            String base64Img = AppUtil.toBase64Pic(img);
            LogDTO logDTO = new LogDTO();
            logDTO.setDetails(details);
            logDTO.setTemperature(temperature);
            logDTO.setObservedImg(base64Img);
            logDTO.setFieldId(fieldId);
            logDTO.setCropId(cropId);
            logService.saveLog(logDTO);
            logger.info("Log saved successfully: " + logDTO);
            return new ResponseEntity<>("Log saved successfully", HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            e.printStackTrace();
            logger.severe("Failed to save log: " + details + " - " + e.getMessage());
            return new ResponseEntity<>("Failed to save log", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to save log: " + details + " - " + e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateLog(
            @Valid
            @PathVariable String id,
            @RequestPart("details") String details,
            @RequestPart("temperature") String temperature,
            @RequestPart("observedImg") MultipartFile observedImg,
            @RequestPart("fieldId") String fieldId,
            @RequestPart("cropId") String cropId) {
        try {
            byte[] img = observedImg.getBytes();
            String base64Img = AppUtil.toBase64Pic(img);
            LogDTO logDTO = new LogDTO();
            logDTO.setDetails(details);
            logDTO.setTemperature(temperature);
            logDTO.setObservedImg(base64Img);
            logDTO.setFieldId(fieldId);
            logDTO.setCropId(cropId);
            logService.updateLog(id, logDTO);
            logger.info("Log updated successfully: " + logDTO);
            return new ResponseEntity<>("Log updated successfully", HttpStatus.NO_CONTENT);
        } catch (DataPersistFailedException e) {
            e.printStackTrace();
            logger.severe("Failed to update log: " + details + " - " + e.getMessage());
            return new ResponseEntity<>("Failed to update log", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to update log: " + details + " - " + e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response findLog(@PathVariable String id) {
        if (id != null) {
            try {
                LogDTO logDTO = logService.searchLog(id);
                logger.info("Log found successfully: " + logDTO);
                return logDTO;
            } catch (Exception e) {
                e.printStackTrace();
                logger.severe("Failed to find log: " + id + " - " + e.getMessage());
                return new ErrorResponse("Failed to find log", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ErrorResponse("Log id is required", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LogDTO> getAllLogs() {
        try {
            List<LogDTO> allLogs = logService.getAllLogs();
            logger.info("All logs found successfully");
            return allLogs;
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to find all logs: " + e.getMessage());
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteLog(@PathVariable String id) {
        if (id != null) {
            try {
                boolean deleted = logService.deleteLog(id);
                if (deleted) {
                    logger.info("Log deleted successfully: " + id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.severe("Failed to delete log: " + id + " - " + e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
