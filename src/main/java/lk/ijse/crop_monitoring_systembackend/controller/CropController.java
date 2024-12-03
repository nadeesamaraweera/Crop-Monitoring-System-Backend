package lk.ijse.crop_monitoring_systembackend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lk.ijse.crop_monitoring_systembackend.customResponse.ErrorResponse;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lk.ijse.crop_monitoring_systembackend.dto.CropDTO;
import lk.ijse.crop_monitoring_systembackend.dto.FieldCropDTO;
import lk.ijse.crop_monitoring_systembackend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.CropService;
import lk.ijse.crop_monitoring_systembackend.util.AppUtil;
import lombok.Builder;
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
@RequestMapping("/api/v1/crop")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CropController {
    @Autowired
    private CropService cropService;

    private static final Logger logger = Logger.getLogger(CropController.class.getName());

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveCrop(
            @Valid @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("cropImg") MultipartFile cropImg,
            @RequestPart("fields") String fields) {
        try {
            System.out.println("Received commonName: " + commonName);
            System.out.println("Received fields: " + fields);

            byte[] img = cropImg.getBytes();
            String base64Img = AppUtil.toBase64Pic(img);

            ObjectMapper objectMapper = new ObjectMapper();
            List<String> fieldList = objectMapper.readValue(fields, new TypeReference<List<String>>() {
            });

            CropDTO cropDTO = CropDTO.builder()
                    .commonName(commonName)
                    .scientificName(scientificName)
                    .category(category)
                    .season(season)
                    .cropImg(base64Img)
                    .fields(fieldList)
                    .build();
            cropService.saveCrop(cropDTO);
            logger.info("Crop saved successfully: " + commonName);
            return new ResponseEntity<>("Crop saved successfully", HttpStatus.CREATED);

        } catch (DataPersistFailedException e) {
            e.printStackTrace();
            logger.severe("Failed to save crop due to persistence issue: " + commonName);
            return new ResponseEntity<>("Failed to save crop due to persistence issue", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to save crop: " + commonName);
            return new ResponseEntity<>("Failed to save crop", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateCrop(
            @PathVariable String id,
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart(value = "cropImg", required = false) MultipartFile cropImg,
            @RequestPart("fields") String fields) {
        try {
            byte[] img = cropImg.getBytes();
            String base64Img = AppUtil.toBase64Pic(img);

            ObjectMapper objectMapper = new ObjectMapper();
            List<String> fieldList = objectMapper.readValue(fields, new TypeReference<>() {});
            CropDTO cropDTO = new CropDTO();
            cropDTO.setCommonName(commonName);
            cropDTO.setScientificName(scientificName);
            cropDTO.setCategory(category);
            cropDTO.setSeason(season);
            cropDTO.setCropImg(base64Img);
            cropDTO.setFields(fieldList);
            cropService.updateCrop(id, cropDTO);
            logger.info("Crop updated successfully: " + commonName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistFailedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to update crop: " + commonName);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response searchCrop(@PathVariable String id) {
        try {
            CropDTO cropDTO = cropService.searchCrop(id);
            logger.info("Crop searched successfully: " + cropDTO.getCommonName());
            return cropDTO;
        } catch (Exception e) {
            logger.severe("Failed to search crop with id: " + id);
            return new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrops() {
        try {
            List<CropDTO> allCrops = cropService.getAllCrops();
            logger.info("All crops fetched successfully");
            return allCrops;
        } catch (Exception e) {
            logger.severe("Failed to fetch all crops");
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCrop(@PathVariable String id) {
        if (id != null) {
            try {
                boolean deleted = cropService.deleteCrop(id);
                if (deleted){
                    logger.info("Crop deleted successfully with id: " + id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (NotFoundException e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                logger.severe("Failed to delete crop with id: " + id);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
