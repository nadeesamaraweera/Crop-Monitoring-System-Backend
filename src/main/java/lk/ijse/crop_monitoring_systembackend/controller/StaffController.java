package lk.ijse.crop_monitoring_systembackend.controller;

import jakarta.validation.Valid;
import lk.ijse.crop_monitoring_systembackend.customResponse.ErrorResponse;
import lk.ijse.crop_monitoring_systembackend.customResponse.Response;
import lk.ijse.crop_monitoring_systembackend.dto.StaffDTO;
import lk.ijse.crop_monitoring_systembackend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.StaffService;
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
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StaffController {
    @Autowired
    private StaffService staffService;

    private static final Logger logger = Logger.getLogger(StaffController.class.getName());

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@Valid @RequestBody StaffDTO staff) {
        if (staff != null) {
            try {
                staffService.saveStaff(staff);
                logger.info("Staff saved successfully: " + staff);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                logger.severe("Failed to save staff: " + staff);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStaff(@Valid @PathVariable("id") String id, @RequestBody StaffDTO staff) {
        if (id != null && staff != null) {
            try {
                staffService.updateStaff(id, staff);
                logger.info("Staff updated successfully: " + staff);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }catch (NotFoundException e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                logger.severe("Failed to update staff: " + staff);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response findStaff(@PathVariable("id") String id) {
        if (id != null) {
            try {
                StaffDTO staffDTO = staffService.searchStaff(id);
                logger.info("Staff found with id: " + id);
                return staffDTO;
            } catch (NotFoundException e) {
                return new ErrorResponse("Staff not found with id: " + id, HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                logger.severe("Failed to find staff with id: " + id);
                return new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ErrorResponse("Invalid staff id", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST') or hasRole('ROLE_ADMINISTRATIVE')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO> getAllStaffs() {
        try {
            List<StaffDTO> allStaffs = staffService.getAllStaffs();
            logger.info("All staffs found");
            return allStaffs;
        } catch (Exception e) {
            logger.severe("Failed to find all staffs");
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable("id") String id) {
        if (id != null) {
            try {
                boolean deleted = staffService.deleteStaff(id);
                if (deleted) {
                    logger.info("Staff deleted successfully: " + id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (NotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                logger.severe("Failed to delete staff with id: " + id);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
