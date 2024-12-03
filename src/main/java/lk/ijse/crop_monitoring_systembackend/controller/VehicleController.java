package lk.ijse.crop_monitoring_systembackend.controller;

import jakarta.validation.Valid;
import lk.ijse.crop_monitoring_systembackend.customResponse.ErrorResponse;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lk.ijse.crop_monitoring_systembackend.dto.VehicleDTO;
import lk.ijse.crop_monitoring_systembackend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.VehicleService;
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
@RequestMapping(value = "/api/v1/vehicle")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    private static final Logger logger = Logger.getLogger(VehicleController.class.getName());

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@Valid @RequestBody VehicleDTO vehicle) {
        if (vehicle != null) {
            try {
                vehicleService.saveVehicle(vehicle);
                logger.info("Vehicle saved successfully: " + vehicle);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (DataPersistFailedException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                logger.severe("Failed to save vehicle: " + vehicle);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateVehicle(@Valid @PathVariable("id") String id, @RequestBody VehicleDTO vehicle) {
        if (id != null && vehicle != null) {
            try {
                vehicleService.updateVehicle(id, vehicle);
                logger.info("Vehicle updated successfully: " + vehicle);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (NotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (DataPersistFailedException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                logger.severe("Failed to update vehicle: " + vehicle);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response searchVehicle(@PathVariable("id") String id) {
        if (id != null) {
            try {
                VehicleDTO vehicleDTO = vehicleService.searchVehicle(id);
                logger.info("Vehicle found successfully: " + vehicleDTO);
                return vehicleDTO;
            } catch (NotFoundException e) {
                return new ErrorResponse("Vehicle not found with id: " + id, HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                logger.severe("Failed to find vehicle: " + id);
                return new ErrorResponse("Failed to find vehicle with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ErrorResponse("Invalid vehicle id", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDTO> getAllVehicles() {
        try {
            List<VehicleDTO> allVehicles = vehicleService.getAllVehicles();
            logger.info("All vehicles found successfully");
            return allVehicles;
        } catch (Exception e) {
            logger.severe("Failed to find all vehicles");
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") String id) {
        if (id != null) {
            try {
                boolean deleted = vehicleService.deleteVehicle(id);
                if (deleted) {
                    logger.info("Vehicle deleted successfully: " + id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (NotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                logger.severe("Failed to delete vehicle: " + id);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}