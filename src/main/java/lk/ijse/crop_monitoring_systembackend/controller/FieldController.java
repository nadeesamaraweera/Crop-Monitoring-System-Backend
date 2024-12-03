package lk.ijse.crop_monitoring_systembackend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lk.ijse.crop_monitoring_systembackend.customResponse.ErrorResponse;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lk.ijse.crop_monitoring_systembackend.dto.FieldDTO;
import lk.ijse.crop_monitoring_systembackend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.FieldService;
import lk.ijse.crop_monitoring_systembackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/field")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    private static final Logger logger = Logger.getLogger(FieldController.class.getName());

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveField(
            @Valid
            @RequestPart("fieldName") String fieldName,
            @RequestPart("location") String fieldLocation,
            @RequestPart("size") String fieldSize,
            @RequestPart("fieldImg1") MultipartFile fieldImg1,
            @RequestPart("fieldImg2") MultipartFile fieldImg2,
            @RequestPart("staffs") String staffs) {
        try {
            Point location = AppUtil.parseLocation(fieldLocation);
            byte[] img1 = fieldImg1.getBytes();
            byte[] img2 = fieldImg2.getBytes();
            String base64Img1 = AppUtil.toBase64Pic(img1);
            String base64Img2 = AppUtil.toBase64Pic(img2);
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> staffList = objectMapper.readValue(staffs, new TypeReference<>() {});
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldName(fieldName);
            fieldDTO.setLocation(location);
            fieldDTO.setSize(fieldSize);
            fieldDTO.setFieldImg1(base64Img1);
            fieldDTO.setFieldImg2(base64Img2);
            fieldDTO.setStaffs(staffList);
            fieldService.saveField(fieldDTO);
            logger.info("Field saved successfully: " + fieldName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to save field: " + fieldName);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateField(
            @Valid
            @PathVariable("id") String id,
            @RequestPart("fieldName") String fieldName,
            @RequestPart("location") String fieldLocation,
            @RequestPart("size") String fieldSize,
            @RequestPart("fieldImg1") MultipartFile fieldImg1,
            @RequestPart("fieldImg2") MultipartFile fieldImg2,
            @RequestPart("staffs") String staffs) {
        try {
            Point location = AppUtil.parseLocation(fieldLocation);
            byte[] img1 = fieldImg1.getBytes();
            byte[] img2 = fieldImg2.getBytes();
            String base64Img1 = AppUtil.toBase64Pic(img1);
            String base64Img2 = AppUtil.toBase64Pic(img2);
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> staffList = objectMapper.readValue(staffs, new TypeReference<>() {});
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldName(fieldName);
            fieldDTO.setLocation(location);
            fieldDTO.setSize(fieldSize);
            fieldDTO.setFieldImg1(base64Img1);
            fieldDTO.setFieldImg2(base64Img2);
            fieldDTO.setStaffs(staffList);
            fieldService.updateField(id, fieldDTO);
            logger.info("Field updated successfully: " + fieldName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistFailedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to update field: " + fieldName);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response findField(@PathVariable("id") String id) {
        if (id != null) {
            try {
                FieldDTO fieldDTO = fieldService.searchField(id);
                logger.info("Field found with id: " + id);
                return fieldDTO;
            } catch (NotFoundException e) {
                return new ErrorResponse("Field not found with id: " + id, HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                logger.severe("Failed to find field with id: " + id);
                return new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ErrorResponse("Invalid field id", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<FieldDTO> getAllFields() {
        try {
            List<FieldDTO> allFields = fieldService.getAllFields();
            logger.info("All fields found");
            return allFields;
        } catch (Exception e) {
            logger.severe("Failed to find all fields");
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteField(@PathVariable("id") String id) {
        if (id != null) {
            try {
                boolean deleted = fieldService.deleteField(id);
                if (deleted) {
                    logger.info("Field deleted successfully: " + id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (NotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                logger.severe("Failed to delete field: " + id);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}