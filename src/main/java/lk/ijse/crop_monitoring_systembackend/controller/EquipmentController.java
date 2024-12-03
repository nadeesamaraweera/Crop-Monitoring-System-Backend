package lk.ijse.crop_monitoring_systembackend.controller;

import jakarta.validation.Valid;
import lk.ijse.crop_monitoring_systembackend.customResponse.ErrorResponse;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lk.ijse.crop_monitoring_systembackend.dto.EquipmentDTO;
import lk.ijse.crop_monitoring_systembackend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/equipment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    private static final Logger logger = Logger.getLogger(EquipmentController.class.getName());

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@Valid @RequestBody EquipmentDTO equipment) {
        if (equipment != null) {
            try {
                equipmentService.saveEquipment(equipment);
                logger.info("Equipment saved successfully: " + equipment);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                logger.severe("Failed to save equipment: " + equipment);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateEquipment(@Valid @PathVariable("id") String id, @RequestBody EquipmentDTO equipment) {
        if (id != null && equipment != null) {
            try {
                equipmentService.updateEquipment(id, equipment);
                logger.info("Equipment updated successfully: " + equipment);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (NotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (DataPersistFailedException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e){
                logger.severe("Failed to update equipment: " + equipment);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response searchEquipment(@PathVariable("id") String id) {
        if (id != null) {
            try {
                EquipmentDTO equipmentDTO = equipmentService.searchEquipment(id);
                logger.info("Equipment found successfully: " + equipmentDTO);
                return equipmentDTO;
            } catch (NotFoundException e) {
                return new ErrorResponse ("Equipment not found with id: " + id, HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                logger.severe("Failed to find equipment with id: " + id);
                return new ErrorResponse("Failed to find equipment with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ErrorResponse("Invalid equipment id", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAllEquipments() {
        try {
            List<EquipmentDTO> allEquipments = equipmentService.getAllEquipments();
            logger.info("All equipments found successfully: " + allEquipments);
            return allEquipments;
        } catch (Exception e) {
            logger.severe("Failed to find all equipments");
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteEquipment(@PathVariable("id") String id) {
        if (id != null) {
            try {
                boolean deleted = equipmentService.deleteEquipment(id);
                if (deleted){
                    logger.info("Equipment deleted successfully: " + id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (NotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                logger.severe("Failed to delete equipment with id: " + id);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}